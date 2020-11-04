package entities;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private int discount;


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(int userId, String name, String email, String password, int discount) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getDiscount() {
        return discount;
    }

    public int getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString(){
        return  "User ID: " + userId + "\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Discount: " + discount + "\n" +
                "Password: " + password;
    }
}
