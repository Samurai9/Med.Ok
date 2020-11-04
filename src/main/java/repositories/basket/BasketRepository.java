package repositories.basket;

import entities.Basket;
import entities.Product;
import entities.User;
import repositories.product.ProductRepository;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BasketRepository implements IBasketRepository{


    //language=SQL
    private static final String SQL_INSERT_BASKET = "INSERT INTO baskets (user_id) VALUES ";
    //language=SQL
    private static final String SQL_INSERT_PRODUCT = "INSERT INTO basket_product (basket_id, product_id) VALUES ";
    //language=SQL
    private static final String SQL_DELETE_PRODUCTS_FROM_BASKET = "DELETE FROM basket_product WHERE basket_id = ";
    //language=SQL
    private static final String SQL_PRODUCT_CONDITION = " AND product_id = ";
    //language=SQL
    private static final String SQL_DELETE_BASKET = "DELETE FROM baskets WHERE user_id = ";
    //language=SQL
    private static final String SQL_SELECT_BASKET = "SELECT basket_id FROM baskets WHERE user_id = ";
    //language=SQL
    private static final String SQL_SELECT_PRODUCTS = "SELECT * FROM basket_product WHERE basket_id = ";
    //language=SQL
    private static final String SQL_UPDATE_COUNT = "UPDATE basket_product SET(basket_id, product_id, count) = ";
    //language=SQL
    private static final String SQL_BASKET_CONDITION = " WHERE basket_id = ";

    private DataSource dataSource;

    public BasketRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }


    @Override
    public void createByUserId(int userId) throws SQLException {
        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        preparedStatement = connection.prepareStatement(SQL_INSERT_BASKET +
                "('" + userId + "')"
        );
        preparedStatement.execute();
        connection.close();
    }

    @Override
    public void deleteBasket(int userId) throws SQLException {
        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        Basket basket = getBasket(userId);

        preparedStatement = connection.prepareStatement(SQL_DELETE_PRODUCTS_FROM_BASKET +
                "('" + basket.getBasketId() + "')"
        );
        preparedStatement.execute();

        preparedStatement = connection.prepareStatement(SQL_DELETE_BASKET +
                "('" + userId + "')"
        );
        preparedStatement.execute();
        connection.close();
    }

    @Override
    public void deleteProduct(int basketId, int productId) throws SQLException {
        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        if ((count = contains(basketId, productId)) == 1) {
            preparedStatement = connection.prepareStatement(SQL_DELETE_PRODUCTS_FROM_BASKET +
                    basketId + SQL_PRODUCT_CONDITION + productId);
            preparedStatement.execute();
        } else {
            count--;
            preparedStatement = connection.prepareStatement(SQL_UPDATE_COUNT +
                    "(" + basketId + ", " + productId + ", " + count + ")" +
                    SQL_BASKET_CONDITION + basketId +
                    SQL_PRODUCT_CONDITION + productId
            );
            preparedStatement.execute();
        }

        connection.close();
    }

    @Override
    public Basket getBasket(int userId) throws SQLException {
        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Basket basket = null;

        preparedStatement = connection.prepareStatement(SQL_SELECT_BASKET +
                "('" + userId + "')"
        );
        resultSet = preparedStatement.executeQuery();
        int basketId = 0;
        while (resultSet.next()){
            basketId = Integer.parseInt(resultSet.getString("basket_id"));
        }


        preparedStatement = connection.prepareStatement(SQL_SELECT_PRODUCTS +
                "('" + basketId + "')" + "ORDER BY product_id"
        );
        resultSet = preparedStatement.executeQuery();
        ProductRepository productRepository = new ProductRepository(dataSource);
        List<HashMap<Product, Integer>> products = new ArrayList<>();
        while (resultSet.next()){
            Product product = productRepository.findById(Integer.parseInt(resultSet.getString("product_id")));
            HashMap<Product, Integer> map = new HashMap<>();
            map.put(product, Integer.parseInt(resultSet.getString("count")));
            products.add(map);
        }
        basket = new Basket(basketId, userId, products);
        connection.close();

        return basket;
    }

    @Override
    public void addProduct(int basketId, int productId) throws SQLException {
        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        if ((count = contains(basketId, productId)) != -1){
            count++;
            preparedStatement = connection.prepareStatement(SQL_UPDATE_COUNT +
                             "(" + basketId + ", " + productId + ", " + count + ")" +
                            SQL_BASKET_CONDITION + basketId +
                            SQL_PRODUCT_CONDITION + productId
                    );
            preparedStatement.execute();
        } else {
            preparedStatement = connection.prepareStatement(SQL_INSERT_PRODUCT +
                    "('" + basketId + "', " + productId + ")"
            );
            preparedStatement.execute();
        }


        connection.close();
    }


    private int contains(int basketId, int productId) throws SQLException{
        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        preparedStatement = connection.prepareStatement(SQL_SELECT_PRODUCTS +
                basketId + SQL_PRODUCT_CONDITION + productId);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            connection.close();
            return Integer.parseInt(resultSet.getString("count"));
        } else {
            connection.close();
            return -1;
        }

    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
