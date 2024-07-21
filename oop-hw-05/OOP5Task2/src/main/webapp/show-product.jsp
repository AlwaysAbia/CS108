<%--
  Created by IntelliJ IDEA.
  User: sandr
  Date: 5/25/2024
  Time: 8:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Task2Classes.Product" %>
<%@ page import="Task2Classes.DatabaseAccess" %>
<html>
    <%
        String id = request.getParameter("id");

        DatabaseAccess dbAccess = new DatabaseAccess();

        Product product = dbAccess.getProduct(id);

        String name;
        String prc;
        String imgPath;

        if(product != null) {
            name = product.getName();
            imgPath = "store-images/" + product.getImagefile();
            prc = "$" + product.getPrice();
        }else{
            name = "nullProblem";
            imgPath = "nullProblem.jpg";
            prc = "nullProblem";
        }
        assert product != null;
    %>
    <head>
        <title><%=name%></title>
    </head>
    <body>
        <h1><%=name%></h1>
        <img src=<%=imgPath%> alt="Cant_Find">
        <div>
            <%=prc%>
            <form action="ServletAddToCart" method = "post">
                <input type="hidden" name="id" value="<%=id%>">
                <button type="submit">Add To Cart</button>
            </form>
        </div>
    </body>
</html>
