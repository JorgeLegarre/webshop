package se.jiv.webshop.repository;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.BookModel;
import se.jiv.webshop.model.ProductModel;

public interface BookRepository {
	public ProductModel createBook(BookModel book) throws WebshopAppException;
}
