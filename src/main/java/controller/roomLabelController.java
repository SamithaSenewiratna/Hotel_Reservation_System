package controller;

import dto.Room;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class roomLabelController {

    public Label lblRoomID;
    public Label lblRoomType;
    public Label lblPrice;
    public ImageView imgRoom;
    public Label lblAvalibility;
    public Label lblRoomNumber;

    private Room room;
    private myListener mylistener;


    public void setData(Room room ,myListener mylistener) {
        this.room = room;
        this.mylistener=mylistener;

        if (this.room != null) {
//          lblRoomID.setText(String.valueOf(this.room.getRoomId()));
            lblRoomNumber.setText(this.room.getRoomNumber());
            lblRoomType.setText(this.room.getRoomType().name());
            lblPrice.setText(String.valueOf(this.room.getPrice()));
            lblAvalibility.setText(String.valueOf(this.room.getAvailability()));


            try {
                Image image = new Image(this.room.getImgSrc());
                imgRoom.setImage(image);
            } catch (Exception e) {
                System.err.println("Image not found: " + this.room.getImgSrc());

            }
        }
    }

    public void click(MouseEvent mouseEvent) {

        mylistener.onCliclListener(room);
    }
}
