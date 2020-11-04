package entities;

import repositories.product.ProductCategory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Product {
    private static final String DEFAULT_PICTURE_PATH = "\\resources\\img\\ProductImages\\default-product-picture.png";


    private int productId;
    private String name;
    private String description;
    private int remaining;
    private double size;
    private double price;
    private double rating;
    private String picture;
    private String category;
    private int reviewsCount;

    public Product(int productId, String name, String description, int remaining, double size, double price, double rating, String picture, String category, int reviewsCount) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.remaining = remaining;
        this.size = size;
        this.price = price;
        this.rating = rating;
        if (picture == null){
            this.picture = DEFAULT_PICTURE_PATH;
        } else {
            this.picture = picture;
        }
        this.category = category;
        this.reviewsCount =reviewsCount;
    }



    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRemaining() {
        return remaining;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public String getPicture() {
        return picture;
    }

    public String getCategory() {
        return category;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }


    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", remaining=" + remaining +
                ", size=" + size +
                ", price=" + price +
                ", rating=" + rating +
                ", picture='" + picture + '\'' +
                ", category='" + category + '\'' +
                ", reviewsCount=" + reviewsCount +
                '}';
    }
}
