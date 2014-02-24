package se.jiv.webshop.repository;

import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.AuthorModel;

public interface AuthorRepository {
	public AuthorModel addAuthor(AuthorModel author) throws WebshopAppException;

	public void updateAuthor(AuthorModel author) throws WebshopAppException;

	public void deleteAuthor(int id) throws WebshopAppException;

	public AuthorModel getAuthor(int id) throws WebshopAppException;

	public List<AuthorModel> getAuthorsByName(String name)
			throws WebshopAppException;

	public List<AuthorModel> getAllAuthors() throws WebshopAppException;

}
