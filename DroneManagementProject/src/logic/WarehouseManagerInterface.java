package logic;

import java.sql.SQLException;

import data.DatabaseConnectionUtilitiesException;

public interface WarehouseManagerInterface {
	public boolean makeSupply(SupplyRequest supplyRequest) throws SQLException, DatabaseConnectionUtilitiesException;
}
