package se.jiv.webshop.main;

import java.util.List;

import org.apache.log4j.Logger;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.CategoryDAO;
import se.jiv.webshop.repository.dao.ProductDAO;
import se.jiv.webshop.repository.dao.ShoppingCartDAO;
import se.jiv.webshop.repository.dao.UserDAO;
import se.jiv.webshop.service.CategoryService;
import se.jiv.webshop.service.ProductService;
import se.jiv.webshop.service.UserService;
import se.jiv.webshop.ui.CategoryUI;
import se.jiv.webshop.ui.ExceptionUI;
import se.jiv.webshop.ui.MainMenuUI;
import se.jiv.webshop.ui.ProductUI;
import se.jiv.webshop.ui.UserUI;

public final class WebShopMain {
	private static final Logger LOGGER = Logger.getLogger(WebShopMain.class);

	public static void main(String[] args) {
		try {
			MainMenuUI mainMenu = new MainMenuUI();
			boolean exit = false;

			while (!exit) {
				int option = mainMenu.firstMenu();

				switch (option) {
				case 1:
					createInfo();
					break;
				case 2:
					editInfo();
					break;
				case 3:
					doLogin();
					break;
				case 4:
					searchProductsByCategory();
					break;
				case 5:
					searchProductByName();
					break;
				case 0:
					exit = true;
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error(e);
			ExceptionUI.printUncontrolledException(e);
		}
	}

	private static void createInfo() {
		MainMenuUI mainMenu = new MainMenuUI();
		int option = mainMenu.createInformation();

		switch (option) {
		case 1:
			createProduct();
			break;
		case 2:
			createCategory();
			break;
		case 3:
			createUser();
			break;
		case 0:
			break;
		default:
			mainMenu.showOptionNotValid();
		}
	}

	private static void createProduct() {
		ProductUI productMenu = new ProductUI();
		ProductService productService = new ProductService(new ProductDAO());

		try {
			ProductModel newProduct = productMenu.createProduct();
			newProduct = productService.createProduct(newProduct);
			productMenu.showCreateSuccess(newProduct);
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}

	}

	private static void createCategory() {
		CategoryUI categoryMenu = new CategoryUI();
		CategoryService categoryService = new CategoryService(new CategoryDAO());

		try {
			CategoryModel newCategory = categoryMenu.askAddCategory();
			newCategory = categoryService.addCategory(newCategory);
			categoryMenu.showCreatedSuccess(newCategory);
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}
	}

	private static void createUser() {
		UserUI userMenu = new UserUI();
		UserService userService = new UserService(new UserDAO(),
				new ShoppingCartDAO());

		try {
			UserModel newUser = userMenu.addUser();
			UserModel existUser = userService.getUser(newUser.getEmail());
			if (existUser == null) {
				userService.addUser(newUser);
				userMenu.showCreatedSuccess(newUser);
			} else {
				userMenu.showExistUser(newUser.getEmail());
			}
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}
	}

	private static void editInfo() {
		MainMenuUI mainMenu = new MainMenuUI();
		int option = mainMenu.editInformation();

		switch (option) {
		case 1:
			editProduct();
			break;
		case 2:
			editCategory();
			break;
		case 3:
			editUser();
			break;
		case 0:
			break;
		default:
			mainMenu.showOptionNotValid();
		}
	}

	private static void editProduct() {
		ProductUI productMenu = new ProductUI();
		ProductService productService = new ProductService(new ProductDAO());

		try {
			int productId = productMenu.askProductId();
			ProductModel oldProduct = productService.getProductById(productId);
			if (oldProduct != null) {
				ProductModel productToUpdate = productMenu
						.updateProduct(productId);
				productService.updateProduct(productToUpdate);
				productMenu.showUpdatedSuccess();
			} else {
				productMenu.showProductNotFound();
			}
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}
	}

	private static void editCategory() {
		CategoryUI categoryMenu = new CategoryUI();
		CategoryService categoryService = new CategoryService(new CategoryDAO());

		try {
			int categoryId = categoryMenu.askForCategoryId();
			CategoryModel oldCategory = categoryService.getCategory(categoryId);
			if (oldCategory != null) {

				CategoryModel categoryToUpdate = categoryMenu
						.askUpdateCategory(categoryId);

				categoryService.updateCategory(categoryToUpdate);

				categoryMenu.showUpdatedSuccess();
			} else {
				categoryMenu.showCategoryNotFound();
			}

		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}
	}

	private static void editUser() {
		UserUI userMenu = new UserUI();
		UserService userService = new UserService(new UserDAO(),
				new ShoppingCartDAO());
		try {
			String userEmail = userMenu.askForEmail();
			UserModel oldUser = userService.getUser(userEmail);
			if (oldUser != null) {
				UserModel newUser = userMenu.updateUser(oldUser);
				userService.updateUser(newUser);
				userMenu.showUpdatedSuccess();
			} else {
				userMenu.showUserNotFound();
			}

		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}
	}

	private static void doLogin() {
		UserUI userMenu = new UserUI();
		UserService userService = new UserService(new UserDAO(),
				new ShoppingCartDAO());

		String email = userMenu.getEmail();
		String password = userMenu.getPassword();

		try {
			boolean isLoginValid = userService.validateLogin(email, password);
			if (isLoginValid) {
				UserModel user = userService.getUser(email);
				userMenu.showLoginSuccess(user);
			} else {
				userMenu.showLoginFailed();
			}
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}

	}

	private static void searchProductsByCategory() {
		ProductUI productMenu = new ProductUI();
		CategoryService categoryService = new CategoryService(new CategoryDAO());
		ProductService productService = new ProductService(new ProductDAO());

		try {
			int categoryId = productMenu.askCategoryToSearch(categoryService
					.getAllCategories());
			List<ProductModel> products = productService
					.getProductsByCategory(categoryId);
			productMenu.showProductsSearch(products);
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}

	}

	private static void searchProductByName() {
		ProductUI productMenu = new ProductUI();
		ProductService productService = new ProductService(new ProductDAO());

		try {
			String productName = productMenu.askProductName();
			List<ProductModel> products = productService
					.getProductsByName(productName);
			productMenu.showProductsSearch(products);
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}

	}
}
