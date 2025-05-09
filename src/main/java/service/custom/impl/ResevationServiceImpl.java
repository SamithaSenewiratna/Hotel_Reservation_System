package service.custom.impl;

import dto.Reservations;
import service.custom.ResevationService;
import util.CrudUtil;
import util.ReservationStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResevationServiceImpl implements ResevationService {
    @Override
    public List<Reservations> getAll() {
        List<Reservations> reservationList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM reservations");
            while (resultSet.next()) {
                reservationList.add(new Reservations(
                        resultSet.getInt("reservation_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getInt("room_id"),
                        resultSet.getDate("check_in_date"),
                        resultSet.getDate("check_out_date"),
                        resultSet.getDouble("total_amount"), // Fix: Include total_amount
                        ReservationStatus.valueOf(resultSet.getString("reservation_status"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving reservations", e);
        }
        return reservationList;
    }

    @Override
    public boolean deleteReservation(String reservationId) throws SQLException {

        String query = "DELETE FROM reservations WHERE reservation_id = ?";
        return CrudUtil.execute(query, reservationId);

    }


    @Override
    public boolean updateReservation(Reservations selectedItem) {

        String query = "UPDATE reservations SET reservation_status = ? WHERE reservation_id = ?";

        try {
            return CrudUtil.executeUpdate(query, selectedItem.getReservationStatus().toString(), selectedItem.getReservationId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}


