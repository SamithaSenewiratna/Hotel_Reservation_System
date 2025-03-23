import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Starter extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage1) throws Exception {

        stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/addCustomerForm.fxml"))));
        stage1.show();

        //Fade in Animation
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), stage1.getScene().getRoot());
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

//        // **Fade Out Animation**
//        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), stage1.getScene().getRoot());
//        fadeOut.setFromValue(1);
//        fadeOut.setToValue(0);
//        fadeOut.play();

    }
}
