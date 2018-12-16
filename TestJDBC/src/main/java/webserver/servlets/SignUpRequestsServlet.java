package webserver.servlets;

import accountService.AccountService;
import accountService.account.exception.DuplicateUserException;
import accountService.account.exception.EmptyLoginOrPasswordException;
import context.Context;
import webserver.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpRequestsServlet extends HttpServlet {

    private Context context;

    public SignUpRequestsServlet(Context context){
        this.context = context;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println(PageGenerator.instance().getPage("signupGet.html", null));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
//        Map<String, Object> pageVariables = createPageVariablesMap(request);
        Map<String, Object> pageVariables = new HashMap<>();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        response.setContentType("text/html;charset=utf-8");
        AccountService ac = context.get(AccountService.class);
        try {
            ac.registerUser(login, password);
            response.setStatus(HttpServletResponse.SC_OK);
            pageVariables.put("value", "Registered user: " + login);
        } catch (DuplicateUserException | EmptyLoginOrPasswordException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            pageVariables.put("value", "Unregistered user " + e.getMessage());
            e.printStackTrace();
        }
        response.getWriter().println(PageGenerator.instance().getPage("signuppost.html", pageVariables));
    }


}
