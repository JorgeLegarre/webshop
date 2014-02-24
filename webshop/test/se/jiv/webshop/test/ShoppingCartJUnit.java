package se.jiv.webshop.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.ShoppingCartDAO;

public class ShoppingCartJUnit {
	ShoppingCartDAO shoppingCart = new ShoppingCartDAO();

	UserModel user1 = new UserModel.Builder("bbq@test.se", "123456", "Tom",
			"Whitemore", "Telegrafvagen 32", "Stockholm", "postcode")
			.address2("C/O Olsen").dob("1949-09-09").telephone("0807384756")
			.build();

	int prod_id1 = -1;
	int prod_id2 = -1;

	private Map<Integer, Integer> getShoppingCart(UserModel user) {
		Map<Integer, Integer> ret = new HashMap<>();
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				String sql = "select * from shopping_cart where user_email = '"
						+ user.getEmail() + "'";

				try (ResultSet rs = stmt.executeQuery(sql)) {
					while (rs.next()) {
						int prod_id = rs.getInt(2);
						int quant = rs.getInt(3);

						ret.put(prod_id, quant);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private void deleteShoppingCartUser(UserModel user) {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				String sql = "delete from shopping_cart where user_email = '"
						+ user.getEmail() + "'";

				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertShoppingCart(String email, int prod_id, int quant) {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				String sql = "insert into shopping_cart(user_email,product_id,quantity) values ('"
						+ email + "', " + prod_id + ", " + quant + ")";

				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setUp() throws WebshopAppException {
		UserJUnit.insertUser(user1);
		prod_id1 = ProductJUnit.insertProduct(ProductModel.builder("Prod1", 1)
				.description("desc1").cost(1).rrp(1).build());
		prod_id2 = ProductJUnit.insertProduct(ProductModel.builder("Prod2", 1)
				.description("desc2").cost(2).rrp(2).build());
	}

	@After
	public void tearDown() throws Exception {
		UserJUnit.deleteUser(user1.getEmail());
		ProductJUnit.deleteProduct(prod_id1);
		ProductJUnit.deleteProduct(prod_id2);
	}

	@Test
	public void canAddProductToCart() {
		Map<Integer, Integer> sc = new HashMap<>();
		try {
			shoppingCart.addProductToCart(user1, prod_id1, 20);

			sc = getShoppingCart(user1);

			deleteShoppingCartUser(user1);
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}

		assertEquals(20, sc.get(prod_id1).intValue());
	}

	@Test
	public void canAddProductToCartThatExist() {
		Map<Integer, Integer> sc = new HashMap<>();
		try {
			insertShoppingCart(user1.getEmail(), prod_id1, 20);
			shoppingCart.addProductToCart(user1, prod_id1, 10);

			sc = getShoppingCart(user1);

			deleteShoppingCartUser(user1);
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}

		assertEquals(30, sc.get(prod_id1).intValue());
	}

	@Test
	public void canAddProductToCartWithUserNull() {
		boolean exception = false;
		try {
			shoppingCart.addProductToCart(null, prod_id1, 20);
		} catch (WebshopAppException e) {
			exception = true;
		}

		assertTrue(exception);
	}

	@Test
	public void canAddProductToCartWithUserThatNotExist() {
		boolean exception = false;
		try {
			UserModel user = new UserModel.Builder("adfadfaa", "fadfaadf",
					"fadfad", "adfadfa", "adfadfa", "adfadfa", "adfadfa")
					.build();
			shoppingCart.addProductToCart(user, prod_id1, 20);
		} catch (WebshopAppException e) {
			exception = true;
		}

		assertTrue(exception);
	}

	@Test
	public void canAddProductToCartWithProductThatNotExist() {
		boolean exception = false;
		try {
			shoppingCart.addProductToCart(user1, -52, 20);
		} catch (WebshopAppException e) {
			exception = true;
		}

		assertTrue(exception);
	}

	@Test
	public void canAddProductToCartWithNegativeQuantity() {
		boolean exception = false;
		try {
			shoppingCart.addProductToCart(user1, prod_id1, -20);
		} catch (WebshopAppException e) {
			exception = true;
		}

		assertTrue(exception);
	}

	@Test
	public void canRemoveProductFromCart() {
		Map<Integer, Integer> sc_inserted = new HashMap<>();
		try {
			insertShoppingCart(user1.getEmail(), prod_id1, 20);

			shoppingCart.removeProductFromCart(user1, prod_id1);
			sc_inserted = getShoppingCart(user1);

			deleteShoppingCartUser(user1);
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}
		assertNull(sc_inserted.get(prod_id1));
	}

	@Test
	public void canRemoveProductFromCartWithUserNull() {
		boolean exception = false;
		try {
			shoppingCart.removeProductFromCart(null, prod_id1);
		} catch (WebshopAppException e) {
			exception = true;
		}
		assertTrue(exception);
	}

	@Test
	public void canRemoveProductFromCartWithProductThatNotExist() {
		boolean exception = false;
		try {
			shoppingCart.removeProductFromCart(user1, -50);
		} catch (WebshopAppException e) {
			exception = true;
		}
		assertFalse(exception);
	}

	@Test
	public void canUpdateCart() {
		Map<Integer, Integer> sc = new LinkedHashMap<>();
		try {
			insertShoppingCart(user1.getEmail(), prod_id1, 20);

			shoppingCart.updateCart(user1, prod_id1, 8);
			sc = getShoppingCart(user1);

			deleteShoppingCartUser(user1);
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}
		assertTrue(sc.get(prod_id1) == 8);
	}

	@Test
	public void canUpdateCartUserNull() {
		boolean exception = false;
		try {
			shoppingCart.updateCart(null, prod_id1, 8);
		} catch (WebshopAppException e) {
			exception = true;
		}
		assertTrue(exception);
	}

	@Test
	public void canUpdateCartNegativeValue() {
		boolean exception = false;
		try {
			insertShoppingCart(user1.getEmail(), prod_id1, 20);

			shoppingCart.updateCart(user1, prod_id1, -8);
		} catch (WebshopAppException e) {
			exception = true;
		}
		Map<Integer, Integer> sc = getShoppingCart(user1);
		deleteShoppingCartUser(user1);

		assertTrue(exception && sc.get(prod_id1) == 20);
	}

	@Test
	public void canUpdateCartZeroValue() {
		boolean isNotException = true;
		try {
			insertShoppingCart(user1.getEmail(), prod_id1, 20);

			shoppingCart.updateCart(user1, prod_id1, 0);
		} catch (WebshopAppException e) {
			isNotException = false;
		}
		Map<Integer, Integer> sc = getShoppingCart(user1);
		deleteShoppingCartUser(user1);

		assertTrue(isNotException && sc.get(prod_id1) == null);
	}

	@Test
	public void canResetShoppingCart() {
		boolean isNotException = true;
		try {
			insertShoppingCart(user1.getEmail(), prod_id1, 20);
			insertShoppingCart(user1.getEmail(), prod_id2, 20);

			shoppingCart.resetShoppingCart(user1);
		} catch (WebshopAppException e) {
			isNotException = false;
		}
		Map<Integer, Integer> sc = getShoppingCart(user1);
		deleteShoppingCartUser(user1);

		assertTrue(isNotException && sc.size() == 0);
	}

	@Test
	public void canResetShoppingCartUserNull() {
		boolean exception = false;
		try {
			shoppingCart.resetShoppingCart(null);
		} catch (WebshopAppException e) {
			exception = true;
		}
		assertTrue(exception);
	}

	@Test
	public void canGetShoppingCart() {
		Map<Integer, Integer> sc = new LinkedHashMap<>();
		try {
			insertShoppingCart(user1.getEmail(), prod_id1, 20);
			insertShoppingCart(user1.getEmail(), prod_id2, 10);

			sc = shoppingCart.getShoppingCart(user1);

			deleteShoppingCartUser(user1);
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}
		assertTrue((sc.size() == 2) && (sc.get(prod_id1) == 20)
				&& (sc.get(prod_id2) == 10));
	}
}
