import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DBFrame extends JFrame {
    protected JTextField metropolis;
    protected JTextField continent;
    protected JTextField population;

    protected JButton add;
    protected JButton search;

    protected JComboBox populationType;
    protected JComboBox matchType;
    
    protected JTable table;
    protected DefaultTableModel model;

    public DBFrame(){
        super("Metropolis table Viewer");

        setLayout(new BorderLayout());

        //TopSide
        JPanel topSide = new JPanel();

        metropolis = new JTextField(10);
        continent = new JTextField(10);
        population = new JTextField(10);

        topSide.add(new JLabel("Metropolis:"));
        topSide.add(metropolis);
        topSide.add(new JLabel("Continent:"));
        topSide.add(continent);
        topSide.add(new JLabel("Population:"));
        topSide.add(population);

        //RightSide
        JPanel rightSide = new JPanel();
        rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));

        add = new JButton("Add");
        search = new JButton("Search");
        Component space = Box.createRigidArea(new Dimension(0, 10));

        rightSide.add(add);
        rightSide.add(space);
        rightSide.add(search);

        //Search Options

        JPanel rightSideBot = new JPanel();
        rightSideBot.setLayout(new BoxLayout(rightSideBot, BoxLayout.Y_AXIS));
        rightSideBot.setBorder(new TitledBorder("Search Options"));

        Component space2 = Box.createRigidArea(new Dimension(0, 10));
        String[] popBox = {"Population More Than", "Population Less Than or Equal to"};
        populationType = new JComboBox(popBox);
        String[] matchBox = {"Exact Match", "Partial Match"};
        matchType = new JComboBox(matchBox);

        rightSideBot.add(populationType);
        rightSideBot.add(space2);
        rightSideBot.add(matchType);

        rightSide.add(rightSideBot);

        //LeftSide
        JPanel leftSide = new JPanel(new BorderLayout());

        String[] colNames = {"Metropolis", "Continent", "Population"};

        model = new DefaultTableModel(new Object[][]{}, colNames);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        leftSide.add(scrollPane, BorderLayout.CENTER);

        //Final
        add(topSide, BorderLayout.NORTH);
        add(rightSide, BorderLayout.EAST);
        add(leftSide, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public DefaultTableModel getModel(){
        return model;
    }
}
