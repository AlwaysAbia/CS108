package Task2Classes;

import java.util.HashMap;

public class ShoppingCart {
    private HashMap<String, Integer> cart;
    private HashMap<String, Double> nameToPrice;
    private double totalCost;

    private DatabaseAccess dbAccess;

    public ShoppingCart() {
        this.totalCost = 0;
        this.dbAccess = new DatabaseAccess();
        this.cart = new HashMap<>();
        this.nameToPrice = new HashMap<>();
    }

    //Puts the item in the cart, if its already there, updates the count
    public void addItem(String itemID) {
        Product product = dbAccess.getProduct(itemID);
        String item = product.getName();

        if(this.cart.containsKey(item)){
            this.cart.put(item,this.cart.get(item)+1);
        }else {
            this.cart.put(item, 1);
        }
        totalCost += product.getPrice();

        if(!this.nameToPrice.containsKey(item)){
            this.nameToPrice.put(item, product.getPrice());
        }
    }

    //Updates the count, if the count is zero, removes the items
    public void updateCount(String itemID, int quantity) {
        Product product = dbAccess.getProduct(itemID);
        String item = product.getName();

        int currQuantity = this.getCount(itemID);
        totalCost -= product.getPrice()*currQuantity;
        totalCost += product.getPrice()*quantity;

        if(quantity == 0){
            this.cart.remove(item);
        }else {
            this.cart.put(item, quantity);
        }
    }
    public void updateCountWithName(String name, int quantity){
        double price = this.nameToPrice.get(name);

        int currQuantity = this.cart.get(name);
        totalCost -= price*currQuantity;
        totalCost += price*quantity;

        if(quantity == 0){
            this.cart.remove(name);
        }else {
            this.cart.put(name, quantity);
        }
    }
    //Gets the HashMap associated with the shoppingCart
    public HashMap<String, Integer> getShoppingCart() {
        return this.cart;
    }

    public HashMap<String, Double> getNameToPrice(){
        return this.nameToPrice;
    }

    public double getTotalCost() {
        return this.totalCost;
    }


    public int getCount(String itemID){
        Product product = dbAccess.getProduct(itemID);
        String item = product.getName();

        return this.cart.get(item);
    }

    public Boolean containsItem(String itemID){
        Product product = dbAccess.getProduct(itemID);
        String item = product.getName();

        return this.cart.containsKey(item);
    }
}
