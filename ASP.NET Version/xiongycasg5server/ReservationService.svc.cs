using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using xiongycadodblib;

namespace xiongycasg5server
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Service1.svc or Service1.svc.cs at the Solution Explorer and start debugging.
    public class ReservationService : IReservationService
    {
        Reservationsdblib rdb;

        public ReservationService()
        {
            rdb = new Reservationsdblib();
        }

        public string GetData(int value)
        {
            return string.Format("You entered: {0}", value);
        }

        public CompositeType GetDataUsingDataContract(CompositeType composite)
        {
            if (composite == null)
            {
                throw new ArgumentNullException("composite");
            }
            if (composite.BoolValue)
            {
                composite.StringValue += "Suffix";
            }
            return composite;
        }

        public List<String> ListCustomers()
        {
            return rdb.ListCustomers();
        }

        public List<String> ListAvailable(DateTime dateIn, DateTime dateOut)
        {
            return rdb.ListAvailable(dateIn, dateOut);
        }

        public double Book(int customerID, int roomNo, DateTime dateIn, DateTime dateOut)
        {
            return rdb.Book(customerID, roomNo, dateIn, dateOut);
        }

    }  
}
