package repository.custom.impl;


import entity.CustomerEntity;
import repository.custom.CustomerDao;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;


public class CustomerDaoImpl implements CustomerDao {

    @Override
    public boolean save(CustomerEntity entity) {


        try {
            String SQL = "INSERT INTO customer ( name, address, contact_details, loyalty_points) VALUES (?, ?, ?, ?)";
            return CrudUtil.execute(SQL,

                    entity.getName(),
                    entity.getAddress(),
                    entity.getContactDetails(),
                    entity.getLoyaltyPoints()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public CustomerEntity search(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        String SQL = "DELETE FROM customer WHERE customer_id = ?";
        try {
            return CrudUtil.execute(SQL,id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(CustomerEntity entity) {


        String SQL = "UPDATE customer SET name=?, address=?, contact_details=?, loyalty_points=? WHERE customer_id=? ";

        try {
            return CrudUtil.execute(SQL,

                    entity.getName(),
                    entity.getAddress(),
                    entity.getContactDetails(),
                    entity.getLoyaltyPoints(),
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
