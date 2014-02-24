package se.jiv.webshop.repository;

import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.CategoryModel;

public interface CategoryRepository {

	public CategoryModel addCategory(CategoryModel category)
			throws WebshopAppException;

	public boolean updateCategory(CategoryModel category)
			throws WebshopAppException;

	public CategoryModel getCategory(int id) throws WebshopAppException;

	public CategoryModel searchCategoryByName(String name)
			throws WebshopAppException;

	public List<CategoryModel> getAllCategories() throws WebshopAppException;

	public boolean deleteCategory(int id) throws WebshopAppException;

}
