package webServer.servlets;

import accountService.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import webServer.templater.PageGenerator;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Configurable
public class SignUpRequestsServlet extends HttpServlet {
    @Autowired
    private AccountService ac;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write(PageGenerator.instance().getPage("signupGet.html", null));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
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
            ac.registerUser(login, password);
            response.setStatus(HttpServletResponse.SC_OK);
            pageVariables.put("value", "Registered user: " + login);
        } catch (Exception e){ //(DuplicateUserException | EmptyLoginOrPasswordException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            pageVariables.put("value", "Unregistered user " + e.getMessage());
            e.printStackTrace();
        }
        response.getWriter().write(PageGenerator.instance().getPage("signupPost.html", pageVariables));
    }


}
