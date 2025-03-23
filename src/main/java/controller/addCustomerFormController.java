package controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.CrudUtil;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class addCustomerFormController {


    public Button btnlogin;
    public AnchorPane anchorPane2;
    public TextField txtUserName;
    public TextField txtPassword;



    public void addNewAccountButtonOnAction1(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/newAccount.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("");
        stage.show();

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), stage.getScene().getRoot());
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();



    }


    public void logInOnAction(ActionEvent actionEvent) throws IOException, SQLException {

        String userName = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();

        if (userName.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Username and Password are required!").show();
            return;
        }

        if (authenticateUser(userName, password)) {

            Parent root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("");
            stage.show();


            FadeTransition fadeIn = new FadeTransition(Duration.millis(700), stage.getScene().getRoot());
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();



        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Username or Password!").show();
        }
    }


    private boolean authenticateUser(String username, String password) throws SQLException {

        String query = "SELECT * FROM user WHERE username = ? AND password_hash = ?";
        String hashedPassword = hashPassword(password);

        ResultSet rs = CrudUtil.execute(query, username, hashedPassword);

        return rs.next();
    }


    private void navigateToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("");
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

    public void forgetPasswordOnAction(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/OTPController.fxml"));
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
