package logic;

import java.sql.Timestamp;

public class SupplyRequest {
	/**
	 * this is a class that allows the creation of supply requests objects
	 * from commands that follow this pattern:
	 * supply <id> <timestamp YYYY-MM-DD HH:MM> <product name 1> <product weight> <quantity> <product name 2> <product weight> <quantity>

	   e.g. "supply 5 2016-10-25 12:32 tomato 5 100 potatoes 6 50 cheese 2 4"
	 */
	private int mSupplyWarehouseID;//id of the warehouse where the product is stored
	private Timestamp mSupplyRequestTimestamp;//timestamp showing when the request was received/executed 
	private String mSuppyProductName;// the name of the product 
	private double mSupplyProductWeight;// the weight of 1 product
	private int mSupplyProductQuantity;// the number of products
	
	public SupplyRequest(int supplyWarehouseID,Timestamp supplyRequestTimestamp,String supplyProductName,
			double supplyProductWeight,int supplyProductQuantity){
		this.mSupplyWarehouseID = supplyWarehouseID;
		this.mSupplyRequestTimestamp = supplyRequestTimestamp;
		this.mSupplyProductWeight = supplyProductWeight;
		this.mSupplyProductQuantity = supplyProductQuantity;
	}

	public int getSupplyWarehouseID() {
		return mSupplyWarehouseID;
	}

	public void setSupplyWarehouseID(int mSupplyWarehouseID) {
		this.mSupplyWarehouseID = mSupplyWarehouseID;
	}

	public Timestamp getSupplyRequestTimestamp() {
		return mSupplyRequestTimestamp;
	}

	public void setSupplyRequestTimestamp(Timestamp mSupplyRequestTimestamp) {
		this.mSupplyRequestTimestamp = mSupplyRequestTimestamp;
	}

	public String getSuppyProductName() {
		return mSuppyProductName;
	}

	public void setSuppyProductName(String mSuppyProductName) {
		this.mSuppyProductName = mSuppyProductName;
	}

	public double getSupplyProductWeight() {
		return mSupplyProductWeight;
	}

	public void setSupplyProductWeight(double mSupplyProductWeight) {
		this.mSupplyProductWeight = mSupplyProductWeight;
	}

	public int getSupplyProductQuantity() {
		return mSupplyProductQuantity;
	}

	public void setSupplyProductQuantity(int mSupplyProductQuantity) {
		this.mSupplyProductQuantity = mSupplyProductQuantity;
	}
	
	

}
