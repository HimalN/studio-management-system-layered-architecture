package lk.ijse.shadowStudio.model;

import lk.ijse.shadowStudio.db.DbConnection;
import lk.ijse.shadowStudio.dto.ItemDto;
import lk.ijse.shadowStudio.dto.RentDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentItemModel {
    public static String generateNextItemId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT itemId FROM item ORDER BY itemId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitItemId(resultSet.getString(1));
        }
        return splitItemId(null);
    }
    private static String splitItemId(String currentItemId) {
        if (currentItemId != null) {
            String[] split = currentItemId.split("[I]");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return String.format("I%03d",id);
        } else {
            return "I001";
        }
    }


    public static boolean saveItem(ItemDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO item VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getItemId());
        pstm.setString(2,dto.getItemName());
        pstm.setString(3,dto.getItemType());
        pstm.setString(4,dto.getRentalPrice());
        pstm.setString(5,dto.getQty());


        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;

    }

    public static boolean deleteItem(String id) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "delete from item where itemId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;

    }

    public static boolean updateItem(ItemDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET itemName = ?, itemType = ?, rentalPrice = ?, qty = ? WHERE itemId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getItemName());
        pstm.setString(2, dto.getItemType());
        pstm.setString(3, dto.getRentalPrice());
        pstm.setString(4, dto.getQty());
        pstm.setString(5,dto.getItemId());


        return pstm.executeUpdate() > 0;
    }

    public static ItemDto searchItem(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select * from item where itemId=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        ItemDto dto = null;

        if(resultSet.next()) {
            String itemId = resultSet.getString(1);
            String itemName = resultSet.getString(2);
            String itemType = resultSet.getString(3);
            String itemRental = resultSet.getString(4);
            String qty = resultSet.getString(5);

            dto = new ItemDto(itemId,itemName,itemType,itemRental,qty );
        }
        return dto;

    }

    public List<ItemDto> getAllItem() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from item";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();



        ArrayList<ItemDto> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new ItemDto(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    )
            );
        }
        return dtoList;

    }
    public boolean updateItem(String itemId, int qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET qty = qty - ? WHERE itemId = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setInt(1,qty);
        ptsm.setString(2,itemId);

        return ptsm.executeUpdate() > 0;
    }
    public boolean updateRent(RentDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE rent SET cust_id = ?,cust_name = ?,itemId = ?,item_name = ?,dayCount = ?,Date = ?,qty = ? WHERE rentId = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1,dto.getCustId());
        ptsm.setString(2,dto.getCustName());
        ptsm.setString(3,dto.getItemId());
        ptsm.setString(4,dto.getItemName());
        ptsm.setString(5,dto.getDaycount());
        ptsm.setDate(6, Date.valueOf(dto.getDate()));
        ptsm.setInt(7,dto.getQty());
        ptsm.setString(8, dto.getRentId());

        return ptsm.executeUpdate() > 0;
    }

}