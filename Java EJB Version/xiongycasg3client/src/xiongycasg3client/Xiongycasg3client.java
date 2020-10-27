package xiongycasg3client;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import xiongycasg3ejb.*;

/**
 *
 * @author Yuchen Xiong
 */

public class Xiongycasg3client {

    public static void main(String[] args) {
        
        ReservationSessionBeanRemote rR;
        
        try{
            InitialContext ic = new InitialContext();
            rR = (ReservationSessionBeanRemote)ic.lookup("java:global/xiongycasg3EJB/ReservationSessionBean!"
                    + "xiongycasg3ejb.ReservationSessionBeanRemote");
            List<String> customerInfo = rR.listcustomers();
            List<String> availableInfo = new ArrayList<String>();
            List<String> availableInfoRoomNo = new ArrayList<String>();
            List<String> availableInfoTotalPrice = new ArrayList<String>();
            Date dateIn = new Date();
            Date dateOut = new Date();
            int cid = 0; 
            int roomNo = 0;
 
            //List all customer information
            System.out.printf("%-5s%-20s%-5s\n", "CID", "Name", "CCNo");
            System.out.println("---- ------------------- -----");
            for(int i = 0; i<customerInfo.size();i++){
                System.out.println(customerInfo.get(i));
            }
            
            
             // Read user command entry
            while(true){
                boolean checkAvailable = false;
                System.out.print("\nPlease press b to book , q to quit > ");
                Scanner input = new Scanner(System.in);
                String command = input.next();
                if(command.equalsIgnoreCase("q")){
                    return;
                }
           
                if(command.equalsIgnoreCase("b")&&checkAvailable==false){
                    boolean validDateFormat = false; 
                    while(true){
                         try {
                              if(validDateFormat){
                                  System.out.print("\nPress b to start a new booking q to quit > ");
                                  command = input.next();
                                  
                                  if(command.equalsIgnoreCase("q")) return;
                                  if(!command.equalsIgnoreCase("b")&&!command.equalsIgnoreCase("q")){
                                      continue;
                                  }
                             }
                             
                               DateFormat sdf = new SimpleDateFormat("MM/dd/yy");
                               System.out.print("\nPlease enter date in (mm/dd/yy) > ");
                               dateIn =  sdf.parse(input.next());
                               System.out.print("\nPlease enter date out (mm/dd/yy) > ");
                               dateOut = sdf.parse(input.next());
                               availableInfo = rR.listavailable(dateIn, dateOut);
                               if(availableInfo.isEmpty()){
                                 System.out.println("\nNo booking information is available for the dates you entered. ");
                                 validDateFormat = true;
                                 continue;
                               }
                               if(availableInfo.size()==1&&!availableInfo.get(0).contains(",")){  
                                 System.out.println("\nNo booking information is available for the dates you entered. ");
                                 System.out.println("\n"+availableInfo.get(0));
                                 validDateFormat = true;
                                 continue;
                               }
                               if(availableInfo.size()>=1&&availableInfo.get(0).contains(",")){ 
                                   System.out.printf("\n%-10s%-10s\n", "RoomNo","Total Price");
                                   System.out.println("--------- ----------");
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
                                   
                                   for(int i = 0; i<availableInfo.size();i++){
                                       System.out.print(availableInfoRoomNo.get(i)+"       ");
                                       System.out.println(availableInfoTotalPrice.get(i));
                                     }
                                    // Make sure user have checked available room info first
                                    checkAvailable = true;
                                    break;
                                }    
                         }                
                        catch (ParseException ex) {
                          System.out.println("\nPlease enter a valid date format.");                      
                        } 
                    }
                }      
        
            System.out.print("\nAbove are the list of available rooms. Please press b to book q to quit > ");
            command = input.next();
            if(command.equalsIgnoreCase("q")) return;
            if(checkAvailable&&command.equalsIgnoreCase("b")){         
                System.out.print("\nPlease enter a customer id > ");   
                cid = input.nextInt();
                System.out.print("\nPlease enter a room number > ");
                roomNo = input.nextInt();
                
                if(!availableInfoRoomNo.contains(String.valueOf(roomNo))){
                    System.out.println("\nReservation failed . Room "+roomNo+" is not available.");
                }
                
                else{
                   double bookResult =  rR.book(cid, roomNo, dateIn, dateOut);
                   if(bookResult!=0){
                      System.out.printf("\nRoom %3d has been booked for a total cost of %3.2f\n",roomNo,bookResult);
                   }
               }
            }
            
           customerInfo.clear();
           availableInfo.clear();
           availableInfoRoomNo.clear();
           availableInfoTotalPrice.clear();
       }
       
         } catch (NamingException ex) {
                Logger.getLogger(Xiongycasg3client.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
}
