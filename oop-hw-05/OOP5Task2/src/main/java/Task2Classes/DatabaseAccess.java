package Task2Classes;
import java.sql.*;
public class DatabaseAccess {
    private String url = "jdbc:mysql://localhost:3306/oop5task2db";
    private String user = "root";
    private String password = "sandro2003";

    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM products WHERE productID = ?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Product getProduct(String id){
        Product product = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String imageFile = rs.getString("imageFile");
                double price = rs.getDouble("price");
                product = new Product(id, name, imageFile, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}