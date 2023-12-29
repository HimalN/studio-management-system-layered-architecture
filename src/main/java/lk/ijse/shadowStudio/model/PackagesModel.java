package lk.ijse.shadowStudio.model;

import lk.ijse.shadowStudio.db.DbConnection;
import lk.ijse.shadowStudio.dto.CustomerDto;
import lk.ijse.shadowStudio.dto.PackageDto;

import java.nio.file.FileAlreadyExistsException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackagesModel {

    public static String generateNextPackageId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT package_id FROM packages ORDER BY package_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitPackageId(resultSet.getString(1));
        }
        return splitPackageId(null);
    }
    private static String splitPackageId(String currentPackageId) {
        if(currentPackageId != null) {
            String[] split = currentPackageId.split("[P]");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return String.format("P%03d",id);
        } else {
            return "P001";
        }
    }


    public static boolean savePackage(PackageDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO packages VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getPackage_id());
        pstm.setString(2,dto.getPackage_name());
        pstm.setString(3,dto.getPackage_type());
        pstm.setString(4,dto.getPackage_description());
        pstm.setString(5,dto.getPackage_price());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public static boolean updateCustomer(PackageDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE packages SET package_name = ?, package_type = ?, package_description = ? , package_price = ? WHERE package_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getPackage_name());
        pstm.setString(2, dto.getPackage_type());
        pstm.setString(3, dto.getPackage_description());
        pstm.setString(4, dto.getPackage_price());
        pstm.setString(5, dto.getPackage_id());

        return pstm.executeUpdate() > 0;
    }

    public static boolean deletePackage(String package_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "delete from packages where package_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, package_id);

        return pstm.executeUpdate() > 0;
    }

    public static PackageDto searchPackage(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select * from packages where package_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        PackageDto dto = null;

        if(resultSet.next()) {
            String package_id = resultSet.getString(1);
            String package_name = resultSet.getString(2);
            String package_type = resultSet.getString(3);
            String package_description = resultSet.getString(4);
            String package_price = resultSet.getString(5);

            dto = new PackageDto(package_id,package_name,package_type,package_description,package_price );
        }
        return dto;
    }

    public List<PackageDto> getAllPackages() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from packages";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        ArrayList<PackageDto> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new PackageDto(
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
}
