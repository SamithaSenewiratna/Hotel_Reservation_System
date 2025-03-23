package service.custom;

import dto.Customer;
import javafx.collections.ObservableList;
import service.superService;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService extends superService {


    List<Customer> getAll();
    boolean saveCustomer(Customer customer) throws SQLException;
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(String customerId);


    Customer searchCustomer(String customerId);



}
