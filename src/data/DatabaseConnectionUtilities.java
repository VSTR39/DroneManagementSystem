package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;

public class DatabaseConnectionUtilities {
	private static String mURL;
	private static String mUser;
	private static String mPassword;

	public DatabaseConnectionUtilities() {
		mURL = "jdbc:mysql//localhost:3306/drone_management_system";
		mUser = "secretuser";
		mPassword = "verysecretpassword";
	}

	public DatabaseConnectionUtilities(String URL, String user, String password) {
		mURL = URL;
		mUser = user;
		mPassword = password;
	}

	public Connection connect() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(mURL, mUser, mPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;
	}

	public LinkedList<Product> getAllAvalailableProducts() throws SQLException {
		LinkedList<Product> result = null;
		String query = "SELECT * FROM products ";
		int i = 0;
		Statement statement = null;
		ResultSet resultSet = null;
		Connection link = this.connect();
		statement = link.createStatement();
		resultSet = statement.executeQuery(query);
		if (resultSet == null) {
			statement.close();
			resultSet.close();
			link.close();
			return null;
		}

		else {
			while (resultSet.next()) {
				result.add(i,
						new Product(resultSet.getInt("id"), resultSet.getInt("warehouse_id"),
								resultSet.getString("name"), resultSet.getInt("quantity"),
								resultSet.getDouble("weight_per_quantity")));
				i++;
			}
		}
		statement.close();
		resultSet.close();
		link.close();
		return result;
	}

	public void updateProducts(int supplyWarehouseID, String supplyProductName, double supplyProductWeight,
			int supplyProductQuantity) throws SQLException, DatabaseConnectionUtilitiesException {
		String query = "UPDATE products SET warehouse_id = ? , name = ? , quantity , weight_per_quantity ";
		PreparedStatement statement = null;
		Connection link = this.connect();
		statement = link.prepareStatement(query);
		statement.setInt(1, supplyWarehouseID);
		statement.setString(2, supplyProductName);
		statement.setInt(3, supplyProductQuantity);
		statement.setDouble(4, supplyProductWeight);
		int rowsInserted = statement.executeUpdate();
		if (!(rowsInserted != 1)) {
			throw new DatabaseConnectionUtilitiesException();
		}
	}

	public void insertSupplyRequest(Timestamp supplyRequestTimestamp, String string)
			throws SQLException, DatabaseConnectionUtilitiesException {
		String query = "INSERT INTO requests_log (request_timestamp,request_type)" + " VALUES (?,?) ";
		PreparedStatement statement = null;
		Connection link = this.connect();
		statement = link.prepareStatement(query);
		statement.setTimestamp(1, supplyRequestTimestamp);
		statement.setString(2, string);
		int rowsInserted = statement.executeUpdate();
		if (!(rowsInserted != 1)) {
			throw new DatabaseConnectionUtilitiesException();
		}
	}

	public LinkedList<Integer> getAllWarehouseIDs() throws SQLException {
		/**
		 * This method returns a list of all available warehouses identified by
		 * their unique IDs.
		 */
		String query = "SELECT id FROM warehouses ";
		int i = 0;
		LinkedList<Integer> result = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Connection link = this.connect();
		statement = link.createStatement();
		resultSet = statement.executeQuery(query);
		if (resultSet == null) {
			statement.close();
			resultSet.close();
			link.close();
			return null;
		}

		else {
			while (resultSet.next()) {
				result.add(i, resultSet.getInt("id"));
				i++;
			}
		}
		return result;
	}

	public void insertProduct(int supplyWarehouseID, String supplyProductName, double supplyProductWeight,
			int supplyProductQuantity) throws SQLException, DatabaseConnectionUtilitiesException {
		String query = "INSERT INTO products (warehouse_id,name,quantity,weight_per_quantity)" + " VALUES (?,?,?,?) ";
		PreparedStatement statement = null;
		Connection link = this.connect();
		statement = link.prepareStatement(query);
		statement.setInt(1, supplyWarehouseID);
		statement.setString(2, supplyProductName);
		statement.setInt(3, supplyProductQuantity);
		statement.setDouble(4, supplyProductWeight);
		int rowsInserted = statement.executeUpdate();
		if (!(rowsInserted != 1)) {
			throw new DatabaseConnectionUtilitiesException();
		}
	}

}
