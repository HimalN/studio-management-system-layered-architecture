package lk.ijse.shadowStudio.BO.custom;

import lk.ijse.shadowStudio.BO.SuperBO;
import lk.ijse.shadowStudio.dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface RentItemBO extends SuperBO {
    public ItemDto searchItem(String searchId) throws SQLException, ClassNotFoundException;

    String generateNextItemID() throws SQLException, ClassNotFoundException;

    boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;

    List<ItemDto> getAllItems() throws SQLException, ClassNotFoundException;
}
