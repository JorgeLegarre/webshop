package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.BookModel;
import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.repository.BookRepository;

public final class BookDAO extends ProductDAO implements BookRepository {
	private static final Logger LOGGER = Logger.getLogger(BookDAO.class
			.getName());

	@Override
	public ProductModel createBook(BookModel book) throws WebshopAppException {
		int generatedId = ProductModel.DEFAULT_PRODUCT_ID;

		if (isValidBook(book, "CREATE_BOOK")) {

			try (Connection conn = getConnection()) {

				conn.setAutoCommit(false);

				try {
					generatedId = super.insertProduct(conn, book);

					insertBook(conn, book, generatedId);

					conn.commit();
				} catch (SQLException e) {
					rollback(conn);
					throw e;
				}

			} catch (SQLException e2) {
				WebshopAppException excep = new WebshopAppException(e2, this
						.getClass().getSimpleName(), "CREATE_BOOK");
				LOGGER.error(excep);
				throw excep;
			}

		}
		return null;
	}

	private void insertBook(Connection conn, BookModel book, int id)
			throws SQLException {
		String sql = "INSERT INTO book(product_id,title,format,isbn, pages, publisher) VALUES(?,?,?,?,?,?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			prepareStatementFromModel(pstmt, book, id);

			pstmt.executeUpdate();

			insertAuthors(conn, id, book.getAuthors());
		}
	}

	private void insertAuthors(Connection conn, int id, List<Integer> authors)
			throws SQLException {

		String sql = "INSERT INTO book_author(product_id,author_id) VALUES(?,?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			for (int author_id : authors) {
				setInteger(pstmt, 1, id);
				setInteger(pstmt, 2, author_id);

				pstmt.addBatch();
			}
			pstmt.executeBatch();
		}

	}

	private void prepareStatementFromModel(PreparedStatement pstmt,
			BookModel book, int id) throws SQLException {
		setInteger(pstmt, 1, book.getId());
		setString(pstmt, 2, book.getTitle());
		setString(pstmt, 3, book.getFormat());
		setInteger(pstmt, 4, book.getIsbn());
		setInteger(pstmt, 5, book.getPages());
		setString(pstmt, 6, book.getPublisher());

	}

	private boolean isValidBook(BookModel book, String functionName)
			throws WebshopAppException {
		WebshopAppException excep = new WebshopAppException(
				"book can not be null", this.getClass().getSimpleName(),
				functionName);
		LOGGER.error(excep);
		throw excep;
	}

}
