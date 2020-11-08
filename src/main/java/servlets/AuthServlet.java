package servlets;

import entities.User;
import services.AuthService;
import services.CheckDataService;
import services.userService.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private User user = null;
    private UserService userService = null;
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.userService = (UserService) servletConfig.getServletContext().getAttribute("userService");
        super.init(servletConfig);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(AuthService.isSigned(req)){
            resp.sendRedirect(req.getContextPath() + "/profile");
        } else {
            getServletContext().setAttribute("title", "Авторизация");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/auth.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        StringBuilder content = new StringBuilder();

        //1-null email, 2-null pass, 3-no user, 4 - wrong pas, -1 -SQLEX
        switch (checkAccount(email, password)){
            case 1: content.append("Вы ввели пустой адрес электронной почты  \n"); break;
            case 2: content.append("Вы ввели пустой пароль \n"); break;
            case 3: content.append("Пользователя с таким электронным адресом не существует \n"); break;
            case 4: content.append("Неверный пароль \n"); break;
            case -1: content.append("Извините, проблема с базой данных"); break;
        }
        if (content.toString().equals("")){
            AuthService.signIn(req, user);
            resp.sendRedirect(req.getContextPath() + "/profile");
        } else {
            getServletContext().setAttribute("text", content.toString());
            resp.sendRedirect(req.getContextPath() + "/fail");
        }
    }

    //1-null email, 2-null pass, 3-no user, 4 - wrong pas, -1 sqlEx
    private int checkAccount(String email, String password){
        if (email.equals("")){
            return 1;
        }
        if (password.equals("")){
            return 2;
        }
//        UserService userService = new UserService();
        try {
            if ((user = userService.findByEmail(email)) == null){
                return 3;
            } else {
                if(CheckDataService.checkPasswords(password, user.getName(), user.getEmail(), user.getPassword())){
                    return 0;
                } else {
                    user = null;
                    return 4;
                }
            }
        } catch (SQLException e) {
            return -1;
        }

    }
}
