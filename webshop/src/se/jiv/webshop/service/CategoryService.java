package se.jiv.webshop.service;

import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.repository.CategoryRepository;

public final class CategoryService {
	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public CategoryModel addCategory(CategoryModel category)
			throws WebshopAppException {
		return categoryRepository.addCategory(category);
	}

	public boolean updateCategory(CategoryModel category)
			throws WebshopAppException {
		return categoryRepository.updateCategory(category);
	}

	public CategoryModel getCategory(int id) throws WebshopAppException {
		return categoryRepository.getCategory(id);
	}

	public CategoryModel searchCategoryByName(String name)
			throws WebshopAppException {
		return categoryRepository.searchCategoryByName(name);
	}

	public List<CategoryModel> getAllCategories() throws WebshopAppException {
		return categoryRepository.getAllCategories();
	}

	public boolean deleteCategory(int id) throws WebshopAppException {
		return categoryRepository.deleteCategory(id);
	}
}
