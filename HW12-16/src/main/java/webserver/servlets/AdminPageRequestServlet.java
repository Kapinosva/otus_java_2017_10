package webserver.servlets;

import app.AccountService;
import accountService.account.UserAccount;
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
public class AdminPageRequestServlet extends HttpServlet {

    @Autowired
    AccountService ac;

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        UserAccount currentUser = (UserAccount)request.getSession().getAttribute("currentUser");
        if (currentUser != null && currentUser.isAdmin()){
            response.setStatus(HttpServletResponse.SC_OK);
            pageVariables.put("usertable", ac.getUserList());
            response.getWriter().println(PageGenerator.instance().getPage("adminpage.html", pageVariables));
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().println(PageGenerator.instance().getPage("default.html", null));
        }
        response.setContentType("text/html;charset=utf-8");
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        UserAccount currentUser = (UserAccount)request.getSession().getAttribute("currentUser");
        if (currentUser != null && currentUser.isAdmin()){
            response.getWriter().println(PageGenerator.instance().getPage("adminpage.html", null));
        }else{
            response.getWriter().println(PageGenerator.instance().getPage("default.html", null));
        }
    }

}
