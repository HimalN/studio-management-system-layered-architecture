package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.Entity.Booking;
import lk.ijse.shadowStudio.Entity.Complain;
import lk.ijse.shadowStudio.dao.SQLUtil;
import lk.ijse.shadowStudio.dao.custom.BookingsDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingsDAOImpl implements BookingsDAO {
    @Override
    public boolean save(Booking dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO bookings VALUES(?,?,?,?,?,?,?,?,?,?)",
                dto.getBooking_id(),
                dto.getCust_id(),
                dto.getCust_name(),
                dto.getPackage_id(),
                dto.getPackage_name(),
                dto.getDate(),
                dto.getTime(),
                dto.getLocation(),
                dto.getDescription(),
                dto.getPaymemt()
        );
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT booking_id FROM bookings ORDER BY booking_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) throws SQLException {
        if(currentId != null) {
            String[] split = currentId.split("[B]");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return String.format("B%03d",id);
        } else {
            return "B001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("delete from bookings where booking_id = ?",id);
    }

    @Override
    public boolean update(Booking dto) throws SQLException {
        return SQLUtil.execute(
                "UPDATE bookings SET cust_id = ?, cust_name = ?, package_id = ?, package_name = ?, Date = ?, Time = ?,location = ?, description = ?, payment=? WHERE booking_id = ?",
                dto.getCust_id(),
                dto.getCust_name(),
                dto.getPackage_id(),
                dto.getPackage_name(),
                dto.getDate(),
                dto.getTime(),
                dto.getLocation(),
                dto.getDescription(),
                dto.getPaymemt(),
                dto.getBooking_id()
        );
    }

    @Override
    public List<Booking> getAll() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT * from bookings");

        ArrayList<Booking> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new Booking(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10)
                    )
            );
        }
        return dtoList;
    }

    @Override
    public Booking search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * from bookings where booking_id=?",id);

        Booking dto = null;

        if(resultSet.next()) {
            String booking_id = resultSet.getString(1);
            String cust_id = resultSet.getString(2);
            String cust_name = resultSet.getString(3);
            String package_id = resultSet.getString(4);
            String package_name = resultSet.getString(5);
            String Date = resultSet.getString(6);
            String Time = resultSet.getString(7);
            String location = resultSet.getString(8);
            String description = resultSet.getString(9);
            String payment = resultSet.getString(10);

            dto = new Booking(
                    booking_id,cust_id, cust_name,package_id,
                    package_name,Date,Time,location,description,payment
            );
        }
        return dto;
    }
}
