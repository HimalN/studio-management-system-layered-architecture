package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.Entity.Complain;
import lk.ijse.shadowStudio.Entity.Item;
import lk.ijse.shadowStudio.Entity.Rent;
import lk.ijse.shadowStudio.dao.SQLUtil;
import lk.ijse.shadowStudio.dao.custom.RentItemDAO;
import lk.ijse.shadowStudio.db.DbConnection;
import lk.ijse.shadowStudio.dto.RentDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentItemDAOImpl implements RentItemDAO {
    @Override
    public boolean save(Item dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO item VALUES(?,?,?,?,?)",
                dto.getItemId(),
                dto.getItemName(),
                dto.getItemType(),
                dto.getRentalPrice(),
                dto.getQty()
        );
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT itemId FROM item ORDER BY itemId DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) throws SQLException {
        if(currentId != null) {
            String[] split = currentId.split("[I]");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return String.format("I%03d",id);
        } else {
            return "I001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("delete from item where item_id = ?",id);
    }

    @Override
    public boolean update(Item dto) throws SQLException {
        return SQLUtil.execute("UPDATE item SET itemName = ?, itemType = ?, rentalPrice = ?, qty = ? WHERE itemId = ?",
                dto.getItemName(),
                dto.getItemType(),
                dto.getRentalPrice(),
                dto.getQty(),
                dto.getItemId()
        );
    }

    @Override
    public List<Item> getAll() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT * from item");

        ArrayList<Item> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new Item(
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

    @Override
    public Item search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * from item where itemId=?",id);

        Item dto = null;

        if(resultSet.next()) {
            String itemId = resultSet.getString(1);
            String itemName = resultSet.getString(2);
            String itemType = resultSet.getString(3);
            String rentalPrice = resultSet.getString(4);
            String qty = resultSet.getString(5);

            dto = new Item(itemId,itemName,itemType,rentalPrice,qty);
        }
        return dto;
    }
    public boolean updateItem(String itemId, int qty) throws SQLException {
        return SQLUtil.execute("UPDATE item SET qty = qty - ? WHERE itemId = ?",qty,itemId);
    }
    public boolean updateRent(Rent dto) throws SQLException {
        return SQLUtil.execute("UPDATE rent SET cust_id = ?,cust_name = ?,itemId = ?,item_name = ?,dayCount = ?,Date = ?,qty = ? WHERE rentId = ?",
                dto.getCustId(),
                dto.getCustName(),
                dto.getItemId(),
                dto.getItemName(),
                dto.getDaycount(),
                dto.getDate(),
                dto.getQty(),
                dto.getPrice(),
                dto.getRentId()
        );
    }
}
