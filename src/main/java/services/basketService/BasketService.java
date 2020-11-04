package services.basketService;

import entities.Basket;
import entities.User;
import repositories.basket.BasketRepository;
import repositories.basket.IBasketRepository;

import javax.sql.DataSource;
import java.sql.SQLException;

public class BasketService implements IBasketService {
    private BasketRepository basketRepository;


    public BasketService(DataSource dataSource){
        basketRepository = new BasketRepository(dataSource);
    }




    @Override
    public void createByUserId(int userId) throws SQLException {
        try {
            basketRepository.createByUserId(userId);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteBasket(int userId) throws SQLException {
        try {
            basketRepository.deleteBasket(userId);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
    }

    @Override
    public Basket getBasket(User user) throws SQLException {
        Basket basket = null;
        try {
            basket = basketRepository.getBasket(user.getUserId());
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
        return basket;
    }


    @Override
    public void addProduct(int basketId, int productId) throws SQLException {
        try {
            basketRepository.addProduct(basketId, productId);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteProduct(int basketId, int productId) throws SQLException {
        try {
            basketRepository.deleteProduct(basketId, productId);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new SQLException(e);
        }
    }
}
