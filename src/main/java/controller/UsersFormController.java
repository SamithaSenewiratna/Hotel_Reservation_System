package controller;

import com.jfoenix.controls.JFXButton;
import dto.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.custom.UserService;
import service.serviceFactory;
import util.ServiceType;
import util.UserRole;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UsersFormController implements Initializable {

    public TextField txtName;
    public TextField txtPhone;
    public PasswordField txtPassword;
    public ComboBox cmbRole;
    public JFXButton btnUpdate;
    public TextField txtSearchUsers;

    public TableView tblUsers;
    public TableColumn colUserID;
    public TableColumn colName;
    public TableColumn colContactNo;
    public TableColumn colPassword;
    public TableColumn colRole;
    public Label lblUserID;

    public TextField txtEmail;

    private ObservableList<User> userList = FXCollections.observableArrayList();
    UserService service = serviceFactory.getInstance().getServiceType(ServiceType.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colUserID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<User, Void> colDelete = new TableColumn<>("Action");
        colDelete.setCellFactory(new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> param) {
                return new TableCell<User, Void>() {
                    private final Button btnDelete = new Button("Delete");

                    {
                        btnDelete.setStyle("-fx-background-color: white; -fx-text-fill: red; -fx-border-color: red; -fx-border-width: 1px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
                        btnDelete.setOnAction(event -> {
                            int index = getIndex();
                            if (index >= 0 && index < getTableView().getItems().size()) {
                                User user = getTableView().getItems().get(index);

                                deleteUser(user);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnDelete);
                        }
                    }
                };
            }
        });
        tblUsers.getColumns().add(colDelete);

        loadTable();
        tblUsers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setTextValues((User) newValue);
        });

        cmbRole.setItems(FXCollections.observableArrayList(UserRole.values())); // Set enum values

    }


    private void deleteUser(User user) {

        boolean isDeleted = service.deleteUser(String.valueOf(user.getUserId()));

            if (isDeleted) {
                loadTable();

            } else {
                new Alert(Alert.AlertType.ERROR, "Error deleting user").show();
            }
    }

    private void setTextValues(User newValue) {
        if (newValue == null) return;
            lblUserID.setText(String.valueOf(newValue.getUserId()));
            txtName.setText(newValue.getUsername());
            txtPhone.setText(newValue.getContactNumber());
            txtEmail.setText(newValue.getEmail());
            txtPassword.setText(newValue.getPasswordHash());
            cmbRole.setValue(newValue.getRole());
    }


    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException {

        String plainPassword = txtPassword.getText();
        String hashedPassword = hashPassword(plainPassword);



        UserRole selectedRole = (UserRole) cmbRole.getValue();
        if (selectedRole == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a role").show();
            return;
        }

                User user = new User(
                        txtName.getText(),
                        txtPhone.getText(),
                        txtEmail.getText(),
                        hashedPassword,
                        selectedRole
                );
        if (service.saveUser(user)) {
                loadTable();
                clearFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "User Not Added").show();
        }
    }


    private String hashPassword(String password) {

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                byte[] hashedBytes = md.digest(password.getBytes());

                StringBuilder sb = new StringBuilder();
                for (byte b : hashedBytes) {
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error hashing password", e);
            }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        int userId = Integer.parseInt(lblUserID.getText());

        UserRole selectedRole = (UserRole) cmbRole.getValue();
            if (selectedRole == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a role").show();
                return;
            }

                User user = new User(
                        userId,
                        txtName.getText(),
                        txtPhone.getText(),
                        txtEmail.getText(),
                        txtPassword.getText(),
                        selectedRole
                );

        if (service.updateUser(user)) {
            loadTable();
            clearFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "User Not Updated").show();
        }
    }

    public void btnSearchOnction(ActionEvent actionEvent) {
        String userId = txtSearchUsers.getText();
        User user = service.searchUser(userId);
                if (user != null) {
                    txtName.setText(user.getUsername());
                    txtPhone.setText(user.getContactNumber());
                    txtPassword.setText(user.getPasswordHash());
                    cmbRole.setValue(user.getRole());
                } else {
                    new Alert(Alert.AlertType.WARNING, "User not found").show();
                }


    }

    private void clearFields() {
        txtName.clear();
        txtPhone.clear();
        txtEmail.clear();
        txtPassword.clear();
        cmbRole.setValue(null);
    }

    private void loadTable() {
        List<User> allUsers = service.getAll();
        ObservableList<User> userObservableList = FXCollections.observableArrayList(allUsers);
        tblUsers.setItems(userObservableList);
        clearFields();
    }
}
