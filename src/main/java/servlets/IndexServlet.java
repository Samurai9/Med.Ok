package servlets;

import entities.Product;
import services.productService.ProductService;
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

@WebServlet("/welcome")
public class IndexServlet extends HttpServlet {

    private ProductService productService = null;
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.productService = (ProductService) servletConfig.getServletContext().getAttribute("productService");
        super.init(servletConfig);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().setAttribute("title", "Med.Ok");
        try {
            List<Product> products = productService.findPopular();
            req.setAttribute("products", products);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
        } catch (SQLException e) {
            getServletContext().setAttribute("text", "Что-то пошло не так");
            resp.sendRedirect(req.getContextPath() + "/fail");
        }
    }
}
