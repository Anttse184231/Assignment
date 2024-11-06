package sample.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.order.OrderDTO;
import sample.order.OrderDetailsDAO;
import sample.shopping.Cart;

public class EditController extends HttpServlet {
    private static final String ERROR = "viewCart.jsp";
    private static final String SUCCESS = "viewCart.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try{
            HttpSession session = request.getSession();
            String id = request.getParameter("Id");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Cart cart = (Cart)session.getAttribute("CART");
            if(cart != null){
                boolean check = cart.editQuantity(id,quantity);
                OrderDTO userOrder = (OrderDTO) session.getAttribute("USER_CURRENT_ORDER");
                OrderDetailsDAO orderDetailsDao =  new OrderDetailsDAO();
                boolean checkEdit = orderDetailsDao.setQuantity(userOrder.getOrderID(), id, quantity);
                if(!checkEdit){
                    log("Unknown error add setQuantity OrderDetailsDAO");
                }
                if (check){
                    url = SUCCESS;
                }
            }
        }catch(Exception e){
            log("Error at EditController" + e.toString());
        }finally{
            request.getRequestDispatcher(url).forward(request, response);;
        }
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
