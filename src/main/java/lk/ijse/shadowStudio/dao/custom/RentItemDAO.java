package lk.ijse.shadowStudio.dao.custom;

import lk.ijse.shadowStudio.Entity.Item;
import lk.ijse.shadowStudio.Entity.Rent;
import lk.ijse.shadowStudio.dao.CrudDAO;
import lk.ijse.shadowStudio.dao.SQLUtil;
import lk.ijse.shadowStudio.dto.RentDto;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public interface RentItemDAO extends CrudDAO<Item> {
    public boolean updateItem(String itemId, int qty) throws SQLException;
    public boolean updateRent(Rent dto) throws SQLException;
}
