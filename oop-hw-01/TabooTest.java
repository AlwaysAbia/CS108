// TabooTest.java
// Taboo class tests -- nothing provided.

import java.util.*;

import com.sun.source.tree.AssertTree;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class TabooTest extends TestCase {
    public void testNoFollow1(){
        List<Character> rules = new ArrayList<Character>();
        rules.add('a');
        rules.add('c');
        rules.add('a');
        rules.add('b');

        Set<Character> ans1 = new HashSet<>();
        ans1.add('c');
        ans1.add('b');

        Set<Character> asn2 = new HashSet<>();
        asn2.add('a');

        Set<Character> ans3 = new HashSet<>();

        Taboo<Character> t = new Taboo<>(rules);

        assertTrue(ans1.equals(t.noFollow('a')));
        assertTrue(asn2.equals(t.noFollow('c')));
        assertTrue(ans3.equals(t.noFollow('b')));
    }

    public void testNoFollow2(){
        List<Integer> rules = new ArrayList<Integer>();
        rules.add(1);
        rules.add(2);
        rules.add(3);
        rules.add(1);
        rules.add(2);
        rules.add(4);

        Taboo<Integer> t = new Taboo<>(rules);

        Set<Integer> ans1 = new HashSet<>();
        ans1.add(3);
        ans1.add(4);

        Set<Integer> ans2 = new HashSet<>();
        ans2.add(2);


        assertTrue(ans1.equals(t.noFollow(2)));
        assertTrue(ans2.equals(t.noFollow(1)));
    }
     public void testReduce1(){
         List<Character> rules = new ArrayList<Character>();
         rules.add('a');
         rules.add('c');
         rules.add('a');
         rules.add('b');

         Taboo<Character> t = new Taboo<>(rules);

         List<Character> inList1 = new ArrayList<>();
         inList1.add('a');
         inList1.add('c');
         inList1.add('b');
         inList1.add('x');
         inList1.add('c');
         inList1.add('a');

         List<Character> ansList1 = new ArrayList<>();
         ansList1.add('a');
         ansList1.add('x');
         ansList1.add('c');

         t.reduce(inList1);

         assertTrue(ansList1.equals(inList1));
     }
    public void testReduce2() {
        List<Character> rules = new ArrayList<>();
        rules.add('x');
        rules.add('b');

        Taboo<Character> t = new Taboo<>(rules);

        List<Character> inList2 = new ArrayList<>();
        inList2.add('a');
        inList2.add('x');
        inList2.add('b');
        inList2.add('b');
        inList2.add('c');

        List<Character> ansList2 = new ArrayList<>();
        ansList2.add('a');
        ansList2.add('x');
        ansList2.add('c');

        t.reduce(inList2);

        assertTrue(ansList2.equals(inList2));
    }

}
