package service.custom;

import dto.Customer;
import dto.Reservations;
import dto.Room;
import javafx.collections.ObservableList;
import service.superService;

import java.sql.SQLException;
import java.util.List;

public interface BillingService extends superService {
    void calculateTotalOnAction();
    void checkOutOnAction();
    void lodeDate();

    List<Customer> getAll();
    List<Room> getAllRooms();

    Customer searchCustomer(String customerID);

    Room searchRoom(String roomID);
    boolean saveReservation(Reservations reservation) throws SQLException;
}
