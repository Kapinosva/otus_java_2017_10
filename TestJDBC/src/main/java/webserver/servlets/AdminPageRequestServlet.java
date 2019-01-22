package webServer.servlets;

import accountService.AccountService;
import accountService.account.UserAccount;
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


public class AdminPageRequestServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "SpringBeans.xml");
        AccountService ac = context.getBean("accountService", AccountService.class);

        UserAccount currentUser = (UserAccount)request.getSession().getAttribute("currentUser");
        if (currentUser != null && currentUser.isAdmin()){
            response.setStatus(HttpServletResponse.SC_OK);
            pageVariables.put("usertable", ac.getUserList());
            response.getWriter().println(PageGenerator.instance().getPage("adminpage.html", pageVariables));
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().println(PageGenerator.instance().getPage("index.html", null));
        }
        response.setContentType("text/html;charset=utf-8");
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        UserAccount currentUser = (UserAccount)request.getSession().getAttribute("currentUser");
        if (currentUser != null && currentUser.isAdmin()){
            response.getWriter().println(PageGenerator.instance().getPage("adminpage.html", null));
        }else{
            response.getWriter().println(PageGenerator.instance().getPage("index.html", null));
        }
    }

}
