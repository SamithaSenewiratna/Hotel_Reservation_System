package service.custom.impl;

import dto.Room;
import entity.RoomEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.RoomDao;
import service.custom.RoomService;
import util.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomServiceImpl implements RoomService {

    private static RoomServiceImpl instance;
    private final RoomDao roomDao = DaoFactory.getInstance().getDaoType(DaoType.ROOM);
    private final ModelMapper modelMapper = new ModelMapper();

    public RoomServiceImpl() {
    }

    public static synchronized RoomServiceImpl getInstance() {
        if (instance == null) {
            instance = new RoomServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Room> getAll() {
        List<Room> roomList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM rooms");
            while (resultSet.next()) {
                roomList.add(new Room(
                        resultSet.getInt("room_id"),
                        resultSet.getString("room_number"),
                        RoomType.valueOf(resultSet.getString("room_type")),
                        resultSet.getDouble("price_per_night"),
                        Availability.valueOf(resultSet.getString("availability")),
                        resultSet.getString("image_path")

                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving rooms", e);
        }
        return roomList;
    }

    @Override
    public boolean saveRoom(Room room) {

        try {
            RoomEntity entity = modelMapper.map(room, RoomEntity.class);
            return roomDao.save(entity);
        } catch (Exception e) {
            System.err.println("Error saving room: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateRoom(Room room) {

        try {
            RoomEntity entity = modelMapper.map(room, RoomEntity.class);
            return roomDao.update(entity);
        } catch (Exception e) {
            System.err.println("Error updating room: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteRoom(String roomId) {

        try {
            return roomDao.delete(String.valueOf(Integer.parseInt(roomId)));
        } catch (NumberFormatException e) {
            System.err.println("Invalid room ID format: " + roomId);
            return false;
        } catch (Exception e) {
            System.err.println("Error deleting room: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Room searchRoom(String roomId) {
//        try {
//            RoomEntity entity = roomDao.find(Integer.parseInt(roomId));
//            return entity != null ? modelMapper.map(entity, Room.class) : null;
//        } catch (NumberFormatException e) {
//            System.err.println("Invalid room ID format: " + roomId);
//            return null;
//        } catch (Exception e) {
//            System.err.println("Error searching room: " + e.getMessage());
//            return null;
//        }
        return null;
    }

    @Override
    public ObservableList<String> getRoomrIds() {
        ObservableList<String> roomIds = FXCollections.observableArrayList();
        try {
            List<RoomEntity> rooms = roomDao.getAll();
            for (RoomEntity room : rooms) {
                roomIds.add(String.valueOf(room.getRoomId()));
            }
        } catch (Exception e) {
            System.err.println("Error retrieving room IDs: " + e.getMessage());
        }
        return roomIds;
    }
}
