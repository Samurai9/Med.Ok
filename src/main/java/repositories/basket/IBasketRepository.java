package repositories.basket;

import entities.Basket;
import entities.Product;
import entities.User;

import java.sql.SQLException;
import java.util.List;

public interface IBasketRepository {
    void createByUserId(int userId) throws SQLException;
    void deleteBasket(int userId) throws SQLException;
    void deleteProduct(int basketId, int productId) throws SQLException;
    Basket getBasket(int userId) throws SQLException;
    void addProduct(int basketId, int productId) throws SQLException;

}
