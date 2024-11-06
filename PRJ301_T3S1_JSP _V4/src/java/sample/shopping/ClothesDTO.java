package sample.shopping;

public class ClothesDTO {
    private String id;
    private String name;
    private int quantity;
    private double price;

    public ClothesDTO() {
    }

    public ClothesDTO(String id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    public ClothesDTO(ClothesDTO other) {
        this.id = other.id;
        this.name = other.name;
        this.quantity = other.quantity;
        this.price = other.price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    } 
}
