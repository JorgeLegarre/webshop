package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import se.jiv.webshop.utils.Properties;

public abstract class GeneralDAO {
	static final String JDBC_DRIVER = Properties.INSTANCE.getDBDriver();
	static final String DB_URL = Properties.INSTANCE.getDB();
	static final String DB_USER = Properties.INSTANCE.getDBUser();
	static final String DB_PASSWORD = Properties.INSTANCE.getDBPassword();

	protected Connection getConnection() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}

	protected void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void commit(Connection conn) {
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setString(PreparedStatement pstmt, int pos, String value)
			throws SQLException {
		if (value == null || "".equals(value)) {
			pstmt.setNull(pos, Types.VARCHAR);
		} else {
			pstmt.setString(pos, value);
		}
	}

	public void setInteger(PreparedStatement pstmt, int pos, Integer value)
			throws SQLException {
		if (value == null) {
			pstmt.setNull(pos, Types.INTEGER);
		} else {
			pstmt.setInt(pos, value);
		}
	}

	public void setDouble(PreparedStatement pstmt, int pos, Double value)
			throws SQLException {
		if (value == null) {
			pstmt.setNull(pos, Types.DOUBLE);
		} else {
			pstmt.setDouble(pos, value);
		}
	}

	public int getInt(ResultSet rs, String column) throws SQLException {
		Integer integer = rs.getInt(column);
		return (integer == null) ? -1 : integer;
	}
}
