package io;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

import data.Location;
import logic.DeliveryRequest;
import logic.DroneManager;
import logic.SupplyRequest;

public class DroneManagementSystemMain {

	public static void performDelivery(String input) throws SQLException {
		String[] words = input.split(" ");
		
		int warehouseID = Integer.parseInt(words[1]);
		Timestamp timestamp = Timestamp.valueOf(words[2] + words[3]);
		String coordinates = words[4];
		
		int warehouseX = Integer.parseInt(coordinates.split(",")[0]);
		int warehouseY = Integer.parseInt(coordinates.split(",")[1]);
		Location deliveryLocation = new Location(warehouseX, warehouseY);
		
		String productName = words[5];
		int productQuantity = Integer.parseInt(words[6]);
	
		DroneManager manager = new DroneManager(new DeliveryRequest(warehouseID, timestamp, deliveryLocation, productName, productQuantity));
	}
	
	public static void performSupply(String input) {
		String[] words = input.split(" ");
		int warehouseID = Integer.parseInt(words[1]);
		Timestamp timestamp = Timestamp.valueOf(words[2] + words[3]);
		
		String productName = words[4];
		int productWeight = Integer.parseInt(words[5]);
		int productQuantity = Integer.parseInt(words[6]);
		
		
	}
	
	public static void main(String[] args) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		String line;
		while (scanner.hasNextLine()) {
			
			line = scanner.nextLine();
			
			if (line.split(" ")[0].equals("delivery")) {
				performDelivery(line);
			} else if (line.split(" ")[0].equals("supply")) {
				System.out.println("Making a supply");
			} else {
				System.err.println("Wrong command!");
			}
		}
		scanner.close();
	}
}
