package com.cmg.motorcycles.db;
import java.sql.*;

import com.cmg.motorcycles.ejb.MotorcycleDetails;

public class ConnectMotorCycleDB {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/test_motorcycles";
   static final String USER = "root";
   static final String PASS = "password";
   
   public static MotorcycleDetails getMotorcycleDetails(String motorcycleStockId) {
   Connection conn = null;
   Statement stmt = null;
   MotorcycleDetails objMotorcycleDetails = new MotorcycleDetails();
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);			//Connecting to database
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * from test_motorcycles.VehicleInfo where VehicleInfo.StockNumber="+motorcycleStockId;
      ResultSet rs = stmt.executeQuery(sql);

      while(rs.next()){
    	  objMotorcycleDetails.setId(Integer.parseInt(motorcycleStockId));
    	 objMotorcycleDetails.setMotorcycle_name(rs.getString("YearOfMake")+" "+rs.getString("DealerModelDescription"));
    	 objMotorcycleDetails.setMotorcycle_orc_included(rs.getString("PriceIncludesORC"));
    	 if(objMotorcycleDetails.getMotorcycle_orc_included() == null || objMotorcycleDetails.getMotorcycle_orc_included() == "" )
    		 objMotorcycleDetails.setMotorcycle_orc_included("");
    	 else if(objMotorcycleDetails.getMotorcycle_orc_included().equalsIgnoreCase("Yes"))
    		 objMotorcycleDetails.setMotorcycle_orc_included("**Price includes ORC");
    	 else if(objMotorcycleDetails.getMotorcycle_orc_included().equalsIgnoreCase("No"))
    		 objMotorcycleDetails.setMotorcycle_orc_included("**Price excludes ORC");
    	 else
    		 objMotorcycleDetails.setMotorcycle_orc_included("");
    	 objMotorcycleDetails.setMotorcycle_descrption(rs.getString("VehicleNote")+"\r\n"+objMotorcycleDetails.getMotorcycle_orc_included());
    	 objMotorcycleDetails.setMotorcycle_engine_cc(rs.getString("EngineCC"));
    	 objMotorcycleDetails.setMotorcycle_odometer_value(rs.getInt("ODOReading"));
    	 objMotorcycleDetails.setMotorcycle_photo("");
    	 objMotorcycleDetails.setMotorcycle_price(rs.getInt("AskingPrice"));
    	 objMotorcycleDetails.setMotorcycle_type(rs.getString("StockGroup"));
         objMotorcycleDetails.setMotorcycle_weekly_price(rs.getString("WeeklyFinancePrice"));
         objMotorcycleDetails.setMotorcycle_manufacturer(rs.getString("Manufacturer"));
         
      }
     
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      
      se.printStackTrace();
   }catch(Exception e){
      
      e.printStackTrace();
   }finally{
      
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
   
   return objMotorcycleDetails;
   
}
}