package se.jiv.webshop.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.repository.dao.ProductDAO;

public class ProductJUnit {
	private final ProductDAO pd = new ProductDAO();

	private static List<Integer> getCategories(Connection conn, int id)
			throws SQLException {

		String sql = "SELECT category_id FROM product_categories where product_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {

				List<Integer> categoryIds = new ArrayList<Integer>();

				while (rs.next()) {
					int categoryId = rs.getInt("category_id");
					categoryIds.add(categoryId);
				}

				return categoryIds;
			}
		}
	}

	private static ProductModel parseModel(Connection conn, ResultSet rs)
			throws SQLException {

		int id = rs.getInt("id");
		String name = rs.getString("name");
		String description = rs.getString("description");
		double price = rs.getDouble("cost");
		double rrp = rs.getDouble("rrp");

		List<Integer> categories = getCategories(conn, id);

		return new ProductModel
				.Builder(name).id(id)
				.description(description).cost(price).rrp(rrp)
				.categories(categories).build();
	}

	static ProductModel insertProduct(ProductModel product) {
		try (Connection conn = DevDBConfig.getConnection()) {
			String sql = "INSERT INTO products (name, description,cost,rrp)"
					+ " VALUES (?,?,?,?)";
			try (PreparedStatement pstmt = conn.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, product.getName());
				pstmt.setString(2, product.getDescription());
				pstmt.setDouble(3, product.getCost());
				pstmt.setDouble(4, product.getRrp());

				pstmt.executeUpdate();

				int productId = -1;
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					if (rs.next()) {
						productId = rs.getInt(1);
					}
				}

				insertProductCategories(conn, productId,
						product.getCategories());

				return new ProductModel(productId,product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void insertProductCategories(Connection conn, int productId,
			List<Integer> categories) throws SQLException {

		String sql = "INSERT INTO product_categories VALUES( ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (int categoryId : categories) {

				pstmt.setInt(1, productId);
				pstmt.setInt(2, categoryId);

				pstmt.addBatch();
			}
			pstmt.executeBatch();
		}

	}

	static void deleteProduct(int prod_id) {
		try (Connection conn = DevDBConfig.getConnection()) {
			String sql = "DELETE FROM products WHERE id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, prod_id);

				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static ProductModel getProductById(int productId) {
		try (Connection conn = DevDBConfig.getConnection()) {

			String sql = "SELECT * FROM products WHERE id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, productId);

				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						return parseModel(conn, rs);
					}
				}
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Test
	public void canCreateProduct() {
		ProductModel addedProduct = new ProductModel
				.Builder("Night Visions")
				.description("Imagine Dragons").cost(149).rrp(400).build();
		ProductModel getProduct = null;
		try {
			addedProduct = pd.createProduct(addedProduct);
			getProduct = getProductById(addedProduct.getId());

			deleteProduct(addedProduct.getId());
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}

		assertTrue("they are not equal", addedProduct.equals(getProduct));

	}

	@Test
	public void canGetProductsByName() {
		ProductModel addedProduct1 = new ProductModel
				.Builder("Night Visions")
				.description("Imagine Dragons").cost(149).rrp(400).build();
		ProductModel addedProduct2 = new ProductModel
				.Builder("Night Visions")
				.description("Imagine Dragons").cost(149).rrp(400).build();
		List<ProductModel> products = null;

		try {
			addedProduct1 = insertProduct(addedProduct1);
			addedProduct2 = insertProduct(addedProduct2);

			products = pd.getProductByName(addedProduct1.getName());

			deleteProduct(addedProduct1.getId());
			deleteProduct(addedProduct2.getId());
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}
		assertTrue(products.size() == 2
				&& addedProduct1.equals(products.get(0))
				&& addedProduct2.equals(products.get(1)));
	}

	@Test
	public void canGetProductByNonExcistingName() {
		List<ProductModel> products = null;
		try {
			products = pd.getProductByName("alskdaskldj");
		} catch (WebshopAppException e) {
		}

		assertTrue(products.size() == 0);
	}

	@Test
	public void canGetProductById() {
		ProductModel addedProduct = new ProductModel
				.Builder("Night Visions")
				.description("Imagine Dragons").cost(149).rrp(400).build();
		ProductModel getProduct = null;
		try {
			addedProduct = insertProduct(addedProduct);
			getProduct = pd.getProductById(addedProduct.getId());

			deleteProduct(addedProduct.getId());
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}

		assertTrue("they are not equal", addedProduct.equals(getProduct));
	}

	@Test
	public void canGetProductByNonExcistingId() {
		ProductModel retrieved = null;
		try {
			retrieved = pd.getProductById(1234);
		} catch (WebshopAppException e) {
		}

		assertNull(retrieved);
	}

	@Test
	public void canGetAllProducts() {
		ProductModel addedProduct1 = new ProductModel
				.Builder("Night Visions")
				.description("Imagine Dragons").cost(149).rrp(400).build();
		ProductModel addedProduct2 = new ProductModel
				.Builder("Night Visions")
				.description("Imagine Dragons").cost(149).rrp(400).build();
		List<ProductModel> products = null;
		boolean isProduct1 = false;
		boolean isProduct2 = false;

		try {
			addedProduct1 = insertProduct(addedProduct1);
			addedProduct2 = insertProduct(addedProduct2);

			products = pd.getAllProducts();

			deleteProduct(addedProduct1.getId());
			deleteProduct(addedProduct2.getId());

		} catch (WebshopAppException e) {
			e.printStackTrace();
		}
		for (ProductModel product : products) {
			if (addedProduct1.equals(product)) {
				isProduct1 = true;
			}
			if (addedProduct2.equals(product)) {
				isProduct2 = true;
			}
		}

		assertTrue(products.size() >= 2 && isProduct1 && isProduct2);

	}

	@Test
	public void canUpdateProduct() {
		ProductModel addedProduct = new ProductModel
				.Builder("Night Visions")
				.description("Imagine Dragons").cost(149).rrp(400).build();
		ProductModel updatedProduct = new ProductModel
				.Builder("Night Visionsadfa")
				.description("Imagine Dragonsadfa").cost(1439).rrp(4030)
				.build();
		ProductModel getProduct = null;

		try {
			addedProduct = insertProduct(addedProduct);
			updatedProduct = new ProductModel(addedProduct.getId(),
					updatedProduct);

			pd.updateProduct(updatedProduct);
			getProduct = getProductById(addedProduct.getId());

			deleteProduct(addedProduct.getId());
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}

		assertEquals("Was not able to update", updatedProduct, getProduct);

	}

	@Test
	public void canDeleteProduct() {
		ProductModel addedProduct = new ProductModel
				.Builder("Night Visions")
				.description("Imagine Dragons").cost(149).rrp(400).build();
		ProductModel getProduct = null;
		try {
			addedProduct = insertProduct(addedProduct);

			pd.deleteProduct(addedProduct.getId());

			getProduct = getProductById(addedProduct.getId());

			deleteProduct(addedProduct.getId());
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}
		assertNull(getProduct);

	}

	@Test
	public void canGetProductsByCost() {
		ProductModel addedProduct = new ProductModel
				.Builder("Night Visions")
				.description("Imagine Dragons").cost(149).rrp(400).build();

		List<ProductModel> products = null;
		boolean isInResult = false;
		try {
			addedProduct = insertProduct(addedProduct);

			products = pd.getProductsByCost(addedProduct.getCost());

			for (ProductModel product : products) {
				if (addedProduct.equals(product)) {
					isInResult = true;
					break;
				}
			}

			deleteProduct(addedProduct.getId());
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}

		assertTrue(isInResult);

	}

	@Test
	public void canGetProductByNonExcistingCost() {
		List<ProductModel> products = new ArrayList<>();
		try {
			products = pd.getProductsByCost(123456);
		} catch (WebshopAppException e) {

		}

		assertTrue((products != null) && (products.size() == 0));
	}

	@Test
	public void canGetProductsByCategory() {
		List<Integer> categories = new ArrayList<>();
		categories.add(CategoryJUnit.getACategory());

		ProductModel addedProduct = new ProductModel
				.Builder("Night Visions")
				.description("Imagine Dragons").cost(149).rrp(400)
				.categories(categories).build();

		List<ProductModel> products = null;
		boolean isInResult = false;
		try {
			addedProduct = insertProduct(addedProduct);

			products = pd.getProductsByCategory(addedProduct.getCategories()
					.get(0));

			for (ProductModel product : products) {
				if (addedProduct.equals(product)) {
					isInResult = true;
					break;
				}
			}

			deleteProduct(addedProduct.getId());
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}

		assertTrue(isInResult);

	}

	@Test
	public void canGetProductByNonExcistingCategory() {
		List<ProductModel> products = new ArrayList<>();
		try {
			products = pd.getProductsByCategory(133234);
		} catch (WebshopAppException e) {

		}

		assertTrue((products != null) && (products.size() == 0));
	}

	@Test
	public void canGetCategoriesOfProduct() {
		List<Integer> categories = new ArrayList<>();
		categories.add(CategoryJUnit.getACategory());

		List<Integer> categories_retrieved = null;

		ProductModel addedProduct = new ProductModel
				.Builder("Night Visions")
				.description("Imagine Dragons").cost(149).rrp(400)
				.categories(categories).build();

		try {
			addedProduct = insertProduct(addedProduct);
			categories_retrieved = pd.getCategoriesOfProduct(addedProduct
					.getId());

			deleteProduct(addedProduct.getId());
		} catch (WebshopAppException e) {

		}

		assertEquals(categories, categories_retrieved);
	}

	@Test
	public void canGetCategoriesOfNonExcistingProduct() {
		List<Integer> categories_retrieved = null;
		try {
			categories_retrieved = pd.getCategoriesOfProduct(1239874);
		} catch (WebshopAppException e) {

		}

		assertTrue((categories_retrieved != null)
				&& (categories_retrieved.size() == 0));
	}

}
