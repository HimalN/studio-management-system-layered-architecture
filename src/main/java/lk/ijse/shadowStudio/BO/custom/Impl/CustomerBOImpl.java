package lk.ijse.shadowStudio.BO.custom.Impl;

import lk.ijse.shadowStudio.BO.custom.CustomerBO;
import lk.ijse.shadowStudio.Entity.Customer;
import lk.ijse.shadowStudio.dao.DAOFactory;
import lk.ijse.shadowStudio.dao.custom.CustomerDAO;
import lk.ijse.shadowStudio.dto.CustomerDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public String generateNextCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNextId();
    }

    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getCust_id(),dto.getCust_Name(),dto.getCust_address(),dto.getCust_nic(), dto.getCust_tp(), dto.getCust_email()));
    }

    @Override
    public List<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDto> customerDto = new ArrayList<>();
        List<Customer> customers = customerDAO.getAll();

        for (Customer customer : customers) {
            customerDto.add(new CustomerDto(customer.getCust_id(),customer.getCust_Name(),customer.getCust_address(),customer.getCust_nic(), customer.getCust_tp(), customer.getCust_email()));
        }
        return customerDto;
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCust_id(),dto.getCust_Name(),dto.getCust_address(),dto.getCust_nic(), dto.getCust_tp(), dto.getCust_email()));
    }

    @Override
    public CustomerDto searchCustomer(String searchId) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(searchId);
        if (customer != null) {
            return new CustomerDto(customer.getCust_id(),customer.getCust_Name(),customer.getCust_address(),customer.getCust_nic(), customer.getCust_tp(), customer.getCust_email());
        } else {
            return null;
        }
    }

    @Override
    public CustomerDto searchCustomerPhoneNumber(String phoneNumber) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.searchCustomerByTp(phoneNumber);
        if (customer != null) {
            return new CustomerDto(customer.getCust_id(),customer.getCust_Name(),customer.getCust_address(),customer.getCust_nic(), customer.getCust_tp(), customer.getCust_email());
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }
}
