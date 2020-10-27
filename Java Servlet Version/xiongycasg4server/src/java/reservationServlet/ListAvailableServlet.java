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
@WebServlet(name = "ListAvailableServlet", urlPatterns = {"/ListAvailable"})
public class ListAvailableServlet extends HttpServlet {

    reservationdblib rb;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        rb =  new reservationdblib();
        
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            response.setContentType("text/html;charset=UTF-8");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
            java.util.Date dateIn=null;
            java.util.Date dateOut=null;
            List<String> availableInfo = new ArrayList<String>();
            List<String> availableInfoRoomNo = new ArrayList<String>();
            List<String> availableInfoTotalPrice = new ArrayList<String>();
           
            try (PrintWriter out = response.getWriter()) {
                
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Assignment 4</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div align=\"center\">");
              
                try{
                 
                 dateIn = sdf.parse(request.getParameter("dateIn"));
                 dateOut = sdf.parse(request.getParameter("dateOut"));
                
                 availableInfo = rb.listavailable(dateIn,dateOut);
                 if(availableInfo.isEmpty()){
                  out.println("<p style=\"font-size:24px;color:red;\">No booking information is available for the dates you entered.</p>");
                  out.println("<p><a href=\"ListCustomers\">Back</p>");
                 }
                 else{
                      
                     if(availableInfo.size()==1&&!availableInfo.get(0).contains(",")){
                         out.println("<p style=\"font-size:24px;color:red;\">No booking information is available for the dates you entered.</p>");
                         out.println("<p style=\"font-size:24px;color:red;\">"+availableInfo.get(0)+"</p>");
                         out.println("<p><a href=\"ListCustomers\">Back</p>");
                     }
                     else{
                
                 out.println("<table border=\"0\" cellspacing=\"10\"><tr><td>RoomNo</td><td>TotalPrice</td>");
                 out.println("<tr><td>-----------</td><td>---------------------</td>");
                 for(int i = 0; i<availableInfo.size();i++){
                     String[] splitInfo = availableInfo.get(i).split(",");
                     for(int j=0;j<splitInfo.length;j++){   
                       if(j%2==0){
                          availableInfoRoomNo.add(splitInfo[j].trim());
                       }
                       else{
                          availableInfoTotalPrice.add(splitInfo[j].trim());
                       }
                     }
                  }
                  
                 if(dateIn!=null&&dateOut!=null){
                    for(int i = 0 ; i < rb.listavailable(dateIn, dateOut).size();i++)
                    {
                        out.println("<tr><td>"+availableInfoRoomNo.get(i)+"</td><td>"+availableInfoTotalPrice.get(i)+"</td></tr>");
                        
                    }
                }
                out.println("</table>");
                out.println("<form action=\"Book\" method=\"GET\" id=\"form1\" name=\"form1\">");
                out.println("<p><strong>Customer No : </strong>");
                out.println("<select name = \"Cid\">");
                out.println("<option> 1");
                out.println("<option> 2");
                out.println("<option> 3");
                out.println("<option> 4");
                out.println("<option> 5");
                out.println("</select></p>");
                out.println("<p><strong>Room No : </strong>:");
                out.println("<select Name = \"RoomNo\">");
                for(int i = 0 ; i < availableInfoRoomNo.size();i++){
                  out.println("<option> "+availableInfoRoomNo.get(i));
                }
                out.println("</select></p>");
                out.println("<p style=\"color:grey;\"><strong>Date in (mm/dd/yy): </strong>&nbsp&nbsp&nbsp&nbsp<input style=\"color:grey; id=\"dateIn\" type=\"text\" readonly name=\"dateIn\" value=\""
                        +request.getParameter("dateIn")+"\"></p>");
                out.println("<p style=\"color:grey;\"><strong>Date out (mm/dd/yy): </strong>&nbsp&nbsp<input  style=\"color:grey; id=\"dateOut\" type=\"text\" readonly name=\"dateOut\" value=\""
                        +request.getParameter("dateOut")+"\"></p>");
                out.println("<p><input id=\"button2\" name=\"btnAdd\" type=\"submit\" value=\"Book\"></p>");
                     }
                 }
              }
              catch(ParseException e ){
                 out.println("<p style=\"font-size:24px;color:red;\">Please enter a valid date format !</p>");
                 out.println("<p><a href=\"ListCustomers\">Back</a><p>");
              }
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
