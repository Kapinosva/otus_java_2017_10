package webserver.servlets;

import accountService.AccountService;
import accountService.account.UserAccount;
import context.Context;
import webserver.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AdminPageRequestServlet extends HttpServlet {


    private Context context;

    public AdminPageRequestServlet(Context context){
        this.context = context;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
                //createPageVariablesMap(request);

        AccountService ac = context.get(AccountService.class);


        UserAccount currentUser= (UserAccount)request.getSession().getAttribute("currentUser");
        if (currentUser != null && currentUser.isAdmin()){
            response.setStatus(HttpServletResponse.SC_OK);
            StringBuilder userTavble = new StringBuilder();
            for ( UserAccount user:ac.getUserList()){
                userTavble.append("<tr>");
                userTavble.append("<td>");
                userTavble.append("<a href=\"/getuser?id=" + user.getId()  + "\">");
                userTavble.append(user.getId());
                userTavble.append("</a>");
                userTavble.append("</td>");
                userTavble.append("<td>");
                userTavble.append(user.getLogin());
                userTavble.append("</td>");
                userTavble.append("<td>");
                userTavble.append(user.getName());
                userTavble.append("</td>");
                userTavble.append("</tr>");
            }

            pageVariables.put("usertable", userTavble.toString());
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
