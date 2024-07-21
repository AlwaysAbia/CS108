package Task2Classes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServletAddToCart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        ShoppingCart sc = (ShoppingCart) session.getAttribute("cart");
        if (sc == null) {
            sc = new ShoppingCart();
            session.setAttribute("cart", sc);
        }

        String id = request.getParameter("id");
        sc.addItem(id);

        session.setAttribute("cart", sc);
        RequestDispatcher dispatcher = request.getRequestDispatcher("shopping-cart.jsp");
        dispatcher.forward(request, response);
    }
}
