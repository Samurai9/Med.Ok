package repositories.user;

import entities.User;
import repositories.basket.BasketRepository;

import javax.sql.DataSource;
import java.io.*;
import java.net.URLDecoder;
import java.security.Policy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserRepository implements IUserRepository {


    //language=SQL
    private static final String SQL_INSERT_INTO_USERS = "INSERT INTO users(name, email, password) VALUES ";
    //language=SQL
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    //language=SQL
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM users WHERE email = ";
    //language=SQL
    private static final String SQL_UPDATE = "UPDATE users SET ";
    //language=SQL
    private static final String SQL_UPDATE_CONDITION = "WHERE user_id = ";
    //language=SQL
    private static final String SQL_DELETE_BY_ID = "DELETE FROM users WHERE user_id = ";

    private DataSource dataSource;

    public UserRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }


    @Override
    public void create(User user) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_USERS +
                "('" + user.getName() + "', '" + user.getEmail() + "', '" + user.getPassword() + "')");
        preparedStatement.executeUpdate();

        connection.close();
    }

    @Override
    public User findByEmail(String email) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        preparedStatement = connection.prepareStatement(SQL_FIND_BY_EMAIL +
                "'" + email + "'");
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            user = new User(
                    Integer.parseInt(resultSet.getString("user_id")),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    Integer.parseInt(resultSet.getString("discount")));
        }
        connection.close();
        return user;
    }

    @Override
    public void update(User user) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        preparedStatement = connection.prepareStatement(SQL_UPDATE +
                "(name, email, password, discount) = (" +
                "'" + user.getName() + "'," +
                "'" + user.getEmail() + "'," +
                "'" + user.getPassword() + "'," +
                "'" + user.getDiscount() + "') " +
                SQL_UPDATE_CONDITION + user.getUserId());

        preparedStatement.executeUpdate();
        connection.close();
    }

    @Override
    public void delete(User user) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        BasketRepository basketRepository = new BasketRepository(dataSource);
        basketRepository.deleteBasket(user.getUserId());

        preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID + user.getUserId());
        preparedStatement.executeUpdate();

        connection.close();
    }


    @Override
    public List<User> findAll() throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();

        preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USERS);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            users.add(new User(
                    Integer.parseInt(resultSet.getString("user_id")),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    Integer.parseInt(resultSet.getString("discount")))
            );
        }
        connection.close();
        return users;
    }

    @Override
    public User findById(int id) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USERS);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            user = new User(
                    Integer.parseInt(resultSet.getString("user_id")),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    Integer.parseInt(resultSet.getString("discount"))
            );
        }
        connection.close();
        return user;
    }



    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
