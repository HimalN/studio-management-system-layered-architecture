package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.Entity.Complain;
import lk.ijse.shadowStudio.dao.custom.ComplainsDAO;

import java.sql.SQLException;
import java.util.List;

public class ComplainsDAOImpl implements ComplainsDAO {

    @Override
    public boolean save(Complain dto) throws SQLException {
        return false;
    }

    @Override
    public String generateNextId() throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String cust_id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Complain dto) throws SQLException {
        return false;
    }

    @Override
    public List<Complain> getAll() throws SQLException {
        return null;
    }

    @Override
    public Complain searchCustomer(String id) throws SQLException {
        return null;
    }

    @Override
    public Complain searchCustomerByTp(String tp) throws SQLException {
        return null;
    }
}
