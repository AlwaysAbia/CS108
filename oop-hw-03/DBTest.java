import junit.framework.TestCase;

import java.sql.*;
import java.util.Objects;

public class DBTest extends TestCase {
    private static final String user = DBInfo.MYSQL_USER;
    private static final String pass = DBInfo.MYSQL_PASS;
    private static final String server = DBInfo.MYSQL_SERVER_NAME;
    private static final String db = DBInfo.MYSQL_DB_NAME;

    private Connection con;

    protected void setUp() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        con = DriverManager.getConnection
                ("jdbc:mysql://" + server, user , pass);
    }

    public void testQuery() throws SQLException, ClassNotFoundException {
        DBFunctionality dbf = new DBFunctionality();

        Statement stmt = con.createStatement();
        stmt.executeUpdate("USE " + db);

        ResultSet rs1 = dbf.searchTable("Population More Than", "Exact Match",
                "", "", "");
        ResultSet rs2 = stmt.executeQuery("SELECT * from metropolises");
        assertTrue(compResultSet(rs1, rs2));

        ResultSet rs3 = stmt.executeQuery("SELECT * from metropolises WHERE population > 2000000");
        ResultSet rs4 = dbf.searchTable("Population More Than", "Exact Match",
                "", "", "2000000");
        assertTrue(compResultSet(rs3, rs4));

        ResultSet rs5 = stmt.executeQuery("SELECT * from metropolises WHERE population <= 2000000");
        ResultSet rs6 = dbf.searchTable("Population Less Than or Equal", "Exact Match",
                "", "", "2000000");
        assertTrue(compResultSet(rs5, rs6));

        ResultSet rs7 = stmt.executeQuery("SELECT * from metropolises WHERE population > 2000000 " +
                "AND continent LIKE '%Eur%'");
        ResultSet rs8 = dbf.searchTable("Population More Than", "Partial Match",
                "", "Eur", "2000000");
        assertTrue(compResultSet(rs7, rs8));

        ResultSet rs9 = stmt.executeQuery("SELECT * FROM metropolises WHERE population <= 2000000" +
                " AND metropolis LIKE '%Lon%' AND continent LIKE '%Eur%'");
        ResultSet rs10 = dbf.searchTable("Population Less Than or Equal to", "Partial Match",
                "Lon", "Eur", "2000000");
        assertTrue(compResultSet(rs9, rs10));

        ResultSet rs11 = stmt.executeQuery("SELECT * FROM metropolises WHERE population <= 2000000" +
                " AND metropolis = 'London' AND continent = 'Europe'");
        ResultSet rs12 = dbf.searchTable("Population Less Than or Equal to", "Exact Match",
                "London","Europe", "2000000");
    }

    public void testUpdate() throws SQLException, ClassNotFoundException {
        DBFunctionality dbf = new DBFunctionality();

        Statement stmt = con.createStatement();
        stmt.executeUpdate("USE " + db);

        dbf.addToTable("testMetrop", "testCont", "12345");
        assertTrue(checkIfContainsRow("testMetrop", "testCont", "12345"));
    }

    //Helper Method to Compare ResultSets
    private boolean compResultSet(ResultSet rs1, ResultSet rs2) throws SQLException {
        while (rs1.next() && rs2.next()) {
            // Compare each column value
            ResultSetMetaData metaData1 = rs1.getMetaData();
            ResultSetMetaData metaData2 = rs2.getMetaData();
            int columnCount1 = metaData1.getColumnCount();
            int columnCount2 = metaData2.getColumnCount();

            if (columnCount1 != columnCount2) {
                return false; // If the number of columns differs, result sets are not equal
            }

            for (int i = 1; i <= columnCount1; i++) {
                Object value1 = rs1.getObject(i);
                Object value2 = rs2.getObject(i);

                if (!Objects.equals(value1, value2)) {
                    return false; // If any column value differs, result sets are not equal
                }
            }
        }

        // If both result sets have the same number of rows and all column values match, they are equal
        return !rs1.next() && !rs2.next();
    }

    //Helper Method to Check whether a query contains a row or not
    private boolean checkIfContainsRow(String metropolis, String continent, String population) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM metropolises");

        while (resultSet.next()) {
            String metropolisValue = resultSet.getString("metropolis");
            String continentValue = resultSet.getString("continent");
            String populationValue = resultSet.getString("population");

            if (metropolisValue.equals(metropolis) && continentValue.equals(continent) && populationValue.equals(population)) {
                return true; // Found the row with matching values
            }
        }
        return false;
    }

}
