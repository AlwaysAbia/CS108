// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		int top = -1;
		int bot = -1;
		int left = -1;
		int right = -1;

		for(int row = 0; row < grid.length; row++){
			for(int col = 0; col < grid[row].length; col++){
				char currChar = grid[row][col];
				if(currChar == ch) {
					if (top == -1) {
						top = row;
						bot = row;
						left = col;
						right = col;
					} else if (row > bot) bot = row;
					else if (row < top) top = row;

					if (col < left) left = col;
					else if (col > right) right = col;
				}
			}
		}
		if(top == -1) return 0;
		int area = (bot - top + 1)*(right - left + 1);
		return area;
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		int plusCount = 0;
		for(int row = 1; row < grid.length-1; row++) {
			for (int col = 1; col < grid[row].length-1; col++) {
				char currChar = grid[row][col];
				int top = searchTop(row, col);
				int bot = searchBot(row, col);
				int left = searchLeft(row, col);
				int right = searchRight(row, col);
				if(top == bot && top == left && top == right && top != 0) plusCount++;
			}
		}
		return plusCount;
	}

	private int searchTop(int row, int col){
		int res = 0;
		char currChar = grid[row][col];

		for(int i = row - 1; i >= 0; i--){
			char currCheck = grid[i][col];
			if(currCheck == currChar) res++;
			else break;
		}
		return res;
	}
	private int searchBot(int row, int col){
		int res = 0;
		char currChar = grid[row][col];

		for(int i = row + 1; i < grid.length; i++){
			char currCheck = grid[i][col];
			if(currCheck == currChar) res++;
			else break;
		}
		return res;
	}
	private int searchLeft(int row, int col){
		int res = 0;
		char currChar = grid[row][col];

		for(int i = col - 1; i >= 0; i--){
			char currCheck = grid[row][i];
			if(currCheck == currChar) res++;
			else break;
		}
		return res;
	}
	private int searchRight(int row, int col){
		int res = 0;
		char currChar = grid[row][col];

		for(int i = col + 1; i < grid[row].length; i++){
			char currCheck = grid[row][i];
			if(currCheck == currChar) res++;
			else break;
		}
		return res;
	}
}
