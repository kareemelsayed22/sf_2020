/**
 * 
 */
package com.ss.sf.lms.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
* Other DAO Objects implement this class to get connected with the database hosted in mySQL
* Credentials to access the database stored in Resources folder. Retrieves data from properties file.
* Creates a connection if there are no instances
* Saves CREATE, DELETE AND UPDATE Queries to execute
* Take the data from Result-set and put it in data structure to use.
*/
public abstract class BaseDAO<T> {

	public String driver = null;
	public String url = null;
	public String username = null;
	public String password = null;
	private static Connection instance = null;

	
	public void retrieveProperties() throws IOException {

		InputStream input = BaseDAO.class.getClassLoader().getResourceAsStream("Resources/mysql.properties");
		Properties prop = new Properties();
		prop.load(input);
		if (driver == null) {
			driver = prop.getProperty("driver");
		}
		if (url == null) {
			url = prop.getProperty("url");
		}
		if (username == null) {
			username = prop.getProperty("username");
		}
		if (password == null) {
			password = prop.getProperty("password");
		}
		input.close();
	}

	
	public Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
		if (instance == null) {
			this.retrieveProperties();
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		}
		return instance;
	}
	


	
	public void save(String sql, Object[] vals) throws ClassNotFoundException, SQLException, IOException {
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		int count = 1;
		for (Object o : vals) {
			pstmt.setObject(count, o);
			count++;
		}
		pstmt.executeUpdate();
	}

	
	public List<T> read(String sql, Object[] vals) throws ClassNotFoundException, SQLException, IOException {
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		int count = 1;
		if (vals != null) {
			for (Object o : vals) {
				pstmt.setObject(count, o);
				count++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}

	
	abstract List<T> extractData(ResultSet rs) throws SQLException, ClassNotFoundException, IOException;

}