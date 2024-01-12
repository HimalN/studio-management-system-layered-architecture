package lk.ijse.shadowStudio.BO.custom.Impl;

import lk.ijse.shadowStudio.BO.custom.RentBO;
import lk.ijse.shadowStudio.Entity.Rent;
import lk.ijse.shadowStudio.dao.DAOFactory;
import lk.ijse.shadowStudio.dao.custom.RentDAO;
import lk.ijse.shadowStudio.dao.custom.RentItemDAO;
import lk.ijse.shadowStudio.dao.custom.RentItemDetailDAO;
import lk.ijse.shadowStudio.db.DbConnection;
import lk.ijse.shadowStudio.dto.RentDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentBOImpl implements RentBO {
    RentItemDAO rentItemDAO = (RentItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RENTITEMS);
    RentDAO rentDAO = (RentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RENT);
    RentItemDetailDAO rentItemDetailDAO = (RentItemDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RENTDETAILS);

    public String generateNextID() throws SQLException {
        return rentDAO.generateNextRentId();
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

            boolean isRentSaved = rentDAO.saveRent(rentId,custId,custName,itemId,itemName,daycount,date,qty,price);
            if (isRentSaved) {
                boolean isUpdated = rentItemDAO.updateItem(dto.getItemId(),dto.getQty());
                if (isUpdated) {
                    System.out.println("awa");
                    boolean isItemDetailSaved = rentItemDetailDAO.saveItemDetail(dto.getRentId(), dto.getCustId());
                    System.out.println("awooo");
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
    public boolean deleteRent(String id) throws SQLException {
        return rentDAO.deleteRent(id);
    }

    public List<RentDto> getAll() throws SQLException {
        ArrayList<RentDto> rentDto = new ArrayList<>();
        List<Rent> rents = rentDAO.getAllRent();
        for (Rent rent : rents){
            rentDto.add(
                    new RentDto(
                            rent.getRentId(),
                            rent.getCustId(),
                            rent.getCustName(),
                            rent.getItemId(),
                            rent.getItemName(),
                            rent.getDaycount(),
                            rent.getDate(),
                            rent.getQty(),
                            rent.getPrice()
                    )
            );
        }
        return rentDto;
    }
}
