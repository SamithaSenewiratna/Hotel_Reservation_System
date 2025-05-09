package entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.Date;
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
@Entity
public class ReservationEntity {

    private int reservationId;
    private int customerId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalAmount;
    private String reservationStatus;

}
