package lk.ijse.shadowStudio.model;

import lk.ijse.shadowStudio.db.DbConnection;
import lk.ijse.shadowStudio.dto.BookingDto;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingsModel {
    private EmployeeModel employeeModel = new EmployeeModel();
    public static String generateNextBookingId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT booking_id FROM bookings ORDER BY booking_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitBookingId(resultSet.getString(1));
        }
        return splitBookingId(null);
    }
    private static String splitBookingId(String currentBookingId) {
        if(currentBookingId != null) {
            String[] split = currentBookingId.split("[B]");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return String.format("B%03d",id);
        } else {
            return "B001";
        }
    }

    @SneakyThrows
    public static boolean saveBooking(BookingDto dto) {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO bookings VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getBooking_id());
        pstm.setString(2,dto.getCust_id());
        pstm.setString(3,dto.getCust_name());
        pstm.setString(4,dto.getPackage_id());
        pstm.setString(5,dto.getPackage_name());
        pstm.setString(6,dto.getDate());
        pstm.setString(7,dto.getTime());
        pstm.setString(8,dto.getLocation());
        pstm.setString(9,dto.getDescription());
        pstm.setString(10,dto.getPaymemt());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    @SneakyThrows
    public List<BookingDto> getAllBookings() {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from bookings";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();



        ArrayList<BookingDto> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new BookingDto(
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

    public boolean deleteBooking(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "delete from bookings where booking_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateBookings(BookingDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE bookings SET cust_id = ?,cust_name=?, package_id = ?,package_name=?, date = ?, time = ?, location=?, description=?, payment=? WHERE booking_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getCust_id());
        pstm.setString(2, dto.getCust_name());
        pstm.setString(3, dto.getPackage_id());
        pstm.setString(4, dto.getPackage_name());
        pstm.setString(5, dto.getDate());
        pstm.setString(6, dto.getTime());
        pstm.setString(7, dto.getLocation());
        pstm.setString(8, dto.getDescription());
        pstm.setString(9, dto.getPaymemt());
        pstm.setString(10, dto.getBooking_id());

        return pstm.executeUpdate() > 0;

    }

    public BookingDto searchBookings(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from bookings where booking_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        BookingDto dto = null;

        if(resultSet.next()) {
            String booking_id = resultSet.getString(1);
            String cust_id = resultSet.getString(2);
            String cust_name = resultSet.getString(3);
            String package_id = resultSet.getString(4);
            String package_name = resultSet.getString(5);
            String date = resultSet.getString(6);
            String time = resultSet.getString(7);
            String location = resultSet.getString(8);
            String description = resultSet.getString(9);
            String payment = resultSet.getString(10);

            dto = new BookingDto(booking_id,cust_id,cust_name,package_id,package_name,date,time,location,description,payment);
        }
        return dto;
    }

/*    public static boolean saveBooking(BookingDto dto) throws SQLException {
        String bookingId = dto.getBooking_id();
        String custId = dto.getCust_id();
        String custName = dto.getCust_name();
        String packageId = dto.getPackage_id();
        String packageName = dto.getPackage_name();
        String time = dto.getTime();
        String location = dto.getLocation();
        String description = dto.getDescription();
        String payment = dto.getPaymemt();

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isBookingSaved = saveBooking(
                    bookingId,
                    custId,
                    custName,
                    packageId,
                    packageName,
                    time,
                    location,
                    description,
                    payment
            );

            if (isBookingSaved) {
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

    private static boolean saveBooking(
            String bookingId,
            String custId,
            String custName,
            String packgeId,
            String packageName,
            String date,
            String time,
            String location,
            String description
            ) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO bookings VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,bookingId);
        pstm.setString(2,custId);
        pstm.setString(3,custName);
        pstm.setString(4,packgeId);
        pstm.setString(5,packageName);
        pstm.setString(6,date);
        pstm.setString(7,time);
        pstm.setString(8,location);
        pstm.setString(9,description);
        pstm.setString(10,payment);

        return pstm.executeUpdate() > 0;
    }*/
}
