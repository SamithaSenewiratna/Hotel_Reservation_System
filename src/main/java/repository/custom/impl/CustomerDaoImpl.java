package repository.custom.impl;


import entity.CustomerEntity;
import repository.custom.CustomerDao;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class CustomerDaoImpl implements CustomerDao {

    @Override
    public boolean save(CustomerEntity entity) {

        try {
            String SQL = "INSERT INTO customers ( name, email, phone, address) VALUES (?, ?, ?, ?)";
            return CrudUtil.execute(SQL,

                    entity.getName(),
                    entity.getEmail(),
                    entity.getContactDetails(),
                    entity.getAddress()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public CustomerEntity search(String customerId) {

            try {
                String SQL = "SELECT * FROM customers WHERE customer_id = ?";
                ResultSet resultSet = CrudUtil.execute(SQL, customerId);
                if (resultSet.next()) {
                    return new CustomerEntity(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    );
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error searching for customer", e);
            }
            return null;
    }

    @Override
    public boolean delete(String id) {
        String SQL = "DELETE FROM customers WHERE customer_id = ?";
            try {
                return CrudUtil.execute(SQL,id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public boolean update(CustomerEntity entity) {

        String SQL = "UPDATE customers SET name=?, email=?, phone=?, address=? WHERE customer_id=? ";

            try {
                return CrudUtil.execute(SQL,

                        entity.getName(),
                        entity.getEmail(),
                        entity.getContactDetails(),
                        entity.getAddress(),
                        entity.getCustomerId()
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public List<CustomerEntity> getAll() {
        return List.of();
    }
}
