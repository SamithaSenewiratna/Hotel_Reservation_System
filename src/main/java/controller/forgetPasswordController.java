package controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import util.CrudUtil;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class forgetPasswordController implements Initializable {

    public PasswordField txtNewPassword;
    public PasswordField txtReEnterPassword;
    public TextField txtOtp;

    otpController o1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        txtReEnterPassword.setVisible(false);
        txtNewPassword.setVisible(false);
        txtOtp.requestFocus();

    }

    public void btnUpdatePasswordOnAction(ActionEvent actionEvent) throws SQLException, IOException {

     String email =otpController.sendemail();
        String plainPassword = txtNewPassword.getText();
        String hashedPassword = hashPassword(plainPassword);

        if ( txtNewPassword.getText().equals(txtReEnterPassword.getText())) {

            String sql = "UPDATE user SET password_hash = ? WHERE email = ?";
            boolean success = CrudUtil.execute(sql,hashedPassword,email);

            if (success) {
                System.out.println("Password updated successfully!");

              clearFeild();

                Parent root = FXMLLoader.load(getClass().getResource("../view/addCustomerForm.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.setTitle("");
                stage.show();

                FadeTransition fadeIn = new FadeTransition(Duration.millis(500), stage.getScene().getRoot());
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();


            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invaild Email");
                alert.setHeaderText("Invaild Email !");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/values/style.css").toExternalForm());
                alert.show();

                System.out.println("Failed to update password. Email not found.");
            }


        }



    }

    private void clearFeild() {
        txtOtp.clear();
        txtNewPassword.clear();
        txtReEnterPassword.clear();

    }

    public void btnVerfyOtpOnAction(ActionEvent actionEvent) throws SQLException {
        String otp = otpController.sendotp();

        if(txtOtp.getText().equals(otp)) {

            txtNewPassword.setVisible(true);
            txtReEnterPassword.setVisible(true);


        }else{

            new Alert(Alert.AlertType.ERROR, "Invalid OTP!").show();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invaild OTP");
            alert.setHeaderText("Invaild OTP !");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/values/style.css").toExternalForm());

            }

    }



    public void btnBackOnAction(MouseEvent mouseEvent) throws IOException {

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


    public static void updatePassword(String email, String newPassword) throws SQLException {
        String query = "UPDATE user SET password = ? WHERE email = ?";
        PreparedStatement stmt =  CrudUtil.execute(query);

        stmt.setString(1, newPassword);
            stmt.setString(2, email);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Password updated successfully!");

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



}
