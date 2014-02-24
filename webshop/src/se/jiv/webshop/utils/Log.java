package se.jiv.webshop.utils;

import org.apache.log4j.Logger;

import se.jiv.webshop.exception.WebshopAppException;

public class Log {
	public static void logOut(Logger LOGGER, Object object,
			String functionName, String... args) {

		StringBuilder builder = new StringBuilder(object.getClass()
				.getSimpleName());
		builder.append(".");
		builder.append(functionName);
		builder.append(" - ");
		for (String arg : args) {
			builder.append(arg);
			builder.append(" ");
		}

		LOGGER.trace(builder.toString());
	}

	public static void logOutWAException(Logger LOGGER, WebshopAppException e) {
		LOGGER.error(e);
	}
}
