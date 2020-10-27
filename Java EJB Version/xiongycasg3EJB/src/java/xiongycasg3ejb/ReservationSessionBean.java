

package xiongycasg3ejb;


import javax.ejb.Stateless;
import java.util.List;
import reservationdblib.*;
/**
 *
 * @author Yuchen Xiong
 */
@Stateless
public class ReservationSessionBean implements ReservationSessionBeanRemote, ReservationSessionBeanLocal {

    reservationdblib rdb;
    
    public ReservationSessionBean() {
        rdb = new reservationdblib();
    }
     
 
    @Override
    public List<String> listcustomers() {
        return rdb.listcustomers();
     }
    
    
    @Override
    public List<String> listavailable(java.util.Date dateIn, java.util.Date dateOut){
        return rdb.listavailable(dateIn, dateOut);
    }
    
    @Override
    public double book(int customerID, int roomNo, java.util.Date dateIn, java.util.Date dateOut) {
        return rdb.book(customerID, roomNo, dateIn, dateOut);
    }
     
}
