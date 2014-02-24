package se.jiv.webshop.ui;

import java.util.Scanner;

public abstract class GeneralUI {

	protected String readStringWithLabel(String label) {
		System.out.println(label);
		return readString();
	}

	protected String readString() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String value = scanner.nextLine();

		return value;
	}

	protected int readIntWithLabel(String label) {
		System.out.println(label);
		return readInt();
	}

	protected int readInt() {
		while (true) {
			int value = -1;
			try {
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(System.in);
				value = scanner.nextInt();
			} catch (Exception e) {
				System.out
						.println("It have to be an int number. Write it again:");
				continue;
			}

			return value;
		}
	}

	protected double readDoubleWithLabel(String label) {
		System.out.println(label);
		return readDouble();
	}

	protected double readDouble() {
		while (true) {
			double value = -1;
			try {
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(System.in);
				value = scanner.nextDouble();
			} catch (Exception e) {
				System.out
						.println("It have to be a double number (x,x). Write it again:");
				continue;
			}

			return value;
		}
	}

}
