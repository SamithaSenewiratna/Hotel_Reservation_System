package controller;

import dto.Reservations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.custom.ResevationService;
import service.serviceFactory;
import util.CrudUtil;
import util.ServiceType;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {

    private final ResevationService service = serviceFactory.getInstance().getServiceType(ServiceType.RESERVATION);

    public Label lblTotalBooking;
    public Label lblTotalRevenue;
    public Label lblAvalableRoomCount;
    public Label lblOccupiedRoomCount;
    public Label lblMaintenceRoomCount;
    public Label lblReservedRoomCount;
    public Label lblTotalCustomers;
    public Label lblOccupacyRate;

    public TableView<Reservations> tblResentReservations;
    public TableColumn<Reservations, String> colCustomerName;
    public TableColumn<Reservations, String> colRoomID;
    public TableColumn<Reservations, String> colCheckIn;
    public TableColumn<Reservations, String> colCheckOut;
    public TableColumn<Reservations, Double> colAmount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        verifyFXMLLinks();
        loadDashboardData();
        loadTable();

        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colRoomID.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
    }

    private void verifyFXMLLinks() {
        if (lblTotalBooking == null || lblTotalRevenue == null || lblAvalableRoomCount == null ||
                lblOccupiedRoomCount == null || lblMaintenceRoomCount == null || lblReservedRoomCount == null ||
                lblTotalCustomers == null || lblOccupacyRate == null) {
            System.err.println("FXML fields are not linked properly. Check your FXML file.");
        }
        if (tblResentReservations == null || colCustomerName == null || colRoomID == null ||
                colCheckIn == null || colCheckOut == null || colAmount == null) {
            System.err.println("Table or columns are not linked properly. Check your FXML file.");
        }
    }

    private void loadTable() {
        try {
            List<Reservations> allReservations = service.getAll();
            ObservableList<Reservations> reservationObservableList = FXCollections.observableArrayList(allReservations);
            tblResentReservations.setItems(reservationObservableList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDashboardData() {
        try {
            updateLabel(lblTotalBooking, "SELECT COUNT(*) AS totalBookings FROM reservations WHERE reservation_status = 'Confirmed'", "totalBookings");
            updateLabel(lblTotalRevenue, "SELECT SUM(total_amount) AS totalRevenue FROM reservations WHERE reservation_status = 'Confirmed'", "totalRevenue", "$");
            updateLabel(lblAvalableRoomCount, "SELECT COUNT(*) AS availableRooms FROM rooms WHERE availability = 'Available'", "availableRooms");
            updateLabel(lblOccupiedRoomCount, "SELECT COUNT(*) AS occupiedRooms FROM rooms WHERE availability = 'Occupied'", "occupiedRooms");
            updateLabel(lblMaintenceRoomCount, "SELECT COUNT(*) AS maintenanceRooms FROM rooms WHERE availability = 'Maintenance'", "maintenanceRooms");
            updateLabel(lblReservedRoomCount, "SELECT COUNT(DISTINCT room_id) AS reservedRooms FROM reservations WHERE reservation_status IN ('Confirmed', 'Pending')", "reservedRooms");
            updateLabel(lblTotalCustomers, "SELECT COUNT(DISTINCT customer_id) AS totalCustomers FROM reservations", "totalCustomers");


            int totalRooms = getCount("SELECT COUNT(*) AS totalRooms FROM rooms");
            int occupiedRooms = getCount("SELECT COUNT(*) AS occupiedRooms FROM rooms WHERE availability = 'Occupied'");
            double occupancyRate = (totalRooms > 0) ? ((double) occupiedRooms / totalRooms) * 100 : 0;
            lblOccupacyRate.setText(String.format("%.2f%%", occupancyRate));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateLabel(Label label, String query, String column) throws SQLException {
        updateLabel(label, query, column, "");
    }

    private void updateLabel(Label label, String query, String column, String prefix) throws SQLException {
        if (label == null) {
            System.err.println("Label is not linked properly to the FXML: " + column);
            return;
        }
        ResultSet rs = CrudUtil.execute(query);
        if (rs.next()) {
            label.setText(prefix + rs.getString(column));
        }
    }

    private int getCount(String query) throws SQLException {
        ResultSet rs = CrudUtil.execute(query);
        return rs.next() ? rs.getInt(1) : 0;
    }
}
