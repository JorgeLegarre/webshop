package se.jiv.webshop.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.repository.dao.CategoryDAO;

public class CategoryJUnit {
	private CategoryModel categoryTest;
	private int generatedStaffId = -1;

	private int insertStaff() throws SQLException {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {

				String sql = "INSERT INTO staff (firstname, surname, dob, street_address, town, postcode, mobile,email, salary)"
						+ " VALUES ('testFirstname', 'testSurname', 'testDob', 'testStreet','testTown','12868','testMobile','testEmail',10)";

				stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

				try (ResultSet rs = stmt.getGeneratedKeys()) {
					if (rs.next()) {
						return rs.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private CategoryModel insertCategory(CategoryModel category) {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {

				String sql = "INSERT INTO categories (name, staff_responsible)"
						+ " VALUES ('" + category.getName() + "', "
						+ generatedStaffId + ")";

				stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

				try (ResultSet rs = stmt.getGeneratedKeys()) {
					if (rs.next()) {
						return new CategoryModel(rs.getInt(1), category);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void deleteCategoryById(int id) {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {

				String sql = "DELETE FROM categories WHERE id = " + id;

				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteStaffById(int id) {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {

				String sql = "DELETE FROM staff WHERE id = " + id;

				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int countCategories() {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {

				String sql = "SELECT COUNT(*) FROM categories";

				try (ResultSet rs = stmt.executeQuery(sql)) {
					if (rs.next()) {
						return rs.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private int getMaxStaffId() {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {

				String sql = "select max(staff_responsible) from webshop.categories";

				try (ResultSet rs = stmt.executeQuery(sql)) {
					if (rs.next()) {
						return rs.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	private CategoryModel getCategoryById(int id) {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {

				String sql = "select * from webshop.categories where id = "
						+ id;

				try (ResultSet rs = stmt.executeQuery(sql)) {
					if (rs.next()) {
						int db_id = rs.getInt(1);
						String db_name = rs.getString(2);
						int db_staff = rs.getInt(3);

						return new CategoryModel(db_id, db_name, db_staff);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private CategoryModel getCategoryByName(String name) {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {

				String sql = "select * from webshop.categories where name = '"
						+ name + "'";

				try (ResultSet rs = stmt.executeQuery(sql)) {
					if (rs.next()) {
						int db_id = rs.getInt(1);
						String db_name = rs.getString(2);
						int db_staff = rs.getInt(3);

						return new CategoryModel(db_id, db_name, db_staff);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	static int getACategory() {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {

				String sql = "select * from webshop.categories";

				try (ResultSet rs = stmt.executeQuery(sql)) {
					if (rs.next()) {
						return rs.getInt(1);

					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Before
	public void setUp() throws Exception {
		generatedStaffId = insertStaff();

		CategoryModel model = new CategoryModel("testName", generatedStaffId);
		categoryTest = insertCategory(model);

	}

	@After
	public void tearDown() throws Exception {
		deleteCategoryById(categoryTest.getId());
		deleteStaffById(generatedStaffId);
	}

	// GET_CATEGORY
	@Test
	public void canGetACategoryThatExist() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel categoryRetrieved = null;
		try {
			categoryRetrieved = cd.getCategory(categoryTest.getId());
		} catch (WebshopAppException e) {
			e.printStackTrace();
			fail("Exception");
		}
		assertEquals(categoryRetrieved, categoryTest);
	}

	@Test
	public void canGetCategoryThatNotExist() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel retrieved = null;
		try {
			retrieved = cd.getCategory(-2);
		} catch (WebshopAppException e) {
			e.printStackTrace();
			fail("Exception");
		}
		assertNull(retrieved);
	}

	// GET_ALL_CATEGORIES
	@Test
	public void canGetAllCategories() {
		CategoryDAO cd = new CategoryDAO();
		List<CategoryModel> categories = null;
		try {
			categories = cd.getAllCategories();
		} catch (WebshopAppException e) {
			e.printStackTrace();
			fail("Exception");
		}

		boolean findTestCategory = false;
		for (CategoryModel cat : categories) {
			if (categoryTest.equals(cat)) {
				findTestCategory = true;
				break;
			}
		}

		assertTrue(findTestCategory && (countCategories() == categories.size()));
	}

	// SEARCH_CATEGORY_BY_NAME
	@Test
	public void canSearchCategoryByName() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel retrieved = null;

		try {
			retrieved = cd.searchCategoryByName(categoryTest.getName());
		} catch (WebshopAppException e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertEquals(retrieved, categoryTest);
	}

	@Test
	public void canSearchCategoryByNameThatNotExist() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel retrieved = null;
		try {
			retrieved = cd.searchCategoryByName("adfaqdafvxrrrq");
		} catch (WebshopAppException e) {
			e.printStackTrace();
			fail("Exception");
		}
		assertNull(retrieved);
	}

	@Test
	public void canSearchCategoryByNameWithNullParameter() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel retrieved = null;
		try {
			retrieved = cd.searchCategoryByName(null);
		} catch (WebshopAppException e) {
			e.printStackTrace();
			fail("Exception");
		}
		assertNull(retrieved);
	}

	// ADD_CATEGORY
	@Test
	public void canAddCategory() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel addCategory = new CategoryModel("toAddCategory",
				generatedStaffId);
		CategoryModel retrieved = null;

		try {
			addCategory = cd.addCategory(addCategory);

			retrieved = getCategoryById(addCategory.getId());

			deleteCategoryById(addCategory.getId());
		} catch (WebshopAppException e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertEquals(addCategory, retrieved);
	}

	@Test
	public void canAddCategoryWithSameName() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel addCategory = new CategoryModel("toAddCategory",
				generatedStaffId);
		CategoryModel addedCategory = null;
		boolean wasException = false;
		try {
			addedCategory = cd.addCategory(addCategory);
			cd.addCategory(addCategory);

		} catch (WebshopAppException e) {
			wasException = true;
		}
		deleteCategoryById(addedCategory.getId());

		assertTrue(wasException);
	}

	@Test
	public void canAddNullCategory() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel addCategory = null;
		boolean wasException = false;
		try {
			cd.addCategory(addCategory);
		} catch (WebshopAppException e) {
			wasException = true;
		}

		assertTrue(wasException);
	}

	@Test
	public void canAddCategoryWithNullName() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel addCategory = new CategoryModel(null, generatedStaffId);
		boolean wasException = false;
		try {
			cd.addCategory(addCategory);
		} catch (WebshopAppException e) {
			wasException = true;
		}

		assertTrue(wasException);
	}

	@Test
	public void canAddCategoryWithStaffThatNotExist() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel addCategory = new CategoryModel("toAddCategory",
				getMaxStaffId() + 10);
		boolean wasException = false;
		try {
			cd.addCategory(addCategory);
		} catch (WebshopAppException e) {
			wasException = true;
		}

		assertTrue(wasException);
	}

	@Test
	public void canAddCategoryNegativeStaff() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel addCategory = new CategoryModel("toAddCategory", -100);
		boolean wasException = false;
		try {
			cd.addCategory(addCategory);
		} catch (WebshopAppException e) {
			wasException = true;
		}

		assertTrue(wasException);
	}

	// UPDATE_CATEGORY
	@Test
	public void canUpdateCategory() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel addCategory = new CategoryModel("toAddCategory",
				generatedStaffId);
		CategoryModel updateCategory = null;
		CategoryModel retrieved = null;

		try {
			addCategory = insertCategory(addCategory);

			updateCategory = new CategoryModel(addCategory.getId(),
					"categoryUpdated", generatedStaffId);

			cd.updateCategory(updateCategory);

			retrieved = getCategoryByName("categoryUpdated");

			deleteCategoryById(addCategory.getId());
		} catch (WebshopAppException e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertEquals(addCategory, retrieved);
	}

	@Test
	public void canUpdateCategoryWithANameThatExist() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel addCategory1 = new CategoryModel("toAddCategory1",
				generatedStaffId);
		CategoryModel addCategory2 = new CategoryModel("toAddCategory2",
				generatedStaffId);
		CategoryModel updateCategory = null;
		boolean wasException = false;

		CategoryModel addedCategory1 = null;
		CategoryModel addedCategory2 = null;

		try {
			addedCategory1 = insertCategory(addCategory1);
			addedCategory2 = insertCategory(addCategory2);

			updateCategory = new CategoryModel(addedCategory1.getId(),
					addedCategory2);

			cd.updateCategory(updateCategory);

		} catch (WebshopAppException e) {
			wasException = true;
		}
		deleteCategoryById(addedCategory1.getId());
		deleteCategoryById(addedCategory2.getId());

		assertTrue(wasException);
	}

	@Test
	public void canUpdateCategoryWithNull() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel updateCategory = null;

		boolean wasException = false;
		try {
			cd.updateCategory(updateCategory);
		} catch (WebshopAppException e) {
			wasException = true;
		}

		assertTrue(wasException);
	}

	@Test
	public void canUpdateCategoryWithNullName() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel addedCategory = new CategoryModel("toAddCategory",
				generatedStaffId);
		CategoryModel updatedCategory = new CategoryModel(null,
				generatedStaffId);

		boolean wasException = false;
		try {
			addedCategory = insertCategory(addedCategory);
			updatedCategory = new CategoryModel(addedCategory.getId(),
					updatedCategory);

			cd.updateCategory(updatedCategory);

		} catch (WebshopAppException e) {
			wasException = true;
		}
		deleteCategoryById(addedCategory.getId());

		assertTrue(wasException);
	}

	@Test
	public void canUpdateWithoutId() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel updatedCategory = new CategoryModel("toAddCategory",
				generatedStaffId);

		try {
			cd.updateCategory(updatedCategory);
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void canUpdatedCategoryWithStaffThatNotExist() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel addedCategory = new CategoryModel("toAddCategory",
				generatedStaffId);
		CategoryModel updatedCategory = new CategoryModel("toAddCategory",
				getMaxStaffId() + 10);
		boolean wasException = false;

		try {
			addedCategory = insertCategory(addedCategory);
			updatedCategory = new CategoryModel(addedCategory.getId(),
					updatedCategory);

			cd.updateCategory(updatedCategory);

		} catch (WebshopAppException e) {
			wasException = true;
		}
		deleteCategoryById(addedCategory.getId());

		assertTrue(wasException);
	}

	@Test
	public void canUpdateCategoryWithNegativeStaff() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel addedCategory = new CategoryModel("toAddCategory",
				generatedStaffId);
		CategoryModel updatedCategory = new CategoryModel("toAddCategory", -100);
		boolean wasException = false;

		try {
			addedCategory = insertCategory(addedCategory);
			updatedCategory = new CategoryModel(addedCategory.getId(),
					updatedCategory);

			cd.updateCategory(updatedCategory);
		} catch (WebshopAppException e) {
			wasException = true;
		}
		deleteCategoryById(addedCategory.getId());

		assertTrue(wasException);
	}

	// DELETE_CATEGORY
	@Test
	public void canDeleteCategoryThatExist() {
		CategoryDAO cd = new CategoryDAO();
		CategoryModel addCategory = new CategoryModel("toAddCategory",
				generatedStaffId);
		CategoryModel addedCategory = null;
		CategoryModel retrieved = null;

		try {
			addedCategory = insertCategory(addCategory);

			cd.deleteCategory(addedCategory.getId());

			retrieved = getCategoryById(addedCategory.getId());

		} catch (WebshopAppException e) {
			deleteCategoryById(addedCategory.getId());
			e.printStackTrace();
			fail("Exception");
		}

		assertNull(retrieved);
	}

	@Test
	public void canDeleteCategoryThatNotExist() {
		CategoryDAO cd = new CategoryDAO();
		boolean notException = true;
		try {
			cd.deleteCategory(-1000);
		} catch (WebshopAppException e) {
			notException = false;
		}
		assertTrue(notException);
	}
}
