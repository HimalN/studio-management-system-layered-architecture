package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.Entity.Item;
import lk.ijse.shadowStudio.dao.custom.RentItemDAO;

import java.sql.SQLException;
import java.util.List;

public class RentItemDAOImpl implements RentItemDAO {
    @Override
    public boolean save(Item dto) throws SQLException {
        return false;
    }

    @Override
    public String generateNextId() throws SQLException {
        return null;
    }

    @Override
    public String splitId(String currentId) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Item dto) throws SQLException {
        return false;
    }

    @Override
    public List<Item> getAll() throws SQLException {
        return null;
    }

    @Override
    public Item search(String id) throws SQLException {
        return null;
    }
}
