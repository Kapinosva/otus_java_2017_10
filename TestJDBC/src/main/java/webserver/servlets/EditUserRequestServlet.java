package webserver.servlets;

import accountService.AccountService;
import accountService.account.UserAccount;
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


public class EditUserRequestServlet extends HttpServlet {


    private Context context;

    public EditUserRequestServlet(Context context){
        this.context = context;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        UserAccount currentUser= (UserAccount)request.getSession().getAttribute("currentUser");
        UserAccount editingUser = null;
        AccountService ac = context.get(AccountService.class);
        long editingUserId;
        try {
            editingUserId = Long.parseLong(request.getParameter("id"));
        }catch (Exception e){
            editingUserId = 0;
        }
        if (editingUserId > 0) {
            editingUser = ac.getRegisteredUserById(editingUserId);
        }

        if (editingUser != null && currentUser != null && (currentUser.isAdmin() || currentUser.getId() == editingUser.getId())){
            pageVariables.put("userLogin", "\""+editingUser.getLogin()+"\"");
            pageVariables.put("userPassword", "\""+editingUser.getPassword()+"\"");
            pageVariables.put("userName", "\""+editingUser.getName()+"\"");
            pageVariables.put("userId", "\""+editingUser.getId()+"\"");
            response.getWriter().println(PageGenerator.instance().getPage("user.html", pageVariables));
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().println(PageGenerator.instance().getPage("index.html", null));
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

        AccountService ac = context.get(AccountService.class);
        UserAccount editingUser = null;
        try {
            editingUser = new UserAccount(login,password,name);
        } catch (EmptyLoginOrPasswordException e) {
            e.printStackTrace();
        }
        long editingUserId;
        try {
            editingUserId = Long.parseLong(request.getParameter("id"));
        }catch (Exception e){
            editingUserId = 0;
        }
        if (editingUserId > 0) {
            ac.updateUser(editingUserId, editingUser);
        }

        if (editingUser != null && currentUser != null && (currentUser.isAdmin() || currentUser.getId() == editingUser.getId())){
            pageVariables.put("userLogin", "\""+editingUser.getLogin()+"\"");
            pageVariables.put("userPassword", "\""+editingUser.getPassword()+"\"");
            pageVariables.put("userName", "\""+editingUser.getName()+"\"");
            pageVariables.put("userId", "\""+editingUser.getId()+"\"");
            response.getWriter().println(PageGenerator.instance().getPage("user.html", pageVariables));
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().println(PageGenerator.instance().getPage("index.html", null));
        }
        response.setContentType("text/html;charset=utf-8");
    }

    /*private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
       /* HashMap<String, Object> pageVariables = new HashMap<>();
        if (request.getParameterMap().isEmpty()){
            pageVariables.put("value", "");
        }else {
            for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                pageVariables.put("value", entry.getValue()[0]);
                break;
            }
        }
        return pageVariables;
    }*/

}
