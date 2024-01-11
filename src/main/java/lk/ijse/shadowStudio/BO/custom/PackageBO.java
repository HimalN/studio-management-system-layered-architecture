package lk.ijse.shadowStudio.BO.custom;

import lk.ijse.shadowStudio.BO.SuperBO;
import lk.ijse.shadowStudio.dao.custom.PackagesDAO;
import lk.ijse.shadowStudio.dto.EmployeeDto;
import lk.ijse.shadowStudio.dto.PackageDto;

import java.sql.SQLException;
import java.util.List;

public interface PackageBO extends SuperBO {
    public PackageDto searchPackage(String searchId) throws SQLException, ClassNotFoundException;

    String generateNextPackageID() throws SQLException, ClassNotFoundException;

    boolean savePackage(PackageDto dto) throws SQLException, ClassNotFoundException;

    boolean updatePackage(PackageDto dto) throws SQLException, ClassNotFoundException;

    boolean deletePackage(String id) throws SQLException, ClassNotFoundException;

    List<PackageDto> getAllPackage() throws SQLException, ClassNotFoundException;
}
