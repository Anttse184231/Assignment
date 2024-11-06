package sample.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.order.OrderDAO;
import sample.shopping.Cart;
import sample.shopping.ClothesDTO;
import sample.users.UserDTO;
import java.sql.Date;
import java.time.LocalDate;
import sample.order.OrderDTO;
import sample.order.OrderDetailsDAO;
import sample.order.OrderDetailsDTO;

@WebServlet(name = "AddController", urlPatterns = {"/AddController"})
public class AddController extends HttpServlet {
    private static final String ERROR = "shopping.jsp";
    private static final String SUCCESS = "shopping.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            ClothesDTO selectedClothes = getClothesByID(session,request.getParameter("productID") );
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            selectedClothes.setQuantity(quantity);
            OrderDAO orderDao = new OrderDAO();
            Cart cart = (Cart) session.getAttribute("CART");
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            OrderDTO currentOrder = (OrderDTO) session.getAttribute("USER_CURRENT_ORDER");
            boolean checkOrderDetailsInsert = false;
            OrderDetailsDAO orderDetailsDao = new OrderDetailsDAO();
            if (currentOrder == null) {
                cart = new Cart();
                String userID = user.getUserID();
                OrderDTO order = new OrderDTO(userID, userID+"_"+orderDao.getCurrentOrderID(userID), Date.valueOf(LocalDate.now()), 0,1);
                orderDao.insert(order );
                session.setAttribute("USER_ORDER", orderDao.getOrderList(userID));
                session.setAttribute("USER_CURRENT_ORDER", order);
                OrderDetailsDTO orderDetails = new OrderDetailsDTO(order.getOrderID(),selectedClothes.getId(),selectedClothes.getPrice(),selectedClothes.getQuantity(),1);
                checkOrderDetailsInsert = orderDetailsDao.insert(orderDetails);
            }else{
                OrderDetailsDTO orderDetails = new OrderDetailsDTO(currentOrder.getOrderID(),selectedClothes.getId(),selectedClothes.getPrice(),selectedClothes.getQuantity(),1);
                checkOrderDetailsInsert = orderDetailsDao.insert(orderDetails);
            }
            boolean checkAdd = cart.add(selectedClothes);
            if (checkAdd && checkOrderDetailsInsert) {
                session.setAttribute("CART", cart);
                request.setAttribute("MESSAGE", "You have successfully add " + selectedClothes.getName() + " with quantity " + quantity);
                url = SUCCESS;
            } else {
                request.setAttribute("MESSAGE", "Add false " + selectedClothes.getName() + " with quantity " + quantity);
            }
        } catch (Exception e) {
            log("Error at AddController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    
    private ClothesDTO getClothesByID(HttpSession session,String ID){
        List<ClothesDTO>  listClothes= (List<ClothesDTO>) session.getAttribute("LIST_CLOTHES");
             ClothesDTO selectedClothes = null;
             if(listClothes.size() >0){
                 String id = ID;
                 for(ClothesDTO clothes :listClothes ){
                     if(clothes.getId().equals(id)){
                         selectedClothes = new ClothesDTO(clothes);
                         return selectedClothes;
                     }
                 }
             }
        return null;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
