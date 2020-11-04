package services.reviewService;

import entities.Review;
import repositories.review.ReviewRepository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ReviewService implements IReviewService {
    private ReviewRepository reviewRepository;

    public ReviewService(DataSource dataSource){
        reviewRepository = new ReviewRepository(dataSource);
    }

    @Override
    public void save(Review review) throws SQLException {
        try {
            reviewRepository.save(review);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
    }

    @Override
    public List<Review> findByProductId(int productId) throws SQLException{
        List<Review> reviews = null;
        try {
            reviews = reviewRepository.findByProductId(productId);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
        return reviews;
    }

}
