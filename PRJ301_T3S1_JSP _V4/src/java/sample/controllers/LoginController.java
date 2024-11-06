package sample.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.order.OrderDAO;
import sample.shopping.ClothesDAO;
import sample.shopping.ClothesDTO;
import sample.users.UserDAO;
import sample.users.UserDTO;

public class LoginController extends HttpServlet {
    private static final String ERROR = "login.jsp";
    private static final String USER_PAGE = "user.jsp";
    private static final String ADMIN_PAGE = "admin.jsp";
    private static final String US = "US";
    private static final String AD = "AD";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            String RecaptchaResponse = request.getParameter("g-recaptcha-response");
            UserDAO dao = new UserDAO();
            UserDTO loginUser = dao.checkLogin(userID, password);
            if (loginUser != null && !RecaptchaResponse.isEmpty()) {
                HttpSession session = request.getSession();
                session.setAttribute("LOGIN_USER", loginUser);
                String roleID = loginUser.getRoleID();
                if (AD.equals(roleID)) {
                    ClothesDAO clothesDao = new ClothesDAO();
                    List<ClothesDTO> listClothes = clothesDao.getClothesList();
                    session.setAttribute("LIST_CLOTHES", listClothes);
                    url = ADMIN_PAGE;
                } else if (US.equals(roleID)) {
                    OrderDAO orderDao = new OrderDAO();
                    session.setAttribute("USER_ORDER", orderDao.getOrderList(userID));
                    session.setAttribute("CART", null);
                    url = USER_PAGE;
                } else {
                    request.setAttribute("ERROR", "Role not supported!");
                }
            } else {
                if (!userID.isEmpty() && !password.isEmpty() && !RecaptchaResponse.isEmpty()) {
                    request.setAttribute("ERROR", "Incorrect User ID or Password!");
                }else if(RecaptchaResponse.isEmpty()){
                    request.setAttribute("ERROR", "Please verify catcha!");
                }
            }
        } catch (Exception e) {
            log("Error at LoginController: " + e.toString());
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
