package lk.ijse.shadowStudio.BO.custom.Impl;

import lk.ijse.shadowStudio.BO.custom.EmployeeBO;
import lk.ijse.shadowStudio.Entity.Complain;
import lk.ijse.shadowStudio.Entity.Employee;
import lk.ijse.shadowStudio.dao.DAOFactory;
import lk.ijse.shadowStudio.dao.custom.ComplainsDAO;
import lk.ijse.shadowStudio.dao.custom.EmployeeDAO;
import lk.ijse.shadowStudio.dto.ComplainDto;
import lk.ijse.shadowStudio.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO{
        EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);


        @Override
        public EmployeeDto searchEmployee(String searchId) throws SQLException, ClassNotFoundException {
                Employee employee = employeeDAO.search(searchId);
                if (employee != null) {
                        return new EmployeeDto(
                                employee.getEmp_id(),
                                employee.getEmp_name(),
                                employee.getEmp_address(),
                                employee.getEmp_nic(),
                                employee.getEmp_tp()
                        );
                } else {
                        return null;
                }
        }

        @Override
        public String generateNextEmployeeID() throws SQLException, ClassNotFoundException {
                return employeeDAO.generateNextId();
        }

        @Override
        public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
                return employeeDAO.save(
                        new Employee(
                                dto.getEmp_id(),
                                dto.getEmp_name(),
                                dto.getEmp_address(),
                                dto.getEmp_nic(),
                                dto.getEmp_tp()
                        )
                );
        }

        @Override
        public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
                return employeeDAO.update(
                        new Employee(
                                dto.getEmp_id(),
                                dto.getEmp_name(),
                                dto.getEmp_address(),
                                dto.getEmp_nic(),
                                dto.getEmp_tp()
                        )
                );
        }

        @Override
        public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
                return employeeDAO.delete(id);
        }

        @Override
        public List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException {
                ArrayList<EmployeeDto> employeeDto = new ArrayList<>();
                List<Employee> employees = employeeDAO.getAll();

                for (Employee employee : employees) {
                        employeeDto.add(
                                new EmployeeDto(
                                        employee.getEmp_id(),
                                        employee.getEmp_name(),
                                        employee.getEmp_address(),
                                        employee.getEmp_nic(),
                                        employee.getEmp_tp()
                                )
                        );
                }
                return employeeDto;
        }

}
