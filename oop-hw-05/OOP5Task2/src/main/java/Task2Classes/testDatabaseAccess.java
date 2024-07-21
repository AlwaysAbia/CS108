package Task2Classes;

import junit.framework.TestCase;

public class testDatabaseAccess extends TestCase{
    DatabaseAccess dbAccess;

    protected void setUp() throws Exception {
        dbAccess = new DatabaseAccess();
    }

    public void testProduct1(){
        Product pr1 = dbAccess.getProduct("HC");
        assertEquals("HC", pr1.getProductID());
        assertEquals("Classic Hoodie", pr1.getName());
        assertEquals("Hoodie.jpg", pr1.getImagefile());
        assertEquals(40.00, pr1.getPrice());
    }

    public void testProduct2(){
        Product pr2 = dbAccess.getProduct("HLE");
        assertEquals("HLE", pr2.getProductID());
        assertEquals("Limited Edition Hood", pr2.getName());
        assertEquals("LimitedEdHood.jpg", pr2.getImagefile());
        assertEquals(54.95, pr2.getPrice());
    }

    public void testProduct3(){
        Product pr3 = dbAccess.getProduct("TLHi");
        assertEquals("TLHi", pr3.getProductID());
        assertEquals("Hindi Tee", pr3.getName());
        assertEquals("HindiTShirt.jpg", pr3.getImagefile());
        assertEquals(17.95, pr3.getPrice());
    }
}
