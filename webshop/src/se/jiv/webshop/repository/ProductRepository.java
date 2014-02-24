package se.jiv.webshop.repository;

import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.*;

public interface ProductRepository {
	public ProductModel createProduct(ProductModel product)
			throws WebshopAppException;

	public boolean updateProduct(ProductModel product)
			throws WebshopAppException;

	public boolean deleteProduct(int productId) throws WebshopAppException;

	public List<Integer> getCategoriesOfProduct(int productId)
			throws WebshopAppException;

	public List<ProductModel> getProductByName(String name)
			throws WebshopAppException;

	public List<ProductModel> getProductsByCost(double cost)
			throws WebshopAppException;

	public List<ProductModel> getProductsByCategory(int categoryId)
			throws WebshopAppException;

	public ProductModel getProductById(int id) throws WebshopAppException;

	public List<ProductModel> getAllProducts() throws WebshopAppException;

}
