package repositories.product;

import entities.Product;
import entities.Review;

import java.sql.SQLException;
import java.util.List;

public interface IProductRepository<Product> {
    Product findById(int id) throws SQLException;
    List<Product> findAll() throws SQLException;
    List<Product> findPopular() throws SQLException;
    List<Product> findAllByCategory(ProductCategory productCategory) throws SQLException;
    List<Product> findAllByPattern(String pattern) throws SQLException;
    void update(Product product) throws SQLException;
    void updateByReview(Review review) throws SQLException;
}
