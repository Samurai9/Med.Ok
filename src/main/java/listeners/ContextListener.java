package listeners;

import com.sun.deploy.net.HttpRequest;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import entities.Review;
import repositories.review.ReviewRepository;
import services.basketService.BasketService;
import services.productService.ProductService;
import services.reviewService.ReviewService;
import services.userService.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        Properties properties = new Properties();
        try {
            properties.load(servletContext.getResourceAsStream("/WEB-INF/properties/dbConnection.properties"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("DB_URL"));
        hikariConfig.setDriverClassName(properties.getProperty("DB_DRIVER_CLASSNAME"));
        hikariConfig.setUsername(properties.getProperty("DB_USERNAME"));
        hikariConfig.setPassword(properties.getProperty("DB_PASSWORD"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("DB_MAX_POOL_SIZE")));

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);


        BasketService basketService = new BasketService(dataSource);
        ProductService productService = new ProductService(dataSource);
        ReviewService reviewService = new ReviewService(dataSource);
        UserService userService = new UserService(dataSource);


        servletContext.setAttribute("basketService", basketService);
        servletContext.setAttribute("productService", productService);
        servletContext.setAttribute("reviewService", reviewService);
        servletContext.setAttribute("userService", userService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.removeAttribute("basketService");
        servletContext.removeAttribute("productService");
        servletContext.removeAttribute("reviewService");
        servletContext.removeAttribute("userService");
    }
}
