package lk.ijse.shadowStudio.BO.custom.Impl;

import lk.ijse.shadowStudio.BO.custom.BookingsBO;
import lk.ijse.shadowStudio.Entity.Booking;
import lk.ijse.shadowStudio.Entity.Complain;
import lk.ijse.shadowStudio.dao.DAOFactory;
import lk.ijse.shadowStudio.dao.custom.BookingsDAO;
import lk.ijse.shadowStudio.dao.custom.ComplainsDAO;
import lk.ijse.shadowStudio.dto.BookingDto;
import lk.ijse.shadowStudio.dto.ComplainDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingsBOImpl implements BookingsBO {

    BookingsDAO bookingsDAO = (BookingsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKINGS);

    @Override
    public BookingDto searchBooking(String searchId) throws SQLException, ClassNotFoundException {
        Booking booking = bookingsDAO.search(searchId);
        if (booking != null) {
            return new BookingDto(
                    booking.getBooking_id(),
                    booking.getCust_id(),
                    booking.getCust_name(),
                    booking.getPackage_id(),
                    booking.getPackage_name(),
                    booking.getDate(),
                    booking.getTime(),
                    booking.getLocation(),
                    booking.getDescription(),
                    booking.getPaymemt()
            );
        } else {
            return null;
        }
    }

    @Override
    public String generateNextBookingID() throws SQLException, ClassNotFoundException {
        return bookingsDAO.generateNextId();
    }

    @Override
    public boolean saveBooking(BookingDto dto) throws SQLException, ClassNotFoundException {
        return bookingsDAO.save(
                new Booking(
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
                )
        );
    }

    @Override
    public boolean updateBooking(BookingDto dto) throws SQLException, ClassNotFoundException {
        return bookingsDAO.update(
                new Booking(
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
                )
        );
    }

    @Override
    public boolean deleteBooking(String id) throws SQLException, ClassNotFoundException {
        return bookingsDAO.delete(id);
    }

    @Override
    public List<BookingDto> getAllBookings() throws SQLException, ClassNotFoundException {
        ArrayList<BookingDto> bookingDtos = new ArrayList<>();
        List<Booking> bookings = bookingsDAO.getAll();

        for (Booking booking : bookings) {
            bookingDtos.add(
                    new BookingDto(
                            booking.getBooking_id(),
                            booking.getCust_id(),
                            booking.getCust_name(),
                            booking.getPackage_id(),
                            booking.getPackage_name(),
                            booking.getDate(),
                            booking.getTime(),
                            booking.getLocation(),
                            booking.getDescription(),
                            booking.getPaymemt()
                    )
            );
        }
        return bookingDtos;
    }
}
