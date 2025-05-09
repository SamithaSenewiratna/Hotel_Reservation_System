package repository.custom.impl;

import entity.ReservationEntity;
import repository.custom.ReservationDao;
import java.util.List;

public class ReservationDaoImpl implements ReservationDao {


    @Override
    public boolean save(ReservationEntity entity) {
        return false;
    }

    @Override
    public ReservationEntity search(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(ReservationEntity entity) {
         return false;
    }

    @Override
    public List<ReservationEntity> getAll() {
        return List.of();
    }
}



