package repositories.review;

import entities.Product;
import entities.Review;
import repositories.product.ProductRepository;
import repositories.user.UserRepository;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReviewRepository implements IReviewRepository {


    //language=SQL
    private static final String SQL_INSERT_INTO = "INSERT INTO reviews(author_id, text, rating, time, product_id, author_name) VALUES ";
    //language=SQL
    private static final String SQL_FIND_BY_PRODUCT_ID = "SELECT * FROM reviews WHERE product_id = ";

    private DataSource dataSource;

    public ReviewRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void save(Review review) throws SQLException{
        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        preparedStatement = connection.prepareStatement(SQL_INSERT_INTO +
                "('" + review.getAuthorId() + "', '" +
                review.getText() + "', '" +
                review.getRating() + "', '" +
                Timestamp.valueOf(LocalDateTime.now()) + "', '" +
                review.getProductId() + "', '" +
                review.getAuthorName() + "')"
        );
        preparedStatement.execute();
        connection.close();
        ProductRepository productRepository = new ProductRepository(dataSource);
        productRepository.updateByReview(review);
    }

    @Override
    public List<Review> findByProductId(int productId) throws SQLException {
        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Review> reviews = new ArrayList<>();


        preparedStatement = connection.prepareStatement(SQL_FIND_BY_PRODUCT_ID + productId);
        DataSource dataSource = null;
        UserRepository userRepository = new UserRepository(dataSource);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            reviews.add(new Review(
                    Integer.parseInt(resultSet.getString("review_id")),
                    Integer.parseInt(resultSet.getString("author_id")),
                    resultSet.getString("text"),
                    Integer.parseInt(resultSet.getString("rating")),
                    resultSet.getString("time"),
                    Integer.parseInt(resultSet.getString("product_id")),
                    resultSet.getString("author_name")
            ));
        }

        connection.close();
        return reviews;
    }

    @Override
    public void delete(int reviewId) throws SQLException {

    }



    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
