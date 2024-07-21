// Board.java

import java.util.Arrays;

import static java.lang.System.arraycopy;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;

	private int maxHeight;
	private int xMaxHeight;

	private int[] widths;
	private int[] xWidths;

	private int[] heights;
	private int[] xHeights;

	private boolean[][] xGrid;
	private boolean placed;
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;

		grid = new boolean[width][height];
		xGrid = new boolean[width][height];

		committed = true;
		
		// YOUR CODE HERE
		widths = new int[height];
		xWidths = new int[height];

		heights = new int[width];
		xHeights = new int[width];



		for(int i = 0; i < height;i++){
			widths[i] = 0;
		}
		for(int i = 0; i < width;i++){
			heights[i] = 0;
		}
		maxHeight = 0;
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight()
	{
		 return maxHeight;
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			// YOUR CODE HERE
			int checkMaxHeight = 0;
			int[] checkWidth = new int[height];
			int[] checkHeight = new int[width];

			for(int i = 0; i < width; i++){
				for (int j = 0; j < height; j++) {
					boolean tile = grid[i][j];
					if(tile){
						checkWidth[j]++;
						if(j + 1 > checkHeight[i]) checkHeight[i] = j + 1;
						if(checkHeight[i] > checkMaxHeight) checkMaxHeight = checkHeight[i];
					}
				}
			}
			if(maxHeight != checkMaxHeight) throw new RuntimeException("Max Height is insane");
			if(!Arrays.equals(heights, checkHeight)) throw new RuntimeException("Heights Array is insane");
			if(!Arrays.equals(widths, checkWidth)) throw new RuntimeException("Width Array is insane");
		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	//Iterates Over Skirt, Sums currSkirt and currHeight
	//To see what point the current tile would fall to with no obstructions
	//Takes the largest of these values and subtracts the skirt to find the
	//y at rest
	public int dropHeight(Piece piece, int x) {
		int[] skirt = piece.getSkirt();

		int lowestPoss = 0;
		int result = 0;
		for (int i = 0; i < skirt.length; i++) {
			int currSkirt = skirt[i];
			int currHeight = heights[x+i];
			int currLowestPossible = currHeight + currSkirt;
			if(currLowestPossible > lowestPoss){
				lowestPoss = currLowestPossible;
				result = lowestPoss - currSkirt;
			}
		}
		return result;
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x)
	{
		return heights[x]; // YOUR CODE HERE
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y)
	{
		 return widths[y]; // YOUR CODE HERE
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y)
	{
		if(x >= width || y >= height || x < 0 || y < 0) return true;
		return grid[x][y];
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");

		int result = PLACE_OK;
		
		// YOUR CODE HERE
		committed = false;
		placed = true;
		//Backup
		System.arraycopy(heights,0,xHeights,0,width);
		System.arraycopy(widths,0,xWidths,0,height);
		for (int i = 0; i < width; i++) {
			System.arraycopy(grid[i],0,xGrid[i],0,height);
		}
		xMaxHeight = maxHeight;

		//Check out of bound
		if(x + piece.getWidth() - 1 >= width || y + piece.getHeight() - 1 >= height || x < 0 || y < 0){
			return PLACE_OUT_BOUNDS;
		}

		TPoint[] bod = piece.getBody();
		for (int i = 0; i < bod.length; i++) {
			int placeX = x + bod[i].x;
			int placeY = y + bod[i].y;
			if(grid[placeX][placeY]) return PLACE_BAD;

			grid[placeX][placeY] = true;
			widths[placeY]++;
			if(heights[placeX] < placeY + 1) heights[placeX] = placeY + 1;
			if(heights[placeX]>maxHeight) maxHeight = heights[placeX];
			if(widths[placeY] == width) result = PLACE_ROW_FILLED;
		}
		sanityCheck();
		return result;
	}
	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		int rowsCleared = 0;
		// YOUR CODE HERE

		//If the clear is not happening right after a piece placement, we still
		//need to make a backup
		if(!placed){
			System.arraycopy(heights,0,xHeights,0,width);
			System.arraycopy(widths,0,xWidths,0,height);
			for (int i = 0; i < width; i++) {
				System.arraycopy(grid[i],0,xGrid[i],0,height);
			}
			xMaxHeight = maxHeight;
		}

		//go up the rows from the bottom
		//cycle of finding how many of the next rows will be cleared
		//and moving the rows after down, then checking if after moving the rows down,
		//there are more to be cleared above
		//updating the widths array
		int to = 0;
		for(int row = 0; row < maxHeight; row++){
			while(widths[row] == width){
				rowsCleared++;
				row++;
			}
			for(int colum = 0; colum < width; colum++){
				grid[colum][to] = grid[colum][row];
			}
			widths[to] = widths[row];
			to++;
		}
		//Setting widths of new rows and the upper cleared rows to 0
		while(to < maxHeight){
			widths[to] = 0;
			for(int colum = 0; colum < width; colum++) {
				grid[colum][to] = false;
			}
			to++;
		}

		maxHeight = maxHeight - rowsCleared;

		//Rebuilding heights array
		for(int i = 0; i < width; i++){
			heights[i] = 0;
		}
		for(int j = maxHeight; j >= 0; j--){
			for(int i = 0; i < width; i++){
				if(grid[i][j]){
					if(j+1 > heights[i]) heights[i] = j + 1;
				}
			}
		}
		sanityCheck();
		return rowsCleared;
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		// YOUR CODE HERE
		if(committed) return;
		boolean[][] temp = grid;
		grid = xGrid;
		xGrid = temp;

		int[] tempWidths = widths;
		widths = xWidths;
		xWidths = tempWidths;

		int[] tempHeights = heights;
		heights = xHeights;
		xHeights = tempHeights;

		int tempMaxHeight = maxHeight;
		maxHeight = xMaxHeight;
		xMaxHeight = tempMaxHeight;

		committed = true;
		placed = false;
		sanityCheck();
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit()
	{
		committed = true;
		placed = false;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


