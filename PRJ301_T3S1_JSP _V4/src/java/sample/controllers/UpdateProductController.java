package sample.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.order.OrderDetailsDAO;
import sample.shopping.ClothesDAO;
import sample.shopping.ClothesDTO;

public class UpdateProductController extends HttpServlet {
    private static final String SUCCESS = "admin.jsp";
    private static final String ERROR = "admin.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String productID = request.getParameter("productID");
            float newPrice = Float.parseFloat(request.getParameter("price"));
            int newQuantity = Integer.parseInt(request.getParameter("quantity"));
            OrderDetailsDAO orderDetailsDao = new OrderDetailsDAO();
            ClothesDAO clothesDao = new ClothesDAO();
            boolean orderDetailsUpdated = orderDetailsDao.updateOrderDetailsPrice(productID, newPrice);
            boolean productUpdated = clothesDao.updateProduct(productID, newPrice, newQuantity);
            HttpSession session = request.getSession();
            List<ClothesDTO> listClothes = clothesDao.getClothesList();
            session.setAttribute("LIST_CLOTHES", listClothes);
            if (orderDetailsUpdated && productUpdated) {
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR", "Failed to update the product.");
            }
        } catch (Exception e) {
            log("Error at UpdateProductController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
