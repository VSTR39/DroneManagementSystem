package logic;

import java.sql.SQLException;
import java.util.LinkedList;

import data.DatabaseConnectionUtilities;
import data.DatabaseConnectionUtilitiesException;
import data.Product;
import data.Warehouse;

public class WarehouseManager implements WarehouseManagerInterface {
	private LinkedList<Product> mProducts;
	private DatabaseConnectionUtilities mUtility;
	public void initialize() throws SQLException{
	    mUtility = new DatabaseConnectionUtilities();
		mProducts = mUtility.getAllAvalailableProducts();
	}
	
	public WarehouseManager() throws SQLException{
		initialize();
	}
	public boolean makeSupply(SupplyRequest supplyRequest) throws SQLException, DatabaseConnectionUtilitiesException {
		// TODO Auto-generated method stub
	int i = 0;
	for(i=0;i<mProducts.size();i++){
		if(mProducts.get(i).getProductWarehouseID() == supplyRequest.getSupplyWarehouseID()){
			mUtility.updateProducts(supplyRequest.getSupplyWarehouseID(),
									supplyRequest.getSuppyProductName(),
									supplyRequest.getSupplyProductWeight(),
									supplyRequest.getSupplyProductQuantity());
			mUtility.insertSupplyRequest(supplyRequest.getSupplyRequestTimestamp(),"supply");
			return true;
		}
	}
	LinkedList<Integer> warehouseIDs = mUtility.getAllWarehouseIDs();
	if(warehouseIDs.contains(supplyRequest.getSupplyWarehouseID())){
		mUtility.insertProduct(supplyRequest.getSupplyWarehouseID(),
							   supplyRequest.getSuppyProductName(),
							   supplyRequest.getSupplyProductWeight(),
							   supplyRequest.getSupplyProductQuantity());
		mUtility.insertSupplyRequest(supplyRequest.getSupplyRequestTimestamp(),"supply");
		return true;
	}
	return false;
	}
	
}
