package se.jiv.webshop.ui;

import java.util.ArrayList;
import java.util.List;

import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.model.ProductModel;

public final class ProductUI extends GeneralUI {

	private ProductModel readProduct(int id) {
		boolean manyCategories = true;
		List<Integer> categories = new ArrayList<>();

		int productType = readIntWithLabel("Product type: ");
		String name = readStringWithLabel("Name: ");
		String description = readStringWithLabel("Description: ");
		double cost = readDoubleWithLabel("Cost: ");
		double rrp = readDoubleWithLabel("Rrp: ");
		int categoryId = readIntWithLabel("Category Id: ");
		categories.add(categoryId);

		while (manyCategories) {
			String anwser = readStringWithLabel("Do you want to add another category to this product? y/n");
			if (anwser.toLowerCase().equals("y")) {
				categoryId = readIntWithLabel("Category Id: ");
				categories.add(categoryId);
			} else {
				manyCategories = false;
			}

		}

		return ProductModel.builder(name, productType).description(description)
				.cost(cost).rrp(rrp).categories(categories).id(id).build();
	}

	public ProductModel createProduct() {
		System.out.println("Introduce the information for the new Product: ");
		return readProduct(ProductModel.DEFAULT_PRODUCT_ID);
	}

	public ProductModel updateProduct(int productId) {
		System.out
				.println("Introduce the new information for the product with id: "
						+ productId);
		return readProduct(productId);
	}

	public int askCategoryToSearch(List<CategoryModel> categories) {

		System.out.println("What category do you want to search books: ");

		for (CategoryModel category : categories) {
			System.out.println(category.getId() + ". " + category.getName());
		}

		return readInt();
	}

	public void showProductsSearch(List<ProductModel> products) {
		System.out.println();
		if (products.size() == 0) {
			System.out.println("We don't found any product in the search.");
		} else {
			System.out.println("Products finded in the search: ");
			for (ProductModel product : products) {
				System.out.println(product);
			}
		}
	}

	public int askProductId() {
		return readIntWithLabel("Please enter the id of the product: ");
	}

	public String askProductName() {
		return readStringWithLabel("Please enter the name of the product that you want to search: ");
	}

	public void showCreateSuccess(ProductModel newProduct) {
		System.out.println("You have created a new product:");
		System.out.println(newProduct);
	}

	public void showUpdatedSuccess() {
		System.out.println("You have updated your product");
	}

	public void showProductNotFound() {
		System.out.println("Product not found.");
	}

}
