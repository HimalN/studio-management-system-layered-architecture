package lk.ijse.shadowStudio.BO.custom.Impl;

import lk.ijse.shadowStudio.BO.custom.RentItemBO;
import lk.ijse.shadowStudio.Entity.Item;
import lk.ijse.shadowStudio.Entity.Package;
import lk.ijse.shadowStudio.Entity.Rent;
import lk.ijse.shadowStudio.dao.DAOFactory;
import lk.ijse.shadowStudio.dao.custom.PackagesDAO;
import lk.ijse.shadowStudio.dao.custom.RentItemDAO;
import lk.ijse.shadowStudio.dto.ItemDto;
import lk.ijse.shadowStudio.dto.PackageDto;
import lk.ijse.shadowStudio.dto.RentDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentItemBOImpl implements RentItemBO {

    RentItemDAO rentItemDAO = (RentItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RENTITEMS);
    @Override
    public ItemDto searchItem(String searchId) throws SQLException, ClassNotFoundException {
        Item item = rentItemDAO.search(searchId);
        if (item != null) {
            return new ItemDto(
                    item.getItemId(),
                    item.getItemName(),
                    item.getItemType(),
                    item.getRentalPrice(),
                    item.getQty()
            );
        } else {
            return null;
        }
    }

    @Override
    public String generateNextItemID() throws SQLException, ClassNotFoundException {
        return rentItemDAO.generateNextId();
    }

    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return rentItemDAO.save(
                new Item(
                        dto.getItemId(),
                        dto.getItemName(),
                        dto.getItemType(),
                        dto.getRentalPrice(),
                        dto.getQty()
                )
        );
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return rentItemDAO.update(
                new Item(
                        dto.getItemId(),
                        dto.getItemName(),
                        dto.getItemType(),
                        dto.getRentalPrice(),
                        dto.getQty()
                )
        );
    }



    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return rentItemDAO.delete(id);
    }

    @Override
    public List<ItemDto> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDto> itemDtos = new ArrayList<>();
        List<Item> items = rentItemDAO.getAll();

        for (Item item : items) {
            itemDtos.add(
                    new ItemDto(
                            item.getItemId(),
                            item.getItemName(),
                            item.getItemType(),
                            item.getRentalPrice(),
                            item.getQty()
                    )
            );
        }
        return itemDtos;
    }

    @Override
    public boolean updateRent(RentDto dto) throws SQLException {
        return rentItemDAO.updateRent(new Rent(dto.getRentId(), dto.getCustId(), dto.getCustName(), dto.getItemId(), dto.getItemName(), dto.getDaycount(), dto.getDate(), dto.getQty(), dto.getPrice()));
    }
}
