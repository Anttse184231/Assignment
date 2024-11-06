package sample.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.order.OrderDTO;
import sample.shopping.ClothesDTO;
import sample.shopping.ClothesDAO;

public class ShopController extends HttpServlet {
    private static final String ERROR = "shopping.jsp";
    private static final String SUCCESS = "shopping.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            ClothesDAO dao = new ClothesDAO();
            List<ClothesDTO> clothesList = dao.getClothesList();
            if (clothesList.size() > 0) {
                HttpSession session = request.getSession();
                session.setAttribute("LIST_CLOTHES", clothesList);            
                OrderDTO userOrder = (OrderDTO) getUserOrder(session);
                session.setAttribute("USER_CURRENT_ORDER", userOrder);
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at ShopController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    
    private OrderDTO getUserOrder(HttpSession session){
        List<OrderDTO> userOrders = (List<OrderDTO> ) session.getAttribute("USER_ORDER");
        if(userOrders !=null){
            for(OrderDTO order : userOrders){
                    if(order.getStatus() ==1){
                        return order;
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
