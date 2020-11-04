package services.userService;

import entities.User;

import java.sql.SQLException;

public interface IUserService {
    void create(User user) throws SQLException;
    User findByEmail(String email) throws SQLException;
    void update(User user) throws SQLException;
    void delete(User user) throws SQLException;
}
