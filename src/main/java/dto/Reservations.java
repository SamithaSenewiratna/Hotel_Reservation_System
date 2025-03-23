package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import util.ReservationStatus;

import java.util.Date;
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class Reservations {

    private int reservationId;
    private int customerId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalAmount;
    private ReservationStatus reservationStatus;


    public Reservations(int customerId, int roomId, Date checkInDate, Date checkOutDate, double totalAmount, ReservationStatus reservationStatus) {
        this.customerId=customerId;
        this.roomId=roomId;
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
        this.totalAmount=totalAmount;
        this.reservationStatus=reservationStatus;


    }
}
