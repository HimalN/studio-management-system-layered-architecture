package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.dao.SQLUtil;
import lk.ijse.shadowStudio.dto.CustomerDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl {
    public ArrayList<CustomerDto>getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer");
        ArrayList<CustomerDto>getAllCustomers = new ArrayList<>();
        while (rst.next()){
            CustomerDto customerDto =
                    new CustomerDto(
                            rst.getString("cust_id"),
                            rst.getString("cust_name"),
                            rst.getString("cust_address"),
                            rst.getString("cust_nic"),
                            rst.getString("cust_tp"),
                            rst.getString("cust_email")
                    );
            getAllCustomers.add(customerDto);
        }
        return getAllCustomers;

    }


}
