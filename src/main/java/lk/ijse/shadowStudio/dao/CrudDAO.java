package lk.ijse.shadowStudio.dao;

import lk.ijse.shadowStudio.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO <T> extends SuperDAO {
    boolean save(T dto) throws SQLException;
    String generateNextId() throws SQLException;
    boolean delete(String cust_id) throws SQLException;
    boolean update(T dto) throws SQLException;
    List<T> getAll() throws SQLException;
    T searchCustomer(String id) throws SQLException;
    T searchCustomerByTp(String tp) throws SQLException;



}
