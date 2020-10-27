/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package reservationServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import reservationdblib.reservationdblib;

/**
 *
 * @author Yuchen Xiong
 */
@WebServlet(name = "BookServlet", urlPatterns = {"/Book"})
public class BookServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    reservationdblib rb; 
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        rb =  new reservationdblib();
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        response.setContentType("text/html;charset=UTF-8");
        String cid = request.getParameter("Cid");
        String roomNo = request.getParameter("RoomNo");
        String dateInString = request.getParameter("dateIn");
        String dateOutString = request.getParameter("dateOut");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        try{
        java.util.Date dateIn = sdf.parse(dateInString);  
        java.util.Date dateOut = sdf.parse(dateOutString);
        double result = rb.book(Integer.parseInt(cid),Integer.parseInt(roomNo), dateIn,dateOut);
         
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Assignment 4</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<div align=\"center\">");
            if(result!=0){
                      out.println("<p style=\"font-size:24px;color:green\">Room "+roomNo+" has been booked for a total cost of  "+result+"</p>");
                      out.println("<p><a href=\"ListCustomers\">Back</p>");
             }
            else{
              out.println("<p style=\"color:red;\">Reservation failed. Room "+roomNo+" is not available.</p>");
            }
             out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
        
    }
        catch(ParseException ex){
          Logger.getLogger(BookServlet.class.getName()).log(Level.SEVERE, null, ex);
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
