package org.EcoMotto.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.EcoMotto.server.services.HiveService;

/**
 * Example code
 *
 */
public class App {
	public static void main(String[] args) {
		try {
			HiveService hive = new HiveService("localhost", "default", "hzou");
			hive.createTable("poo", true);
			ResultSet res = hive.showTables();
			while (res.next()) {
				System.out.println(res.getString(1));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
