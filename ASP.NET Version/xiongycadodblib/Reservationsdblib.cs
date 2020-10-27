using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace xiongycadodblib{

    public class Reservationsdblib{
        private SqlConnection reservationsDB;
        
        public Reservationsdblib() {

            reservationsDB = new SqlConnection();
            reservationsDB.ConnectionString = "Data Source=(local);Initial Catalog=reservationsSQL;User ID=ism6236;Password=ism6236bo";
            reservationsDB.Open();
        }

        public List<String> ListCustomers(){

            String query = "SELECT CustomerID, Name, CCNo FROM Customer;";
            
            List<String> customersInfo = new List<String>();
            
            DataSet ds = new DataSet();
            
            try
            {
                SqlDataAdapter da = new SqlDataAdapter();
                SqlCommand cmd = new SqlCommand(query, reservationsDB);
                da.SelectCommand = cmd;
                da.Fill(ds);
                DataTable dt = ds.Tables[0];
                for (int i = 0; i < dt.Rows.Count; i++) {
                  for (int j = 0; j < dt.Columns.Count; j++){
                    customersInfo.Add(dt.Rows[i][j].ToString());
                  }

                }
                reservationsDB.Close();
            }

            catch (Exception e) 
            { 
                Console.WriteLine(e.Message); 
            }
            
            
            
            return customersInfo;
        }


        public List<String> ListAvailable(DateTime dateIn, DateTime dateOut){

            String query = "SELECT Min(Date) AS minDate, Max(Date) AS maxDate FROM Pricing;"; 

            List<String> availableInfo = new List<String>();
            
            DataSet ds = new DataSet();

            String sqldateIn = dateIn.ToShortDateString();
            String sqldateOut = dateOut.ToShortDateString();

            try
            {
                SqlDataAdapter da = new SqlDataAdapter();
                SqlCommand cmd = new SqlCommand(query, reservationsDB);
                da.SelectCommand = cmd;
                da.Fill(ds);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

            DataTable dt = ds.Tables[0];


            DateTime minDate = DateTime.Parse(dt.Rows[0][0].ToString());
            DateTime maxDate = DateTime.Parse(dt.Rows[0][1].ToString());
           
            if (minDate > dateIn || maxDate < dateOut)
            {
                String error = "The date range you can book is from "
                             + "" + minDate.ToShortDateString() + " to " + maxDate.ToShortDateString() + " ! ";
                availableInfo.Add(error);
                return availableInfo;
            }

            if (dateIn > dateOut)
            {
                String error = "The move-in date should be no later than move-out date !";
                availableInfo.Add(error);
                return availableInfo;
            }

           
            query = String.Format("Select Room.RoomNo, SUM(Pricing.Price) From  Room, Pricing  Where Room.RoomType = Pricing.RoomType AND Pricing.Date >= '{0}' AND Pricing.Date < '{1}' And Room.RoomNo Not In (Select Reservation.RoomNo From Reservation Where (Reservation.DateIn< '{2}' And Reservation.DateIn>='{3}') Or (Reservation.DateOut <='{4}' And Reservation.DateOut>'{5}')) GROUP BY Room.RoomNo ORDER BY Room.RoomNo",
                                 sqldateIn,sqldateOut,sqldateOut,sqldateIn,sqldateOut,sqldateIn);

            
            ds = new DataSet();

            try
            {
                SqlDataAdapter da = new SqlDataAdapter();
                SqlCommand cmd = new SqlCommand(query, reservationsDB);
                da.SelectCommand = cmd;
                da.Fill(ds);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

            dt = ds.Tables[0];

            for (int i = 0; i < dt.Rows.Count; i++)
            {
                for (int j = 0; j < dt.Columns.Count; j++)
                {
                    availableInfo.Add(dt.Rows[i][j].ToString());
                }

            }
           reservationsDB.Close();
           return availableInfo;  
            
        }

      
        public double Book(int customerID, int roomNo, DateTime dateIn, DateTime dateOut){
       
            double totalPrice = 0;
            String sqldateIn = dateIn.ToShortDateString();
            String sqldateOut = dateOut.ToShortDateString();

            String query = String.Format("SELECT RoomType FROM Room WHERE RoomNo = '{0}';",roomNo);
            
            DataSet ds = new DataSet();
            
            try{
                SqlDataAdapter da = new SqlDataAdapter();
                SqlCommand cmd = new SqlCommand(query, reservationsDB);
                da.SelectCommand = cmd;
                da.Fill(ds);
            }
            catch (Exception e) { 
                Console.WriteLine(e.Message); 
            }
            
            DataTable dt = ds.Tables[0];
            
            String roomType = dt.Rows[0][0].ToString();

            ds = new DataSet();
            query = String.Format("Select SUM(Price) AS TotalPrice From Pricing Where RoomType = '{0}' And Date>= '{1}' And Date<'{2}'",roomType,sqldateIn,sqldateOut);
                
            try{
                SqlDataAdapter da = new SqlDataAdapter();
                SqlCommand cmd = new SqlCommand(query, reservationsDB);
                da.SelectCommand = cmd;
                da.Fill(ds);
            }
            catch (Exception e) { 
                Console.WriteLine(e.Message); 
            }

            dt = ds.Tables[0];   
            totalPrice = Double.Parse(dt.Rows[0][0].ToString());
            query = String.Format("INSERT INTO Reservation VALUES ({0},{1},'{2}','{3}',{4})", customerID, roomNo, dateIn.ToShortDateString(), dateOut.ToShortDateString(), totalPrice);
            SqlTransaction trans = reservationsDB.BeginTransaction();

            try
            {
                
                
                
                SqlCommand cmd = new SqlCommand();
                cmd.Connection = reservationsDB;
                cmd.Transaction = trans;
                cmd.CommandText = query;
                cmd.ExecuteNonQuery();
                trans.Commit();
            }

            catch (InvalidOperationException ex)
            {
                trans.Rollback();
                throw ex;
            }
            catch (Exception ex)
            {
                trans.Rollback();
                throw ex;
            }
            reservationsDB.Close();
            return totalPrice;  
      }
    }
}
