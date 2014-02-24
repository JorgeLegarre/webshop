package se.jiv.webshop.ui;

public final class MainMenuUI extends GeneralUI {

	public int firstMenu() {
		System.out
				.println("\n-------------------------------------------------");
		System.out.println("Welcome!");
		System.out.println();
		System.out.println("These are your options:");
		System.out.println("1. Create Info");
		System.out.println("2. Edit Info");
		System.out.println("3. Log in");
		System.out.println("4. Search products by category");
		System.out.println("5. Search products by name");
		System.out.println("0. Exit");
		System.out.print("\nPlease introduce an option: ");

		return readInt();

	}

	public int createInformation() {
		System.out.println("What do you want to create?");
		System.out.println("1. Create a Product.");
		System.out.println("2. Create a Category.");
		System.out.println("3. Create an User.");
		System.out.println("0. Exit");

		return readInt();
	}

	public int editInformation() {
		System.out.println("What do you want to edit?");
		System.out.println("1. Edit Products.");
		System.out.println("2. Edit Categories.");
		System.out.println("3. Edit Users.");
		System.out.println("0. Exit");

		return readInt();
	}

	public void showOptionNotValid() {
		System.out.println("\nThe introduced option is not valid.");
	}

}
