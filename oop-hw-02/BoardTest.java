import junit.framework.TestCase;

import static junit.framework.Assert.assertEquals;


public class BoardTest extends TestCase {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	
	protected void setUp() throws Exception {
		b = new Board(3, 6);
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		
		b.place(pyr1, 0, 0);
	}

	//Checks Basics on an Empty Board
	public void testEmptyBoard(){
		Board a = new Board(3, 5);
		assertEquals(a.getMaxHeight(),0);
		assertEquals(a.getWidth(), 3);
		assertEquals(a.getHeight(), 5);
		assertEquals(a.getColumnHeight(0), 0);
		assertEquals(a.getRowWidth(0), 0);
		assertTrue(a.getGrid(3, 5));
		assertFalse(a.getGrid(0,0));
	}

	public void testPlaceBasic(){
		Piece testPiece = new Piece(Piece.STICK_STR);
		Piece testPiece2 = testPiece.fastRotation();

		Piece testPiece3 = new Piece(Piece.S1_STR);
		Piece testPiece4 = testPiece3.fastRotation();

		Board a = new Board(3, 3);
		assertEquals(a.place(testPiece, 0, 0), Board.PLACE_OUT_BOUNDS);
		assertFalse(a.committed);
		a.commit();
		assertTrue(a.committed);
		assertEquals(a.place(testPiece, -1, 0), Board.PLACE_OUT_BOUNDS);
	}

	// Check the basic width/height/max after the one placement
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		System.out.println(b.toString());
	}
	
	// Place sRotated into the board, then check some measures
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}
	
	// Makre  more tests, by putting together longer series of 
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.
	public void testPlaceAndUndo(){
		Board a = new Board(10, 10);
		Board xA = new Board(10,10);

		Piece testPiece = new Piece(Piece.S1_STR);
		Piece testPiece2 = testPiece.fastRotation();

		assertEquals(a.place(testPiece, 0, 0), Board.PLACE_OK);
		assertFalse(a.toString().equals(xA.toString()));
		a.undo();
		assertTrue(a.toString().equals(xA.toString()));
	}

	public void testPlaceAndFillRow(){
		Board a = new Board(4, 5);
		Piece testPiece = new Piece(Piece.STICK_STR);
		Piece testPiece2 = testPiece.fastRotation();

		assertEquals(a.place(testPiece2, 0, 0), Board.PLACE_ROW_FILLED);
		a.commit();
		assertEquals(a.place(testPiece, 0, 2), Board.PLACE_OUT_BOUNDS);
		a.undo();
		assertEquals(a.place(testPiece, 0, 0), Board.PLACE_BAD);
		a.undo();
		assertEquals(a.place(testPiece,0,1), Board.PLACE_OK);
		a.commit();
	}

	public void testDropHeight(){
		Board a = new Board(5, 5);

		Piece pyra = new Piece(Piece.PYRAMID_STR);
		Piece sti = new Piece(Piece.STICK_STR).fastRotation();
		Piece sq = new Piece(Piece.SQUARE_STR);

		assertEquals(a.place(pyra,1, 0), Board.PLACE_OK);
		assertEquals(a.dropHeight(sti,1), 2);
		assertEquals(a.dropHeight(sq, 0), 1);
	}

	public void testClearRows(){
		Board a = new Board(4, 4);
		Board xA = new Board(4,4);

		Piece testPiece = new Piece(Piece.STICK_STR).fastRotation();
		assertEquals(a.place(testPiece,0,0), Board.PLACE_ROW_FILLED);
		a.commit();
		assertEquals(a.clearRows(), 1);
		a.commit();
		assertEquals(a.toString(), xA.toString());
	}
}
