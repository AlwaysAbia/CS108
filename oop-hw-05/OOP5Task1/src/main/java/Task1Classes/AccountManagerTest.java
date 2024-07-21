package Task1Classes;

import junit.framework.TestCase;

public class AccountManagerTest extends TestCase{
    AccountManager am;

    protected void setUp() throws Exception {
        am = new AccountManager();
    }

    public void testBaseAccounts(){
        assertTrue(am.existsAccount("Molly"));
        assertTrue(am.existsAccount("Patrick"));
        assertTrue(am.checkPassword("Molly", "FloPup"));
        assertTrue(am.checkPassword("Patrick", "1234"));
        assertFalse(am.checkPassword("Molly", "2222"));
        assertFalse(am.checkPassword("Patrick", "FloPupppp"));
    }

    public void testCreateAccount(){
        assertFalse(am.existsAccount("Gio"));
        assertFalse(am.existsAccount("DATO"));

        am.addAccount("Gio", "Givi");
        am.addAccount("DATO", ":(");

        assertTrue(am.existsAccount("Gio"));
        assertTrue(am.existsAccount("DATO"));

        assertTrue(am.checkPassword("Gio", "Givi"));
        assertTrue(am.checkPassword("DATO", ":("));
    }

}
