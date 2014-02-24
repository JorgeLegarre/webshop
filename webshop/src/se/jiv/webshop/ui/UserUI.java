package se.jiv.webshop.ui;

import se.jiv.webshop.model.UserModel;

public final class UserUI extends GeneralUI {
	public UserModel addUser() {
		String email = null;
		String password = null;
		String firstname = null;
		String lastname = null;
		String dob = null;
		String telephone = null;
		String address1 = null;
		String address2 = null;
		String town = null;
		String postcode = null;
		boolean correctDate = false;
		UserModel user = null;

		System.out.println("Enter the firstname of the new user: ");
		firstname = readString();
		System.out.println("Enter the lastname of the new user: ");
		lastname = readString();
		do {
			System.out.println("Enter the email of the new user: ");
			email = readString();
		} while ("".equals(email) || (email == null));
		System.out.println("Enter the password of the new user: ");
		password = readString();
		System.out
				.println("Enter the date of birth of the new user: (yyyy-mm-dd) ");
		do {
			dob = readString();
			if (dob.matches("\\d{4}-\\d{2}-\\d{2}")) {
				correctDate = true;
			} else {
				System.out
						.println("Please use the format: yyyy-mm-dd - Your input was: "
								+ dob);
			}
		} while (correctDate == false);

		System.out.println("Enter a telephone number of the new user: ");
		telephone = readString();
		System.out.println("Enter an address of the new user:");
		address1 = readString();
		System.out.println("Enter a C/O of the new user:");
		address2 = readString();
		System.out.println("Enter a town of the new user:");
		town = readString();
		System.out.println("Enter a postcode of the new user:");
		postcode = readString();

		user = getUserModel(email, password, firstname, lastname, dob,
				telephone, address1, address2, town, postcode);

		return user;
	}

	private UserModel getUserModel(String email, String password,
			String firstname, String lastname, String dob, String telephone,
			String address1, String address2, String town, String postcode) {

		return new UserModel.Builder(email, password, firstname, lastname,
				address1, town, postcode).address2(address2).dob(dob)
				.telephone(telephone).build();
	}

	public String askForEmail() {
		String email;
		System.out
				.println("Update user \n Enter the email address of the user to update");
		email = readString();
		return email;
	}

	public UserModel updateUser(UserModel user) {
		String email = user.getEmail();
		String password = user.getPassword();
		String firstname = user.getFirstname();
		String lastname = user.getLastname();
		String dob = user.getDob();
		String telephone = user.getTelephone();
		String address1 = user.getAddress1();
		String address2 = user.getAddress2();
		String town = user.getTown();
		String postcode = user.getPostcode();
		int selectedUpdate = 0;
		boolean correctDate = false;
		boolean updateDone = false;
		UserModel newUser;

		System.out.println("Updating: " + user.toString());
		do {
			System.out.println("Fields to update: \n" + "1. Firstname \n"
					+ "2. Lastname \n" + "3. password \n"
					+ "4. date of birth \n" + "5. telephone number \n"
					+ "6. address \n" + "7. town \n" + "8. postcode \n");
			do {
				selectedUpdate = readInt();
			} while (selectedUpdate <= 0);

			switch (selectedUpdate) {
			case 1:
				firstname = readStringWithLabel("Enter the new firstname of the user");
				break;
			case 2:
				lastname = readStringWithLabel("Enter the new lastname of the user");
				break;
			case 3:
				password = readStringWithLabel("Enter the new password of the user");
				break;
			case 4:
				do {
					dob = readStringWithLabel("Enter the new date of birth of the user (yyyy-mm-dd)");
					if (dob.matches("\\d{4}-\\d{2}-\\d{2}")) {
						correctDate = true;
					} else {
						System.out
								.println("Please use the format: yyyy-mm-dd - Your input was: "
										+ dob);
					}
				} while (correctDate == false);
				break;
			case 5:
				telephone = readStringWithLabel("Enter the new telephone number of the user");
				break;
			case 6:
				address1 = readStringWithLabel("Enter the new address of the user");

				address2 = readStringWithLabel("Enter the new c/o of the user");
				break;
			case 7:
				town = readStringWithLabel("Enter the new town of the user");
				break;
			case 8:
				postcode = readStringWithLabel("Enter the new postcode of the user");
				break;
			}

			String yes = null;
			do {
				System.out.println("Do you want to update further? y/n");
				yes = readString();
			} while (!yes.equals("y") && !yes.equals("n"));
			if (yes.equals("n")) {
				updateDone = true;
			}
		} while (updateDone == false);

		newUser = getUserModel(email, password, firstname, lastname, dob,
				telephone, address1, address2, town, postcode);
		;

		return newUser;
	}

	public String getEmail() {
		return readStringWithLabel("Enter your email:");
	}

	public String getPassword() {
		return readStringWithLabel("Enter your password:");
	}

	public void showLoginSuccess(UserModel user) {
		System.out.println("Login successful! Logged in as: "
				+ user.getFirstname() + " " + user.getLastname());
	}

	public void showLoginFailed() {
		System.out.println("Login failed!");
	}

	public void showUserNotFound() {
		System.out.println("User not found");
	}

	public void showCreatedSuccess(UserModel newUser) {
		System.out.println("You have created a new User:");
		System.out.println(newUser);
	}

	public void showUpdatedSuccess() {
		System.out.println("You have updated your User.");
	}

	public void showExistUser(String email) {
		System.out.println("Sorry!!!email: " + email
				+ " already exist in our system.");
	}

}
