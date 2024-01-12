package lk.ijse.shadowStudio.dao.custom;

import lk.ijse.shadowStudio.dao.SuperDAO;

import java.sql.SQLException;

public interface DashboardDAO extends SuperDAO {
    int countCustomer() throws SQLException;
    int countItems() throws SQLException;
    int countBookings() throws SQLException;
    void countprofit();
    int countComplains() throws SQLException;
    int countRentItems() throws SQLException;


}
