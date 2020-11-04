package services.basketService;

import entities.Basket;
import entities.User;

import java.sql.SQLException;

public interface IBasketService {
    void createByUserId(int userId) throws SQLException;
    void deleteBasket(int userId) throws SQLException;
    void deleteProduct(int basketId, int productId) throws SQLException;
    Basket getBasket(User user) throws SQLException;
    void addProduct(int basketId, int productId) throws SQLException;
}
