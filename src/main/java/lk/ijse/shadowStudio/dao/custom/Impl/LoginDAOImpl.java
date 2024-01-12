package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.dao.SQLUtil;
import lk.ijse.shadowStudio.dao.custom.LoginDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOImpl implements LoginDAO {
    public boolean login(String username, String password) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM user WHERE use_name = ? AND password = ?", username, password);
        return rst.next();
    }
}
