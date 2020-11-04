package repositories;

import java.sql.SQLException;
import java.util.List;

public interface ICRUD<T> {
    void create(T entity) throws SQLException;
    void update(T entity) throws SQLException;
    void delete(T entity) throws SQLException;
    T findById(int id) throws SQLException;
    List<T> findAll() throws SQLException;

}
