package lk.ijse.shadowStudio.BO.custom;

import lk.ijse.shadowStudio.BO.SuperBO;
import lk.ijse.shadowStudio.dto.ComplainDto;

import java.sql.SQLException;
import java.util.List;

public interface ComplainBO extends SuperBO {

    public ComplainDto searchComplain(String searchId) throws SQLException, ClassNotFoundException;

    String generateNextComplainID() throws SQLException, ClassNotFoundException;

    boolean saveComplain(ComplainDto dto) throws SQLException, ClassNotFoundException;

    boolean updateComplain(ComplainDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteComplain(String id) throws SQLException, ClassNotFoundException;

    List<ComplainDto> getAllComplains() throws SQLException, ClassNotFoundException;
}
