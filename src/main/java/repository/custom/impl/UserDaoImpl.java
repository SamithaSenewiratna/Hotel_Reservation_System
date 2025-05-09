package repository.custom.impl;

import entity.UserEntity;
import repository.custom.UserDao;
import util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(UserEntity entity) {
            try {
                String SQL = "INSERT INTO user ( username, contact_number,email, password_hash, role) VALUES (?, ?, ?,?,?)";
                return CrudUtil.execute(SQL,
                        entity.getUsername(),
                        entity.getContactNumber(),
                        entity.getEmail(),
                        entity.getPasswordHash(),
                        entity.getRole()
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public UserEntity search(String id) {
            try {
                String SQL = "SELECT * FROM user WHERE id = ?";
                ResultSet resultSet = CrudUtil.execute(SQL, id);
                if (resultSet.next()) {
                    return new UserEntity(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("contact_number"),
                            resultSet.getString("email"),
                            resultSet.getString("password_hash"),
                            resultSet.getString("role")
                    );
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error searching for user", e);
            }
            return null;
    }

    @Override
    public boolean delete(String id) {
        String SQL = "DELETE FROM user WHERE userId = ?";
            try {
                return CrudUtil.execute(SQL,Integer.valueOf(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public boolean update(UserEntity entity) {

        String SQL = "UPDATE user SET username=?, contact_number=?,email=?, password_hash=?, role=? WHERE id=? ";

           try {
                return CrudUtil.execute(SQL,

                        entity.getUsername(),
                        entity.getContactNumber(),
                        entity.getEmail(),
                        entity.getPasswordHash(),
                        entity.getRole(),
                        entity.getUserId()
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

      @Override
      public List<UserEntity> getAll() {
        return List.of();
      }
}
