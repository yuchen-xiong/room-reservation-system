using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using xiongycasg5client.ReservationService;
//using xiongycadodblib;


namespace xiongycasg5client
{
    class Program
    {
        static void Main(string[] args)
        {
            String input;
            List<String> customersInfo = new List<String>();
            List<String> availableInfo = new List<String>();
            String cid,roomNo;
            DateTime dateIn = new DateTime();
            DateTime dateOut = new DateTime();
            IReservationService irs = new ReservationServiceClient();
            double bookResult = 0;


            for (int k = 0; k < irs.ListCustomers().Length; k++)
            {
                customersInfo.Add(irs.ListCustomers()[k]);
            }
            Console.WriteLine(String.Format("{0,-5}{1,-16}{2,-4}", "CID", "Name", "CCNo"));
            Console.WriteLine(String.Format("{0,-5}{1,-16}{2,-4}", "----", "--------------", "----"));
            for (int i = 0; i < customersInfo.Count; i = i + 3)
            {
                Console.WriteLine(String.Format("{0,-5}{1,-16}{2,-4}", customersInfo[i], customersInfo[i + 1], customersInfo[i + 2]));
            }

            while (true)
            {
                bool checkAvailable = false;
                Console.Write("Please press b to book , q to quit > ");
                input = Console.ReadLine();
                if (input.Equals("q"))
                {
                    return;
                }
                if (input.Equals("b") && checkAvailable == false)
                {
                    bool validDateFormat = false;
                    while (true)
                    {
                        try
                        {
                            if (validDateFormat)
                            {
                                Console.Write("Press b to start a new booking q to quit > ");
                                input = Console.ReadLine();
                                if (input.Equals("q")) return;
                                if (!input.Equals("b") && !input.Equals("q"))
                                {
                                    continue;
                                }

                            }

                            Console.Write("Please enter date in (mm/dd/yy) > ");
                            String inputDateIn = Console.ReadLine();
                            Console.Write("Please enter date out (mm/dd/yy) > ");
                            String inputDateOut = Console.ReadLine();

                            dateIn = DateTime.ParseExact(inputDateIn, "MM/dd/yy", CultureInfo.InvariantCulture);
                            dateOut = DateTime.ParseExact(inputDateOut, "MM/dd/yy", CultureInfo.InvariantCulture);

                            for (int k = 0; k < irs.ListAvailable(dateIn, dateOut).Length; k++)
                            {
                                availableInfo.Add(irs.ListAvailable(dateIn, dateOut)[k]);
                            }

                            if (availableInfo.Count == 0)
                            {
                                Console.WriteLine("No booking information is available for the dates you entered. ");
                                validDateFormat = true;
                                availableInfo.Clear();
                                continue;
                            }


                            if (availableInfo.Count == 1)
                            {
                                Console.WriteLine("No booking information is available for the dates you entered. ");
                                Console.WriteLine(availableInfo[0]);
                                validDateFormat = true;
                                availableInfo.Clear();
                                continue;
                            }

                            if (availableInfo.Count >= 2)
                            {
                                Console.WriteLine(String.Format("{0,-12}{1,-12}", "RoomNo", "Total Price"));
                                Console.WriteLine(String.Format("{0,-12}{1,-21}", "-----------", "---------------------"));
                                for (int i = 0; i < availableInfo.Count; i = i + 2)
                                {
                                    Console.WriteLine(String.Format("{0,-12}{1,-12:F2}", availableInfo[i], Double.Parse(availableInfo[i + 1])));
                                }
                                checkAvailable = true;
                                break;
                            }

                        }
                        catch (Exception e)
                        {
                            //Console.WriteLine(e.Message);
                            Console.WriteLine("Please enter a valid date format.");
                        }
                    } 
                }

                             Console.WriteLine("Above are the list of available rooms. Please press b to book q to quit > ");
                             input = Console.ReadLine();
                             if (input.Equals("q")) return;
                             if (checkAvailable && input.Equals("b"))
                             {
                                 Console.Write("Please enter a customer id > ");
                                 cid = Console.ReadLine();
                                 Console.Write("Please enter a room number > ");
                                 roomNo = Console.ReadLine();

                                 for (int i = 0; i < availableInfo.Count; i = i + 2)
                                 {

                                     if (availableInfo[i].Contains(roomNo))
                                     {
                                         bookResult = irs.Book(Convert.ToInt32(cid), Convert.ToInt32(roomNo), dateIn, dateOut);
                                         if (bookResult != 0)
                                         {
                                             Console.WriteLine("Room {0:D3} has been booked for a total cost of {1:F2}", roomNo, bookResult);
                                             break;
                                         }
                                     }

                                 }

                                
                                 if (bookResult == 0) {
                                     Console.WriteLine("Reservation failed . Room " + roomNo + " is not available.");
                                 }

                                
                                 
                             } 
                        customersInfo.Clear();
                        availableInfo.Clear();
                        bookResult = 0;
            }
        }
    }
}
