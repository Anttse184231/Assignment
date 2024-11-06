package sample.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import sample.utils.DBUtils;

public class OrderDetailsDAO {
    private static final String INSERT = "INSERT INTO orderDetails (orderID, productID, price, quantity, status) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_PRICE = "UPDATE OrderDetails SET price = ? WHERE productID = ? AND status = 1";
    private static final String UPDATE_QUANTITY = "UPDATE orderDetails SET quantity = quantity + 1 WHERE orderID = ? AND productID = ?";
    private static final String CHECK_DUPLICATE = "SELECT COUNT(*) FROM orderDetails WHERE orderID = ? AND productID = ?";
    private static final String UPDATE_QUANTITY_BY_IDS = "UPDATE orderDetails SET quantity = ? WHERE orderID = ? AND productID = ?";
    private static final String DELETE_BY_IDS = "DELETE FROM orderDetails WHERE orderID = ? AND productID = ?";
    private static final String SEARCH_WITH_PRODUCT_NAME = "SELECT od.orderID, od.productID, od.price, od.quantity, od.status, p.name " +
                                                           "FROM orderDetails od " +
                                                           "JOIN products p ON od.productID = p.productID " +
                                                           "WHERE od.orderID=?";
    public List<OrderDetailsDTO> getOrderDetailsList(String orderID) throws SQLException, ClassNotFoundException, NamingException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_WITH_PRODUCT_NAME);
                ptm.setString(1, orderID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    int status = rs.getInt("status");
                    String productName = rs.getString("name");
                    orderDetailsList.add(new OrderDetailsDTO(orderID, productID, price, quantity, status, productName));
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return orderDetailsList;
    }
    
    public boolean insert(OrderDetailsDTO product) throws SQLException, Exception {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                // Check if the combination of orderID and productID already exists
                ptm = conn.prepareStatement(CHECK_DUPLICATE);
                ptm.setString(1, product.getOrderID());
                ptm.setString(2, product.getProductID());
                rs = ptm.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        // If duplicate found, update quantity
                        ptm = conn.prepareStatement(UPDATE_QUANTITY);
                        ptm.setString(1, product.getOrderID());
                        ptm.setString(2, product.getProductID());
                        check = ptm.executeUpdate() > 0;
                    } else {
                        // If no duplicate found, insert new row
                        ptm = conn.prepareStatement(INSERT);
                        ptm.setString(1, product.getOrderID());
                        ptm.setString(2, product.getProductID());
                        ptm.setDouble(3, product.getPrice());
                        ptm.setInt(4, product.getQuantity());
                        ptm.setInt(5, product.getStatus());
                        check = ptm.executeUpdate() > 0;
                    }
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check;
    }

    public boolean setQuantity(String orderID, String productID, int quantity) throws SQLException, ClassNotFoundException, NamingException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_QUANTITY_BY_IDS);
                ptm.setInt(1, quantity);
                ptm.setString(2, orderID);
                ptm.setString(3, productID);
                check = ptm.executeUpdate() > 0;
            }
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check;
    }
    
    public boolean delete(String orderID, String productID) throws SQLException, ClassNotFoundException, NamingException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE_BY_IDS);
                ptm.setString(1, orderID);
                ptm.setString(2, productID);
                check = ptm.executeUpdate() > 0;
            }
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check;
    }
    
    public boolean updateOrderDetailsPrice(String productID, float newPrice) throws SQLException, NamingException, ClassNotFoundException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_PRICE);
                ptm.setFloat(1, newPrice);
                ptm.setString(2, productID);
                check = ptm.executeUpdate() > 0;
            }
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check;
    }
}
