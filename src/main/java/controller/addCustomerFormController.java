package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class addCustomerFormController {



    public void addNewAccountButtonOnAction(MouseEvent mouseEvent) throws IOException {



    }

    public void addNewAccountButtonOnAction1(MouseEvent mouseEvent) throws IOException {




        Parent root = FXMLLoader.load(getClass().getResource("../view/newAccount.fxml"));


        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();


        stage.setScene(new Scene(root));
        stage.setTitle("New Account Form");
        stage.show();




    }

    public void dd(ActionEvent actionEvent) {

        System.out.println("hi hi ");
    }

    public void logInOnAction(ActionEvent actionEvent) throws IOException {



        Parent root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));


        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();


        stage.setScene(new Scene(root));
        stage.setTitle("New Account Form");
        stage.show();


    }
}
