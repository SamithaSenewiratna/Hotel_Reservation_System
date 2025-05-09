package service.custom;

import dto.Room;

import javafx.collections.ObservableList;
import service.superService;

import java.util.List;

public interface RoomService extends superService {

    List<Room> getAll();
    boolean saveRoom(Room room) ;
    boolean updateRoom(Room room);
    boolean deleteRoom(String roomId);


    Room searchRoom(String roomId);

    ObservableList<String> getRoomrIds();

}
