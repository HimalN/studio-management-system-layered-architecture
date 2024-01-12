package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.dao.SQLUtil;
import lk.ijse.shadowStudio.dao.custom.DashboardDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAOImpl implements DashboardDAO {

    @Override
    public int countCustomer() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT count(*) from customer");
        rs.next();
        return rs.getInt(1);
    }

    @Override
    public int countItems() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT count(*) from customer");
        rs.next();
        return rs.getInt(1);
    }

    @Override
    public int countBookings() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT count(*) from customer");
        rs.next();
        return rs.getInt(1);
    }

    @Override
    public void countprofit() {

    }

    @Override
    public int countComplains() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT count(*) from customer");
        rs.next();
        return rs.getInt(1);
    }

    @Override
    public int countRentItems() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT count(*) from customer");
        rs.next();
        return rs.getInt(1);
    }
}
