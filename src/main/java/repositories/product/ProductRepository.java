package repositories.product;

import entities.Product;
import entities.Review;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductRepository implements IProductRepository {


    //language=SQL
    private static final String SQL_SELECT_ALL = "SELECT * FROM products";
    //language=SQL
    private static final String SQL_ORDER_PRODUCT_ID = " ORDER BY product_id";
    //language=SQL
    private static final String SQL_ORDER_RATING = " ORDER BY rating DESC";
    //language=SQL
    private static final String SQL_RATING_CONDITION = " WHERE rating >= ";
    //language=SQL
    private static final String SQL_SELECT_ALL_PATTERN = "SELECT * FROM products WHERE name LIKE ";
    //language=SQL
    private static final String SQL_SELECT_BY_CATEGORY = "SELECT  * FROM products WHERE product_category = ";
    //language=SQL
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM products WHERE product_id = ";
    //language=SQL
    private static final String SQL_UPDATE_BY_REVIEW = "UPDATE products SET (rating, reviews_count) = ";

    private DataSource dataSource;

    public ProductRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Product findById(int id) throws SQLException{
        Product product = null;
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID + id);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            product = new Product(
                    Integer.parseInt(resultSet.getString("product_id")),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    Integer.parseInt(resultSet.getString("remaining")),
                    Double.parseDouble(resultSet.getString("size")),
                    Double.parseDouble(resultSet.getString("price")),
                    Double.parseDouble(resultSet.getString("rating")),
                    resultSet.getString("picture"),
                    resultSet.getString("product_category"),
                    Integer.parseInt(resultSet.getString("reviews_count"))
            );
        }
        connection.close();

        return product;
    }

    @Override
    public List<Product> findAll() throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> products = new ArrayList<>();

        preparedStatement = connection.prepareStatement(SQL_SELECT_ALL + SQL_ORDER_PRODUCT_ID);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            products.add(new Product(
                    Integer.parseInt(resultSet.getString("product_id")),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    Integer.parseInt(resultSet.getString("remaining")),
                    Double.parseDouble(resultSet.getString("size")),
                    Double.parseDouble(resultSet.getString("price")),
                    Double.parseDouble(resultSet.getString("rating")),
                    resultSet.getString("picture"),
                    resultSet.getString("product_category"),
                    Integer.parseInt(resultSet.getString("reviews_count"))
            ));
        }
        connection.close();

        return products;
    }

    @Override
    public List findPopular() throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> products = new ArrayList<>();

        preparedStatement = connection.prepareStatement(SQL_SELECT_ALL +
                SQL_RATING_CONDITION + 4 +
                SQL_ORDER_RATING);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            products.add(new Product(
                    Integer.parseInt(resultSet.getString("product_id")),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    Integer.parseInt(resultSet.getString("remaining")),
                    Double.parseDouble(resultSet.getString("size")),
                    Double.parseDouble(resultSet.getString("price")),
                    Double.parseDouble(resultSet.getString("rating")),
                    resultSet.getString("picture"),
                    resultSet.getString("product_category"),
                    Integer.parseInt(resultSet.getString("reviews_count"))
            ));
        }
        connection.close();

        return products;
    }

    @Override
    public List<Product> findAllByCategory(ProductCategory productCategory) throws SQLException{
        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Product> products = new ArrayList<>();


        preparedStatement = connection.prepareStatement(SQL_SELECT_BY_CATEGORY +
                "'" + productCategory.toString() +
                "'" + SQL_ORDER_RATING);
        resultSet = preparedStatement.executeQuery();



        while (resultSet.next()){
            products.add(new Product(
                    Integer.parseInt(resultSet.getString("product_id")),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    Integer.parseInt(resultSet.getString("remaining")),
                    Double.parseDouble(resultSet.getString("size")),
                    Double.parseDouble(resultSet.getString("price")),
                    Double.parseDouble(resultSet.getString("rating")),
                    resultSet.getString("picture"),
                    resultSet.getString("product_category"),
                    Integer.parseInt(resultSet.getString("reviews_count"))
            ));
        }
        connection.close();

        return products;
    }

    @Override
    public List<Product> findAllByPattern(String pattern) throws SQLException {
        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Product> products = new ArrayList<>();


        preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_PATTERN +
                "'%" + pattern +
                "%'" + SQL_ORDER_RATING);
        resultSet = preparedStatement.executeQuery();


        while (resultSet.next()){
            products.add(new Product(
                    Integer.parseInt(resultSet.getString("product_id")),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    Integer.parseInt(resultSet.getString("remaining")),
                    Double.parseDouble(resultSet.getString("size")),
                    Double.parseDouble(resultSet.getString("price")),
                    Double.parseDouble(resultSet.getString("rating")),
                    resultSet.getString("picture"),
                    resultSet.getString("product_category"),
                    Integer.parseInt(resultSet.getString("reviews_count"))
            ));
        }
        connection.close();

        return products;
    }

    @Override
    public void update(Object o) throws SQLException{

    }

    @Override
    public void updateByReview(Review review) throws SQLException{
        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        ProductRepository productRepository = new ProductRepository(dataSource);
        Product product = productRepository.findById(review.getProductId());
        double tempRating = (double) product.getReviewsCount() * product.getRating();
        tempRating = (double)(tempRating + review.getRating())/(product.getReviewsCount() + 1);
        preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_REVIEW +
                "('" + tempRating + "', '" + (product.getReviewsCount() + 1) + "') WHERE product_id = "
                + product.getProductId());
        preparedStatement.execute();
        connection.close();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
