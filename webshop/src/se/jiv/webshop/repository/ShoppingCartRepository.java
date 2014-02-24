package se.jiv.webshop.repository;

import java.util.Map;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;

public interface ShoppingCartRepository {
	public void addProductToCart(UserModel user, int productId, int quantity)
			throws WebshopAppException;

	public void removeProductFromCart(UserModel user, int productId)
			throws WebshopAppException;

	public void updateCart(UserModel user, int productId, int quantity)
			throws WebshopAppException;

	public Map<Integer, Integer> getShoppingCart(UserModel user)
			throws WebshopAppException;

	public void resetShoppingCart(UserModel user) throws WebshopAppException;

}
