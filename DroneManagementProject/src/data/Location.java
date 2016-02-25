package data;

public class Location {
	/**
	 * A class that represents the table locations as it is described in
	 * drone_management_system_DB.sql following this ER diagram
	 * https://github.com/VSTR39/DroneManagementSystem/blob/master/Drone%
	 * 20Management%20System.jpg
	 *  Each location has:
	 *  - unique id;
	 *  - x coordinate;
	 *  - y coordinate;
	 */

	private final int mLocationID;
	private final double mLocationXCoordinate;
	private final double mLocationYCoordinate;

	public Location(int locationID, double locationXCoordinate, double locationYCoordinate) {
		this.mLocationID = locationID;
		this.mLocationXCoordinate = locationXCoordinate;
		this.mLocationYCoordinate = locationYCoordinate;
	}

	public int getLocationID() {
		/**
		 * returns a int value representing the location's unique id
		 */
		return mLocationID;
	}

	public double getLocationXCoordinate() {
		/**
		 * returns a double value representing the location's x coordinate
		 */
		return mLocationXCoordinate;
	}

	public double getLocationYCoordinate() {
		/**
		 * returns a double value representing the location's y coordinate
		 */
		return mLocationYCoordinate;
	}

}
