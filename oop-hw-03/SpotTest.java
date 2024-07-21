import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

public class SpotTest extends TestCase{

    private Sudoku sudoku;
    private Sudoku.Spot spot1;
    private Sudoku.Spot spot2;

    protected void setUp() throws Exception{
        sudoku = new Sudoku(Sudoku.easyGrid);

        System.out.println(sudoku);

        spot1 = sudoku.new Spot(6, 8);
        spot2 = sudoku.new Spot(7, 7);
    }

    public void testGet(){
        assertEquals(spot1.getValue(),0);

        spot1.setValue(5);

        assertEquals(spot1.getValue(),5);

        spot1.setValue(0);

        assertEquals(spot1.getValue(),0);
    }

    public void testAvailableSpots(){
        assertEquals(spot1.getAvailableSpots(), new HashSet<Integer>(Set.of(3, 5)));
        assertEquals(spot2.getAvailableSpots(), new HashSet<Integer>(Set.of(4, 8)));
    }

    public void testSudoku(){
        String easyAns =
                "1 6 4 7 9 5 3 8 2" +
        "\n2 8 7 4 6 3 9 1 5" +
        "\n9 3 5 2 8 1 4 6 7" +
        "\n3 9 1 8 7 6 5 2 4" +
        "\n5 4 6 1 3 2 7 9 8" +
        "\n7 2 8 9 5 4 1 3 6" +
        "\n8 1 9 6 4 7 2 5 3" +
        "\n6 7 3 5 2 9 8 4 1" +
        "\n4 5 2 3 1 8 6 7 9\n";

        sudoku.solve();
        assertTrue(sudoku.getSolutionText().trim().equals(easyAns.trim()));
    }
}
