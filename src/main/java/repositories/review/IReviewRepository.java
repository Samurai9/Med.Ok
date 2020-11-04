package repositories.review;

import entities.Review;

import java.sql.SQLException;
import java.util.List;

public interface IReviewRepository {
    void save(Review review) throws SQLException;
    List<Review> findByProductId(int productId) throws SQLException;
    void delete(int reviewId) throws SQLException;
}
