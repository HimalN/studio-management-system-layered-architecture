package lk.ijse.shadowStudio.BO.custom;

import lk.ijse.shadowStudio.BO.SuperBO;
import lk.ijse.shadowStudio.dao.SuperDAO;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {
    boolean login(String username, String password) throws SQLException;
}
