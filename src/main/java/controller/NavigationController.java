package controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavigationController implements Initializable {
    @FXML
    public AnchorPane AnchorPane;
    @FXML
    public AnchorPane dashboard;
    @FXML
    public Button btnRoom, btnDashboard1, btnReservation, btnReports, btnSetting, btnLogOut, btnBilling, btnUsers, btnRegister;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadView("/view/room.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadView(String fxmlPath) throws IOException {
        URL resource = getClass().getResource(fxmlPath);

        if (resource == null) {
            System.err.println("FXML file not found: " + fxmlPath);
            return;
        }

        FXMLLoader loader = new FXMLLoader(resource);
        Parent load = loader.load();

        if (AnchorPane != null) {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(400), load);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();

            AnchorPane.getChildren().clear();
            AnchorPane.getChildren().add(load);
        } else {
            System.err.println("Error: AnchorPane is null.");
        }
    }

    // Navigation
    public void rooms(ActionEvent actionEvent) throws IOException { loadView("/view/room.fxml"); }
    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException { loadView("/view/dashboard.fxml"); }
    public void Reservation(ActionEvent actionEvent) throws IOException { loadView("/view/reservatins.fxml"); }
    public void Reports(ActionEvent actionEvent) throws IOException { loadView("/view/reports.fxml"); }
    public void Setting(ActionEvent actionEvent) throws IOException { loadView("/view/setting.fxml"); }
    public void Billing(ActionEvent actionEvent) throws IOException { loadView("/view/billing.fxml"); }
    public void Users(ActionEvent actionEvent) throws IOException { loadView("/view/users.fxml"); }
    public void Register(ActionEvent actionEvent) throws IOException { loadView("/view/Customers.fxml"); }

    // Logout Animation
    public void LogOut(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/values/style.css").toExternalForm());

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            Stage currentStage = (Stage) btnLogOut.getScene().getWindow();

            // **Fade Out Animation**
            FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentStage.getScene().getRoot());
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {
                try {
                    currentStage.close();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/addCustomerForm.fxml"))));


                    stage.getScene().getRoot().setOpacity(0);
                    stage.show();
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(600), stage.getScene().getRoot());
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);
                    fadeIn.play();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            fadeOut.play();
        }
    }


    public void btnEntered(Button x) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), x);
        scale.setToX(1.1);
        scale.setToY(1.1);
        scale.play();
        x.setStyle("-fx-background-color:#C9E6F0;-fx-background-radius: 9px");
    }

    public void btnExit(Button x) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), x);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.play();
        x.setStyle("-fx-background-color:white;-fx-background-radius: 9px");
    }


    private void click(Button x) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), x);
        scaleUp.setToX(1.2);
        scaleUp.setToY(1.2);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100), x);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        scaleUp.setOnFinished(e -> scaleDown.play());
        scaleUp.play();
    }

    public void btnRoomOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnRoom); }
    public void btnRoomOnMouseExited(MouseEvent mouseEvent) { btnExit(btnRoom); }
    public void btnRoomsOnClick(MouseEvent mouseEvent) { click(btnRoom); }

    public void btnDashboardOnClick(MouseEvent mouseEvent) { click(btnDashboard1); }
    public void btnDashboardOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnDashboard1); }
    public void btnDashboardOnMouseExited(MouseEvent mouseEvent) { btnExit(btnDashboard1); }

    public void btnReservationOnClick(MouseEvent mouseEvent) { click(btnReservation); }
    public void btnReservationOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnReservation); }
    public void btnReservationOnMouseExited(MouseEvent mouseEvent) { btnExit(btnReservation); }

    public void btnReportsOnClick(MouseEvent mouseEvent) { click(btnReports); }
    public void btnReportsOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnReports); }
    public void btnReportsOnMouseExited(MouseEvent mouseEvent) { btnExit(btnReports); }

    public void btnSettingOnClick(MouseEvent mouseEvent) { click(btnSetting); }
    public void btnSettingOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnSetting); }
    public void btnSettingOnMouseExited(MouseEvent mouseEvent) { btnExit(btnSetting); }

    public void btnBillingOnMouseExited(MouseEvent mouseEvent) { btnExit(btnBilling); }
    public void btnBillingOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnBilling); }
    public void btnBillingOnClick(MouseEvent mouseEvent) { click(btnBilling); }

    public void btnUsersOnClick(MouseEvent mouseEvent) { click(btnUsers); }
    public void btnUsersOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnUsers); }
    public void btnUsersOnMouseExited(MouseEvent mouseEvent) { btnExit(btnUsers); }

    public void btnRegisterOnClick(MouseEvent mouseEvent) { click(btnRegister); }
    public void btnRegisterOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnRegister); }
    public void btnRegisterOnMouseExited(MouseEvent mouseEvent) { btnExit(btnRegister); }

    public void btnLogOutOnClick(MouseEvent mouseEvent) { click(btnLogOut); }
    public void btnLogOutOnMouseEntered(MouseEvent mouseEvent) { btnEntered(btnLogOut); }
    public void btnLogOutOnMouseExited(MouseEvent mouseEvent) { btnExit(btnLogOut); }
}
