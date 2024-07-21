<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Task2Classes.ShoppingCart" %>
<%@ page import="java.util.Map" %>

<html>
    <head>
        <title>Shopping Cart</title>
    </head>
    <body>
        <h1>Shopping Cart</h1>

        <%
            String id = request.getParameter("id");
            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
            if (cart != null) {
                Map<String, Integer> shoppingCart = cart.getShoppingCart();
                Map<String, Double> nameToPrice = cart.getNameToPrice();
                if (shoppingCart != null && !shoppingCart.isEmpty()) {
        %>
        <form action="ServletUpdateCart" method="post">
            <ul>
                <%
                    for (Map.Entry<String, Integer> entry : shoppingCart.entrySet()) {
                        String itemName = entry.getKey();
                        int quantity = entry.getValue();
                        double price = nameToPrice.get(itemName);
                %>
                <li>
                    <input type="hidden" name="itemName" value="<%= itemName %>">
                    <label><%= itemName %></label>
                    <input type="number" name="count_<%= itemName %>" value="<%= quantity %>" min="0">
                    <span>Price: $<%= price %></span>
                </li>
                    <% } %>
            </ul>
                <div style="text-align: center;">
                    <p>Total Cost: $<%= cart.getTotalCost() %></p>
                    <input type="submit" value="Update Cart">
                </div>
        </form>
        <%
                }
            }
        %>
        <div style="text-align: center;">
            <a href="homePage.jsp">Back to Homepage</a>
        </div>
    </body>
</html>
