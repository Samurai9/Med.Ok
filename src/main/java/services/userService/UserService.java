package services.userService;

import entities.User;
import repositories.user.UserRepository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.SQLException;

public class UserService implements IUserService {

    private UserRepository userRepository;

    public UserService(DataSource dataSource){
        userRepository = new UserRepository(dataSource);
    }

    public void create(User user) throws SQLException {
        try {
            userRepository.create(user);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
    }

    public User findByEmail(String email) throws SQLException{
        User user = null;
        try{
            user = userRepository.findByEmail(email);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
        return user;
    }

    public void update(User user) throws SQLException{
        try{
            userRepository.update(user);
        } catch(SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
    }

    public void delete(User user) throws SQLException{
        try{
            userRepository.delete(user);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
    }
}
