package lk.ijse.shadowStudio.dao;

import lk.ijse.shadowStudio.Entity.Package;
import lk.ijse.shadowStudio.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO <T> extends SuperDAO {
    boolean save(T dto) throws SQLException;
    String generateNextId() throws SQLException;
    String splitId(String currentId) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(T dto) throws SQLException;
    List<T> getAll() throws SQLException;
    T search(String id) throws SQLException;
}
