package com.cmg.motorcycles.db;
import java.sql.*;

import com.cmg.motorcycles.ejb.EuromarqueCarDetails;


public class ConnectEuroDB {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/test_motorcycles";
   static final String USER = "root";
   static final String PASS = "password";
   public static final String BIKECAR_PATH = "http://192.168.2.169:8081/InformationCardDynamic/images/";
   public static EuromarqueCarDetails getVehicleDetails(String vehicleStockId) {
   Connection conn = null;
   Statement stmt = null;
   EuromarqueCarDetails objEuromarqueCarDetails = new EuromarqueCarDetails();
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);			//Connecting to database
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * from test_motorcycles.VehicleInfo where VehicleInfo.StockNumber="+vehicleStockId;
      ResultSet rs = stmt.executeQuery(sql);

      while(rs.next()){
    	  
    	   objEuromarqueCarDetails.setId(Integer.parseInt(vehicleStockId));
    	   objEuromarqueCarDetails.setIn_AskingPrice(rs.getInt("AskingPrice"));
    	   objEuromarqueCarDetails.setIn_Odometer(rs.getInt("ODOReading"));
    	   objEuromarqueCarDetails.setSt_AudioFeatures(rs.getString("AudioCommunication"));
    	   objEuromarqueCarDetails.setSt_ChassisNumber(rs.getString("ChassisNumber"));
    	   objEuromarqueCarDetails.setSt_CountryLastReg(rs.getString("Countrylastregistered"));
    	   objEuromarqueCarDetails.setSt_EngineCapacity(rs.getString("EngineCapacity"));
    	   objEuromarqueCarDetails.setSt_ExteriorFeatures(rs.getString("Exteriorfeatures"));
    	   objEuromarqueCarDetails.setSt_FuelAnnualCost(rs.getString("Fuelannualcost"));
    	   objEuromarqueCarDetails.setSt_FuelLitrePer100KM(rs.getString("FuelLper100km"));
    	   objEuromarqueCarDetails.setSt_FuelStar(rs.getString("Fuelstarrating"));
    	   objEuromarqueCarDetails.setSt_FuelType(rs.getString("FuelType"));
    	   objEuromarqueCarDetails.setSt_GeneralFeatures(rs.getString("GeneralFeatures"));
    	   objEuromarqueCarDetails.setSt_ImportedDamagedVehicle(rs.getString("ImportedasdamagedVehicle"));
    	   objEuromarqueCarDetails.setSt_InteriorFeatures(rs.getString("Interiorfeatures"));
    	   objEuromarqueCarDetails.setSt_LicenseExpriy(rs.getString("Vehiclelicenseexpiry"));
    	   objEuromarqueCarDetails.setSt_Name(rs.getString("DealerModelDescription"));
    	   objEuromarqueCarDetails.setSt_OutstandingRoadUserCharges(rs.getString("OutstandingRoadusercharges"));
    	   objEuromarqueCarDetails.setSt_RadioReceiverCapability(rs.getString("Radioreceivercapability"));
    	   objEuromarqueCarDetails.setSt_RegisteredVehicle(rs.getString("RegisteredVehicle"));
    	   objEuromarqueCarDetails.setSt_RegistrationNumber(rs.getString("RegistrationNumber"));
    	   objEuromarqueCarDetails.setSt_ReRegisteredVehicle(rs.getString("ReregisteredVehicle"));
    	   objEuromarqueCarDetails.setSt_RoadUserCharges(rs.getString("Roadusercharges"));
    	   objEuromarqueCarDetails.setSt_SafetyFeatures(rs.getString("SafetyTechnology"));
    	   objEuromarqueCarDetails.setSt_SecurityInterest(rs.getString("Securityinterest"));
    	   objEuromarqueCarDetails.setSt_VehicleLicense(rs.getString("Vehiclelicensepresent"));
    	   objEuromarqueCarDetails.setSt_WOF(rs.getString("WarrantofFitness"));
    	   objEuromarqueCarDetails.setSt_WOFExpiry(rs.getString("WOFExpirydate"));
    	   objEuromarqueCarDetails.setSt_YearFirstRegNZ(rs.getString("YearfirstregisteredinNZ"));
    	   objEuromarqueCarDetails.setSt_YearFirstRegOverseas(rs.getString("Year1stregisteredoverseas"));
    	   objEuromarqueCarDetails.setSt_YearOfMake(rs.getString("YearOfMake"));
    	   objEuromarqueCarDetails.setSt_photo(BIKECAR_PATH+objEuromarqueCarDetails.getId()+"-1.jpg");
    	   objEuromarqueCarDetails.setSt_VehicleFamily(rs.getString("VehicleFamily"));
    	   objEuromarqueCarDetails.setSt_Manufacturer(rs.getString("Manufacturer"));
    	   objEuromarqueCarDetails.setSt_PricesincludesORC(rs.getString("PriceIncludesORC"));
    	   objEuromarqueCarDetails.setSt_WeeklyFinancePrice(rs.getString("WeeklyFinancePrice"));
    	  
    	  
    	   
         
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
   
   return objEuromarqueCarDetails;
   
}
}