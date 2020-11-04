package filters;

import services.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {
    protected final String[] path = {"/profile", "/profile/edit", "/basket"};

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        boolean needFilter = false;

        for (String path : path) {
            if (path.equals(req.getRequestURI().substring(req.getContextPath().length()))) {
                needFilter = true;
                break;
            }
        }

        if(needFilter && !AuthService.isSigned(req)){
            getServletContext().setAttribute("text", "Доступно только авторизованным пользователям");
            res.sendRedirect(req.getContextPath() + "/fail");
        }
        else {
            if (AuthService.isSigned(req)) {
                req.getSession().setAttribute("user", AuthService.getUser(req));
            }
            chain.doFilter(req, res);
        }
    }
}
