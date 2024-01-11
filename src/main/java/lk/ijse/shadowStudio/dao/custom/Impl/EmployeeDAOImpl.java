package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.Entity.Complain;
import lk.ijse.shadowStudio.Entity.Employee;
import lk.ijse.shadowStudio.dao.SQLUtil;
import lk.ijse.shadowStudio.dao.custom.EmployeeDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public boolean save(Employee dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO employee VALUES(?,?,?,?,?)",
                dto.getEmp_id(),
                dto.getEmp_name(),
                dto.getEmp_address(),
                dto.getEmp_nic(),
                dto.getEmp_tp()
        );
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) throws SQLException {
        if(currentId != null) {
            String[] split = currentId.split("[E]");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return String.format("E%03d",id);
        } else {
            return "E001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("delete from employee where emp_id = ?",id);
    }

    @Override
    public boolean update(Employee dto) throws SQLException {
        return SQLUtil.execute("UPDATE employee SET emp_name = ?, emp_address = ?, emp_nic = ?, emp_tp = ? WHERE emp_id = ?",
                dto.getEmp_name(),
                dto.getEmp_address(),
                dto.getEmp_nic(),
                dto.getEmp_tp(),
                dto.getEmp_id()
        );
    }

    @Override
    public List<Employee> getAll() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT * from employee");

        ArrayList<Employee> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new Employee(
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
    public Employee search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * from employee where emp_id=?",id);

        Employee dto = null;

        if(resultSet.next()) {
            String emp_id = resultSet.getString(1);
            String emp_name = resultSet.getString(2);
            String emp_address = resultSet.getString(3);
            String emp_nic = resultSet.getString(4);
            String emp_tp= resultSet.getString(5);

            dto = new Employee(emp_id,emp_name,emp_address,emp_nic,emp_tp);
        }
        return dto;
    }
}
