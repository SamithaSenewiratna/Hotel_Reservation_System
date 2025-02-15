package controller;

import dto.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.custom.CustomerService;
import service.serviceFactory;
import util.ServiceType;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;



public class RegisterFormController implements Initializable {
    public TextField txtSearch;
    public TextField txtLoyalPoint;
    public TextField txtContactDetails;
    public TextField txtAddress;
    public TextField txtCustomerName;

    public TableView tblCustomers;
    public TableColumn colAddress;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colContactDetail;
    public TableColumn colPoint;
    public Label lblID;


    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    CustomerService service = serviceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initialize the other columns
        colID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactDetail.setCellValueFactory(new PropertyValueFactory<>("contactDetails"));
        colPoint.setCellValueFactory(new PropertyValueFactory<>("loyaltyPoints"));

        // Define the delete button column
        TableColumn<Customer, Void> colDelete = new TableColumn<>("");

        // Define the button cell factory
        colDelete.setCellFactory(new Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>>() {
            @Override
            public TableCell<Customer, Void> call(TableColumn<Customer, Void> param) {
                return new TableCell<Customer, Void>() {
                    private final Button btnDelete = new Button("Delete");

                    {
                        btnDelete.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                        btnDelete.setOnAction(event -> {
                            Customer customer = getTableView().getItems().get(getIndex()); // Get the customer from the current row
                            deleteCustomer(customer); // Call your delete method with the customer
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null); // Remove button if the row is empty
                        } else {
                            setGraphic(btnDelete); // Show button
                        }
                    }
                };
            }
        });

        // Add the delete column to the TableView
        tblCustomers.getColumns().add(colDelete);

        loadTable(); // Load table data




        tblCustomers.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextValues((Customer) newValue);}));

    }



    private void deleteCustomer(Customer customer) {
        // Your code to delete the customer
        boolean isDeleted = service.deleteCustomer(String.valueOf(customer.getCustomerID()));
        if (isDeleted) {
            loadTable(); // Reload the table after deletion
        } else {
            new Alert(Alert.AlertType.ERROR, "Error deleting customer").show();
        }
    }








        private void setTextValues(Customer newValue) {
            lblID.setText(String.valueOf(newValue.getCustomerID()));
            txtCustomerName.setText(newValue.getName());
            txtAddress.setText(newValue.getAddress());
            txtContactDetails.setText(newValue.getContactDetails());
            txtLoyalPoint.setText(String.valueOf(newValue.getLoyaltyPoints()));

        }





    public void addCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        CustomerService customerService= serviceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

        Customer customer = new Customer(

                txtCustomerName.getText(),
                txtAddress.getText(),
                txtContactDetails.getText(),
                Integer.parseInt(txtLoyalPoint.getText())

        );

        if(customerService.saveCustomer(customer)){

             loadTable();
            clearFields();


        }else {
            new Alert(Alert.AlertType.ERROR,"Customer Not Added : (").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        CustomerService customerService = serviceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

        // Get the customer ID from lblID
        int customerId = Integer.parseInt(lblID.getText()); // Make sure lblID is populated

        // Create the updated customer object with the customer ID
        Customer customer = new Customer(
                customerId,  // Include the customer ID
                txtCustomerName.getText(),
                txtAddress.getText(),
                txtContactDetails.getText(),
                Integer.parseInt(txtLoyalPoint.getText())
        );

        if (customerService.updateCustomer(customer)) {

            loadTable();
            clearFields();
        } else {

        }

    }

    public void deleteOnAction(ActionEvent actionEvent) {
        if(service.deleteCustomer(lblID.getText())){
            loadTable();


        }

    }







    private void clearFields() {
        txtCustomerName.clear();
        txtAddress.clear();
        txtContactDetails.clear();
        txtLoyalPoint.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadTable() {
        List<Customer> all = service.getAll();
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        all.forEach(customer -> {
            customerObservableList.add(customer);
        });
        tblCustomers.setItems(customerObservableList);
    }




}




