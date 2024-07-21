package Task2Classes;

import junit.framework.TestCase;

import java.util.HashMap;

public class testShoppingCart extends TestCase {
    ShoppingCart sc;

    protected void setUp() throws Exception {
        sc = new ShoppingCart();
    }

    public void testAddItem() {
        sc.addItem("HC");
        assertEquals(1, sc.getCount("HC"));
        sc.addItem("HC");
        assertEquals(2, sc.getCount("HC"));
    }

    public void testUpdateCount(){
        sc.addItem("HLE");
        sc.addItem("HLE");
        sc.updateCount("HLE", 3);
        assertEquals(3, sc.getCount("HLE"));
        sc.updateCount("HLE", 0);
        assertFalse(sc.containsItem("HLE"));
    }

    public void testUpdateCountWithName(){
        sc.addItem("TS");
        sc.addItem("TS");
        sc.updateCountWithName("Seal Tee", 3);

        int tst = sc.getShoppingCart().get("Seal Tee");
        assertEquals(3, tst);
        sc.updateCountWithName("Seal Tee", 0);
        assertFalse(sc.getShoppingCart().containsKey("Seal Tee"));
    }

    public void testGetCart(){
        sc.addItem("ALn");
        HashMap<String, Integer> cart = sc.getShoppingCart();
        assertTrue(cart.containsKey("Lanyard"));
    }

    public void testTotalCost(){
        ShoppingCart sc2 = new ShoppingCart();
        sc2.addItem("HLE");
        sc2.addItem("HLE");
        assertEquals(54.95*2, sc2.getTotalCost());
        sc2.updateCount("HLE", 5);
        sc2.addItem("TC");
        assertEquals(54.95*5 + 13.95, sc2.getTotalCost());
    }

    public void testPriceMapping(){
        ShoppingCart sc3 = new ShoppingCart();
        sc3.addItem("HLE");
        HashMap<String, Double> priceMapping = sc3.getNameToPrice();
        assertEquals(54.95, priceMapping.get("Limited Edition Hood"));
    }

}
