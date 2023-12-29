package lk.ijse.shadowStudio.model;

import lk.ijse.shadowStudio.db.DbConnection;
import lk.ijse.shadowStudio.dto.CustomerDto;
import lk.ijse.shadowStudio.dto.EmployeeDto;
import lk.ijse.shadowStudio.dto.ItemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {


    public static boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmp_id());
        pstm.setString(2,dto.getEmp_name());
        pstm.setString(3,dto.getEmp_address());
        pstm.setString(4,dto.getEmp_nic());
        pstm.setString(5,dto.getEmp_tp());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public static String generateNextEmployeeId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitEmployeeId(resultSet.getString(1));
        }
        return splitEmployeeId(null);
    }
    private static String splitEmployeeId(String currentEmployeeId) {
        if (currentEmployeeId != null) {
            String[] split = currentEmployeeId.split("[E]");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return String.format("E%03d",id);
        } else {
            return "E001";
        }
    }
    public static boolean deleteEmployee(String emp_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "delete from employee where emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, emp_id);

        return pstm.executeUpdate() > 0;

    }

    public static EmployeeDto searchEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select * from employee where emp_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto dto = null;

        if(resultSet.next()) {
            String emp_id = resultSet.getString(1);
            String emp_name = resultSet.getString(2);
            String  emp_address= resultSet.getString(3);
            String  emp_nic= resultSet.getString(3);
            String emp_tp = resultSet.getString(4);

            dto = new EmployeeDto(emp_id,emp_name,emp_address,emp_nic,emp_tp);
        }
        return dto;
    }

    public static boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET emp_name = ?, emp_address = ?, emp_nic = ? , emp_tp=? WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmp_name());
        pstm.setString(2, dto.getEmp_address());
        pstm.setString(3, dto.getEmp_nic());
        pstm.setString(4, dto.getEmp_tp());
        pstm.setString(5, dto.getEmp_id());

        return pstm.executeUpdate() > 0;
    }

    public List<EmployeeDto> getAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from employee";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();



        ArrayList<EmployeeDto> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new EmployeeDto(
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
