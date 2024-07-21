import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SudokuFrame extends JFrame {

	private JTextArea puzzle;
	private JTextArea solution;
	private JButton checkButton;
	private JCheckBox autoCheckCheckbox;

	public SudokuFrame() {
		super("Sudoku Solver");
		
		// YOUR CODE HERE
		setLayout(new BorderLayout(4,4));

		puzzle = new JTextArea(15,20);
		solution = new JTextArea(15,20);

		puzzle.setBorder(new TitledBorder("Puzzle"));
		solution.setBorder(new TitledBorder("Solution"));
		solution.setEditable(false);

		add(puzzle, BorderLayout.WEST);
		add(solution, BorderLayout.EAST);

		Box bottomBox = Box.createHorizontalBox();
		checkButton = new JButton("Check");
		autoCheckCheckbox = new JCheckBox("Auto Check");
		autoCheckCheckbox.setSelected(true);

		bottomBox.add(autoCheckCheckbox);
		bottomBox.add(checkButton);
		add(bottomBox, BorderLayout.SOUTH);

		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkSolution();
			}
		});

		puzzle.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(autoCheckCheckbox.isSelected()) {
					checkSolution();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(autoCheckCheckbox.isSelected()) {
					checkSolution();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if(autoCheckCheckbox.isSelected()) {
					checkSolution();
				}
			}
		});
		// Could do this:
		// setLocationByPlatform(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

	private void checkSolution(){
		Sudoku sudoku;

		try{
			sudoku = new Sudoku(Sudoku.textToGrid(puzzle.getText()));
		}catch (Exception e){
			solution.setText("Parsing Problem");
			return;
		}

		int solutionNum = sudoku.solve();
		solution.setText(sudoku.getSolutionText() + "\nSolutions:" + solutionNum +
													"\nElapsed:" + sudoku.getElapsed() + "ms");
	}
}
