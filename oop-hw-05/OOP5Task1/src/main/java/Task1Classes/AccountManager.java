package Task1Classes;

import java.util.HashMap;

public class AccountManager {
    private HashMap<String, String> accountData;

    //Constructor -> Initializes the hashMap and Adds the Test Accounts
    public AccountManager(){
        accountData = new HashMap<>();

        this.addAccount("Patrick", "1234");
        this.addAccount("Molly", "FloPup");
    }

    //Checks if the account corresponding to the inputted name exists
    public Boolean existsAccount(String accountName) {
        return accountData.containsKey(accountName);
    }

    //Checks if the password inputted corresponds to the
    //real password of the inputted accountName
    public Boolean checkPassword(String accountName, String password) {
        return accountData.get(accountName).equals(password);
    }

    //Adds account
    public void addAccount(String accountName, String password) {
        accountData.put(accountName, password);
    }
}
