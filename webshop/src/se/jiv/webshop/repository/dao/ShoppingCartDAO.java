package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.ShoppingCartRepository;
import se.jiv.webshop.utils.Log;

public final class ShoppingCartDAO extends GeneralDAO implements
		ShoppingCartRepository {
	private static final Logger LOGGER = Logger.getLogger(ShoppingCartDAO.class
			.getSimpleName());

	@Override
	public void addProductToCart(UserModel user, int productId, int quantity)
			throws WebshopAppException {

		if (isValidUser(user, "ADD_PRODUCT_TO_SHOPPING_CART")
				&& isPositiveQuantity(quantity, "ADD_PRODUCT_TO_SHOPPING_CART")) {

			try (Connection conn = getConnection()) {

				int db_quantity = getProductQuantity(conn, user, productId);

				deleteProductFromShoppingCart(conn, user, productId);

				int newQuantity = db_quantity + quantity;

				insertProductQuantity(conn, user, productId, newQuantity);

				Log.logOut(LOGGER, this, "ADD_PRODUCT_TO_SHOPPING_CART",
						"Added ", String.valueOf(quantity), " product of id: ",
						String.valueOf(productId), " to user: ",
						user.getEmail());

			} catch (SQLException e) {
				WebshopAppException excep = new WebshopAppException(e, this
						.getClass().getSimpleName(),
						"ADD_PRODUCT_TO_SHOPPING_CART");
				Log.logOutWAException(LOGGER, excep);
				throw excep;
			}
		}

	}

	@Override
	public void removeProductFromCart(UserModel user, int productId)
			throws WebshopAppException {

		if (isValidUser(user, "REMOVE_PRODUCT_FROM_SHOPPING_CART")) {

			try (Connection conn = getConnection()) {

				deleteProductFromShoppingCart(conn, user, productId);

				Log.logOut(LOGGER, this, "REMOVE_PRODUCT_FROM_SHOPPING_CART",
						"Removed product ", String.valueOf(productId),
						" from user: ", user.getEmail());

			} catch (SQLException e) {
				WebshopAppException excep = new WebshopAppException(e, this
						.getClass().getSimpleName(),
						"REMOVE_PRODUCT_FROM_SHOPPING_CART");
				Log.logOutWAException(LOGGER, excep);
				throw excep;
			}
		}
	}

	@Override
	public void updateCart(UserModel user, int productId, int quantity)
			throws WebshopAppException {

		if (isValidUser(user, "UPDATE_CART")
				&& isPositiveQuantity(quantity, "UPDATE_CART")) {

			try (Connection conn = getConnection()) {

				deleteProductFromShoppingCart(conn, user, productId);

				if (quantity > 0) {
					insertProductQuantity(conn, user, productId, quantity);
				}

				Log.logOut(LOGGER, this, "UPDATE_CART",
						"Updated cart of user: ", user.getEmail(),
						" for product: ", String.valueOf(productId),
						" wtih a quantity of: ", String.valueOf(quantity));

			} catch (SQLException e) {
				WebshopAppException excep = new WebshopAppException(
						e.getMessage(), this.getClass().getSimpleName(),
						"UPDATE_CART");
				Log.logOutWAException(LOGGER, excep);
				throw excep;
			}
		}

	}

	@Override
	public void resetShoppingCart(UserModel user) throws WebshopAppException {

		if (isValidUser(user, "RESET_SHOPPING_CART")) {

			try (Connection conn = getConnection()) {

				String sql = "DELETE FROM shopping_cart WHERE user_email = ?";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

					setString(pstmt, 1, user.getEmail());

					pstmt.executeUpdate();

					Log.logOut(LOGGER, this, "RESET_SHOPPING_CART",
							"Resetted shopping cart of user: ", user.getEmail());
				}
			} catch (SQLException e) {
				WebshopAppException excep = new WebshopAppException(
						e.getMessage(), this.getClass().getSimpleName(),
						"RESET_SHOPPING_CART");
				Log.logOutWAException(LOGGER, excep);
				throw excep;
			}
		}

	}

	@Override
	public Map<Integer, Integer> getShoppingCart(UserModel user)
			throws WebshopAppException {

		if (isValidUser(user, "GET_SHOPPING_SHOPPING_CART_CONTENTS")) {

			try (Connection conn = getConnection()) {

				String sql = "SELECT product_id, quantity FROM shopping_cart WHERE user_email = ?";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					setString(pstmt, 1, user.getEmail());

					try (ResultSet rs = pstmt.executeQuery()) {

						Map<Integer, Integer> contents = new LinkedHashMap<>();
						while (rs.next()) {
							contents.put(rs.getInt("product_id"),
									rs.getInt("quantity"));
						}

						Log.logOut(LOGGER, this,
								"GET_SHOPPING_SHOPPING_CART_CONTENTS",
								"Get shopping cart from user: ",
								user.getEmail());

						return contents;
					}
				}
			} catch (SQLException e) {
				WebshopAppException excep = new WebshopAppException(
						e.getMessage(), this.getClass().getSimpleName(),
						"GET_SHOPPING_SHOPPING_CART_CONTENTS");
				Log.logOutWAException(LOGGER, excep);
				throw excep;
			}
		}

		return null;

	}

	private int getProductQuantity(Connection conn, UserModel user,
			int productId) throws SQLException {

		String sql = "SELECT quantity FROM shopping_cart WHERE user_email = ? and product_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			setString(pstmt, 1, user.getEmail());
			setInteger(pstmt, 2, productId);

			try (ResultSet rs = pstmt.executeQuery()) {

				int quantity = 0;
				if (rs.next()) {
					quantity = rs.getInt(1);
				}

				return quantity;
			}
		}
	}

	private void deleteProductFromShoppingCart(Connection conn, UserModel user,
			int productId) throws SQLException {
		String sql = "DELETE FROM shopping_cart WHERE user_email = ? and product_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			setString(pstmt, 1, user.getEmail());
			setInteger(pstmt, 2, productId);

			pstmt.executeUpdate();
		}

	}

	private void insertProductQuantity(Connection conn, UserModel user,
			int productId, int newQuantity) throws SQLException {
		String sql = "INSERT INTO shopping_cart (user_email, product_id, quantity) VALUES (?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			setString(pstmt, 1, user.getEmail());
			setInteger(pstmt, 2, productId);
			setInteger(pstmt, 3, newQuantity);

			pstmt.executeUpdate();
		}

	}

	private boolean isPositiveQuantity(int quantity, String functionName)
			throws WebshopAppException {
		if (quantity < 0) {
			WebshopAppException excep = new WebshopAppException(
					"quantity can not be negative", this.getClass()
							.getSimpleName(), functionName);
			Log.logOutWAException(LOGGER, excep);
			throw excep;
		}

		return true;
	}

	private boolean isValidUser(UserModel user, String functionName)
			throws WebshopAppException {
		if (user == null) {
			WebshopAppException excep = new WebshopAppException(
					"user can not be null", this.getClass().getSimpleName(),
					functionName);
			Log.logOutWAException(LOGGER, excep);
			throw excep;
		}

		return true;

	}

}
