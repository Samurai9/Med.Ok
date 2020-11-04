package servlets;

import entities.Basket;
import entities.Product;
import entities.User;
import services.basketService.BasketService;
import services.userService.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/basket")
public class BasketServlet extends HttpServlet {

    private BasketService basketService = null;
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.basketService = (BasketService) servletConfig.getServletContext().getAttribute("basketService");
        super.init(servletConfig);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            Basket basket = basketService.getBasket(user);
            List<HashMap<Product, Integer>> products = basket.getProducts();
            getServletContext().setAttribute("title", "Корзина");
            req.setAttribute("basket", basket);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/basket.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("text", "Что-то пошло не так");
            resp.sendRedirect(req.getContextPath() + "/fail");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Basket basket = null;
        try {
            basket = basketService.getBasket((User) req.getSession().getAttribute("user"));
            User user = (User) req.getSession().getAttribute("user");
        } catch (SQLException e) {
            getServletContext().setAttribute("text", "Что-то пошло не так");
            resp.sendRedirect(req.getContextPath() + "/fail");
        }

        switch (req.getParameter("method")){
            case "PUT":
                try {
                    basketService.addProduct(basket.getBasketId(), Integer.parseInt((String)req.getParameter("product_id")));;
                    resp.sendRedirect(req.getContextPath() + "/product/" + (String)req.getParameter("product_id"));
                } catch (SQLException e) {
                    getServletContext().setAttribute("text", "Что-то пошло не так");
                    resp.sendRedirect(req.getContextPath() + "/fail");
                }
                break;

            case "DELETE":
                try {
                    basketService.deleteProduct(basket.getBasketId(), Integer.parseInt((String) req.getParameter("product_id")));
                    resp.sendRedirect(req.getContextPath() + "/basket");
                } catch (SQLException e) {
                    getServletContext().setAttribute("text", "Что-то пошло не так");
                    resp.sendRedirect(req.getContextPath() + "/fail");
                }
                break;
        }

    }
}
