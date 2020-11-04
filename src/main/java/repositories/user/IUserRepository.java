package repositories.user;

import repositories.ICRUD;
import entities.User;

import java.sql.SQLException;

public interface IUserRepository extends ICRUD<entities.User> {
    User findByEmail(String email) throws SQLException;
}

