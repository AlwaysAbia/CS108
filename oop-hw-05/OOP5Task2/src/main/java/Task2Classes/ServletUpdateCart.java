package Task2Classes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ServletUpdateCart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ShoppingCart sc = (ShoppingCart) session.getAttribute("cart");

        Set<String> keys = new HashSet<>(sc.getShoppingCart().keySet());
        for(String key : keys){
            String paramName = "count_" + key;
            String paramValue = request.getParameter(paramName);
            if (paramValue != null && !paramValue.isEmpty()) {
                try {
                    int newQuantity = Integer.parseInt(paramValue);
                    sc.updateCountWithName(key, newQuantity);
                } catch (NumberFormatException e) {
                    // Handle invalid number format
                    // For example, you can log the error or perform other error handling
                    System.err.println("Invalid quantity format for item " + key + ": " + paramValue);
                }
            }
        }

        session.setAttribute("cart", sc);
        RequestDispatcher dispatcher = request.getRequestDispatcher("shopping-cart.jsp");
        dispatcher.forward(request, response);
    }

}
