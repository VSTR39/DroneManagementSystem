package data;


public class Product {
	/**
	 * A class that represents the table products as it is described in
	 * drone_management_system_DB.sql following this ER diagram
	 * https://github.com/VSTR39/DroneManagementSystem/blob/master/Drone%
	 * 20Management%20System.jpg Each Product has:
	 *  - unique id;
	 *  - warehouse id;
	 *  - name;
	 *  - quantity;
	 *  - weight per quantity unit;
	 */

	private final int mProductID;
	private int mProductWarehouseID;
	private String mProductName;
	private int mProductQuantity;// the number of quantity units in store
	private final double mProductWeightPerQuantityUnit;// the mass of 1 quantity
														// unit

	public Product(int productID, int warehouseID, String productName, int productQuantity,
			double productWeightPerQuantityUnit) {
		this.mProductID = productID;
		this.mProductWarehouseID = warehouseID;
		this.mProductName = productName;
		this.mProductQuantity = productQuantity;
		this.mProductWeightPerQuantityUnit = productWeightPerQuantityUnit;
	}

	public int getProductWarehouseID() {
		/**
		 * returns an int value representing the warehouse's id where a product
		 * is stored
		 */
		return mProductWarehouseID;
	}

	public void setProductWarehouseID(int mProductWarehouseID) {
		/**
		 * takes an int value representing the warehouse's id where a product is
		 * store and sets it
		 */
		this.mProductWarehouseID = mProductWarehouseID;
	}

	public String getProductName() {
		/**
		 * returns a string value representing the name of a product
		 */
		return mProductName;
	}

	public void setProductName(String mProductName) {
		/**
		 * takes an string value and sets the name of a product to the given
		 * value
		 */
		this.mProductName = mProductName;
	}

	public int getProductQuantity() {
		/**
		 * returns an int value representing the number of units of a given
		 * product that are in store
		 */
		return mProductQuantity;
	}

	public void setProductQuantity(int mProductQuantity) {
		/**
		 * takes an int value and sets the number of units of a given product
		 * that are in store
		 */
		this.mProductQuantity = mProductQuantity;
	}

	public int getProductID() {
		/**
		 * returns an int value representing a product's unique id
		 */
		return mProductID;
	}

	public double getProductWeightPerQuantityUnit() {
		/**
		 * returns a double value representing the weight of 1 product
		 */
		return mProductWeightPerQuantityUnit;
	}


}
