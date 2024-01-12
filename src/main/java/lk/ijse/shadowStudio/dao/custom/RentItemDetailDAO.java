package lk.ijse.shadowStudio.dao.custom;

import lk.ijse.shadowStudio.dao.SQLUtil;
import lk.ijse.shadowStudio.dao.SuperDAO;

import java.sql.SQLException;

public interface RentItemDetailDAO extends SuperDAO {
    public boolean saveItemDetail(String itemId, String custId) throws SQLException;
    public boolean deleteRent(String id) throws SQLException;
}
