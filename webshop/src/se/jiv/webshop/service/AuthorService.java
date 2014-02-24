package se.jiv.webshop.service;

import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.AuthorModel;
import se.jiv.webshop.repository.AuthorRepository;

public class AuthorService {
	private final AuthorRepository authorRepository;

	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	public void addAuthor(AuthorModel author) throws WebshopAppException {
		authorRepository.addAuthor(author);
	}

	public void updateAuthor(AuthorModel author) throws WebshopAppException {
		authorRepository.updateAuthor(author);
	}

	public void deleteAuthor(int id) throws WebshopAppException {
		authorRepository.deleteAuthor(id);
	}

	public AuthorModel getAuthor(int id) throws WebshopAppException {
		return authorRepository.getAuthor(id);
	}

	public List<AuthorModel> getAuthorsByName(String name)
			throws WebshopAppException {
		return authorRepository.getAuthorsByName(name);
	}

	public List<AuthorModel> getAllAuthors() throws WebshopAppException {
		return authorRepository.getAllAuthors();
	}
}
