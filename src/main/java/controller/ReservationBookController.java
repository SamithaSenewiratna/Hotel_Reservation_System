package controller;

import dto.Reservations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.custom.ResevationService;
import service.serviceFactory;
import util.ReservationStatus;
import util.ServiceType;


import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationBookController implements Initializable {

    @FXML
    public TableView<Reservations> tblReservations;
    @FXML
    public TableColumn<Reservations, String> colReservationID;
    @FXML
    public TableColumn<Reservations, String> colCustomerID;
    @FXML
    public TableColumn<Reservations, String> colRoomID;
    @FXML
    public TableColumn<Reservations, String> colCheckIN;
    @FXML
    public TableColumn<Reservations, String> colCheckOUT;
    @FXML
    public TableColumn<Reservations, Double> colAmount;
    @FXML
    public TableColumn<Reservations, String> colStatus;
    public ComboBox cmbStatus;
    public Label lblID;
    public DatePicker txtCheck_out_date;
    public DatePicker txtCheck_in_date;
    public ComboBox cmbSelectRoom;

    private ObservableList<Reservations> reservationsList = FXCollections.observableArrayList();
    private ResevationService service = serviceFactory.getInstance().getServiceType(ServiceType.RESERVATION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addDeleteButtonToTable();
        load();

    }


    public void load(){

        colReservationID.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colRoomID.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colCheckIN.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        colCheckOUT.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("reservationStatus"));


        tblReservations.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setTextValues((Reservations) newValue);
        });

        cmbStatus.setItems(FXCollections.observableArrayList(ReservationStatus.values()));
        loadTable();

    }



    private void setTextValues(Reservations newValue) {
        if(newValue==null)return;

        cmbStatus.setValue(newValue.getReservationStatus());
        lblID.setText(String.valueOf(newValue.getReservationId()));
        cmbSelectRoom.setValue(newValue.getRoomId());
        txtCheck_in_date.setValue(LocalDate.parse(newValue.getCheckInDate().toString()));
        txtCheck_out_date.setValue(LocalDate.parse(newValue.getCheckOutDate().toString()));
    }







    public void changeStatusUpdateOnAction(ActionEvent actionEvent) {
        Reservations selectedItem = tblReservations.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert(Alert.AlertType.WARNING, "Reservation status update", "Please select a reservation to update!");
            return;
        }

        if (cmbStatus.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Reservation status update", "Please select a status!");
            return;
        }

        selectedItem.setReservationStatus((ReservationStatus) cmbStatus.getValue());

        boolean isUpdated = service.updateReservation(selectedItem);
        loadTable();
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Reservation status update", "Reservation status updated successfully!");
                tblReservations.refresh();
                loadTable();
                load();
            } else {
                showAlert(Alert.AlertType.ERROR, "Reservation status update", "Failed to update reservation status!");
            }

    }


    private void addDeleteButtonToTable() {
        TableColumn<Reservations, Void> colDelete = new TableColumn<>("Action");

        colDelete.setCellFactory(param -> new TableCell<>() {
            private final Button btnDelete = new Button("Delete");

                {
                    btnDelete.setStyle("-fx-background-color: white; -fx-text-fill: red; -fx-border-color: red; -fx-border-width: 1px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
                    btnDelete.setOnAction(event -> {
                        Reservations reservation = getTableView().getItems().get(getIndex());
                        try {
                            deleteReservation(reservation);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnDelete);
                }
            }
        });

        tblReservations.getColumns().add(colDelete);

    }




    private void deleteReservation(Reservations reservation) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this reservation?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            boolean isDeleted = service.deleteReservation(String.valueOf(reservation.getReservationId()));
            if (isDeleted) {
                loadTable();
            } else {

                showAlert(Alert.AlertType.ERROR, "Reservation update ", "Failed to delete reservation!");
            }
        }


    }

    private void loadTable() {
        List<Reservations> allReservations = service.getAll();
        ObservableList<Reservations> reservationObservableList = FXCollections.observableArrayList(allReservations);
        tblReservations.setItems(reservationObservableList);
    }


    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/values/style.css").toExternalForm());
        alert.show();
    }


}
