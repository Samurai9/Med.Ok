package services.productService;

import entities.Product;
import entities.Review;
import repositories.product.ProductCategory;

import java.sql.SQLException;
import java.util.List;

public interface IProductService {
    Product findById(int id) throws SQLException;
    List<Product> findAll() throws SQLException;
    List<Product> findPopular() throws SQLException;
    List<Product> findAllByPatter(String pattern) throws SQLException;
    List<Product> findAllByCategory(ProductCategory productCategory) throws SQLException;
    void update(Product product) throws SQLException;
    void updateByReview(Review review) throws SQLException;
}
