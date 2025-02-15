package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class newAccountController {
    public AnchorPane anchorPane2;

    public void CreateAccountOnAction(ActionEvent actionEvent) throws IOException {



        Parent root = FXMLLoader.load(getClass().getResource("../view/addCustomerForm.fxml"));


        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();


        stage.setScene(new Scene(root));
        stage.setTitle("New Account Form");
        stage.show();

    }
}
