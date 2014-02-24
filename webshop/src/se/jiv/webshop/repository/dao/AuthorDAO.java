package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.AuthorModel;
import se.jiv.webshop.repository.AuthorRepository;

public class AuthorDAO extends GeneralDAO implements AuthorRepository {
	private static final Logger LOGGER = Logger.getLogger(AuthorDAO.class
			.getSimpleName());

	@Override
	public AuthorModel addAuthor(AuthorModel author) throws WebshopAppException {
		int generatedId = AuthorModel.DEFAULT_ID;
		if (isValidAuthor(author, "ADD_AUTHOR")) {
			try (Connection conn = getConnection()) {

				String sql = "INSERT INTO author (firstname, lastname, dob, country)"
						+ "VALUES (?, ?, ?, ?)";
				try (PreparedStatement pstmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS)) {
					prepareStatementFromModel(pstmt, author);

					pstmt.executeUpdate();

					try (ResultSet rs = pstmt.getGeneratedKeys()) {

						if (rs.next()) {
							generatedId = rs.getInt(1);
						}

					}

				}

				LOGGER.trace(String.format("%s.ADD_AUTHOR - %s %s", this
						.getClass().getSimpleName(), "Author added: ", author));

			} catch (SQLException e) {
				WebshopAppException excep = new WebshopAppException(
						e.getMessage(), this.getClass().getSimpleName(),
						"ADD_AUTHOR");
				LOGGER.error(excep);
				throw excep;
			}
		} else {
			LOGGER.error("Add author: Author is null.");
		}
		return new AuthorModel.Builder(generatedId, author.getFirstname(),
				author.getLastname()).dob(author.getDob())
				.country(author.getCountry()).build();
	}

	@Override
	public void updateAuthor(AuthorModel author) throws WebshopAppException {

		if (isValidAuthor(author, "UPDATE_AUTHOR")) {

			try (Connection conn = getConnection()) {

				String sql = "UPDATE author SET firstname = ?, lastname = ?, dob = ?, country = ? WHERE id = ?";

				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					prepareStatementFromModel(pstmt, author);
					pstmt.setInt(5, author.getId());

					pstmt.executeUpdate();
					LOGGER.trace(String.format("%s.UPDATE_AUTHOR - %s %s", this
							.getClass().getSimpleName(), "Author updated: ",
							author));
				}
			} catch (SQLException e) {
				WebshopAppException excep = new WebshopAppException(
						e.getMessage(), this.getClass().getSimpleName(),
						"UPDATE_AUTHOR");
				LOGGER.error(excep);
				throw excep;
			}
		} else {
			LOGGER.error("Update author: Author is null.");
		}

	}

	@Override
	public void deleteAuthor(int id) throws WebshopAppException {

		if (id > 0) {

			try (Connection conn = getConnection()) {

				String sql = "DELETE FROM author WHERE id = ?";

				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setInt(1, id);

					pstmt.executeUpdate();
					LOGGER.trace(String.format("%s.DELETE_AUTHOR - %s %s %s",
							this.getClass().getSimpleName(), "Author of id: ",
							id, "deleted"));
				}

			} catch (SQLException e) {
				WebshopAppException excep = new WebshopAppException(
						e.getMessage(), this.getClass().getSimpleName(),
						"DELETE_AUTHOR");
				LOGGER.error(excep);
				throw excep;
			}
		} else {
			LOGGER.error("Delete author: id is < 0");
		}
	}

	@Override
	public AuthorModel getAuthor(int id) throws WebshopAppException {
		if (id > 0) {

			try (Connection conn = getConnection()) {

				String sql = "SELECT * FROM author WHERE id = ?";

				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setInt(1, id);

					try (ResultSet rs = pstmt.executeQuery()) {

						if (rs.next()) {
							AuthorModel author = parseAuthor(rs);
							LOGGER.trace(String.format("%s.GET_AUTHOR - %s %s",
									this.getClass().getSimpleName(),
									"Author get from database: ", author));
							return author;
						}
					}

				}

			} catch (SQLException e) {
				WebshopAppException excep = new WebshopAppException(
						e.getMessage(), this.getClass().getSimpleName(),
						"GET_AUTHOR");
				LOGGER.error(excep);
				throw excep;
			}
		} else {
			LOGGER.error("Get Author: Input = null");
		}

		return null;
	}

	private AuthorModel parseAuthor(ResultSet rs) throws SQLException {
		return new AuthorModel.Builder(rs.getInt("id"),
				rs.getString("firstname"), rs.getString("lastname"))
				.dob(rs.getString("dob")).country(rs.getString("country"))
				.build();
	}

	@Override
	public List<AuthorModel> getAuthorsByName(String name)
			throws WebshopAppException {
		if (name != null) {

			try (Connection conn = getConnection()) {

				String sql = "SELECT * FROM author WHERE lastname = ? or firstname = ?";

				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					setString(pstmt, 1, name);
					setString(pstmt, 2, name);

					try (ResultSet rs = pstmt.executeQuery()) {
						List<AuthorModel> authorList = new ArrayList<AuthorModel>();
						while (rs.next()) {
							authorList.add(parseAuthor(rs));
						}
						LOGGER.trace(String.format(
								"%s.GET_AUTHOR_BY_NAME - %s %s", this
										.getClass().getSimpleName(),
								"Authors get from database: ", authorList));
						return authorList;
					}

				}

			} catch (SQLException e) {
				WebshopAppException excep = new WebshopAppException(
						e.getMessage(), this.getClass().getSimpleName(),
						"GET_AUTHORS_BY_NAME");
				LOGGER.error(excep);
				throw excep;
			}
		} else {
			LOGGER.error("Get Author: Input = null");
		}

		return null;
	}

	@Override
	public List<AuthorModel> getAllAuthors() throws WebshopAppException {

		try (Connection conn = getConnection()) {

			String sql = "SELECT * FROM author";

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

				try (ResultSet rs = pstmt.executeQuery()) {
					List<AuthorModel> authorList = new ArrayList<AuthorModel>();
					while (rs.next()) {
						authorList.add(parseAuthor(rs));
					}
					LOGGER.trace(String.format("%s.GET_ALL_AUTHORS - %s %s",
							this.getClass().getSimpleName(),
							"Authors get from database: ", authorList));
					return authorList;
				}

			}

		} catch (SQLException e) {
			WebshopAppException excep = new WebshopAppException(e.getMessage(),
					this.getClass().getSimpleName(), "GET_AUTHORS_BY_NAME");
			LOGGER.error(excep);
			throw excep;
		}
	}

	private void prepareStatementFromModel(PreparedStatement pstmt,
			AuthorModel author) throws SQLException {
		pstmt.setString(1, author.getFirstname());
		pstmt.setString(2, author.getLastname());
		pstmt.setString(3, author.getDob());
		pstmt.setString(4, author.getCountry());
	}

	private boolean isValidAuthor(AuthorModel author, String functionName)
			throws WebshopAppException {
		if (author == null) {
			WebshopAppException excep = new WebshopAppException(
					"author can not be null", this.getClass().getSimpleName(),
					functionName);
			LOGGER.error(excep);
			throw excep;
		}

		return true;
	}
}
