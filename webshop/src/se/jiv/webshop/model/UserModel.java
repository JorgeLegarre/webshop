package se.jiv.webshop.model;

public final class UserModel {
	private final String email;
	private final String password;
	private final String firstname;
	private final String lastname;
	private final String dob;
	private final String telephone;
	private final String address1;
	private final String address2;
	private final String town;
	private final String postcode;

	public static class Builder {
		// required fields
		private final String email;
		private final String password;
		private final String firstname;
		private final String lastname;
		private final String address1;
		private final String town;
		private final String postcode;

		// optional fields
		private String dob;
		private String telephone;
		private String address2;

		public Builder(String email, String password, String firstname,
				String lastname, String address1, String town, String postcode) {
			this.email = email;
			this.password = password;
			this.firstname = firstname;
			this.lastname = lastname;
			this.address1 = address1;
			this.town = town;
			this.postcode = postcode;
		}

		public Builder dob(String dob) {
			this.dob = dob;
			return this;
		}

		public Builder telephone(String telephone) {
			this.telephone = telephone;
			return this;
		}

		public Builder address2(String address2) {
			this.address2 = address2;
			return this;
		}

		public UserModel build() {
			return new UserModel(this);
		}
	}

	private UserModel(Builder builder) {
		this.email = builder.email;
		this.password = builder.password;
		this.firstname = builder.firstname;
		this.lastname = builder.lastname;
		this.dob = builder.dob;
		this.telephone = builder.telephone;
		this.address1 = builder.address1;
		this.address2 = builder.address2;
		this.town = builder.town;
		this.postcode = builder.postcode;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getDob() {
		return dob;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getTown() {
		return town;
	}

	public String getPostcode() {
		return postcode;
	}

	@Override
	public String toString() {
		return String
				.format("User: %s Firstname: %s Lastname: %s Dob: %s Telephone: %s Address: %s %s %s %s ",
						getEmail(), getFirstname(), getLastname(), getDob(),
						getTelephone(), getAddress1(), getAddress2(),
						getTown(), getPostcode());
	}

	@Override
	public int hashCode() {
		int result = 1;
		result += 37 * this.email.hashCode();
		result += 37 * this.getClass().hashCode();

		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (other instanceof UserModel) {
			UserModel otherUser = (UserModel) other;
			boolean isSameClass = this.getClass().equals(otherUser.getClass());

			return (email.equals(otherUser.email)) && isSameClass;
		}

		return false;
	}

}
