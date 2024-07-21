import java.sql.*;

public class DBFunctionality {
    private static final String user = DBInfo.MYSQL_USER;
    private static final String pass = DBInfo.MYSQL_PASS;
    private static final String server = DBInfo.MYSQL_SERVER_NAME;
    private static final String db = DBInfo.MYSQL_DB_NAME;

    private final Connection con;

    public DBFunctionality() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        con = DriverManager.getConnection
                ("jdbc:mysql://" + server, user , pass);
    }

    public ResultSet searchTable(String populationType, String matchType,
                            String metropolis, String continent, String population) throws SQLException {
        Statement stmt = con.createStatement();
        stmt.executeUpdate("USE " + db);

        String query = "SELECT * " +
                "FROM metropolises";
        if(!population.isEmpty() || !continent.isEmpty() || !metropolis.isEmpty()){
            query += " WHERE";
        }
        if(populationType.equals("Population More Than")) {
            if(!population.isEmpty()) {
                query += " population >" + population;
            }
        }else{
            if(!population.isEmpty()) {
                query += " population <=" + population;
            }
        }
        if(matchType.equals("Exact Match")) {
            if(!metropolis.isEmpty()){
                if(!population.isEmpty()) {
                    query += " AND";
                }
                query += " metropolis = '" + metropolis + "'";
            }
            if(!continent.isEmpty()){
                if(!population.isEmpty() || !metropolis.isEmpty()) {
                    query += " AND";
                }
                query += " continent = '" + continent + "'";
            }
        }else{
            if(!metropolis.isEmpty()){
                if(!population.isEmpty()) {
                    query += " AND";
                }
                query += " metropolis LIKE '%" + metropolis + "%'";
            }
            if(!continent.isEmpty()){
                if(!population.isEmpty() || !metropolis.isEmpty()) {
                    query += " AND";
                }
                query += " continent LIKE '%" + continent + "%'";
            }
        }
        return stmt.executeQuery(query);
    }

    public void addToTable(String metropolis, String continent, String population) throws SQLException {
        String update = "INSERT INTO metropolises (metropolis, continent, population) VALUES (?, ?, ?)";

        PreparedStatement pstmt = con.prepareStatement(update);
        pstmt.setString(1, metropolis);
        pstmt.setString(2, continent);
        pstmt.setString(3, population);

        pstmt.executeUpdate();

    }
}
