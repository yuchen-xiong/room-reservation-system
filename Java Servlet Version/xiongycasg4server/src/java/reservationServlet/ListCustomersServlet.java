package reservationServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import reservationdblib.*;

/**
 *
 * @author Yuchen Xiong
 */
@WebServlet(name = "ListCustomersServlet", urlPatterns = {"/ListCustomers"})
public class ListCustomersServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
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
        List<String> customerInfo = new ArrayList<String>();
        List<String> customerID = new ArrayList<String>();
        List<String> customerName = new ArrayList<String>();
        List<String> customerNo = new ArrayList<String>();
         
        customerInfo = rb.listcustomers();
        for(int i = 0; i<customerInfo.size();i++){
            String[] splitInfo = customerInfo.get(i).split(",");
                     for(int j=0;j<splitInfo.length;j++){   
                         switch(j%3){
                             case 0:
                                 customerID.add(splitInfo[j].trim());
                                 break;
                             case 1:
                                 customerName.add(splitInfo[j].trim());
                                 break;
                             case 2:
                                 customerNo.add(splitInfo[j].trim());
                                 break;
                       }
                     }
                  }
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Assignment 4</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<div align=\"center\">");
            out.printf("<table border=\"0\" cellspacing=\"5\"><tr><td>CID</td><td>Name</td><td>CCNo</td></tr>");
            out.printf("<tr><td>-----</td><td>-------------</td><td>----</td></tr>");
            for(int i = 0 ; i<customerID.size();i++){
                out.println("<tr><td>"+customerID.get(i)+"</td><td>"+customerName.get(i)+"</td><td>"+
                        customerNo.get(i)+"</td></tr>");               
            }
            out.println("</table>");
            out.println("<form method=\"GET\" action=\"ListAvailable\">");
            out.println("<p><Strong>Please enter date in (mm/dd/yy) : </Strong>&nbsp&nbsp&nbsp&nbsp<input type=\"text\" name=\"dateIn\"></p>");
            out.println("<p><Strong>Please enter date out (mm/dd/yy) : </Strong>&nbsp&nbsp<input type=\"text\" name=\"dateOut\"></p>");
            out.println("<p><input type=\"submit\" name=\"submit\" value=\"Submit\"></p>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
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
