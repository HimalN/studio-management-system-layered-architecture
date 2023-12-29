package lk.ijse.shadowStudio.model;

import lk.ijse.shadowStudio.db.DbConnection;
import lk.ijse.shadowStudio.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public static boolean saveCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCust_id());
        pstm.setString(2,dto.getCust_Name());
        pstm.setString(3,dto.getCust_address());
        pstm.setString(4,dto.getCust_nic());
        pstm.setString(5,dto.getCust_tp());
        pstm.setString(6,dto.getCust_email());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }
    public static String generateNextCustomerId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT cust_id FROM customer ORDER BY cust_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitCustomerId(resultSet.getString(1));
        }
        return splitCustomerId(null);
    }
    private static String splitCustomerId(String currentCustomerId) {
        if(currentCustomerId != null) {
            String[] split = currentCustomerId.split("[C]");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return String.format("C%03d",id);
        } else {
            return "C001";
        }
    }

    public static boolean deleteCustomer(String cust_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "delete from customer where cust_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, cust_id);

        return pstm.executeUpdate() > 0;

    }

    public  boolean updateCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET cust_name = ?, cust_address = ?, cust_nic = ?, cust_tp = ?, cust_email = ? WHERE cust_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getCust_Name());
        pstm.setString(2, dto.getCust_address());
        pstm.setString(3, dto.getCust_nic());
        pstm.setString(4, dto.getCust_tp());
        pstm.setString(5,dto.getCust_email());
        pstm.setString(6, dto.getCust_id());

        return pstm.executeUpdate() > 0;
    }
    public List<CustomerDto> getAllCustomer() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from customer";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();



        ArrayList<CustomerDto> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new CustomerDto(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6)
                    )
            );
        }
        return dtoList;
    }
    public static CustomerDto searchCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from customer where cust_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        CustomerDto dto = null;

        if(resultSet.next()) {
            String cust_id = resultSet.getString(1);
            String cust_name = resultSet.getString(2);
            String cust_address = resultSet.getString(3);
            String cust_nic = resultSet.getString(4);
            String cust_tp = resultSet.getString(5);
            String cust_email = resultSet.getString(6);

            dto = new CustomerDto(cust_id, cust_name, cust_address,cust_nic,cust_tp,cust_email);
        }
        return dto;
    }


    public CustomerDto searchCustomerByTp(String tp) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE cust_tp = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1, tp);
        ResultSet resultSet = ptsm.executeQuery();

        CustomerDto dto = null;
        if (resultSet.next()) {
            String cust_id = resultSet.getString(1);
            String cust_name = resultSet.getString(2);
            String cust_address = resultSet.getString(3);
            String cust_nic = resultSet.getString(4);
            String cust_tp = resultSet.getString(5);
            String cust_email = resultSet.getString(6);

            dto = new CustomerDto(cust_id, cust_name, cust_address,cust_nic,cust_tp,cust_email);
        }
        return dto;
    }
}
