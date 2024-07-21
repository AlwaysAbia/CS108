//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {
	
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	private boolean[][] grid;
	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}
	
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		for(int y = grid[0].length - 1; y >= 0; y--){
			boolean rowStatus = true;
			for(int x = 0; x < grid.length; x++){
				if(!grid[x][y]) rowStatus = false;
			}
			if(rowStatus){
				moveDown(y);
			}
		}
	}
	private void moveDown(int y){
		for(int j = y; j < grid[0].length - 1; j++){
			for(int i = 0; i < grid.length; i++){
				grid[i][y] = grid[i][y+1];
			}
		}
		for(int i = 0; i < grid.length; i++){
			grid[i][grid[0].length-1] = false;
		}
	}
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid()
	{
		return grid;
	}
}
