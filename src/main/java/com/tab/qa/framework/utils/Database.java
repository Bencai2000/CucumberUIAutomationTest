package com.tab.qa.framework.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * <b>Description: 
 * This is a database utility class provides APIs to retrieve/update column data from a given table.
 */
public class Database {
	private static Logger logger = Logger.getLogger(Database.class);
	
	private Connection _con;
	private String _table;
	private String _recordNumber;
	private String _dbUrl = "jdbc:mysql://lo-dev-02:3306/selenium_test_db";
	private String _dbUsername = "selenium";
    private String _dbPassword = "selenium";

	public Database() {
		this._con = connect();
	}
	
	public Database(String table, String recordNumber) {
		this._table = table;
		this._recordNumber = recordNumber;
		this._con = connect();
	}
	
	/**
	 * <b>Description: 
	 * Returns the record(row) number where the cursor is currently set.
	 */
	public String getRecordNumber() {
		logger.info("getRecordNumber()");
		
		return _recordNumber;
	}
	
	/**
	 * <b>Description: 
	 * This method moves the cursor to the specified record.
	 */
	public void setRecordNumber(String recordNumber) {
		logger.info(String.format("setRecordNumber(%s)", recordNumber));
		
		this._recordNumber = recordNumber;
	}
	
	/**
	 * <b>Description: 
	 * Returns the table name that is currently set.
	 */
	public String getTable() {
		logger.info("getTable()");
		
		return _table;
	}
	
	/**
	 * <b>Description: 
	 * Once you have a connection object to the db, you can then call this method to switch to tables
	 */
	public void setTable(String table) {
		logger.info(String.format("setTable(%s)", table));
		
		this._table = table;
	}
	

	/**
	 * <b>Description: 
	 * Returns specified column data of the initialised db table
	 */
	public String data(String columnName) {
		logger.info(String.format("data(%s)", columnName));
		
		String columndata = null;
		try {
			ResultSet result = executeQuery("Select " + columnName + " from " + _table + " where id" + _table + "='" + _recordNumber + "';");
			result.next();
			columndata = result.getString(columnName);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return columndata;
	}
	
	/**
	 * <b>Description: 
	 * Sets or updates the specified column data of the initialised db table
	 */
	public void setData(String columnName, String dataToSet) {
		logger.info(String.format("setData(%s)", dataToSet));
		try {
			executeUpdate("UPDATE " + getTable() + " SET " + columnName + "='" + dataToSet + "' WHERE id" + getTable() + "='" + getRecordNumber() + "';");
		} catch (Throwable t) {
			logger.error(t.getMessage());
		}
		
	}
	
	/**
	 * <b>Description: 
	 * Checks to see if this connection object is still open and closes the connection
	 */
	public void closeConnection() {
		logger.info("closeConnection()");
		
		try {
			if (!_con.isClosed()) {
				_con.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
	    }
	}
	
	/**
	 * <b>Description: 
	 * Returns a connection object to specified db url 
	 */
	private Connection connect() {
		logger.info("connect()");
		
		Connection con = null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");
	        con = DriverManager.getConnection(_dbUrl, _dbUsername, _dbPassword);
		
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return con;
	}
     
	private ResultSet executeQuery(String query) {
		logger.info(String.format("executeStatement(%s)", query));
		
		try {
			PreparedStatement statement = _con.prepareStatement(query);
			return statement.executeQuery();
		} catch (Exception e) {
			logger.error(e.getMessage());
	    }
		return null;
	}
	
	private Object executeUpdate(String query) {
		logger.info(String.format("executeStatement(%s)", query));
		
		try {
			PreparedStatement statement = _con.prepareStatement(query);
			return statement.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
	    }
		return null;
	}
      
}
