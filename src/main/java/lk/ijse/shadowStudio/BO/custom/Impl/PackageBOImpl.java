package lk.ijse.shadowStudio.BO.custom.Impl;

import lk.ijse.shadowStudio.BO.custom.PackageBO;
import lk.ijse.shadowStudio.Entity.Complain;
import lk.ijse.shadowStudio.Entity.Package;
import lk.ijse.shadowStudio.dao.DAOFactory;
import lk.ijse.shadowStudio.dao.custom.ComplainsDAO;
import lk.ijse.shadowStudio.dao.custom.PackagesDAO;
import lk.ijse.shadowStudio.dto.ComplainDto;
import lk.ijse.shadowStudio.dto.EmployeeDto;
import lk.ijse.shadowStudio.dto.PackageDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageBOImpl implements PackageBO {

    PackagesDAO packagesDAO = (PackagesDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PACKAGES);
    @Override
    public PackageDto searchPackage(String searchId) throws SQLException, ClassNotFoundException {
        Package aPackage = packagesDAO.search(searchId);
        if (aPackage != null) {
            return new PackageDto(aPackage.getPackage_id(),aPackage.getPackage_name(),aPackage.getPackage_type(), aPackage.getPackage_description(),aPackage.getPackage_price());
        } else {
            return null;
        }

    }

    @Override
    public String generateNextPackageID() throws SQLException, ClassNotFoundException {
        return packagesDAO.generateNextId();
    }

    @Override
    public boolean savePackage(PackageDto dto) throws SQLException, ClassNotFoundException {
        return packagesDAO.save(new Package(dto.getPackage_id(),dto.getPackage_name(),dto.getPackage_type(), dto.getPackage_description(),dto.getPackage_price()));
    }

    @Override
    public boolean updatePackage(PackageDto dto) throws SQLException, ClassNotFoundException {
        return packagesDAO.update(
                new Package(
                        dto.getPackage_id(),
                        dto.getPackage_name(),
                        dto.getPackage_type(),
                        dto.getPackage_description(),
                        dto.getPackage_price()
                )
        );
    }

    @Override
    public boolean deletePackage(String id) throws SQLException, ClassNotFoundException {
        return packagesDAO.delete(id);
    }

    @Override
    public List<PackageDto> getAllPackage() throws SQLException, ClassNotFoundException {
        ArrayList<PackageDto> packageDto = new ArrayList<>();
        List<Package> packages = packagesDAO.getAll();

        for (Package packagess : packages) {
            packageDto.add(new PackageDto(packagess.getPackage_id(),packagess.getPackage_name(),packagess.getPackage_type(),packagess.getPackage_description(),packagess.getPackage_price()));
        }
        return packageDto;
    }
}
