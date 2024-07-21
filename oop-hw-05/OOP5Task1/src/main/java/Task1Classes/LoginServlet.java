package Task1Classes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private AccountManager am;

    @Override
    public void init() throws ServletException {
        // Retrieve the AccountManager from the servlet context
        am = (AccountManager) getServletContext().getAttribute("AccountManager");
        if (am == null) {
            throw new ServletException("AccountManager not initialized in Listener");
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean isValid = am.existsAccount(username) &&
                am.checkPassword(username, password);

        if(isValid){
            RequestDispatcher dispatcher = req.getRequestDispatcher("goodLogin.jsp");
            dispatcher.forward(req, res);
        }else{
            RequestDispatcher dispatcher = req.getRequestDispatcher("badLogin.jsp");
            dispatcher.forward(req, res);
        }
    }
}
