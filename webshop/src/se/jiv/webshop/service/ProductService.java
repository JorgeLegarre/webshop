package se.jiv.webshop.service;

import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.repository.ProductRepository;

public final class ProductService {
	private ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public ProductModel createProduct(ProductModel product)
			throws WebshopAppException {
		return productRepository.createProduct(product);
	}

	public boolean updateProduct(ProductModel product)
			throws WebshopAppException {
		return productRepository.updateProduct(product);
	}

	public boolean deleteProduct(int productId) throws WebshopAppException {
		return productRepository.deleteProduct(productId);
	}

	public List<ProductModel> getProductsByName(String name)
			throws WebshopAppException {
		return productRepository.getProductByName(name);
	}

	public List<ProductModel> getProductsByCost(double cost)
			throws WebshopAppException {
		return productRepository.getProductsByCost(cost);
	}

	public ProductModel getProductById(int id) throws WebshopAppException {
		return productRepository.getProductById(id);
	}

	public List<ProductModel> getAllProducts() throws WebshopAppException {
		return productRepository.getAllProducts();
	}

	public List<ProductModel> getProductsByCategory(int categoryId)
			throws WebshopAppException {
		return productRepository.getProductsByCategory(categoryId);
	}

}
