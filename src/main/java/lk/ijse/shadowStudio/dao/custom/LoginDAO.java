package lk.ijse.shadowStudio.dao.custom;

import lk.ijse.shadowStudio.dao.SuperDAO;

import java.sql.SQLException;

public interface LoginDAO extends SuperDAO {
    public boolean login(String username, String password) throws SQLException;
}
