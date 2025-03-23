package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import util.Availability;
import util.RoomType;
import util.UserRole;

import java.io.InputStream;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Room {

    private int roomId;
    private String roomNumber;
    private RoomType roomType;
    private Double price;
    private Availability availability;
    private String imgSrc;



    public Room( String roomNumber, RoomType roomType, Double price, Availability availability ,String imgSrc) {

        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.availability = availability;
        this.imgSrc=imgSrc;
    }





}
