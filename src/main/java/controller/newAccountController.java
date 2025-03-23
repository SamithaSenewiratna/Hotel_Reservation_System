package controller;

import dto.User;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.custom.UserService;
import service.serviceFactory;
import util.ServiceType;
import util.UserRole;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;



public class newAccountController implements Initializable {
    public AnchorPane anchorPane2;
    public CheckBox checkBox;
    public TextField txtPassword;
    public TextField txtConfirmPassword;
    public ComboBox cmbRole;
    public TextField txtName;
    public TextField txtPhoneNumber;
    public TextField txtEmails;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cmbRole.setItems(FXCollections.observableArrayList(UserRole.values()));
    }



    public void btnCreateAccountOnAction(ActionEvent actionEvent) throws IOException {

        UserService service = serviceFactory.getInstance().getServiceType(ServiceType.USER);

            if (!isValid()) {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields correctly").show();
                return;
            }

        String plainPassword = txtPassword.getText();
        String hashedPassword = hashPassword(plainPassword);

        UserRole selectedRole = (UserRole) cmbRole.getValue();
            if (selectedRole == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a role").show();
                return;
            }

                    User user = new User(
                            txtName.getText(),
                            txtPhoneNumber.getText(), // Corrected from txtPhone
                            txtEmails.getText(),
                            hashedPassword,
                            selectedRole
                    );

        if (service.saveUser(user)) {
            loadTable();
            clearFields();
            navigateToAddCustomerForm(actionEvent);
        } else {
            new Alert(Alert.AlertType.ERROR, "User Not Added").show();
        }
    }


    private boolean isValid() {
        if (txtName.getText().trim().isEmpty() || txtPhoneNumber.getText().trim().isEmpty()
                || txtPassword.getText().trim().isEmpty() || txtConfirmPassword.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields must be filled").show();
            return false;
        }

        if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
            return false;
        }

        return true;
    }



    private void navigateToAddCustomerForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/addCustomerForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("New Account Form");
        stage.show();
    }


    private String hashPassword(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString(); // 40-character hash
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private void loadTable() {

    }

    private void clearFields() {
        txtName.clear();
        txtPhoneNumber.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        txtEmails.clear();
        cmbRole.getSelectionModel().clearSelection();
    }

    public void backToLoginOnAction(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/addCustomerForm.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("");
        stage.show();

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), stage.getScene().getRoot());
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

    }


}
