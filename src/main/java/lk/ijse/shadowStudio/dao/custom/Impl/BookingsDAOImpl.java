package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.Entity.Booking;
import lk.ijse.shadowStudio.dao.custom.BookingsDAO;

import java.sql.SQLException;
import java.util.List;

public class BookingsDAOImpl implements BookingsDAO {
    @Override
    public boolean save(Booking dto) throws SQLException {
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
    public boolean update(Booking dto) throws SQLException {
        return false;
    }

    @Override
    public List<Booking> getAll() throws SQLException {
        return null;
    }

    @Override
    public Booking search(String id) throws SQLException {
        return null;
    }
}
