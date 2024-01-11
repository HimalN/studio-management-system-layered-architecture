package lk.ijse.shadowStudio.BO.custom;

import lk.ijse.shadowStudio.BO.SuperBO;
import lk.ijse.shadowStudio.dto.BookingDto;
import lk.ijse.shadowStudio.dto.ComplainDto;

import java.sql.SQLException;
import java.util.List;

public interface BookingsBO extends SuperBO {
    public BookingDto searchBooking(String searchId) throws SQLException, ClassNotFoundException;

    String generateNextBookingID() throws SQLException, ClassNotFoundException;

    boolean saveBooking(BookingDto dto) throws SQLException, ClassNotFoundException;

    boolean updateBooking(BookingDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteBooking(String id) throws SQLException, ClassNotFoundException;

    List<BookingDto> getAllBookings() throws SQLException, ClassNotFoundException;
}
