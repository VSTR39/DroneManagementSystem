package data;


public class Warehouse {
	/**
	 * A class that represents the table warehouses as it is described in
	 * drone_management_system_DB.sql following this ER diagram
	 * https://github.com/VSTR39/DroneManagementSystem/blob/master/Drone%
	 * 20Management%20System.jpg
	 *  Each warehouse has:
	 *   - unique id;
	 *   - location id;
	 */

	private final int mWarehouseID;// represents the unique id of a warehouse
	// it is used to map a warehouse with a product
	private final int mWarehouseLocationID;// represents the id of the location
	// where the warehouse is situated
	// it is used to map a warehouse to a location

	public Warehouse(int warehouseID, int warehouseLocationID) {
		this.mWarehouseID = warehouseID;
		this.mWarehouseLocationID = warehouseLocationID;
	}

	public int getWarehouseID() {
		return mWarehouseID;
	}

	public int getWarehouseLocationID() {
		return mWarehouseLocationID;
	}
	
}
