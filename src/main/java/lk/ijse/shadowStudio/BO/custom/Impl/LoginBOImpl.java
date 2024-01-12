package lk.ijse.shadowStudio.BO.custom.Impl;

import lk.ijse.shadowStudio.BO.custom.LoginBO;
import lk.ijse.shadowStudio.dao.DAOFactory;
import lk.ijse.shadowStudio.dao.custom.LoginDAO;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {
    LoginDAO loginBO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGIN);

    public boolean login(String username, String password) throws SQLException {
        return loginBO.login(username, password);
    }
}
