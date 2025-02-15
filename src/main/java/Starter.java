import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage1) throws Exception {

        stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/addCustomerForm.fxml"))));
        stage1.show();




    }
}
