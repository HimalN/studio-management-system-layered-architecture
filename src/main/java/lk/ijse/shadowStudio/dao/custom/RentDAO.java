package lk.ijse.shadowStudio.dao.custom;

import lk.ijse.shadowStudio.Entity.Rent;
import lk.ijse.shadowStudio.dao.SuperDAO;

import java.sql.SQLException;
import java.util.List;

public interface RentDAO extends SuperDAO {
    public  String generateNextRentId() throws SQLException;
    public  String splitRentId(String currentRentId);
    public List<Rent> getAllRent() throws SQLException;

    public boolean deleteRent(String id) throws SQLException;
    boolean saveRent(String rentId, String custId, String custName, String itemId, String itemName, String daycount, String date, int qty, String price) throws SQLException;
}
