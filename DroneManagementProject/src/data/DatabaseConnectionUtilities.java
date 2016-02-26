package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
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
		/**
		 * This method returns a list of all products stored in a given warehouse 
		 */
		LinkedList<Product> result = new LinkedList<Product>();
		String query = "SELECT * FROM products ";//query that extracts the content of table products
		int i = 0;
		Statement statement = null;
		ResultSet resultSet = null;
		Connection link = this.connect(); //establish connection
		statement = link.createStatement();// create query
		resultSet = statement.executeQuery(query);//execute query
		if (resultSet == null) {
			// if the table is empty the result will be EmptySet the equivalent of which is null
			statement.close();
			link.close();
			return null;
		}

		else {
			while (resultSet.next()) {
				//returns the next available row of the table(it starts positioned behind the first row)
				result.add(i,
						new Product(resultSet.getInt("id"),
								resultSet.getInt("warehouse_id"),
								resultSet.getString("name"),
								resultSet.getInt("quantity"),
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
		/**
		 * This method updates the content of table products.
		 * executeUpdate returns the number of rows affected by the change
		 * it there aren't such rows the returned value is 0 which indicates
		 * some kind of a problem with the execution of the update
		 * The expected value of rows to be updated is 1 , beacuse of the design of the DB.		 * 
		 */
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
		/**
		 * This method inserts a new query into table requests_log.
		 * executeUpdate returns the number of rows affected by the change
		 * it there aren't such rows the returned value is 0 which indicates
		 * some kind of a problem with the execution of the insert
		 * The expected value of rows to be updated is 1 , beacuse of the design of the DB.
		 */
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

	@SuppressWarnings("null")
	public LinkedList<Integer> getAllWarehouseIDs() throws SQLException {
		/**
		 * This method returns a list of all available warehouses identified by
		 * their unique IDs.
		 */
		String query = "SELECT id FROM warehouses ";
		int i = 0;
		LinkedList<Integer> result = new LinkedList<Integer>();
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
		/**
		 * This method inserts a new query into table products.
		 * executeUpdate returns the number of rows affected by the change
		 * it there aren't such rows the returned value is 0 which indicates
		 * some kind of a problem with the execution of the insert
		 */
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

	public HashMap<Integer, Location> getLocations(String query) throws SQLException {
		//String query = "SELECT location.id,warehouses.id,x_coordinate,y_coordinate FROM locations JOIN warehouses ON locations.id = (SELECT location_id FROM warehouses)";
		/**
		 * This method creates a HashMap that maps id of a warehouse to a certain location.
		 * The key of the pair is the warehouse id and the value is an object of class location,
		 * that stores the corresponding location extracted from the DB.
		 */
		HashMap<Integer, Location> result = new HashMap<Integer,Location>();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection link = this.connect();
		statement = link.createStatement();
		resultSet = statement.executeQuery(query);
		if (resultSet == null) {
			statement.close();
			link.close();
			return null;
		} else {
			while (resultSet.next()) {
				result.put(resultSet.getInt(2), new Location(resultSet.getInt(1), resultSet.getDouble("x_coordinate"),
						resultSet.getDouble("y_coordinate")));
			}
		}
		return result;
	}

	@SuppressWarnings("null")
	public LinkedList<Drone> getAllAvailableDrones() throws SQLException {
		/**
		 * This method creats a list storing all the information extracted from table drones
		 */
		String query = "SELECT * FROM drones";
		LinkedList<Drone> result = new LinkedList<Drone>();
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
		} else {
			while (resultSet.next()) {
				result.add(new Drone(resultSet.getInt("id"),
									 resultSet.getInt("location_id"),
									 resultSet.getDouble("battery"),
									 resultSet.getDouble("capacity"),
									 resultSet.getDouble("charging_rate")));
			}
		}
		return result;
	}

	public void updateRequestsLog(Timestamp deliveryTimestamp, String string) {
		//TO DO
		/** This method 
		 * 
		 */
	}

	public void updateDrone(double fuelBurned) {
		//TO DO
		/**
		 * This method updates the battery of a given drone.
		 */
	}

}
