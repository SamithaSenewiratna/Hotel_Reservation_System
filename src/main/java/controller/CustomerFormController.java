package controller;

import dto.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.custom.CustomerService;
import service.serviceFactory;
import util.ServiceType;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;



public class CustomerFormController implements Initializable {
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
    public TextField txtEmail;
    public TableColumn colEmail;


    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    CustomerService service = serviceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        colID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactDetail.setCellValueFactory(new PropertyValueFactory<>("contactDetails"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadTable();

        tblCustomers.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextValues((Customer) newValue);}));

    }


    private void deleteCustomer(Customer customer) {

        boolean isDeleted = service.deleteCustomer(String.valueOf(customer.getCustomerID()));
        if (isDeleted) {
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error deleting customer").show();
        }
    }


    private void setTextValues(Customer newValue) {
        if(newValue==null)return;
            lblID.setText(String.valueOf(newValue.getCustomerID()));
            txtCustomerName.setText(newValue.getName());
            txtEmail.setText(newValue.getEmail());
            txtContactDetails.setText(newValue.getContactDetails());
            txtAddress.setText(newValue.getAddress());
        }


    public void addCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        CustomerService customerService= serviceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

        Customer customer = new Customer(

                txtCustomerName.getText(),
                txtEmail.getText(),
                txtContactDetails.getText(),
                txtAddress.getText()

        );

        if(customerService.saveCustomer(customer)){
            clearFields();
            loadTable();


        }else {
            new Alert(Alert.AlertType.ERROR,"Customer Not Added : (").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        CustomerService customerService = serviceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

        int customerId = Integer.parseInt(lblID.getText());

        Customer customer = new Customer(
                customerId,  // Include the customer ID
                txtCustomerName.getText(),
                txtEmail.getText(),
                txtContactDetails.getText(),
                txtAddress.getText()

        );

        if (customerService.updateCustomer(customer)) {
            clearFields();
            loadTable();

        } else {

        }

    }

    public void deleteOnAction(ActionEvent actionEvent) {
        if(service.deleteCustomer(lblID.getText())){
            clearFields();
            loadTable();

        }

    }

    private void clearFields() {
            txtCustomerName.clear();
            txtAddress.clear();
            txtContactDetails.clear();
            txtEmail.clear();
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


    public void btnSearcOnAction(ActionEvent actionEvent) {
        String customerId = txtSearch.getText();
        Customer customer = service.searchCustomer(customerId);
        if (customer != null) {
            txtCustomerName.setText(customer.getName());
            txtEmail.setText(customer.getEmail());
            txtContactDetails.setText(customer.getContactDetails());
            txtLoyalPoint.setText(customer.getAddress());
        } else {
            new Alert(Alert.AlertType.WARNING, "Customer not found").show();
        }
    }
}




