package service.custom;

import dto.Customer;
import dto.User;
import javafx.collections.ObservableList;
import service.superService;


import java.util.List;

public interface UserService extends superService {
    List<User> getAll();
    boolean saveUser(User user) ;
    boolean updateUser(User user);
    boolean deleteUser(String userId);


    User searchUser(String userId);

    ObservableList<String> getUserIds();
}
