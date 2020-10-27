

package xiongycasg3ejb;


import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Yuchen Xiong
 */
@Local
public interface ReservationSessionBeanLocal {
     public List<String> listcustomers();
     public List<String> listavailable(java.util.Date dateIn, java.util.Date dateOut) ;
     public double book(int customerID, int roomNo, java.util.Date dateIn, java.util.Date dateOut);
}
