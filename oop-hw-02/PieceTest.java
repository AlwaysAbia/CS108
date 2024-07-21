import junit.framework.TestCase;

import java.util.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest extends TestCase {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s, sRotated;

	protected void setUp() throws Exception {
		super.setUp();

		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();

		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
	}
	
	// Here are some sample tests to get you started
	
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}
	
	
	// Test the skirt returned by a few pieces
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
	}

	public void testEquals(){
		assertTrue(new Piece("1 0 1 1 1 2 0 1").equals(new Piece("1 1 1 0 1 2 0 1")));
		assertFalse(new Piece("1 0 1 1 1 2 0 1").equals(new Piece("1 3 1 1 1 2 0 1")));
		assertFalse(new Piece("1 1 1 2 1 3 1 4").equals(new Piece("1 2 1 3 1 4 1 5")));
		assertTrue(new Piece("1 0 1 1 1 2 1 3").equals(new Piece("1 0 1 1 1 2 1 3")));
		assertFalse(new Piece("0 0 1 1 2 2 3 3").equals(new Piece("1 1 2 2 0 0 3 4")));
	}

	public void testRegularRotationsPyrAndSq(){
		Piece pyr = new Piece(Piece.PYRAMID_STR);
		Piece pyrTwo = pyr.computeNextRotation();
		Piece pyrThree = pyrTwo.computeNextRotation();
		Piece pyrFour = pyrThree.computeNextRotation();

		Piece sq = new Piece(Piece.SQUARE_STR);

		assertTrue(pyrTwo.equals(new Piece("1 0 1 1 1 2 0 1")));
		assertTrue(pyrThree.equals(new Piece("2 1 1 1 0 1 1 0")));
		assertTrue(pyrFour.equals(new Piece("0 0 0 1 0 2 1 1")));
		assertTrue(pyrFour.computeNextRotation().equals(pyr));

		assertTrue(sq.computeNextRotation().equals(sq));
	}

	public void testFastRotation(){
		Piece L1 = new Piece(Piece.L1_STR);
		Piece L12 = L1.fastRotation();
		Piece L13 = L12.fastRotation();
		Piece L14 = L13.fastRotation();

		assertTrue(L12.equals(new Piece("0 0 1 0 2 0 2 1")));
		assertTrue(L13.equals(new Piece("0 2 1 0 1 1 1 2")));
		assertTrue(L14.equals(new Piece("0 0 0 1 1 1 2 1")));
		assertTrue(L14.fastRotation().equals(L1));
	}

	public void testBody(){
		Piece sti = new Piece(Piece.STICK_STR);
		TPoint[] stickBod = new TPoint[4];
		for(int i = 0; i < 4; i++) {
			stickBod[i] = new TPoint(0, i);
		}

		assertTrue(Arrays.equals(stickBod, sti.getBody()));
	}

	public void testGetPieces(){
		Piece[] firstRots = Piece.getPieces();
		assertTrue(firstRots[Piece.S1].equals(new Piece(Piece.S1_STR)));
		assertTrue(firstRots[Piece.S2].equals(new Piece(Piece.S2_STR)));
		assertTrue(firstRots[Piece.L1].equals(new Piece(Piece.L1_STR)));
		assertTrue(firstRots[Piece.L2].equals(new Piece(Piece.L2_STR)));
		assertTrue(firstRots[Piece.SQUARE].equals(new Piece(Piece.SQUARE_STR)));
		assertTrue(firstRots[Piece.STICK].fastRotation().equals(new Piece(Piece.STICK_STR).fastRotation()));
		assertTrue(firstRots[Piece.PYRAMID].fastRotation().equals(new Piece(Piece.PYRAMID_STR).fastRotation()));
	}
}
