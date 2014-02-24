package se.jiv.webshop.model;

import java.util.List;

public class BookModel extends ProductModel {
	private final String title;
	private final int isbn;
	private final int pages;
	private final String publisher;
	private final String format;
	private final List<Integer> authors;

	public static abstract class Builder<T extends Builder<T>> extends
			ProductModel.Builder<T> {
		// Required parameters
		private final String title;
		private final int isbn;
		private final int pages;
		private final String publisher;
		private final List<Integer> authors;

		// Optional parameters
		private String format;

		public Builder(String name, int productType, List<Integer> authors,
				String title, int isbn, int pages, String publisher) {
			super(name, productType);
			this.title = title;
			this.authors = authors;
			this.isbn = isbn;
			this.pages = pages;
			this.publisher = publisher;
			format = "";
		}

		public T format(String format) {
			this.format = format;
			return self();
		}

		@Override
		public BookModel build() {
			return new BookModel(this);
		}
	}

	private static class Builder2 extends Builder<Builder2> {
		public Builder2(String name, int productType, List<Integer> authors,
				String title, int isbn, int pages, String publisher) {
			super(name, productType, authors, title, isbn, pages, publisher);
		}

		@Override
		protected Builder2 self() {
			return this;
		}
	}

	public static Builder<?> builder(String name, int productType,
			List<Integer> authors, String title, int isbn, int pages,
			String publisher) {
		return new Builder2(name, productType, authors, title, isbn, pages,
				publisher);
	}

	protected BookModel(Builder<?> builder) {
		super(builder);
		this.title = builder.title;
		this.isbn = builder.isbn;
		this.pages = builder.pages;
		this.publisher = builder.publisher;
		this.format = builder.format;
		this.authors = builder.authors;
	}

	public String getTitle() {
		return title;
	}

	public int getIsbn() {
		return isbn;
	}

	public int getPages() {
		return pages;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getFormat() {
		return format;
	}

	public List<Integer> getAuthors() {
		return authors;
	}

	@Override
	public String toString() {
		return super.toString()
				+ String.format(
						" - Book: %s by %s | %s | published by %s - format: %s, pages %s",
						this.title, this.authors, this.isbn, this.publisher,
						this.format, this.pages);
	}

	@Override
	public int hashCode() {
		int result = 1;
		result += 37 * this.getIsbn();

		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (other instanceof BookModel) {
			BookModel otherBook = (BookModel) other;
			boolean isSameClass = this.getClass().equals(otherBook.getClass());

			return (this.isbn == otherBook.isbn) && isSameClass;
		}

		return false;
	}
}
