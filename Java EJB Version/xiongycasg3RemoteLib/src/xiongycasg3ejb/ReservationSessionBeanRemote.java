/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xiongycasg3ejb;


import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Yuchen Xiong
 */
@Remote
public interface ReservationSessionBeanRemote {
     public List<String> listcustomers() ;
     public List<String> listavailable(java.util.Date dateIn, java.util.Date dateOut);
     public double book(int customerID, int roomNo, java.util.Date dateIn, java.util.Date dateOut) ;
}
