package lk.ijse.shadowStudio.dao.custom;


import lk.ijse.shadowStudio.Entity.Customer;
import lk.ijse.shadowStudio.dao.CrudDAO;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    Customer searchCustomerByTp(String tp) throws SQLException;
}
