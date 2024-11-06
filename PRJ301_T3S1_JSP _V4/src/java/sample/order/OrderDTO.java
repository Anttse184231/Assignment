package sample.order;

import java.sql.Date;
import java.util.List;

public class OrderDTO {
    private String userID;
    private String orderID;
    private Date date;
    private double total;
    private int status;
    private List<OrderDetailsDTO> details;
    public OrderDTO(String userID, String orderID, Date date, double total, int status) {
        this.userID = userID;
        this.orderID = orderID;
        this.date = date;
        this.total = total;
        this.status = status;
    }
    
    public List<OrderDetailsDTO> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetailsDTO> details) {
        this.details = details;
    }
    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
