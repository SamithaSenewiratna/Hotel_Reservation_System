package entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import util.Availability;
import util.RoomType;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class RoomEntity {

    private int roomId;
    private String roomNumber;
    private String roomType;  // Enum type for roomType
    private Double price;
    private String availability;
    private String imgSrc;// Enum type for availability

}
