package se.jiv.webshop.ui;

import se.jiv.webshop.exception.WebshopAppException;

public final class ExceptionUI {
	public static void printException(WebshopAppException e) {
		System.err.println("Upps!!! Controlled Exception: " + e.getClassName()
				+ "." + e.getActionName() + ": " + e.getMessage());
	}

	public static void printUncontrolledException(Exception e) {
		System.err
				.println("ERROR: An uncontrolled exception happened.\nApplication is going to be closed.\nPlease contact with the administrator of the System.\nThanks and sorry for the inconveniences.");
		e.printStackTrace();
	}
}
