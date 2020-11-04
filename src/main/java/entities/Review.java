package entities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Review {
    private int reviewId;
    private int authorId;
    private String text;
    private int rating;
    private String time;
    private int productId;
    private String authorName;

    public Review(int reviewId, int authorId, String text, int rating, String time, int productId, String authorName) {
        this.reviewId = reviewId;
        this.authorId = authorId;
        this.text = text;
        this.rating = rating;
        this.time = time;
        this.productId = productId;
        this.authorName = authorName;
    }

    public Review(int authorId, String text, int rating, int productId, String authorName) {
        this.authorId = authorId;
        this.text = text;
        this.rating = rating;
        this.productId = productId;
        this.authorName = authorName;
    }

    public String beautifulTime(){
        String result = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(time);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            result = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss").format(timestamp);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
}

    public int getReviewId() {
        return reviewId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public String getTime() {
        return time;
    }

    public int getProductId() {
        return productId;
    }

    public String getAuthorName() {
        return authorName;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", authorId=" + authorId +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                ", time='" + time + '\'' +
                ", productId=" + productId +
                '}';
    }
}
