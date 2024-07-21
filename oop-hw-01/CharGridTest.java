
// Test cases for CharGrid -- a few basic tests are provided.

import junit.framework.TestCase;

public class CharGridTest extends TestCase {
	
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}
	
	
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}
	public void testCountPlus1(){
		char[][] grid = new char[][] {
				{' ', 'e', ' ', ' ', ' '},
				{' ', 'p', ' ', 'x', ' '},
				{'p', 'p', 'p', 'z', 'p'},
				{' ', 'p', ' ', 'y', ' '},
				{' ', 'e', 'a', 'b', 'c'}
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.countPlus());
	}
	public void testCountPlus2(){
		char[][] grid = new char[][] {
				{' ', ' ', ' ', ' ', ' '},
				{' ', 'p', ' ', ' ', ' '},
				{'p', 'p', 'p', 'p', 'p'},
				{' ', 'p', ' ', ' ', ' '},
				{' ', ' ', ' ', ' ', ' '}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}
	public void testCountPlus3(){
		char[][] grid = new char[][] {
				{' ', 'p', 'p', ' ', ' '},
				{'p', 'p', 'p', 'y', 'p'},
				{'p', 'p', 'p', 'p', 'p'},
				{'p', 'y', 'p', 'p', 'p'},
				{'y', 'y', 'y', 'p', 'p'},
				{'p', 'y', 'p', 'p', 'p'},
				{' ', 'p', ' ', ' ', ' '}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(2, cg.countPlus());

	}
	
	
}
