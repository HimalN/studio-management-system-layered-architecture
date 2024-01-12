package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.Entity.Rent;
import lk.ijse.shadowStudio.dao.SQLUtil;
import lk.ijse.shadowStudio.dao.custom.RentDAO;
import lk.ijse.shadowStudio.dto.RentDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentDAOImpl implements RentDAO {
    public String generateNextRentId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT rentId FROM rent ORDER BY rentId DESC LIMIT 1");
        if(resultSet.next()) {
            return splitRentId(resultSet.getString(1));
        }
        return splitRentId(null);
    }
    public String splitRentId(String currentRentId) {
        if(currentRentId != null) {
            String[] split = currentRentId.split("[R]");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return String.format("R%03d",id);
        } else {
            return "R001";
        }
    }

    public List<Rent> getAllRent() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT * from rent");
        ArrayList<Rent> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new Rent(
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
        return SQLUtil.execute("delete from rent where rentId = ?", id);
    }


    public boolean saveRent(String rentId, String custId, String custName, String itemId, String itemName, String daycount, String date, int qty, String price) throws SQLException {
        return SQLUtil.execute("INSERT INTO rent VALUES(?,?,?,?,?,?,?,?,?)", rentId, custId, custName, itemId, itemName, daycount, date, qty, price);
    }
}
