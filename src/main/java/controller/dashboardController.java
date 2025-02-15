package controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {
    public javafx.scene.layout.AnchorPane AnchorPane;
    public javafx.scene.layout.AnchorPane dashboard;

    public Button btnRoom;
    public Button btnDashboard1;
    public Button btnReservation;
    public Button btnReports;
    public Button btnSetting;
    public Button btnLogOut;
    public javafx.scene.layout.AnchorPane main;
    public Button btnBilling;
    public Button btnUsers;
    public Button btnRegister;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void loadView(String fxmlPath) throws IOException {
        URL resource = getClass().getResource(fxmlPath);

        if (resource == null) {
            System.err.println("FXML file not found: " + fxmlPath);
            return;
        }

        System.out.println("Loading FXML: " + resource); // Debugging

        FXMLLoader loader = new FXMLLoader(resource);
        Parent load = loader.load();

        if (AnchorPane != null) {
            AnchorPane.getChildren().clear();
            AnchorPane.getChildren().add(load);
        } else {
            System.err.println("Error: AnchorPane is null.");
        }
    }

    // Rooms
    public void rooms(ActionEvent actionEvent) throws IOException { loadView("/view/rooms.fxml"); }
    public void btnRoomOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnRoom); }
    public void btnRoomOnMouseExited(MouseEvent mouseEvent) { btnExit(btnRoom); }
    public void btnRoomsOnClick(MouseEvent mouseEvent) { click(btnRoom); }

    // Dashboard
    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException { loadView("/view/dashboard.fxml"); }
    public void btnDashboardOnClick(MouseEvent mouseEvent) { click(btnDashboard1); }
    public void btnDashboardOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnDashboard1); }
    public void btnDashboardOnMouseExited(MouseEvent mouseEvent) { btnExit(btnDashboard1); }

    // Reservation
    public void Reservation(ActionEvent actionEvent) throws IOException { loadView("/view/reservatins.fxml"); }
    public void btnReservationOnClick(MouseEvent mouseEvent) { click(btnReservation); }
    public void btnReservationOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnReservation); }
    public void btnReservationOnMouseExited(MouseEvent mouseEvent) { btnExit(btnReservation); }

    // Reports
    public void Reports(ActionEvent actionEvent) throws IOException { loadView("/view/reports.fxml"); }
    public void btnReportsOnClick(MouseEvent mouseEvent) { click(btnReports); }
    public void btnReportsOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnReports); }
    public void btnReportsOnMouseExited(MouseEvent mouseEvent) { btnExit(btnReports); }

    // Settings
    public void Setting(ActionEvent actionEvent) throws IOException { loadView("/view/setting.fxml"); }
    public void btnSettingOnClick(MouseEvent mouseEvent) { click(btnSetting); }
    public void btnSettingOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnSetting); }
    public void btnSettingOnMouseExited(MouseEvent mouseEvent) { btnExit(btnSetting); }


    // Billing
    public void Billing(ActionEvent actionEvent) throws IOException {loadView("/view/billing.fxml");}
    public void btnBillingOnMouseExited(MouseEvent mouseEvent) {btnExit(btnBilling);}
    public void btnBillingOnMouseEntered(MouseEvent mouseEvent) {btnEntered(btnBilling);}
    public void btnBillingOnClick(MouseEvent mouseEvent) {click(btnBilling);}

    //Users
    public void Users(ActionEvent actionEvent) throws IOException {loadView("/view/users.fxml");}
    public void btnUsersOnClick(MouseEvent mouseEvent) {click(btnUsers);}
    public void btnUsersOnMouseEntered(MouseEvent mouseEvent) {btnEntered(btnUsers);}
    public void btnUsersOnMouseExited(MouseEvent mouseEvent) {btnExit(btnUsers);}

    //Register
    public void Register(ActionEvent actionEvent) throws IOException {loadView("/view/register.fxml");}
    public void btnRegisterOnClick(MouseEvent mouseEvent) {click(btnRegister);}
    public void btnRegisterOnMouseEntered(MouseEvent mouseEvent) {btnEntered(btnRegister);}
    public void btnRegisterOnMouseExited(MouseEvent mouseEvent) {btnExit(btnRegister);}

















    // LogOut
    public void LogOut(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to log out?");

        // Apply the custom CSS to the alert's buttons and dialog
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/values/style.css").toExternalForm());

        // Show the alert and wait for the user's response
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            // If the user clicks OK, close the current stage and open the new one
            Stage currentStage = (Stage) btnLogOut.getScene().getWindow();
            currentStage.close();

            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/addCustomerForm.fxml"))));
            stage.show();
        }
    }

    public void btnLogOutOnClick(MouseEvent mouseEvent) { click(btnLogOut); }
    public void btnLogOutOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnLogOut); }
    public void btnLogOutOnMouseExited(MouseEvent mouseEvent) { btnExit(btnLogOut); }





    // Button hover and click effects
    private void btnEntered(Button x) {
        x.setStyle("-fx-background-color:#C9E6F0;-fx-background-radius: 9px");
    }

    private void btnExit(Button x) {

        x.setStyle("-fx-background-color:white;-fx-background-radius: 9px");;

    }

    private void click(Button x) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(50), x);
        x.setStyle("-fx-background-color: white;");
        scale.setOnFinished(e -> x.setStyle("-fx-background-color:#C9E6F0;-fx-background-radius: 9px")
        );
        scale.play();


    }



}
