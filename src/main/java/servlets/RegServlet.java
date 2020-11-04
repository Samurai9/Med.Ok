package servlets;

import entities.User;
import services.CheckDataService;
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

@WebServlet("/reg")
public class RegServlet extends HttpServlet {

    private BasketService basketService = null;
    private UserService userService = null;
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.basketService = (BasketService) servletConfig.getServletContext().getAttribute("basketService");
        this.userService = (UserService) servletConfig.getServletContext().getAttribute("userService");
        super.init(servletConfig);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/auth");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeat_password");
        String licenceAgreement = req.getParameter("licence_agreement");


        String content = checkData(name, email, password, repeatPassword, licenceAgreement);


        if (content.equals("")){
            try {
                addUser(name, email, password);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                getServletContext().setAttribute("text", "Что-то пошло не так");
                getServletContext().getRequestDispatcher("/fail").forward(req,resp);
            }
            getServletContext().setAttribute("text", "Регистрация прошла успешно, теперь Вы можете авторизоваться");
            getServletContext().getRequestDispatcher("/success").forward(req, resp);
        } else {
            getServletContext().setAttribute("text", content.toString());
            getServletContext().getRequestDispatcher("/fail").forward(req, resp);
        }
    }

    private String checkData(String name, String email, String password, String repeatPassword, String licenceAgreement){
        StringBuilder content = new StringBuilder();
        //1 - email already taken
        switch (CheckDataService.checkEmailInDb(email, userService)){
            case 1: content.append("Этот адрес электронной почты уже зарегестрирован \n");
        }
        //1 - null, 2 - no email
        switch (CheckDataService.checkEmail(email)){
            case 1: content.append("Вы ввели пустой адрес электронной почты \n"); break;
            case 2: content.append("Неверный адрес электронной почты \n"); break;
        }
        switch (CheckDataService.checkName(name)){
            case 1: content.append("Имя слишком короткое \n"); break;
        }
        switch (CheckDataService.checkPasswordsCorrectness(password, repeatPassword)){
            case 1: content.append("Вы не ввели пароль \n"); break;
            case 2: content.append("Вы не ввели повтор пароля\n"); break;
            case 3: content.append("Пароль должен состоять минимум из 8 символов \n"); break;
            case 4: content.append("Пароли не совпадают \n"); break;
        }

        switch (CheckDataService.checkAgreement(licenceAgreement)) {
            case 1:
                content.append("Необходимо согласиться с условиями \n");
                break;
        }

        return content.toString();
    }


    private void addUser(String name, String email, String password) throws SQLException {
        String updatedPassword = CheckDataService.updatePassword(password, name, email);
        User user = new User(name, email, updatedPassword);
        userService.create(user);
        user = userService.findByEmail(email);
        basketService.createByUserId(user.getUserId());
    }
}
