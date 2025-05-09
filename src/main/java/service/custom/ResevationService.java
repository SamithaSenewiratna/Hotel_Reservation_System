package service.custom;


import dto.Reservations;
import service.superService;

import java.sql.SQLException;
import java.util.List;

public interface ResevationService extends superService {

    List<Reservations> getAll();


    boolean deleteReservation(String reservationId) throws SQLException;

    boolean updateReservation(Reservations selectedItem);
}
