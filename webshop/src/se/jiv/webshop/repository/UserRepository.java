package se.jiv.webshop.repository;

import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;

public interface UserRepository {
	public void addUser(UserModel user) throws WebshopAppException;

	public void updateUser(UserModel user) throws WebshopAppException;

	public void deleteUser(UserModel user) throws WebshopAppException;

	public UserModel getUser(String email) throws WebshopAppException;

	public List<UserModel> getAllUsers() throws WebshopAppException;

	public boolean validateLogin(String email, String password)
			throws WebshopAppException;

}
