package service.custom.impl;

import dto.Customer;
import entity.CustomerEntity;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.CustomerDao;
import service.custom.CustomerService;
import util.CrudUtil;
import util.DaoType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private static CustomerServiceImpl instance;
    private final CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
    private final ModelMapper modelMapper = new ModelMapper();


    public CustomerServiceImpl() {
    }

    public static CustomerServiceImpl getInstance() {
        return instance == null ? instance = new CustomerServiceImpl() : instance;
    }



    @Override
    public List<Customer> getAll() {
        List<Customer> customerList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM customers");
            while (resultSet.next()) {
                customerList.add(new Customer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }

    @Override
    public boolean saveCustomer(Customer customer) throws SQLException {

        CustomerEntity entity= new ModelMapper().map(customer,CustomerEntity.class);
        CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        customerDao.save(entity);

        return true;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return customerDao.update(modelMapper.map(customer, CustomerEntity.class));

    }

    @Override
    public boolean deleteCustomer(String customerId) {
        return customerDao.delete(customerId);
    }

    @Override
    public Customer searchCustomer(String customerId) {

        CustomerEntity entity = customerDao.search(customerId);
            if (entity != null) {

                return modelMapper.map(entity, Customer.class);
            }
        return null;
    }


}
