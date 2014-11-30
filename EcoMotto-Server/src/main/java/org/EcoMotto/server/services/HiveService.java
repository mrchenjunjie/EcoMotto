package org.EcoMotto.server.services;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

/**
 * JDBC with hive service
 * @author hzou
 *
 */
public class HiveService {
	final static String DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";
	private String host;
	private int port;
	private String dbname;
	private String username;
	private Statement stmt;

	/**
	 * Hive Service Constructor
	 * @param host
	 * @param port
	 * @param dbname
	 * @param username
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public HiveService(String host, int port, String dbname, String username) throws SQLException, ClassNotFoundException {
		super();
		this.host = host;
		this.port = port;
		this.dbname = dbname;
		this.username = username;

		Class.forName(DRIVER_NAME);
		String connection = String.format("jdbc:hive2://%s:%d/%s", this.host, this.port, this.dbname);
		Connection con = DriverManager.getConnection(connection, this.username, "");
		this.stmt = con.createStatement();
	}
	/**
	 * Hive Service Constructor with default port number=10000
	 * @param host
	 * @param dbname
	 * @param username
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public HiveService(String host, String dbname, String username) throws SQLException, ClassNotFoundException {
		super();
		this.host = host;
		this.port = 10000;
		this.dbname = dbname;
		this.username = username;

		Class.forName(DRIVER_NAME);
		String connection = String.format("jdbc:hive2://%s:%d/%s", this.host, this.port, this.dbname);
		Connection con = DriverManager.getConnection(connection, this.username, "");
		this.stmt = con.createStatement();
	}
	/**
	 * create table with given table name
	 * @param tableName
	 * @param dropIfExist
	 * @throws SQLException
	 */
	public boolean createTable(String tableName, boolean dropIfExist) throws SQLException {
		String sql = String.format("create table %s (key int, value string)", tableName);
		if (dropIfExist) {
			stmt.execute("drop table if exists " + tableName);
		}
		return this.stmt.execute(sql);
	}
	/**
	 * show all the tables
	 * @return
	 * @throws SQLException
	 */
	public ResultSet showTables() throws SQLException {
		String sql = "show tables";
		return this.stmt.executeQuery(sql);
	}
	/**
	 * describe the table with given table name
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public ResultSet describleTable(String tableName) throws SQLException {
		String sql = String.format("describe %s", tableName);
		return this.stmt.executeQuery(sql);
	}

}
