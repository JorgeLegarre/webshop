package se.jiv.webshop.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DevDBConfig {
	// DEVELOP DB - Hardcode, we don't use DB properties to avoid test in
	// production
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost/webshop";
	public static final String DB_USER = "javamaster";
	public static final String DB_PASSWORD = "java";

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}

}
