package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.dao.SQLUtil;
import lk.ijse.shadowStudio.dao.custom.RentDAO;
import lk.ijse.shadowStudio.dao.custom.RentItemDetailDAO;
import lk.ijse.shadowStudio.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RentItemDetailDAOImpl implements RentItemDetailDAO {
    public boolean saveItemDetail(String itemId, String custId) throws SQLException {
        return SQLUtil.execute("INSERT INTO rent_item_details VALUES (?,?)", itemId, custId);
    }

    public boolean deleteRent(String id) throws SQLException {
        return SQLUtil.execute("delete from rent_item_details where cust_id = ?",id);

    }
}
