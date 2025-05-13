package br.com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/empresa";
	 private static final String ROOT_URL = "jdbc:mysql://localhost:3306/";
    private static final String user = "";
    private static final String password = "";
    
    

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, user, password);
    }
    
    	
    public static Connection getConnectionSemBanco() throws SQLException {
        return DriverManager.getConnection(ROOT_URL, user, password);
    }
}
