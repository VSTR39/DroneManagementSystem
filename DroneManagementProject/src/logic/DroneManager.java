package logic;

public class DroneManager implements DroneManagerInterface{
	
	public long makeDelivery(DeliveryRequest deliveryRequest) {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		
		long endTime = System.currentTimeMillis();
		return startTime-endTime;
	}

}
