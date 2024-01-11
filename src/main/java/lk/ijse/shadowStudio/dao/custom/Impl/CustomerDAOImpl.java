package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.Entity.Customer;
import lk.ijse.shadowStudio.dao.SQLUtil;
import lk.ijse.shadowStudio.dao.custom.CustomerDAO;
import lk.ijse.shadowStudio.dto.CustomerDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO{

    @Override
    public boolean save(Customer dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?,?)",dto.getCust_id(),dto.getCust_Name(),
                dto.getCust_address(),dto.getCust_nic(),dto.getCust_tp(),dto.getCust_email());
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT cust_id FROM customer ORDER BY cust_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) throws SQLException {
        if(currentId != null) {
            String[] split = currentId.split("[C]");
            int id = Integer.parseInt(split[1]); //01
            id++;
            return String.format("C%03d",id);
        } else {
            return "C001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("delete from customer where cust_id = ?",id);
    }

    @Override
    public boolean update(Customer dto) throws SQLException {
        return SQLUtil.execute("UPDATE customer SET cust_name = ?, cust_address = ?, cust_nic = ?, cust_tp = ?, cust_email = ? WHERE cust_id = ?",
                dto.getCust_Name(),dto.getCust_address(),dto.getCust_nic(),dto.getCust_tp(),dto.getCust_email(),dto.getCust_id());
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT * from customer");



        ArrayList<Customer> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new Customer(
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

    @Override
    public Customer search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * from customer where cust_id=?",id);

        Customer dto = null;

        if(resultSet.next()) {
            String cust_id = resultSet.getString(1);
            String cust_name = resultSet.getString(2);
            String cust_address = resultSet.getString(3);
            String cust_nic = resultSet.getString(4);
            String cust_tp = resultSet.getString(5);
            String cust_email = resultSet.getString(6);

            dto = new Customer(cust_id, cust_name, cust_address,cust_nic,cust_tp,cust_email);
        }
        return dto;
    }

    public Customer searchCustomerByTp(String tp) throws SQLException {
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "SELECT * FROM customer WHERE cust_tp = ?";
//        PreparedStatement ptsm = connection.prepareStatement(sql);
//        ptsm.setString(1, tp);
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE cust_tp = ?",tp);

        Customer dto = null;
        if (resultSet.next()) {
            String cust_id = resultSet.getString(1);
            String cust_name = resultSet.getString(2);
            String cust_address = resultSet.getString(3);
            String cust_nic = resultSet.getString(4);
            String cust_tp = resultSet.getString(5);
            String cust_email = resultSet.getString(6);

            dto = new Customer(cust_id, cust_name, cust_address,cust_nic,cust_tp,cust_email);
        }
        return dto;
    }
}
