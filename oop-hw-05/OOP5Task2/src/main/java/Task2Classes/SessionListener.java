package Task2Classes;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("cart", new ShoppingCart());
    }

    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
