package service.custom.impl;

import dto.Customer;
import dto.Reservations;
import dto.Room;

import service.custom.BillingService;
import util.Availability;
import util.CrudUtil;

import util.RoomType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillingServiceImpl implements BillingService {
    @Override
    public void calculateTotalOnAction() {
    }

    @Override
    public void checkOutOnAction() {
    }

    @Override
    public void lodeDate() {
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
    public List<Room> getAllRooms() {


        List<Room>  roomList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM rooms");
            while (resultSet.next()) {
                roomList.add(new Room(
                        resultSet.getInt("room_id"),
                        resultSet.getString("room_number"),
                        RoomType.valueOf(resultSet.getString("room_type")),
                        resultSet.getDouble("price_per_night"),
                        Availability.valueOf(resultSet.getString("availability")),
                        resultSet.getString("image_path")


                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomList;

    }

    @Override
    public Customer searchCustomer(String customerID) {
        String SQL = "SELECT * FROM customers WHERE customer_id = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, customerID);
            if (resultSet.next()) {
                return new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching for customer", e);
        }
        return null; //
    }

    @Override
    public Room searchRoom(String roomID) {
        String SQL = "SELECT * FROM rooms WHERE room_id = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, roomID);
            if (resultSet.next()) {
                return new Room(
                        resultSet.getInt("room_id"),
                        resultSet.getString("room_number"),
                        RoomType.valueOf(resultSet.getString("room_type")),
                        resultSet.getDouble("price_per_night"),
                        Availability.valueOf(resultSet.getString("availability")),
                        resultSet.getString("image_path")

                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching for room", e);
        }
        return null;
    }

    @Override
    public boolean saveReservation(Reservations reservation) throws SQLException {


        return true;
    }


}
