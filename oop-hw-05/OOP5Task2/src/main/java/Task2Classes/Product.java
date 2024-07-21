package Task2Classes;

public class Product {
    private final String productID;
    private final String name;
    private final String imagefile;
    private final double price;

    public Product(String id, String name, String image, double price){
        this.productID = id;
        this.name = name;
        this.imagefile = image;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }
    public String getName() {
        return name;
    }
    public String getImagefile() {
        return imagefile;
    }
    public double getPrice() {
        return price;
    }
}
