package data;


public class Drone {
	/**
	 * A class that represent the table drones as it is described in
	 * drone_management_system_DB.sql following this ER diagram
	 * https://github.com/VSTR39/DroneManagementSystem/blob/master/Drone%
	 * 20Management%20System.jpg
	 * Each drone has a:
    	- unique id
    	- location id
    	- battery 
    	- capacity 
    	- charging rate 
	 */
	
	private final int mDroneID; // this is the unique ID of the drone
	private final int mDroneLocationID; //this is the id of the location where the drone is
	private double mDroneBattery;// this represents the fuel reservoir of the drone
	private final double mDroneCapacity;// this represents the drone workload capacity 
	private final double mDroneChargingRate;// this represents the drone fuel expenses - it is measure in battery units/minute
	
	public Drone(int droneID,int droneLocationID,double droneBattery,double droneCapacity,double droneChargingRate){
		this.mDroneID = droneID;
		this.mDroneLocationID = droneLocationID;
		this.mDroneBattery = droneBattery;
		this.mDroneCapacity = droneCapacity;
		this.mDroneChargingRate = droneChargingRate;
	}

	public double getDroneBattery() {
		return mDroneBattery;
		/**
		 * Returns a double value representing the drone's available fuel
		 */
	}

	public void setDroneBattery(double mDroneBattery) {
		this.mDroneBattery = mDroneBattery;
		/**
		 * Takes a double value representing the drone reservoir capacity
		 * and sets it as the drone's currently available fuel
		 */
	}

	public int getDroneID() {
		return mDroneID;
		/**
		 * returns an int value representing the drone's unique id
		 */
	}

	public double getDroneCapacity() {
		/**
		 * returns a double value representing the drone's workload(payload) capacity
		 */
		return mDroneCapacity;
	}

	public double getDroneChargingRate() {
		return mDroneChargingRate;
		/**
		 * returns a double values representing the drone's fuel expenses measured in battery units per minute
		 */
	}
	
	public int getDroneLocationID(){
		/**
		 * returns an int value representing the id of the location where the drone is
		 */
		return mDroneLocationID;
	}
	
}
