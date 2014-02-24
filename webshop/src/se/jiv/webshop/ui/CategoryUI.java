package se.jiv.webshop.ui;

import se.jiv.webshop.model.CategoryModel;

public final class CategoryUI extends GeneralUI {
	public CategoryModel askAddCategory() {
		System.out
				.println("Please enter the name of the category you want to create: ");
		String categoryName = readString();
		System.out
				.println("Please enter the id nr of the of the staff member responsible for \nthe new category: ");
		int categoryId = readInt();

		CategoryModel newCategory = new CategoryModel(categoryName, categoryId);

		return newCategory;
	}

	public CategoryModel askUpdateCategory(int categoryId) {
		System.out.println("Please enter the new name of the category: ");
		String name = readString();
		System.out
				.println("Please enter the new id of the staff member responsible: ");
		int staffId = readInt();

		CategoryModel newCategory = new CategoryModel(categoryId, name, staffId);

		return newCategory;
	}

	public void showCreatedSuccess(CategoryModel newCategory) {
		System.out.println("You have created a new Cagegory:");
		System.out.println(newCategory);
	}

	public void showUpdatedSuccess() {
		System.out.println("You have updated your category.");
	}

	public int askForCategoryId() {
		System.out.println("Enter the id of the category: ");
		return readInt();
	}

	public void showCategoryNotFound() {
		System.out.println("Category not found.");
	}

}
