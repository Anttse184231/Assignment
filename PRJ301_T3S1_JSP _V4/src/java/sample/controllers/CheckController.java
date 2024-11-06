package sample.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.order.OrderDAO;
import sample.order.OrderDTO;
import sample.shopping.Cart;
import sample.shopping.ClothesDAO;

public class CheckController extends HttpServlet {
    private static final String ERROR = "viewCart.jsp";
    private static final String SUCCESS = "viewCart.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        boolean check = false;
        try {
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("CART");
            ClothesDAO dao = new ClothesDAO();
            if(cart != null){
                check = dao.reduceClothesQuantity(cart.getCart());
                OrderDTO userOrder = getUserOrder(session);
                OrderDAO orderDao = new OrderDAO();
                check = check == true? orderDao.checkOut(userOrder.getOrderID()): false;
            }
            if (check) {
                session.setAttribute("CART", null);
                session.setAttribute("USER_ORDER", null);
                session.setAttribute("USER_CURRENT_ORDER", null);
                request.setAttribute("MESSAGE", "Check out thanh cong!");
                url = SUCCESS;
            } else if (cart != null) {
                request.setAttribute("MESSAGE", "Ko du hang` ban oi!");
            } else if (cart == null) {
                request.setAttribute("MESSAGE", "Khong co do` trong gio hang sao tinh tien`!");
            }
        } catch (Exception e) {
            log("Error at CheckController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    
    
    private OrderDTO getUserOrder(HttpSession session){
        List<OrderDTO> userOrders = (List<OrderDTO> ) session.getAttribute("USER_ORDER");
        for(OrderDTO order : userOrders){
                if(order.getStatus() ==1){
                    return order;
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
