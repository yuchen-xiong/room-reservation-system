package reservationdblib;


import java.math.BigDecimal;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yuchen Xiong
 */
public class reservationdblib {
    
    Connection msServer;
    

    public reservationdblib(){
    
        try {
            setDBConnection();
        } 
        catch (Exception e) {
            e.printStackTrace();
            return; 
        }
     }
    
     public void setDBConnection(){
         try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("ClassForname works");
            String connectionUrl = "jdbc:sqlserver://localhost;databaseName=reservationsSQL;user=ism6236;password=ism6236bo;";
            msServer = DriverManager.getConnection(connectionUrl);
         } 
         catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error code:" + ex.getErrorCode());
            //ex.printStackTrace();
         } 
         catch (Exception e) {
            e.printStackTrace();
         }   
      }
     
     public List<String> listcustomers(){
         
       List<String> customersInfo = new ArrayList<String>();
        try {
             PreparedStatement ps = msServer.prepareStatement( "SELECT CustomerID, Name, CCNo FROM Customer;");
             ResultSet rs = ps.executeQuery();
             String line;
             
             while (rs.next()) {
                int customerID = rs.getInt(1);
                String name = rs.getString(2);
                String ccNo = rs.getString(3);
                customersInfo.add(String.format("%-5d,%-20s,%-5s,", customerID, name, ccNo));
            }
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customersInfo;
     }
     
     public List<String> listavailable(java.util.Date dateIn, java.util.Date dateOut){
       
        List<String> availableInfo = new ArrayList<String>();
        try {
             java.sql.Date sqlDateIn = new java.sql.Date(dateIn.getTime());
             java.sql.Date sqlDateOut = new java.sql.Date(dateOut.getTime());
            
             java.util.Date maxDate = null;
             java.util.Date minDate = null; 
             
             PreparedStatement psMaxDate = msServer.prepareStatement("SELECT Min(Date) AS minDate, Max(Date) AS maxDate FROM Pricing");
             ResultSet rsDate = psMaxDate.executeQuery();
             
             while(rsDate.next()){
               minDate = rsDate.getDate(1);
               maxDate = rsDate.getDate(2);
             }
             
             DateFormat sdf = new SimpleDateFormat("MM/dd/yy");
             
             if (dateIn.before(minDate)|| dateOut.after(maxDate)){
                 String error = "The date range you can book is from "
                         + ""+sdf.format(minDate).toString()+" to " +sdf.format(maxDate).toString()+"!";
                 availableInfo.add(error);
                 return availableInfo;
             }
             
             if(dateIn.after(dateOut)){
                 String error = "The move-in date should be no later than move-out date !";
                 availableInfo.add(error);
                 return availableInfo;
             }
             
            PreparedStatement ps = msServer.prepareStatement( 
                    "Select Room.RoomNo, SUM(Pricing.Price) From  Room, Pricing  Where Room.RoomType = Pricing.RoomType  AND Pricing.Date >= ? AND Pricing.Date < ? And Room.RoomNo Not In (Select Reservation.RoomNo From Reservation Where (Reservation.DateIn< ? And Reservation.DateIn>=?) Or (Reservation.DateOut <=? And Reservation.DateOut>?)) GROUP BY Room.RoomNo ORDER BY Room.RoomNo"
                     );
             
             ps.setDate(1, sqlDateIn);
             ps.setDate(2, sqlDateOut);
             ps.setDate(3, sqlDateOut);
             ps.setDate(4, sqlDateIn);
             ps.setDate(5, sqlDateOut);
             ps.setDate(6, sqlDateIn);
             
             ResultSet rs = ps.executeQuery();
             
             String line;
            
             availableInfo.clear();
             
             
             while (rs.next()) {
                int roomNo = rs.getInt(1);
               // String roomType = rs.getString(2);
                //int numbeds = rs.getInt(3);
                double totalprice = rs.getDouble(2);        
                availableInfo.add(String.format("%-10d,%-10.2f,", roomNo,totalprice));
            }
            
             ps.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableInfo;
     }
     
     public double book(int customerID, int roomNo, java.util.Date dateIn, java.util.Date dateOut){
       
        PreparedStatement ps;
        double totalPrice = 0;
       
        try {
            java.sql.Date sqlDateIn = new java.sql.Date(dateIn.getTime());
            java.sql.Date sqlDateOut = new java.sql.Date(dateOut.getTime());
            
             ps = msServer.prepareStatement("SELECT RoomType FROM Room WHERE RoomNo = ?");
             ps.setInt(1, roomNo);
             ResultSet roomTypeRS = ps.executeQuery();
             
             while(roomTypeRS.next()){
                 String roomtype = roomTypeRS.getString(1);
                 ps = msServer.prepareStatement("Select SUM(Price) AS TotalPrice From Pricing Where RoomType = ? And Date>= ? And Date<? ");
                 ps.setString(1,roomtype);
                 ps.setDate(2,sqlDateIn);
                 ps.setDate(3,sqlDateOut);
                 
                 ResultSet totalPriceRS = ps.executeQuery();
                 while(totalPriceRS.next()){
                     totalPrice = totalPriceRS.getDouble(1);
                 }
             }
            
            ps = msServer.prepareStatement("INSERT INTO Reservation VALUES (?,?,?,?,?)");
             
            ps.setInt(1, customerID);
            ps.setInt(2, roomNo);
            ps.setDate(3, sqlDateIn);
            ps.setDate(4, sqlDateOut);
            ps.setDouble(5, totalPrice);
            ps.executeUpdate();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(reservationdblib.class.getName()).log(Level.SEVERE, null, ex);
        }
          catch (Exception e) {
            e.printStackTrace();
        }
         
         return totalPrice;
     }
                
     
}
