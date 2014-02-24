package se.jiv.webshop.model;

public class AuthorModel {
	public static final int DEFAULT_ID = -1;

	private final int id;
	private final String firstname;
	private final String lastname;
	private final String dob;
	private final String country;

	public static class Builder {
		// required fields
		private final int id;
		private final String firstname;
		private final String lastname;

		// optional fields
		private String dob;
		private String country;

		public Builder(int id, String firstname, String lastname) {
			this.id = id;
			this.firstname = firstname;
			this.lastname = lastname;
		}

		public Builder dob(String dob) {
			this.dob = dob;
			return this;
		}

		public Builder country(String country) {
			this.country = country;
			return this;
		}

		public AuthorModel build() {
			return new AuthorModel(this);
		}
	}

	private AuthorModel(Builder builder) {
		this.id = builder.id;
		this.firstname = builder.firstname;
		this.lastname = builder.lastname;
		this.dob = builder.dob;
		this.country = builder.country;

	}

	public int getId() {
		return id;
	}

	public String getDob() {
		return dob;
	}

	public String getCountry() {
		return country;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	@Override
	public String toString() {
		return String.format("Author name: %s %s, Dob: %s, from: %s \n",
				this.getFirstname(), this.getLastname(), this.getDob(),
				this.getCountry());
	}

	@Override
	public int hashCode() {
		return 37 * id;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (other instanceof AuthorModel) {
			AuthorModel otherAuthor = (AuthorModel) other;
			boolean isSameClass = this.getClass()
					.equals(otherAuthor.getClass());

			return (this.id == otherAuthor.id) && isSameClass;
		}

		return false;
	}

}
