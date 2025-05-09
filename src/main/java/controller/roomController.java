package controller;

import com.jfoenix.controls.JFXTextField;
import dto.Room;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import service.custom.RoomService;
import service.serviceFactory;
import util.Availability;
import util.RoomType;
import util.ServiceType;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class roomController implements Initializable {

    public ScrollPane scroll;
    public Label lblID;
    public JFXTextField txtRoomNumber;
    public JFXTextField txtPricePerNight;
    public ComboBox<RoomType> cmbRoomType;
    public GridPane grid1;
    public ImageView selectedImg;

    public TextArea txtImgParth;
    public ComboBox<Availability> cmbAvalibility;

    private final RoomService service = serviceFactory.getInstance().getServiceType(ServiceType.ROOM);
    private List<Room> rooms;
    private myListener mylistener;
    private Room room;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbRoomType.setItems(FXCollections.observableArrayList(RoomType.values()));
        cmbAvalibility.setItems(FXCollections.observableArrayList(Availability.values()));
        selectedImg.setImage(new Image("img/addroomimg.png"));

        rooms = service.getAll();
        if (!rooms.isEmpty()) {
            mylistener = this::setChosenRoom;
        }
        loadData();

        cmbRoomType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) setPredefinedPriceBasedOnRoomType(newValue);
        });
    }

    private void setPredefinedPriceBasedOnRoomType(RoomType newValue) {
        double price = switch (newValue) {
            case Single -> 1200;
            case Double -> 2800;
            case Suite -> 3200;
        };
        txtPricePerNight.setText(String.valueOf(price));
    }

    private void loadData() {
        grid1.getChildren().clear();
        int row = 1, column = 0;

        try {
            for (Room room : rooms) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/roomLabel.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                roomLabelController controller = fxmlLoader.getController();
                controller.setData(room, mylistener);

                grid1.add(anchorPane, column++, row);
                if (column == 3) {
                    column = 0;
                    row++;
                }

                GridPane.setMargin(anchorPane, new Insets(5));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setChosenRoom(Room room) {
        if (room != null) {
            this.room = room;
            lblID.setText(String.valueOf(room.getRoomId()));
            cmbRoomType.setValue(room.getRoomType());
            txtRoomNumber.setText(room.getRoomNumber());
            txtPricePerNight.setText(String.valueOf(room.getPrice()));
            cmbAvalibility.setValue(room.getAvailability());
            txtImgParth.setText(room.getImgSrc());
            selectedImg.setImage(new Image(room.getImgSrc()));
        }
    }

    public void btnSElectImageOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Room Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                txtImgParth.setText(file.getAbsolutePath());
                selectedImg.setImage(new Image(file.toURI().toString()));
            }
    }

    public void btnAddRoomOnAction(ActionEvent actionEvent) {
        RoomType selectedRoomType = cmbRoomType.getValue();
        Availability selectedAvailability = cmbAvalibility.getValue();

            if (selectedRoomType == null || selectedAvailability == null) {
                new Alert(Alert.AlertType.ERROR, "Please fill in all fields").show();
                return;
            }

        double price;
            try {
                price = Double.parseDouble(txtPricePerNight.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid price entered").show();
                return;
            }

        Room room = new Room(txtRoomNumber.getText(), selectedRoomType, price, selectedAvailability, txtImgParth.getText());
            if (service.saveRoom(room)) {
                clearFields();
                rooms = service.getAll();
                loadData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Room Not Added").show();
            }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        int roomId = Integer.parseInt(lblID.getText());
        RoomType selectedRoomType = cmbRoomType.getValue();
        Availability selectedAvailability = cmbAvalibility.getValue();
        double price = Double.parseDouble(txtPricePerNight.getText());

        Room room = new Room(roomId, txtRoomNumber.getText(), selectedRoomType, price, selectedAvailability, txtImgParth.getText());
            if (service.updateRoom(room)) {
                clearFields();
                rooms = service.getAll();
                loadData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Room Not Updated").show();
            }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if (service.deleteRoom(lblID.getText())) {
            clearFields();
            rooms = service.getAll();
            loadData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Room Not Deleted").show();
        }
    }

    private void clearFields() {
        txtRoomNumber.clear();
        txtPricePerNight.clear();
        cmbAvalibility.setValue(null);
        cmbRoomType.setValue(null);
        txtImgParth.clear();
        selectedImg.setImage(new Image("img/addroomimg.png"));
    }
}
