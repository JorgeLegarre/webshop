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
import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.repository.ProductRepository;
import se.jiv.webshop.utils.Log;

public class ProductDAO extends GeneralDAO implements ProductRepository {

	private static final Logger LOGGER = Logger.getLogger(ProductDAO.class
			.getName());

	private boolean isValidProduct(ProductModel product, String functionName)
			throws WebshopAppException {
		if (product == null) {
			WebshopAppException excep = new WebshopAppException(
					"product can not be null", this.getClass().getSimpleName(),
					functionName);
			Log.logOutWAException(LOGGER, excep);
			throw excep;
		}

		return true;
	}

	@Override
	public ProductModel createProduct(ProductModel product)
			throws WebshopAppException {
		int generatedId = ProductModel.DEFAULT_PRODUCT_ID;
		if (isValidProduct(product, "CREATE_PRODUCT")) {
			try (Connection conn = getConnection()) {

				conn.setAutoCommit(false);
				try {
					generatedId = insertProduct(conn, product);

					conn.commit();

				} catch (SQLException e) {
					rollback(conn);
					throw e;
				}

			} catch (SQLException e2) {
				WebshopAppException excep = new WebshopAppException(e2, this
						.getClass().getSimpleName(), "CREATE_PRODUCT");
				Log.logOutWAException(LOGGER, excep);
				throw excep;
			}
		}

		ProductModel newProduct = ProductModel
				.builder(product.getName(), product.getProductType())
				.id(generatedId).description(product.getDescription())
				.cost(product.getCost()).rrp(product.getRrp())
				.categories(product.getCategories()).build();

		Log.logOut(LOGGER, this, "CREATE_PRODUCT", "Product created:",
				newProduct.toString());

		return newProduct;
	}

	protected int insertProduct(Connection conn, ProductModel product)
			throws SQLException {
		int generatedId = ProductModel.DEFAULT_PRODUCT_ID;
		String sql = "INSERT INTO products(name,description,cost,rrp, product_type) VALUES(?,?,?,?,?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS)) {
			setString(pstmt, 1, product.getName());
			setString(pstmt, 2, product.getDescription());
			setDouble(pstmt, 3, product.getCost());
			setDouble(pstmt, 4, product.getRrp());
			setInteger(pstmt, 5, product.getProductType());

			pstmt.executeUpdate();

			try (ResultSet rs = pstmt.getGeneratedKeys()) {

				if (rs.next()) {
					generatedId = rs.getInt(1);
				}

			}

			insertProductCategories(conn, generatedId, product.getCategories());
		}
		return generatedId;
	}

	private void insertProductCategories(Connection conn, int productId,
			List<Integer> categories) throws SQLException {

		String sql = "INSERT INTO product_categories VALUES( ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (int categoryId : categories) {

				setInteger(pstmt, 1, productId);
				setInteger(pstmt, 2, categoryId);

				pstmt.addBatch();
			}
			pstmt.executeBatch();
		}

	}

	@Override
	public boolean updateProduct(ProductModel product)
			throws WebshopAppException {

		if (isValidProduct(product, "UPDATE_PRODUCT")) {

			try (Connection conn = getConnection()) {
				conn.setAutoCommit(false);

				try {
					int nUpdates = updateProduct(conn, product);

					if (nUpdates > 0) {
						deleteProductCategories(conn, product.getId());

						insertProductCategories(conn, product.getId(),
								product.getCategories());
					}

					commit(conn);

					Log.logOut(LOGGER, this, "UPDATE_PRODUCT",
							"Updated product:", product.toString());

					return true;
				} catch (SQLException e) {
					rollback(conn);
					throw e;
				}

			} catch (SQLException e2) {
				WebshopAppException excep = new WebshopAppException(e2, this
						.getClass().getSimpleName(), "UPDATE_PRODUCT");
				Log.logOutWAException(LOGGER, excep);
				throw excep;
			}
		}

		return false;
	}

	private int updateProduct(Connection conn, ProductModel product)
			throws SQLException {

		String sql = "UPDATE products SET name = ?, description = ?, cost = ?, rrp = ?, product_type = ? WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			setString(pstmt, 1, product.getName());
			setString(pstmt, 2, product.getDescription());
			setDouble(pstmt, 3, product.getCost());
			setDouble(pstmt, 4, product.getRrp());
			setInteger(pstmt, 5, product.getProductType());
			setInteger(pstmt, 6, product.getId());

			return pstmt.executeUpdate();
		}
	}

	private void deleteProductCategories(Connection conn, int productId)
			throws SQLException {

		String sql = "DELETE FROM product_categories WHERE product_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			setInteger(pstmt, 1, productId);

			pstmt.executeUpdate();
		}
	}

	@Override
	public boolean deleteProduct(int productId) throws WebshopAppException {

		try (Connection conn = getConnection()) {

			String sql = "DELETE FROM products WHERE id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				setInteger(pstmt, 1, productId);

				pstmt.executeUpdate();

				// There are delete_on_cascade in BBDD but we delete anyway
				deleteProductCategories(conn, productId);

				Log.logOut(LOGGER, this, "DELETE_PRODUCT",
						"Product with product id=", String.valueOf(productId),
						" deleted");

				return true;
			}
		} catch (SQLException e1) {
			WebshopAppException excep = new WebshopAppException(e1, this
					.getClass().getSimpleName(), "DELETE_PRODUCT");
			LOGGER.error(e1);
			throw excep;
		}
	}

	@Override
	public List<ProductModel> getProductByName(String productName)
			throws WebshopAppException {

		if (productName != null) {

			try (Connection conn = getConnection()) {

				String sql = "SELECT * FROM products WHERE name = ?";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					setString(pstmt, 1, productName);

					try (ResultSet rs = pstmt.executeQuery()) {

						List<ProductModel> foundProducts = new ArrayList<>();
						while (rs.next()) {
							foundProducts.add(parseModel(conn, rs));
						}

						Log.logOut(LOGGER, this, "GET_PRODUCT_BY_NAME",
								"Found products: ", foundProducts.toString());

						return foundProducts;
					}
				}

			} catch (SQLException e) {
				WebshopAppException excep = new WebshopAppException(e, this
						.getClass().getSimpleName(), "GET_PRODUCT_BY_NAME");
				Log.logOutWAException(LOGGER, excep);
				throw excep;
			}
		}
		return new ArrayList<ProductModel>();
	}

	private ProductModel parseModel(Connection conn, ResultSet rs)
			throws SQLException {

		int id = rs.getInt("id");
		String name = rs.getString("name");
		String description = rs.getString("description");
		double price = rs.getDouble("cost");
		double rrp = rs.getDouble("rrp");
		int productType = rs.getInt("product_type");

		List<Integer> categories = getCategories(conn, id);

		return ProductModel.builder(name, productType).id(id)
				.description(description).cost(price).rrp(rrp)
				.categories(categories).build();
	}

	@Override
	public List<ProductModel> getProductsByCost(double cost)
			throws WebshopAppException {

		try (Connection conn = getConnection()) {

			String sql = "SELECT * FROM products WHERE cost = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				setDouble(pstmt, 1, cost);

				try (ResultSet rs = pstmt.executeQuery()) {

					List<ProductModel> foundProducts = new ArrayList<>();
					while (rs.next()) {
						foundProducts.add(parseModel(conn, rs));
					}

					Log.logOut(LOGGER, this, "GET_PRODUCT_BY_COST",
							"Found products: ", foundProducts.toString());

					return foundProducts;
				}
			}
		} catch (SQLException e) {
			WebshopAppException excep = new WebshopAppException(e, this
					.getClass().getSimpleName(), "GET_PRODUCT_BY_COST");
			Log.logOutWAException(LOGGER, excep);
			throw excep;
		}
	}

	@Override
	public ProductModel getProductById(int productId)
			throws WebshopAppException {

		try (Connection conn = getConnection()) {

			String sql = "SELECT * FROM products WHERE id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				setInteger(pstmt, 1, productId);

				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {

						Log.logOut(LOGGER, this, "GET_PRODUCT_BY_ID",
								"Found product with id: ",
								String.valueOf(productId));

						return parseModel(conn, rs);
					}
				}
			}
			return null;
		} catch (SQLException e) {
			WebshopAppException excep = new WebshopAppException(e, this
					.getClass().getSimpleName(), "GET_PRODUCT_BY_ID");
			Log.logOutWAException(LOGGER, excep);
			throw excep;
		}

	}

	@Override
	public List<ProductModel> getProductsByCategory(int categoryId)
			throws WebshopAppException {

		try (Connection conn = getConnection()) {

			String sql = "SELECT id, name, description, cost, rrp,product_type FROM product_categories "
					+ "INNER JOIN products ON product_id = products.id"
					+ " WHERE category_id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				setInteger(pstmt, 1, categoryId);

				try (ResultSet rs = pstmt.executeQuery()) {

					List<ProductModel> foundProducts = new ArrayList<>();
					while (rs.next()) {
						foundProducts.add(parseModel(conn, rs));
					}

					Log.logOut(LOGGER, this, "GET_PRODUCT_BY_CATEGORY",
							"Found products: ", foundProducts.toString());

					return foundProducts;
				}
			}
		} catch (SQLException e) {
			WebshopAppException excep = new WebshopAppException(e, this
					.getClass().getSimpleName(), "GET_PRODUCT_BY_CATEGORY");
			Log.logOutWAException(LOGGER, excep);
			throw excep;
		}
	}

	@Override
	public List<ProductModel> getAllProducts() throws WebshopAppException {

		try (Connection conn = getConnection()) {

			String sql = "SELECT * FROM products";
			try (Statement stmt = conn.createStatement()) {

				try (ResultSet rs = stmt.executeQuery(sql)) {

					List<ProductModel> foundProducts = new ArrayList<>();
					while (rs.next()) {
						foundProducts.add(parseModel(conn, rs));
					}

					Log.logOut(LOGGER, this, "GET_ALL_PRODUCTS",
							"Found products: ", foundProducts.toString());

					return foundProducts;
				}
			}
		} catch (SQLException e) {
			WebshopAppException excep = new WebshopAppException(e, this
					.getClass().getSimpleName(), "GET_ALL_PRODUCTS");
			Log.logOutWAException(LOGGER, excep);
			throw excep;
		}
	}

	private List<Integer> getCategories(Connection conn, int id)
			throws SQLException {

		String sql = "SELECT category_id FROM product_categories where product_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			setInteger(pstmt, 1, id);

			try (ResultSet rs = pstmt.executeQuery()) {

				List<Integer> categoryIds = new ArrayList<Integer>();

				while (rs.next()) {
					int categoryId = rs.getInt("category_id");
					categoryIds.add(categoryId);
				}

				Log.logOut(LOGGER, this, "GET_CATEGORIES",
						"Found categories: ", categoryIds.toString());

				return categoryIds;
			}
		}
	}

	@Override
	public List<Integer> getCategoriesOfProduct(int productId)
			throws WebshopAppException {

		try (Connection conn = getConnection()) {

			return getCategories(conn, productId);

		} catch (SQLException e) {
			WebshopAppException excep = new WebshopAppException(e, this
					.getClass().getSimpleName(), "GET_CATEGORIES_OF_PRODUCT");
			Log.logOutWAException(LOGGER, excep);
			throw excep;
		}
	}

}
