package lk.ijse.shadowStudio.BO.custom;

import lk.ijse.shadowStudio.BO.SuperBO;
import lk.ijse.shadowStudio.dto.ComplainDto;
import lk.ijse.shadowStudio.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    public EmployeeDto searchEmployee(String searchId) throws SQLException, ClassNotFoundException;

    String generateNextEmployeeID() throws SQLException, ClassNotFoundException;

    boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;

    boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;

    List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException;

}
