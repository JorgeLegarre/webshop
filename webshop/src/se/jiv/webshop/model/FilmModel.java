package se.jiv.webshop.model;

public class FilmModel extends ProductModel {
	private final String title;
	private final int rating;
	private final String format;
	private final String director;

	public static abstract class Builder<T extends Builder<T>> extends
			ProductModel.Builder<T> {
		// Required parameters
		private final String title;
		private final int rating;
		private String director;

		// Optional parameters
		private String format;

		public Builder(String name, int productType, String title, int rating,
				String director) {
			super(name, productType);
			this.director = director;
			this.title = title;
			this.rating = rating;
			this.director = director;
			format = "";
		}

		public T format(String format) {
			this.format = format;
			return self();
		}

		@Override
		public FilmModel build() {
			return new FilmModel(this);
		}
	}

	private static class Builder2 extends Builder<Builder2> {
		public Builder2(String name, int productType, String title, int rating,
				String director) {
			super(name, productType, title, rating, director);
		}

		@Override
		protected Builder2 self() {
			return this;
		}
	}

	public static Builder<?> builder(String name, int productType,
			String title, int rating, String director) {
		return new Builder2(name, productType, title, rating, director);
	}

	protected FilmModel(Builder<?> builder) {
		super(builder);
		this.title = builder.title;
		this.format = builder.format;
		this.director = builder.director;
		this.rating = builder.rating;
	}

	public String getTitle() {
		return title;
	}

	public String getFormat() {
		return format;
	}

	public String getDirector() {
		return director;
	}

	public int getRating() {
		return rating;
	}

	@Override
	public String toString() {
		return super.toString()
				+ String.format(" - Film: %s by %s - %s in format %s ",
						this.getTitle(), this.getFormat(), this.getDirector(),
						this.getFormat());
	}

	@Override
	public int hashCode() {
		int result = 1;
		result += 37 * this.getTitle().hashCode();
		result += 37 * this.getDirector().hashCode();

		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (other instanceof FilmModel) {
			FilmModel otherBook = (FilmModel) other;
			boolean isSameClass = this.getClass().equals(otherBook.getClass());

			return (this.getTitle().equals(otherBook.getTitle()))
					&& this.getDirector().equals(otherBook.getDirector())
					&& isSameClass;
		}

		return false;
	}
}
