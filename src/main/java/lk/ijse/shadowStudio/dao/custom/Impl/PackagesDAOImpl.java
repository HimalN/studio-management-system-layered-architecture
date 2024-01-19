package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.Entity.Complain;
import lk.ijse.shadowStudio.Entity.Package;
import lk.ijse.shadowStudio.dao.SQLUtil;
import lk.ijse.shadowStudio.dao.custom.PackagesDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackagesDAOImpl implements PackagesDAO {
    @Override
    public boolean save(Package dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO packages VALUES(?,?,?,?,?)",
                dto.getPackage_id(),
                dto.getPackage_name(),
                dto.getPackage_type(),
                dto.getPackage_description(),
                dto.getPackage_price()
        );
    }
    @Override
    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT package_id FROM packages ORDER BY package_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) throws SQLException {
        if(currentId != null) {
            String[] split = currentId.split("[P]");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return String.format("P%03d",id);
        } else {
            return "P001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("delete from packages where package_id = ?",id);
    }

    @Override
    public boolean update(Package dto) throws SQLException {
        return SQLUtil.execute("UPDATE packages SET package_name = ?, package_type = ?, package_description = ? , package_price = ? WHERE package_id = ?",
                dto.getPackage_name(),
                dto.getPackage_type(),
                dto.getPackage_description(),
                dto.getPackage_price(),
                dto.getPackage_id()
        );
    }

    @Override
    public List<Package> getAll() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT * from packages");

        ArrayList<Package> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new Package(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    )
            );
        }
        return dtoList;

    }

    @Override
    public Package search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * from packages where package_id=?",id);

        Package dto = null;

        if(resultSet.next()) {
            String package_id = resultSet.getString(1);
            String package_name = resultSet.getString(2);
            String package_type = resultSet.getString(3);
            String package_description = resultSet.getString(4);
            String package_price = resultSet.getString(5);

            dto = new Package(package_id,package_name,package_type,package_description,package_price);
        }
        return dto;
    }
}
