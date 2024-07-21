import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.

	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);

		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}
	
	
	private final int[][] grid;
	private ArrayList<int[][]> solvedSudokuList;
	long solveTime;
	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		// YOUR CODE HERE
		this.grid = ints;
	}

	public Sudoku(String text){
		this(textToGrid(text));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				sb.append(grid[i][j]).append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	//Initializes ArrayList of empty spots (placed in the grid that are equal to 0)
	private void initSpotList(ArrayList<Spot> spotList){
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				if(grid[i][j] == 0){
					spotList.add(new Spot(i, j));
				}
			}
		}
		spotList.sort((spot1, spot2) -> {
            HashSet<Integer> availableSpots1 = spot1.getAvailableSpots();
            HashSet<Integer> availableSpots2 = spot2.getAvailableSpots();
            return Integer.compare(availableSpots1.size(), availableSpots2.size());
        });
	}

	private void solveHelper(int index, ArrayList<Spot> spotList){
		if(index >= spotList.size()){
			/*This Means we're at the end without encountering any problems
			*So this grid is valid
			*/
			if(solvedSudokuList.size() == MAX_SOLUTIONS) return;

			int[][] clonedGrid = new int[SIZE][SIZE];
			for (int i = 0; i < SIZE; i++) {
				clonedGrid[i] = grid[i].clone();
			}
			solvedSudokuList.add(clonedGrid);

			return;
		}

		Spot currSpot = spotList.get(index);
		HashSet<Integer> currAvailableNums = currSpot.getAvailableSpots();

		if(currAvailableNums.isEmpty()){
			/*No legal number can be put into this spot
			*Meaning no valid solution for this grid config,
			*so we need to return
			*/
			return;
		}

		for(Integer num : currAvailableNums){
			currSpot.setValue(num);
			solveHelper(index + 1, spotList);
			currSpot.setValue(0);
			if (solvedSudokuList.size() == MAX_SOLUTIONS) return; // Stop further exploration
		}

	}
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		long t = System.currentTimeMillis();

		//Initialize solvedSudokuList
		this.solvedSudokuList = new ArrayList<>();
		//Initialize Spot ArrayList
		ArrayList<Spot> spotList = new ArrayList<>();
		initSpotList(spotList);
		solveHelper(0, spotList);

		solveTime = System.currentTimeMillis() - t;

		return solvedSudokuList.size(); // YOUR CODE HERE
	}
	
	public String getSolutionText() {
		if(solvedSudokuList.isEmpty()) return "";
		int[][] sol1 = solvedSudokuList.get(0);
		String result = "";
		for (int i = 0; i < SIZE; i++) {
			result += Arrays.toString(sol1[i]).replace("[", "")
					.replace("]", "").replace(",", "") + "\n";
		}
		return result;
	}
	
	public long getElapsed() {
		return solveTime; // YOUR CODE HERE
	}

	public class Spot{

		private final int row;
		private final int col;

		private static HashSet<Integer> fullSet = null;

		public Spot(int row, int col) {
			//Singleton Init of fullSet
			if (fullSet == null) {
				fullSet = new HashSet<>(Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
			}
			//Init Coordinates of this spot
			this.row = row;
			this.col = col;
		}
		//Returns Grid[row][col] value
		public int getValue(){
			return grid[row][col];
		}
		//Sets Grid[row][col] value
		public void setValue(int value){
			grid[row][col] = value;
		}
		/*Takes a copy of fullSet, goes over the row,col, and box
		  of the slot and removes any numbers present from the copied
		 fullSet, then returns it*/
		public HashSet<Integer> getAvailableSpots() {
			if(getValue() != 0) return null;
			HashSet<Integer> retSet = new HashSet<>(fullSet);

			//Go over the row
			for(int i = 0; i < SIZE; i++){
				int num = grid[row][i];
                retSet.remove(num);
			}
			//Go over the col
			for(int i = 0; i < SIZE; i++){
				int num = grid[i][col];
                retSet.remove(num);
			}
			//Go over the Box
			for(int i = 0; i < SIZE; i++) {
				int r = (row/PART)*PART + i/PART;
				int c = (col/PART)*PART + i%PART;
				int num = grid[r][c];
				retSet.remove(num);
			}

			return retSet;
		}
	}

}
