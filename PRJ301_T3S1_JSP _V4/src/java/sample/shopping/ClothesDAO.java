package sample.shopping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import sample.utils.DBUtils;

public class ClothesDAO {  
    private static final String SELECTALL = "SELECT productID,name,price,quantity FROM products";
    private static final String UPDATE = "UPDATE products SET quantity=? WHERE productID=?";
    private static final String SET_QUANTIY_PRICE = "UPDATE Products SET price = ?, quantity = ? WHERE productID = ?";
    private static final String GETPRODUCT = "SELECT productID,name,price,quantity FROM products WHERE productID=?";   
    public boolean updateProduct(String productID, float newPrice, int newQuantity) throws SQLException, NamingException, ClassNotFoundException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SET_QUANTIY_PRICE);
                ptm.setFloat(1, newPrice);
                ptm.setInt(2, newQuantity);
                ptm.setString(3, productID);
                check = ptm.executeUpdate() > 0;
            }
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check;
    }
    
    public List<ClothesDTO> getClothesList() throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        List<ClothesDTO> clothesList = new ArrayList<>();
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SELECTALL);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("productID");
                    String name = rs.getString("name");
                    String priceStr = String.format("%.2f", rs.getDouble("price"));
                    double price = Double.parseDouble(priceStr);
                    int quantity = rs.getInt("quantity");
                    clothesList.add(new ClothesDTO(id, name, quantity, price));
                }
            }
        } catch (Exception e) {
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
        return clothesList;
    }
    
    public ClothesDTO getClothes(String productID) throws SQLException {
        ClothesDTO result = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if(conn != null) {
                ptm = conn.prepareStatement(GETPRODUCT);
                ptm.setString(1, productID);
                rs = ptm.executeQuery();            
                if (rs.next()) {
                    String id = rs.getString("productID");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    result = new ClothesDTO(id, name, quantity, price);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
             }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        return result;
    }

    public boolean reduceClothesQuantity(Map<String, ClothesDTO> productsToUpdate) throws SQLException, ClassNotFoundException, NamingException {
        boolean allItemsAvailable = true; 
        boolean check= false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;    
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SELECTALL);
                rs = ptm.executeQuery();
                ptm = conn.prepareStatement(UPDATE);
                while (rs.next()) {
                    String id = rs.getString("productID");
                    for (Map.Entry<String, ClothesDTO> entry : productsToUpdate.entrySet()) {
                        String productID = entry.getKey();
                        if(id.equals(productID)){
                            int quantity = entry.getValue().getQuantity();
                            if (rs.getInt("quantity") < quantity) {
                                allItemsAvailable = false;
                                break;
                            }
                            ptm.setInt(1, rs.getInt("quantity") - quantity);
                            ptm.setString(2, productID);
                            ptm.addBatch();
                        }
                    }
                    if(allItemsAvailable == false) break;
                }
                if(allItemsAvailable){
                    ptm.executeBatch();
                    check = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check; 
    }
}
