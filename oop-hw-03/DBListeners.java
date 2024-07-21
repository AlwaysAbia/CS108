import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBListeners implements ActionListener {
    private DBFrame frame;
    private DBFunctionality dbf;

    public DBListeners(DBFrame frame, DBFunctionality dbf) {
        this.frame = frame;
        this.dbf = dbf;

        frame.add.addActionListener(this);
        frame.search.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frame.search) {
            frame.getModel().setRowCount(0);

            String metr = frame.metropolis.getText();
            String cont = frame.continent.getText();
            String pop = frame.population.getText();

            String popType = (String)frame.populationType.getSelectedItem();
            String matchType = (String)frame.matchType.getSelectedItem();

            try {
                ResultSet rs = dbf.searchTable(popType, matchType, metr, cont, pop);
                while (rs.next()) {
                    String metropolisValue = rs.getString("metropolis");
                    String continentValue = rs.getString("continent");
                    String populationValue = rs.getString("population");

                    frame.getModel().addRow(new Object[] {metropolisValue, continentValue, populationValue});
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == frame.add) {
            frame.getModel().setRowCount(0);

            String metr = frame.metropolis.getText();
            String cont = frame.continent.getText();
            String pop = frame.population.getText();

            try {
                dbf.addToTable(metr, cont, pop);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            frame.getModel().addRow(new Object[]{metr, cont, pop});
        }
    }
}
