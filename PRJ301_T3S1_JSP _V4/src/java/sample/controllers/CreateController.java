package sample.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sample.users.UserDAO;
import sample.users.UserDTO;
import sample.users.UserError;

public class CreateController extends HttpServlet {
    private static final String ERROR = "create.jsp";
    private static final String SUCCESS = "login.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        UserError userError = new UserError();
        try {
            boolean checkValidation = true;
            String userID = request.getParameter("userID");
            String fullname = request.getParameter("fullname");
            String roleID = request.getParameter("roleID");
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm");
            UserDAO dao = new UserDAO();
            if(userID.length()<2 || userID.length() >10){
                checkValidation = false;
                userError.setUserIDError("User ID must be in [2,10]");
            }
//            boolean checkDuplicate = dao.checkDuplicate(userID);
//            if(checkDuplicate){
//                checkValidation = false;
//                userError.setUserIDError("User ID already exist");
//            }
            if(fullname.length()<5 || userID.length() >50){
                checkValidation = false;
                userError.setFullNameError("Fullname must be in [5,50]");
            }
            if(!confirm.equals(password)){
                checkValidation = false;
                userError.setConfirmError("password khong giong nhau");
            }
            if(checkValidation){
                UserDTO user = new UserDTO(userID,fullname,roleID,password);
                boolean check = dao.insert(user);
                if(check){
                    url = SUCCESS;
                }
            }else{
                request.setAttribute("USER_ERROR", userError);
            }
        } catch (Exception e) {
            log("Error at DeleteController: " + e.toString());
            if(e.toString().contains("duplicate")){
                userError.setUserIDError("trung id roi kia");
                request.setAttribute("USER_ERROR", userError);
            }
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
