package logic;

import java.sql.Timestamp;

import data.Location;

public class DeliveryRequest {

	private int mDeliveryWarehouseID;// id of the warehouse where we are
										// supposed to get supplies from
	private Timestamp mDeliveryTimestamp;//timestamp of the delivery request
	private Location mDeliveryLocation;//location of the delivery
	private String mDeliveryProductName;//name of the product that is to be delivered
	private int mProductQuantity;//quantity of the product that is to be delivered

	public DeliveryRequest(int deliveryWarehouseID, Timestamp deliveryTimestamp, Location deliveryLocation,
			String deliveryProductName, int deliveryQuantity) {
		this.mDeliveryWarehouseID = deliveryWarehouseID;
		this.mDeliveryTimestamp = deliveryTimestamp;
		this.mDeliveryLocation = deliveryLocation;
		this.mDeliveryProductName = deliveryProductName;
		this.mProductQuantity = deliveryQuantity;
	}

	public int getDeliveryWarehouseID() {
		return mDeliveryWarehouseID;
	}

	public void setDeliveryWarehouseID(int mDeliveryWarehouseID) {
		this.mDeliveryWarehouseID = mDeliveryWarehouseID;
	}

	public Timestamp getDeliveryTimestamp() {
		return mDeliveryTimestamp;
	}

	public void setDeliveryTimestamp(Timestamp mDeliveryTimestamp) {
		this.mDeliveryTimestamp = mDeliveryTimestamp;
	}

	public Location getDeliveryLocation() {
		return mDeliveryLocation;
	}

	public void setDeliveryLocation(Location mDeliveryLocation) {
		this.mDeliveryLocation = mDeliveryLocation;
	}

	public String getDeliveryProductName() {
		return mDeliveryProductName;
	}

	public void setDeliveryProductName(String mDeliveryProductName) {
		this.mDeliveryProductName = mDeliveryProductName;
	}

	public int getProductQuantity() {
		return mProductQuantity;
	}

	public void setProductQuantity(int mProductQuantity) {
		this.mProductQuantity = mProductQuantity;
	}
	
	
}
