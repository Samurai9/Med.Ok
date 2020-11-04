package services.productService;

import entities.Product;
import entities.Review;
import entities.User;
import repositories.product.IProductRepository;
import repositories.product.ProductCategory;
import repositories.product.ProductRepository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService {
    private ProductRepository productRepository;

    public ProductService(DataSource dataSource){
        productRepository = new ProductRepository(dataSource);
    }

    @Override
    public Product findById(int id) throws SQLException {
        Product product = null;
        try {
            product = productRepository.findById(id);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
        return product;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> products = null;
        try {
            products = productRepository.findAll();
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
        return products;
    }

    @Override
    public List<Product> findPopular() throws SQLException {
        List<Product> products = null;
        try {
            products = productRepository.findPopular();
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
        return products;
    }

    @Override
    public List<Product> findAllByCategory(ProductCategory productCategory) throws SQLException {
        List<Product> products = null;
        try {
            products = productRepository.findAllByCategory(productCategory);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
        return products;
    }

    @Override
    public List<Product> findAllByPatter(String pattern) throws SQLException {
        List<Product> products = new ArrayList<>();
        try {
            products = productRepository.findAllByPattern(pattern);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
        return products;
    }


    @Override
    public void update(Product product) throws SQLException {
        try {
            productRepository.update(product);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
    }

    @Override
    public void updateByReview(Review review) throws SQLException {
        try {
            productRepository.updateByReview(review);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
    }
}
