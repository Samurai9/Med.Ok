package servlets;


import entities.User;
import services.AuthService;
import services.CheckDataService;
import services.userService.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/profile/edit")
public class ProfileEditServlet extends HttpServlet {

    private UserService userService = null;
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.userService = (UserService) servletConfig.getServletContext().getAttribute("userService");
        super.init(servletConfig);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Редактирование");
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/profileEdit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = null;
        String email = null;
        String password = null;
        String repeatPassword = null;
        String agreement = null;
        User user = (User) req.getSession().getAttribute("user");
        StringBuilder content = new StringBuilder();

        switch (req.getParameter("action")){
            case ("change_inf"):

                try {
                    name = req.getParameter("name");
                    email = req.getParameter("email");
                    password = req.getParameter("password");


                    if (CheckDataService.checkPasswords(password, user.getName(), user.getEmail(), user.getPassword())){
                        if (!user.getName().equals(name)){
                            if (CheckDataService.checkName(name) != 0) {
                                content.append("Неверное имя");
                            }
                        }
                        if (!user.getEmail().equals(email)){
                            if (CheckDataService.checkEmail(email) != 0){
                                content.append("Неверная почта");
                            } else if (CheckDataService.checkEmailInDb(email, userService) == 1){
                                content.append("Пользователь с такой почтой уже зарегестрирован");
                            }
                        }
                    } else {
                        content.append("Неверный пароль");
                    }

                    if (content.toString().equals("")){
                        String updatedPassword = CheckDataService.updatePassword(password, name, email);
                        userService.update(new User(user.getUserId(), name, email, updatedPassword, user.getDiscount()));
                        user = userService.findByEmail(email);
                        AuthService.signOut(req);
                        AuthService.signIn(req, user);
                        getServletContext().setAttribute("text", "Изменения успешно применены");
                        resp.sendRedirect(req.getContextPath() + "/success");
                    } else {
                        getServletContext().setAttribute("text", content.toString());
                        resp.sendRedirect(req.getContextPath() + "/fail");
                    }
                } catch (SQLException e) {
                    getServletContext().setAttribute("text", "Что-то пошло не так");
                    resp.sendRedirect(req.getContextPath() + "/fail");
                }

                break;


            case ("change_pas"):

                try {
                    password = req.getParameter("new_password");
                    repeatPassword = req.getParameter("repeat_password");
                    switch (CheckDataService.checkPasswordsCorrectness(password, repeatPassword)){
                        case 1: content.append("Вы не ввели пароль \n"); break;
                        case 2: content.append("Вы не ввели повтор пароля \n"); break;
                        case 3: content.append("Пароль должен состоять минимум из 8 символов \n"); break;
                        case 4: content.append("Пароли не совпадают \n"); break;
                    }
                    String oldPassword = req.getParameter("password");
                    if (!CheckDataService.checkPasswords(oldPassword, user.getName(), user.getEmail(), user.getPassword())){
                        content.append("Неверный пароль \n");
                    }
                    if (content.toString().equals("")){
                        String updatedPassword = CheckDataService.updatePassword(password, user.getName(), user.getEmail());

                        userService.update(new User(user.getUserId(), user.getName(), user.getEmail(), updatedPassword, user.getDiscount()));
                        User user2 = userService.findByEmail(user.getEmail());
                        AuthService.signOut(req);
                        AuthService.signIn(req, user2);
                        getServletContext().setAttribute("text", "Изменения успешно применены");
                        resp.sendRedirect(req.getContextPath() + "/success");
                    } else {
                        getServletContext().setAttribute("text", content.toString());
                        resp.sendRedirect(req.getContextPath() + "/fail");
                    }
                } catch (SQLException e) {
                    getServletContext().setAttribute("text", "Что-то пошло не так");
                    resp.sendRedirect(req.getContextPath() + "/fail");
                }

                break;


            case("delete_acc"):
                try {
                    password = req.getParameter("password");
                    agreement = req.getParameter("delete_agreement");
                    if (!CheckDataService.checkPasswords(password, user.getName(), user.getEmail(), user.getPassword())){
                        content.append("Неверный пароль");
                    }
                    if (CheckDataService.checkAgreement(agreement) == 1){
                        content.append("Нужно согласиться с удалением");
                    }
                    if(content.toString().equals("")){
                        userService.delete(user);
                        AuthService.signOut(req);
                        getServletContext().setAttribute("text", "Удаление успешно");
                        resp.sendRedirect(req.getContextPath() + "/success");
                    } else {
                        getServletContext().setAttribute("text", content.toString());
                        resp.sendRedirect(req.getContextPath() + "/fail");
                    }
                } catch (SQLException e) {
                    getServletContext().setAttribute("text", "Что-то пошло не так");
                    resp.sendRedirect(req.getContextPath() + "/fail");
                }

                break;


            default:
                getServletContext().setAttribute("text", "Как ты это сделал? Решил мне сайт сломать? Низзя, запрещено");
                resp.sendRedirect(req.getContextPath() + "/fail");
        }
    }
}
