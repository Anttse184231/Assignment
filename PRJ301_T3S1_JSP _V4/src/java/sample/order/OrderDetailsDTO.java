package sample.order;

public class OrderDetailsDTO {
    String orderID;
    String productID;
    String productName;
    OrderDetailsDTO(String orderID, String productID, double price, int quantity, int status, String productName) {
        this.orderID = orderID;
        this.productID = productID;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    double price;
    int quantity;
    int status;

    public OrderDetailsDTO(String orderID, String productID, double price, int quantity, int status) {
        this.orderID = orderID;
        this.productID = productID;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
