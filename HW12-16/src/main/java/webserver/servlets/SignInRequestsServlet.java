package webserver.servlets;

import accountService.LoginService;
import accountService.account.exception.NoSuchUserException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import webserver.templater.PageGenerator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configurable
public class SignInRequestsServlet extends HttpServlet {
    @Autowired
    private LoginService loginService;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        AllRequestsServlet defaultGet = new AllRequestsServlet();
        defaultGet.doGet(request, response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        response.setContentType("text/html;charset=utf-8");
        try {
            loginService.loginUser(login,password, request.getSession());
            response.setStatus(HttpServletResponse.SC_OK);
            pageVariables.put("value", "Login as! " + login);
        } catch (NoSuchUserException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            pageVariables.put("value", "Unauthorized! " + e.getMessage());
        }


        response.getWriter().write(PageGenerator.instance().getPage("signin.html", pageVariables));
    }


}
