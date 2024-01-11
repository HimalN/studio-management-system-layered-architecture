package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.Entity.Rent;
import lk.ijse.shadowStudio.dao.custom.RentDAO;

import java.sql.SQLException;
import java.util.List;

public class RentDAOImpl implements RentDAO {
    @Override
    public boolean save(Rent dto) throws SQLException {
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
    public boolean update(Rent dto) throws SQLException {
        return false;
    }

    @Override
    public List<Rent> getAll() throws SQLException {
        return null;
    }

    @Override
    public Rent search(String id) throws SQLException {
        return null;
    }
}
