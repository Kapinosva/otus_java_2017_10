package webServer.servlets;

import accountService.AccountService;
import accountService.account.UserAccount;
import accountService.account.exception.NoSuchUserException;

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
public class EditUserRequestServlet extends HttpServlet {
    @Autowired
    private AccountService ac;

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        UserAccount currentUser = (UserAccount)request.getSession().getAttribute("currentUser");
        UserAccount editingUser = getEditingUser(request, response);
        if (editingUser == null){
            editingUser = currentUser;
        }

        if (editingUser != null && currentUser != null && (currentUser.isAdmin() || currentUser.getId() == editingUser.getId())){
            fillPageVariables(pageVariables, editingUser);
            response.getWriter().write(PageGenerator.instance().getPage("user.html", pageVariables));
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().write(PageGenerator.instance().getPage("index.html", null));
        }
        response.setContentType("text/html;charset=utf-8");
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        UserAccount currentUser = (UserAccount)request.getSession().getAttribute("currentUser");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        UserAccount editingUser = null;
        try {
            editingUser = getEditingUser(request, response);
            editingUser.setLogin(login);
            editingUser.setPassword(password);
            editingUser.setName(name);
            ac.updateUser(editingUser);
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }
        if (editingUser != null && currentUser != null && (currentUser.isAdmin() || currentUser.getId() == editingUser.getId())){
            fillPageVariables(pageVariables, editingUser);
            if (currentUser.getId() == editingUser.getId()){
                request.getSession().setAttribute("currentUser", editingUser);
            }
            response.getWriter().write(PageGenerator.instance().getPage("user.html", pageVariables));
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().write(PageGenerator.instance().getPage("index.html", null));
        }
        response.setContentType("text/html;charset=utf-8");
    }

    private void fillPageVariables(Map<String, Object> pageVariables, UserAccount editingUser){
        pageVariables.put("userLogin", "\""+editingUser.getLogin()+"\"");
        pageVariables.put("userPassword", "\""+editingUser.getPassword()+"\"");
        pageVariables.put("userName", "\""+editingUser.getName()+"\"");
        pageVariables.put("userId", "\""+editingUser.getId()+"\"");
    }

    private UserAccount getEditingUser(HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        UserAccount result = null;
        long editingUserId;
        try {
            editingUserId = Long.parseLong(request.getParameter("id"));
        }catch (Exception e){
            editingUserId = 0;
        }
        if (editingUserId > 0) {
            try {
                result = ac.getRegisteredUserById(editingUserId);
            } catch (NoSuchUserException e) {
                e.printStackTrace();
            }
        }
        return result;

    }

}
