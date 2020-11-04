package entities;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Basket {
    private int basketId;
    private int userId;

    private List<HashMap<Product, Integer>> products;

    public Basket(int basketId, int userId, List<HashMap<Product, Integer>> products) {
        this.basketId = basketId;
        this.userId = userId;
        this.products = products;
    }

    public int getBasketId() {
        return basketId;
    }

    public int getUserId() {
        return userId;
    }

    public List<HashMap<Product, Integer>> getProducts() {
        return products;
    }


    @Override
    public int hashCode() {
        return Objects.hash(basketId, userId, products);
    }

    public double getTotalPrice(){
        double result = 0;
        for (HashMap<Product, Integer> map: products){
            for(Product product: map.keySet()){
                result += product.getPrice() * map.get(product);
            }
        }
        return result;
    }

    public int getCountOfProducts(){
        int result = 0;
        for (HashMap<Product, Integer> map: products){
            for(Integer integer: map.values()){
                result += integer;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "basketId=" + basketId +
                ", userId=" + userId +
                ", products=" + products +
                '}';
    }
}
