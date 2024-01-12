package lk.ijse.shadowStudio.BO.custom;

import lk.ijse.shadowStudio.BO.SuperBO;
import lk.ijse.shadowStudio.db.DbConnection;
import lk.ijse.shadowStudio.dto.RentDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RentBO extends SuperBO {
    public String generateNextID() throws SQLException;

    public boolean saveRentDetails(RentDto dto) throws SQLException;

    public boolean deleteRent(String id) throws SQLException;

    public List<RentDto> getAll() throws SQLException;
}
