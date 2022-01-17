package org.mk.postgresql.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCPostgreSQLConnection {
	
	    private final static String url = "jdbc:postgresql://localhost/springbank";
	    private final static String user = "user";
	    private final static String password = "0rd";

	    private static final String QUERY = "select * from bank_account where id =?";
	    private static final String SELECT_ALL_QUERY = "select * from bank_account";
	    

	    public void getUserById() {
	        // using try-with-resources to avoid closing resources (boiler plate
	        // code)
	    	
	        // Step 1: Establishing a Connection
	        try (
	        		Connection connection = DriverManager.getConnection(url, user, password);
	        		
	        	// Step 2:Create a statement using connection object
	                PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
	        		) {
	            preparedStatement.setInt(1, 5);
	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("full_name");
	                String balance = rs.getString("balance");
	                System.out.println(id + "," + name + "," + balance + "");
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	    }

	    public void getAllUsers() {
	        // using try-with-resources to avoid closing resources (boiler plate
	        // code)

	        // Step 1: Establishing a Connection
	        try (Connection connection = DriverManager.getConnection(url, user, password);
	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);) {
	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("full_name");
	                String balance = rs.getString("balance");
	                System.out.println(id + "," + name + "," + balance + "");
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	    }
	    //Insert Query
	    
	    private static final String INSERT_USERS_SQL = "INSERT INTO bank_account" +
	            "  (id, full_name, balance) VALUES " +
	            " (?, ?, ?);";
	    
	   
	    
	    //Function Insert:
	    public void insertRecord() throws SQLException {
	        System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
	        try (Connection connection = DriverManager.getConnection(url, user, password);

	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
	            preparedStatement.setInt(1, 1);
	            preparedStatement.setString(2, "Tony");
	            preparedStatement.setDouble(3, 6000);

	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {

	            // print SQL exception information
	            printSQLException(e);
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	    
	    //Update Record
	    
	    private static final String UPDATE_USERS_SQL = "update bank_account set full_name = ? where id = ?;";
	    
	    public void updateRecord() throws SQLException {
	        System.out.println(UPDATE_USERS_SQL);
	        // Step 1: Establishing a Connection
	        try (Connection connection = DriverManager.getConnection(url, user, password);

	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
	            preparedStatement.setString(1, "Michel");
	            preparedStatement.setInt(2, 1);

	            // Step 3: Execute the query or update query
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {

	            // print SQL exception information
	            printSQLException(e);
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	    
	    //Delete User:
	    private static final String DELETE_USERS_SQL = "delete from bank_account where id = ?;";
	    
	    public void deleteRecord() throws SQLException {
	        System.out.println(DELETE_USERS_SQL);

	        // Step 1: Establishing a Connection
	        try (Connection connection = DriverManager.getConnection(url, user, password);

	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL);) {
	            preparedStatement.setInt(1, 1);

	            // Step 3: Execute the query or update query
	            int result = preparedStatement.executeUpdate();
	            System.out.println("Number of records affected :: " + result);
	        } catch (SQLException e) {

	            // print SQL exception information
	            printSQLException(e);
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }



	    public static void main(String[] args) throws SQLException {
	    	JDBCPostgreSQLConnection example = new JDBCPostgreSQLConnection();
	        
	        example.insertRecord();
	        example.updateRecord();
	        
	        example.getUserById();
	        example.getAllUsers();
	        
	        example.deleteRecord();
	    }
	    
	  

	    public static void printSQLException(SQLException ex) {
	        for (Throwable e: ex) {
	            if (e instanceof SQLException) {
	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
	                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
	                System.err.println("Message: " + e.getMessage());
	                Throwable t = ex.getCause();
	                while (t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
	        }
	    }
	}
