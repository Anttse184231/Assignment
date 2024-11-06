package sample.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sample.utils.DBUtils;
import java.sql.Date;
import javax.naming.NamingException;

public class OrderDAO {
    private static final String SEARCH = "SELECT userID, orderID, date, total, status FROM [order ]WHERE userID=?";
    private static final String INSERT = "INSERT INTO [order] (userID, orderID, date, total, status) VALUES (?, ?, ?, ?, ?)";
    private static final String COUNT = "SELECT COUNT(*) FROM [order] WHERE userID = ?";
    private static final String UPDATE_STATUS = "UPDATE [order] SET status = ? WHERE orderID = ?";
    private static final String UPDATE_DETAIL_STATUS = "UPDATE [orderdetails] SET status = ? WHERE orderID = ?";
    private static final String CALCULATE_TOTAL = "SELECT SUM(price * quantity) AS total FROM [orderdetails] WHERE orderID = ?";
    private static final String UPDATE_TOTAL = "UPDATE [order] SET total = ? WHERE orderID = ?";
    public List<OrderDTO> getOrderList(String userID_) throws SQLException, ClassNotFoundException, NamingException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        List<OrderDTO> orderList = new ArrayList<>();
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH);
                ptm.setString(1, userID_);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String userID = rs.getString("userID");
                    String orderID = rs.getString("orderID");
                    Date date =  rs.getDate("date");
                    double total = rs.getDouble("total");
                    int status = rs.getInt("status");
                    orderList.add(new OrderDTO(userID, orderID, date, total,status));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return orderList;
    }
    
    public int getCurrentOrderID(String userID) throws SQLException, ClassNotFoundException, NamingException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        int orderID = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(COUNT);
                ptm.setString(1, userID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                  orderID =   rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return orderID;
    }
    
    public boolean insert(OrderDTO order) throws SQLException, Exception{
        Connection conn= null;
        PreparedStatement ptm= null;
        ResultSet rs= null;
        boolean check = false;
        try {
            conn= DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT);
                ptm.setString(1, order.getUserID());
                ptm.setString(2, order.getOrderID());
                ptm.setDate(3, order.getDate());
                ptm.setDouble(4, order.getTotal());
                ptm.setInt(5, order.getStatus());
                check = ptm.executeUpdate() >0? true:false;
            }
        } 
        finally{
            if(rs!= null) rs.close();
            if(ptm!= null) ptm.close();
            if(conn!= null) conn.close();
        }
        return check;
    }
    
    public boolean checkOut(String orderID) throws SQLException, ClassNotFoundException, NamingException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        boolean check1 = false;
        boolean check2 = false;
        boolean check3 = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CALCULATE_TOTAL);
                ptm.setString(1, orderID);
                rs = ptm.executeQuery();
                double total = 0;
                if (rs.next()) {
                    total = rs.getDouble("total");
                }
                ptm.close();
                ptm = conn.prepareStatement(UPDATE_TOTAL);
                ptm.setDouble(1, total);
                ptm.setString(2, orderID);
                check1 = ptm.executeUpdate() > 0;
                ptm.close();
                ptm = conn.prepareStatement(UPDATE_STATUS);
                ptm.setInt(1, 0);
                ptm.setString(2, orderID);
                check2 = ptm.executeUpdate() > 0;
                ptm.close();
                ptm = conn.prepareStatement(UPDATE_DETAIL_STATUS);
                ptm.setInt(1, 0);
                ptm.setString(2, orderID);
                check3 = ptm.executeUpdate() > 0;
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check1 && check2 && check3;
    }
}
