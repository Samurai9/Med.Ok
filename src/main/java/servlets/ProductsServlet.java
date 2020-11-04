package servlets;

import entities.Product;
import entities.Review;
import repositories.product.ProductCategory;
import services.productService.ProductService;
import services.reviewService.ReviewService;
import services.userService.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/product/*")
public class ProductsServlet extends HttpServlet {

    private ProductService productService = null;
    private ReviewService reviewService = null;
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.productService = (ProductService) servletConfig.getServletContext().getAttribute("productService");
        this.reviewService = (ReviewService) servletConfig.getServletContext().getAttribute("reviewService");
        super.init(servletConfig);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uri = req.getRequestURI().substring(req.getContextPath().length()).split("/");
        if(uri.length < 3){
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        String string = uri[2].toUpperCase();

        boolean category = false;
        for(ProductCategory productCategory: ProductCategory.values()){
            if (string.equals(productCategory.toString())){
                category = true;
                break;
            }
        }

        if (category) {
            try {
                ProductCategory productCategory = ProductCategory.valueOf(string);
                List<Product> products = productService.findAllByCategory(productCategory);
                getServletContext().setAttribute("title", "Продукты");
                req.setAttribute("products", products);
                req.setAttribute("category", string.toLowerCase());
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/productsList.jsp").forward(req, resp);
            } catch (SQLException e) {
                getServletContext().setAttribute("text", "Что-то пошло не так");
                resp.sendRedirect(req.getContextPath() + "/fail");
            }
        } else {
            try {
                Product product = productService.findById(Integer.parseInt(string));

                if (product == null){
                    getServletContext().setAttribute("text", "Такого товара не сущетсвует");
                    resp.sendRedirect(req.getContextPath() + "/fail");
                } else {
                    List<Review> reviews = reviewService.findByProductId(product.getProductId());

                    req.setAttribute("product", product);
                    req.setAttribute("reviews", reviews);
                    getServletContext().setAttribute("title", product.getName());
                    getServletContext().getRequestDispatcher("/WEB-INF/jsp/productPage.jsp").forward(req, resp);
                }
            } catch (SQLException e) {
                getServletContext().setAttribute("text", "Что-то пошло не так");
                resp.sendRedirect(req.getContextPath() + "/fail");
            } catch (NumberFormatException e) {
                getServletContext().setAttribute("text", "Такого товара не существует");
                resp.sendRedirect(req.getContextPath() + "/fail");
            } catch (Exception e) {
                getServletContext().setAttribute("text", "Неверный запрос");
                resp.sendRedirect(req.getContextPath() + "/fail");
            }
        }
    }
}
