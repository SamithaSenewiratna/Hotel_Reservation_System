package repository.custom.impl;

import entity.RoomEntity;
import repository.custom.RoomDao;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class RoomDaoImpl implements RoomDao {
    @Override
    public boolean save(RoomEntity entity) {

        try {
            String SQL = "INSERT INTO rooms (room_number, room_type, price_per_night, availability,image_path) VALUES (?, ?, ?, ?,?)";
            return CrudUtil.execute(SQL,
                    entity.getRoomNumber(),
                    entity.getRoomType(),
                    entity.getPrice(),
                    entity.getAvailability(),
                    entity.getImgSrc()

            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RoomEntity search(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {

        String SQL = "DELETE FROM rooms WHERE room_id = ?";

        try {
            return CrudUtil.execute(SQL,id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(RoomEntity entity) {

        String SQL = "UPDATE rooms SET room_number=?, room_type=?, price_per_night=?, availability=? WHERE room_id=? ";

        try {
            return CrudUtil.execute(SQL,

                    entity.getRoomNumber(),
                    entity.getRoomType(),
                    entity.getPrice(),
                    entity.getAvailability(),
                    entity.getRoomId()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RoomEntity> getAll() {
        return List.of();
    }
}
