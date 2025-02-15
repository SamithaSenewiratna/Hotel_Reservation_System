package repository;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface crudDao <T> extends superDao{

    boolean save(T entity);
    T search(String id);
    boolean delete(String id);
    boolean update(T entity);
    List<T> getAll();

}
