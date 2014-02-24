package se.jiv.webshop.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.UserDAO;

public class UserJUnit {
	UserDAO ud = new UserDAO();
	UserModel user1 = new UserModel.Builder("bbq@test.se", "123456", "Tom",
			"Whitemore", "Telegrafvagen 32", "Stockholm", "postcode")
			.address2("C/O Olsen").dob("1949-09-09").telephone("0807384756")
			.build();

	private static void prepareStatementFromModel(PreparedStatement pstmt,
			UserModel user) throws SQLException {
		pstmt.setString(1, user.getEmail());
		pstmt.setString(2, user.getPassword());
		pstmt.setString(3, user.getFirstname());
		pstmt.setString(4, user.getLastname());
		pstmt.setString(5, user.getDob());
		pstmt.setString(6, user.getTelephone());
		pstmt.setString(7, user.getAddress1());
		pstmt.setString(8, user.getAddress2());
		pstmt.setString(9, user.getTown());
		pstmt.setString(10, user.getPostcode());

	}

	static void insertUser(UserModel user) {
		try (Connection conn = DevDBConfig.getConnection()) {
			String sql = "INSERT INTO users (email, password, firstname, lastname, dob, telephone, address1, address2, town, postcode)"
					+ " VALUES (?,?,?,?,STR_TO_DATE(?, '%Y-%m-%d'),?,?,?,?,?)";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				prepareStatementFromModel(pstmt, user);

				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void deleteUser(String email) {
		try (Connection conn = DevDBConfig.getConnection()) {
			String sql = "DELETE FROM users WHERE email = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, email);

				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private UserModel getUserByEmail(String email) {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {

				String sql = "select * from users where email = '" + email
						+ "'";

				try (ResultSet rs = stmt.executeQuery(sql)) {
					if (rs.next()) {
						return parseModel(rs);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private UserModel parseModel(ResultSet rs) throws SQLException {
		String db_email = rs.getString("email");
		String db_password = rs.getString("password");
		String db_firstname = rs.getString("firstname");
		String db_lastname = rs.getString("lastname");
		String db_dob = "" + rs.getDate("dob");
		String db_telephone = rs.getString("telephone");
		String db_address1 = rs.getString("address1");
		String db_address2 = rs.getString("address2");
		String db_town = rs.getString("town");
		String db_postcode = rs.getString("postcode");

		return new UserModel.Builder(db_email, db_password, db_firstname,
				db_lastname, db_address1, db_town, db_postcode)
				.address2(db_address2).dob(db_dob).telephone(db_telephone)
				.build();
	}

	@Before
	public void setUp() throws WebshopAppException {
		insertUser(user1);
	}

	@After
	public void tearDown() throws Exception {
		deleteUser(user1.getEmail());
	}

	@Test
	public void canAddUser() {
		UserModel getUser = null;
		UserModel addedUser = null;
		try {
			addedUser = new UserModel.Builder("bbq1@test.se", "123456", "Tom",
					"Whitemore", "Telegrafvagen 32", "Stockholm", "postcode")
					.address2("C/O Olsen").dob("1949-09-09")
					.telephone("0807384756").build();

			ud.addUser(addedUser);

			getUser = getUserByEmail(addedUser.getEmail());

			deleteUser(addedUser.getEmail());
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}

		assertEquals(addedUser, getUser);

	}

	@Test
	public void canAddUserNull() {
		boolean exception = false;
		try {
			ud.addUser(null);

		} catch (WebshopAppException e) {
			exception = true;
		}

		assertTrue(exception);

	}

	@Test
	public void canAddUserValuesNulls() {
		boolean exception = false;
		try {
			UserModel addedUser = new UserModel.Builder(null, null, null, null,
					null, null, null).address2("C/O Olsen").dob("1949-09-09")
					.telephone("0807384756").build();
			ud.addUser(addedUser);

		} catch (WebshopAppException e) {
			exception = true;
		}

		assertTrue(exception);

	}

	@Test
	public void canAddUserThatExist() {
		UserModel addedUser = new UserModel.Builder("bbq1@test.se", "123456",
				"Tom", "Whitemore", "Telegrafvagen 32", "Stockholm", "postcode")
				.address2("C/O Olsen").dob("1949-09-09")
				.telephone("0807384756").build();

		boolean exception = false;
		try {

			insertUser(addedUser);

			ud.addUser(addedUser);

		} catch (WebshopAppException e) {
			exception = true;
		}
		deleteUser(addedUser.getEmail());
		assertTrue(exception);

	}

	@Test
	public void canGetUser() {
		UserModel addedUser = new UserModel.Builder("bbq1@test.se", "123456",
				"Tom", "Whitemore", "Telegrafvagen 32", "Stockholm", "postcode")
				.address2("C/O Olsen").dob("1949-09-09")
				.telephone("0807384756").build();
		UserModel getUser = null;
		try {
			insertUser(addedUser);
			getUser = ud.getUser(addedUser.getEmail());
		} catch (WebshopAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		deleteUser(addedUser.getEmail());
		assertEquals(addedUser, getUser);
	}

	@Test
	public void canGetAllUsers() {
		UserModel addedUser = new UserModel.Builder("bbq1@test.se", "123456",
				"Tom", "Whitemore", "Telegrafvagen 32", "Stockholm", "postcode")
				.address2("C/O Olsen").dob("1949-09-09")
				.telephone("0807384756").build();

		List<UserModel> getUsers = new ArrayList<UserModel>();

		try {
			insertUser(addedUser);
			getUsers = ud.getAllUsers();
			deleteUser(addedUser.getEmail());

		} catch (WebshopAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean finded1 = false;
		boolean finded2 = false;
		for (UserModel user : getUsers) {
			if (addedUser.equals(user)) {
				finded1 = true;
			}
			if (user1.equals(user)) {
				finded2 = true;
			}
		}

		assertTrue(finded1 && finded2);
	}

	@Test
	public void canUpdateUser() {

		UserModel addedUser = new UserModel.Builder("bbq1@test.se", "123456",
				"Tom", "Whitemore", "Telegrafvagen 32", "Stockholm", "postcode")
				.address2("C/O Olsen").dob("1949-09-09")
				.telephone("0807384756").build();
		UserModel updatedUser = new UserModel.Builder("bbq1@test.se", "523456",
				"To1m", "White5more", "Telegra1fvagen 32", "Stoc5kholm",
				"postc1ode").address2("C/O Olsen").dob("1949-09-09")
				.telephone("0807384756").build();
		UserModel getUser = null;

		try {
			insertUser(addedUser);

			ud.updateUser(updatedUser);

			getUser = getUserByEmail(addedUser.getEmail());

			deleteUser(addedUser.getEmail());

		} catch (WebshopAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(updatedUser, getUser);

	}

	@Test
	public void canDeleteUser() {
		UserModel getUser = null;
		UserModel addedUser = new UserModel.Builder("bbq1@test.se", "123456",
				"Tom", "Whitemore", "Telegrafvagen 32", "Stockholm", "postcode")
				.address2("C/O Olsen").dob("1949-09-09")
				.telephone("0807384756").build();

		insertUser(addedUser);
		try {
			ud.deleteUser(addedUser);
			getUser = ud.getUser(addedUser.getEmail());
			deleteUser(addedUser.getEmail());
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}
		assertTrue(getUser == null);
	}

	@Test
	public void canLogin() {
		UserModel addedUser = new UserModel.Builder("bbq1@test.se", "123456",
				"Tom", "Whitemore", "Telegrafvagen 32", "Stockholm", "postcode")
				.address2("C/O Olsen").dob("1949-09-09")
				.telephone("0807384756").build();

		boolean loggedIn = false;
		boolean loggedInFailed = false;
		try {
			insertUser(addedUser);

			loggedIn = ud.validateLogin(addedUser.getEmail(),
					addedUser.getPassword());
			loggedInFailed = ud.validateLogin(addedUser.getEmail(), "tabasco");
			deleteUser(addedUser.getEmail());
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}
		assertTrue(loggedIn && !loggedInFailed);
	}
}
