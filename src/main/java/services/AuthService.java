package services;

import entities.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    public static User getUser(HttpServletRequest req) {
        if(isSigned(req)){
            User user = (User) req.getSession().getAttribute("user");
            return user;
        }
        return null;
    }

    public static boolean isSigned(HttpServletRequest req){
        return req.getSession().getAttribute("user") != null;
    }

    public static void signIn(HttpServletRequest req, User user){
        req.getSession().setAttribute("user", user);
    }

    public static void signOut(HttpServletRequest req){
        req.getSession().removeAttribute("user");
    }
}
