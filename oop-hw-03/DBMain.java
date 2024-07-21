import java.sql.SQLException;

public class DBMain {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBFrame frame = new DBFrame();
        DBFunctionality dbf = new DBFunctionality();
        DBListeners dbl = new DBListeners(frame, dbf);
    }
}
