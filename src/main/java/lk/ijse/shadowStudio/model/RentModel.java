package lk.ijse.shadowStudio.model;

import lk.ijse.shadowStudio.db.DbConnection;
import lk.ijse.shadowStudio.dto.RentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentModel {
    private RentItemModel rentItemModel = new RentItemModel();

    private RentItemDetailsModel rentItemDetailsModel = new RentItemDetailsModel();

    public static String generateNextRentId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT rentId FROM rent ORDER BY rentId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitRentId(resultSet.getString(1));
        }
        return splitRentId(null);
    }
    private static String splitRentId(String currentRentId) {
        if(currentRentId != null) {
            String[] split = currentRentId.split("[R]");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return String.format("R%03d",id);
        } else {
            return "R001";
        }
    }

    public List<RentDto> getAllRent() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from rent";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();



        ArrayList<RentDto> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new RentDto(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getInt(8),
                            rs.getString(9)
                    )
            );
        }
        return dtoList;
    }

    public boolean deleteRent(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "delete from rent where rentId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean saveRentDetails(RentDto dto) throws SQLException {
        String rentId = dto.getRentId();
        String custId = dto.getCustId();
        String custName = dto.getCustName();
        String itemId = dto.getItemId();
        String itemName = dto.getItemName();
        String daycount = dto.getDaycount();
        String date = dto.getDate();
        int qty = dto.getQty();
        String price = dto.getPrice();

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isRentSaved = saveRent(rentId,custId,custName,itemId,itemName,daycount,date,qty,price);
            if (isRentSaved) {
                boolean isUpdated = rentItemModel.updateItem(dto.getItemId(),dto.getQty());
                if (isUpdated) {
                    boolean isItemDetailSaved = rentItemDetailsModel.saveItemDetail(dto.getItemId(), dto.getCustId());
                    if (isItemDetailSaved) {
                        connection.commit();
                    }
                }
            }
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    private boolean saveRent(String rentId, String custId, String custName, String itemId, String itemName, String daycount, String date, int qty, String price) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO rent VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,rentId);
        pstm.setString(2,custId);
        pstm.setString(3,custName);
        pstm.setString(4,itemId);
        pstm.setString(5,itemName);
        pstm.setString(6,daycount);
        pstm.setString(7,date);
        pstm.setInt(8,qty);
        pstm.setString(9,price);

        return pstm.executeUpdate() > 0;
    }
}