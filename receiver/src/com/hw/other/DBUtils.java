package com.hw.other;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

public class DBUtils {
	
	private static DataSource ds = null;
	
	static {
		HikariDataSource hds = new HikariDataSource();
		hds.setJdbcUrl("jdbc:mysql://123.57.157.230:3319/time");
		hds.setDriverClassName("com.mysql.jdbc.Driver");
		hds.setUsername("time");
		hds.setPassword("fm4649.");
		hds.setMaximumPoolSize(10);
		ds = hds;
	}
	
	public static Connection getConn() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) throws SQLException {
		Connection conn = getConn();
		System.out.println(conn);
		conn.close();
	}
}
