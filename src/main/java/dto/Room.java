package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Room {

    private int roomId;
    private String roomNumber;
    private RoomType roomType;  // Enum type for roomType
    private Double price;
    private Availability availability;  // Enum type for availability

    // Enum for Room Type
    public enum RoomType {
        SINGLE, DOUBLE, SUITE
    }

    // Enum for Availability Status
    public enum Availability {
        AVAILABLE, OCCUPIED
    }
}
