package service.custom.impl;

import dto.User;
import entity.UserEntity;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.UserDao;
import service.custom.UserService;
import util.CrudUtil;
import util.DaoType;
import util.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance;
    private final UserDao userDao = DaoFactory.getInstance().getDaoType(DaoType.USER);
    private final ModelMapper modelMapper = new ModelMapper();

    public UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance == null ? instance = new UserServiceImpl() : instance;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM user");
            while (resultSet.next()) {
                userList.add(new User(
                        resultSet.getInt(1),

                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        UserRole.valueOf(resultSet.getString(6)


                        )
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public boolean saveUser(User user){

            UserEntity entity= new ModelMapper().map(user,UserEntity.class);
            UserDao userDo = DaoFactory.getInstance().getDaoType(DaoType.USER);
            userDo.save(entity);

        return true;
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.update(modelMapper.map(user, UserEntity.class));
    }

    @Override
    public boolean deleteUser(String userId) {
        return userDao.delete(userId);
    }



    @Override
    public User searchUser(String userId) {

        UserEntity entity = userDao.search(userId);
            if (entity != null) {

                return modelMapper.map(entity, User.class);
            }
        return null;
    }

    @Override
    public ObservableList<String> getUserIds() {
        return null;
    }

}
