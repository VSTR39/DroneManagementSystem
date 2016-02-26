package logic;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import data.DatabaseConnectionUtilities;
import data.Drone;
import data.Location;
import data.Product;

public class DroneManager implements DroneManagerInterface {
	/**
	 * This Class stores all information necessary for the execution of a delivery request.
	 * In details:
	 *  - mProducts - a list of all Products stored in table products
	 *  - mWarehouseLocations - a HashMap that connects the id of a warehouse to a give location
	 *  - mDrones - a list of all available drones;
	 *  - mDroneLocations - a mapping between all Drones ids and their respective locations
	 *  - mUtility - database connection support;
	 *  - mDeliveryRequest - the request which we will try to execute
	 */
	private LinkedList<Product> mProducts;
	private HashMap<Integer, Location> mWarehouseLocations;
	private LinkedList<Drone> mDrones;
	private HashMap<Integer, Location> mDroneLocations;
	private DatabaseConnectionUtilities mUtility;
	private DeliveryRequest mDeliveryRequest;

	public void initialize() throws SQLException {
		/**
		 * Extracts all the necessary information that is stored in the DB.
		 */
		mUtility = new DatabaseConnectionUtilities();
		mProducts = mUtility.getAllAvalailableProducts();
		mWarehouseLocations = mUtility.getLocations(
				"SELECT location.id,warehouses.id,x_coordinate,y_coordinate "
			  + "FROM locations "
			  + "JOIN warehouses "
			  + "ON locations.id = "
			  + "("
			  + "SELECT location_id "
			  + "FROM warehouses"
			  + ")");
		mDrones = mUtility.getAllAvailableDrones();
		mDroneLocations = mUtility.getLocations(
				"SELECT locations.id,drones.id,x_coordinate,y_coordinate "
				+ "FROM locations "
				+ "JOIN drones "
				+ "ON locations.id="
				+ "("
				+ "SELECT location_id "
				+ "FROM drones"
				+ ")");
	}

	public DroneManager(DeliveryRequest deliveryRequest) throws SQLException {
		initialize();
		mDeliveryRequest = deliveryRequest;
	}

	public void makeDelivery() {
		/**
		 * updates tables requests_log and drones
		 * recording the executed request and the change in the drone's battery
		 */
		int i = 0;
		Drone drone = selectDrone();
		double distance;
		int dronesKey;
		int warehousesKey;
		LinkedList<Drone> drones = selectDrones();
		if (isProductAvailable() && drone != null) {
			/*if a product is available and a drone has been selected
			 * update the 2 tables with the following information:
			 * timestamp extracted from mDeliveryRequest
			 * and burned fuel in relation 1 : 1 to the traveled distance (1BU = 1DU)
			 * Note: this do not uses the drone's charging rate(it should but the
			 * given task is ambiguous so i leave this correction to later refactoring)
			 */
			dronesKey = drone.getDroneID();//this is the key of the selected drone (its ID)
			warehousesKey = mDeliveryRequest.getDeliveryWarehouseID();//this is the key of the warehouse (its ID) where the product is stored
			Location droneLocation = mDroneLocations.get(dronesKey);//we use the ID/key of the drone to map it to a location
			Location warehouseLocation = mWarehouseLocations.get(warehousesKey);//we use the ID/key of the warehouse to map it to a location
			Location deliveryLocation = mDeliveryRequest.getDeliveryLocation();//we get the delivery location
			distance = calculateDistance(droneLocation, warehouseLocation)
					+ calculateDistance(warehouseLocation, deliveryLocation);
			/*
			 * and then calculate the total distance that a drone must travel from its current location where
			 * the products is stored and from the warehouseLocation to the delivery location
			 */
			// Timestamp timestamp = mDeliveryRequest.getDeliveryTimestamp();
			mUtility.updateRequestsLog(mDeliveryRequest.getDeliveryTimestamp(), "request");
			mUtility.updateDrone(distance);
		} else if (isProductAvailable() && drones != null) {
			/*
			 * if the product is available ,but the variable drone is null
			 * then this indicates that the workload is to huge to be carried by one
			 * drone so we check if the work can be executed by a list of drones(drones!=null)
			 * if the list is null then the request is unexecutable and there is nothing more we can do.
			 * If however the list is not empty/null then we 
			 * iterate through the list using the same algorithm(as when the load is carried by 1 drone)
			 * 			 */
			mUtility.updateRequestsLog(mDeliveryRequest.getDeliveryTimestamp(), "request");
			for (i = 0; i < drones.size(); i++) {
				dronesKey = drones.get(i).getDroneID();
				warehousesKey = mDeliveryRequest.getDeliveryWarehouseID();
				Location droneLocation = mDroneLocations.get(dronesKey);
				Location warehouseLocation = mWarehouseLocations.get(warehousesKey);
				Location deliveryLocation = mDeliveryRequest.getDeliveryLocation();
				distance = calculateDistance(droneLocation, warehouseLocation)
						+ calculateDistance(warehouseLocation, deliveryLocation);
				mUtility.updateDrone(distance);
			}
		}

	}

	private boolean isProductAvailable() {
		/**
		 * returns true if there is product with the same name as the one given in the delivery request
		 */
		int i = 0;
		for (i = 0; i < mProducts.size(); i++) {
			if (mProducts.get(i).getProductName().equals(mDeliveryRequest.getDeliveryProductName())) {
				return true;
			}
		}
		return false;
	}

	private double calculateDistance(Location l1, Location l2) {
		/**
		 * calculate the distance between two points in 2D euclidian sapce
		 */
		double x = Math.pow(l1.getLocationXCoordinate() - l2.getLocationXCoordinate(), 2);
		double y = Math.pow(l1.getLocationYCoordinate() - l2.getLocationYCoordinate(), 2);
		return Math.sqrt(x + y);
	}

	private Double getWorkload() {
		/**
		 * returns the workload of a delivery request
		 * it is calculated by multiplying the quantity of the delivery and the product weightPerQuantity
		 */
		Double workload = null;
		int i = 0;
		for (i = 0; i < mProducts.size(); i++) {
			if (mProducts.get(i).getProductName().equals(mDeliveryRequest.getDeliveryProductName())) {
				workload = mProducts.get(i).getProductWeightPerQuantityUnit() * mDeliveryRequest.getProductQuantity();
				break;
			}
		}
		return workload;
	}

	private Drone selectDrone() {
		/**
		 * returns the drone that will execute the delivery with best effort performance
		 * returns null if no drone can execute the delivery
		 */
		int i = 0;
		Integer index = null;
		double max = 0;
		double distance;
		Double workload = getWorkload();
		for (i = 0; i < mDrones.size(); i++) {
			int dronesKey = mDrones.get(i).getDroneID();
			int warehousesKey = mDeliveryRequest.getDeliveryWarehouseID();
			Location droneLocation = mDroneLocations.get(dronesKey);
			Location warehouseLocation = mWarehouseLocations.get(warehousesKey);
			Location deliveryLocation = mDeliveryRequest.getDeliveryLocation();
			distance = calculateDistance(droneLocation, warehouseLocation)
					+ calculateDistance(warehouseLocation, deliveryLocation);
			if (mDrones.get(i).getDroneBattery() >= distance && workload <= mDrones.get(i).getDroneCapacity()) {
				/*
				 * if a drone can travel the total distance of a delivery on its battery
				 * and can carry the workload then we check is this the drone with the
				 * maximum fuel left and if it is we store its index in the list
				 */
				if (max <= mDrones.get(i).getDroneBattery() - distance) {
					index = i;
					max = mDrones.get(i).getDroneBattery() - distance;
				}
			}
		}
		// we return the drone with maximum fuel left after executing the delivery
		return mDrones.get(index);
	}

	private LinkedList<Drone> selectDrones() {
		int i = 0;
		/*
		 * selectDrones follow the same algorithm as selectDrone
		 * the only difference being that we know the workload is too big
		 * for any 1 drone ,so we split it into parts as many times
		 * as needed to be carried(minimum of 2).in other words we send as much drones as
		 * needed to carry the load 1 by one and store them in a list;
		 *the method returns null if the workload can't be carried by all the drones
		 */
		double distance = 0;
		Double workload = getWorkload();
		LinkedList<Drone> result = new LinkedList<Drone>();
		for (i = 0; i < mDrones.size(); i++) {
			int dronesKey = mDrones.get(i).getDroneID();
			int warehousesKey = mDeliveryRequest.getDeliveryWarehouseID();
			Location droneLocation = mDroneLocations.get(dronesKey);
			Location warehouseLocation = mWarehouseLocations.get(warehousesKey);
			Location deliveryLocation = mDeliveryRequest.getDeliveryLocation();
			distance = calculateDistance(droneLocation, warehouseLocation)
					+ calculateDistance(warehouseLocation, deliveryLocation);
			if (mDrones.get(i).getDroneBattery() >= distance && workload > mDrones.get(i).getDroneCapacity()) {
				result.add(mDrones.get(i));
				workload = workload - mDrones.get(i).getDroneCapacity();
			} else if (mDrones.get(i).getDroneBattery() >= distance && workload < mDrones.get(i).getDroneCapacity()) {
				result.add(mDrones.get(i));
				workload = (double) 0;
				break;
			}
		}
		if(workload!=0){
			result = null;
		}
		return result;
	}

}
