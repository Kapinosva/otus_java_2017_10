package webServer.servlets;

import accountService.LoginService;
import accountService.account.exception.NoSuchUserException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import webServer.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignInRequestsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        AllRequestsServlet defaultGet = new AllRequestsServlet();
        defaultGet.doGet(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        response.setContentType("text/html;charset=utf-8");

        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "SpringBeans.xml");
        LoginService loginService = context.getBean("loginService", LoginService.class);
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