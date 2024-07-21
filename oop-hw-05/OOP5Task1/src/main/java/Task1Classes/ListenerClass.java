package Task1Classes;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ListenerClass implements ServletContextListener {
    //EmptyConstructor
    public ListenerClass(){

    }
    //AccountListener Initialization
    public void contextInitialized(ServletContextEvent sce) {
        AccountManager am = new AccountManager();
        sce.getServletContext().setAttribute("AccountManager", am);
    }
    //Empty contextDestroyed
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
