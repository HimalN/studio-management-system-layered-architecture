package lk.ijse.shadowStudio.model;

import lk.ijse.shadowStudio.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RentItemDetailsModel {
    public boolean saveItemDetail(String itemId, String custId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO rent_item_details VALUES (?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,itemId);
        ptsm.setString(2,custId);

        return ptsm.executeUpdate() > 0;
    }

    public boolean deleteRent(String id) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "delete from rent_item_details where cust_id = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, id);

            return pstm.executeUpdate() > 0;


    }
}