package com.cmg.motorcycles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmg.motorcycles.db.ConnectEuroDB;
import com.cmg.motorcycles.ejb.EuromarqueCarDetails;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;


public class EuromarqueInformationCardServlet extends HttpServlet {
	public static final String IMAGE1 = "M://ZZ-SRUTHY//MotorcyclesInformationCard//InformationCardDynamic//images//EuromarqueICT1.jpg";
	public static final String IMAGE2= "M://ZZ-SRUTHY//MotorcyclesInformationCard//InformationCardDynamic//images//EuromarqueICT2.jpg";
    
    public static final String DEST = "M://ZZ-SRUTHY//MotorcyclesInformationCard//output//testeuro.pdf";
    public static final String DEST1 = "M://ZZ-SRUTHY//MotorcyclesInformationCard//output//testeuro1.pdf";
    public static final String FUEL1 = "http://192.168.2.169:8081/InformationCardDynamic/images/1.png";
    public static final String FUEL2 = "http://192.168.2.169:8081/InformationCardDynamic/images/2.png";
    public static final String FUEL3 = "http://192.168.2.169:8081/InformationCardDynamic/images/3.png";
    public static final String FUEL4 = "http://192.168.2.169:8081/InformationCardDynamic/images/4.png";
    public static final String FUEL5 = "http://192.168.2.169:8081/InformationCardDynamic/images/5.png";
    public static final String FUEL6 = "http://192.168.2.169:8081/InformationCardDynamic/images/6.png";
  
    public static final String DESTNEW= "M://InformationCard//";
    public static final String RESOURCENEW = "M://ZZ-SRUTHY//MotorcyclesInformationCard//InformationCardDynamic//images//EuromarqueICT2.pdf";
    public static String Servleterror ="";

      
// Method to handle GET method request.
public void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException 
  
  {
   try
   {
	String stockId= (String) request.getParameter("stockid");
   EuromarqueCarDetails objEuromarqueCarDetails = new EuromarqueCarDetails();
   ConnectEuroDB objConnectEuroDB = new ConnectEuroDB();
   objEuromarqueCarDetails = objConnectEuroDB.getVehicleDetails(stockId);
   
   if(objEuromarqueCarDetails.getId() == 0)
   {
	   Exception e = null;
	   Servleterror = "No car found for this stockNumber";
	   throw new Exception(e);
   }
   else if((objEuromarqueCarDetails.getSt_Manufacturer().equalsIgnoreCase("Bike") || objEuromarqueCarDetails.getSt_Manufacturer().equalsIgnoreCase("Ducati") || objEuromarqueCarDetails.getSt_Manufacturer().equalsIgnoreCase("Harley Davidson") ||objEuromarqueCarDetails.getSt_Manufacturer().equalsIgnoreCase("Honda") ||objEuromarqueCarDetails.getSt_Manufacturer().equalsIgnoreCase("Indian") || objEuromarqueCarDetails.getSt_Manufacturer().equalsIgnoreCase("Kymco") || objEuromarqueCarDetails.getSt_Manufacturer().equalsIgnoreCase("MotoGuzzi") || objEuromarqueCarDetails.getSt_Manufacturer().equalsIgnoreCase("Piaggio") || objEuromarqueCarDetails.getSt_Manufacturer().equalsIgnoreCase("Triumph") ||objEuromarqueCarDetails.getSt_Manufacturer().equalsIgnoreCase("Vespa") ||objEuromarqueCarDetails.getSt_Manufacturer().equalsIgnoreCase("BMW Bike") ||objEuromarqueCarDetails.getSt_Manufacturer().equalsIgnoreCase("Yamaha")))
   {
	   Exception e = null;
	   Servleterror = "No car found for this stockNumber";
	   throw new Exception(e);
   }
   else
  	   {
   File file = new File(DEST);
   file.getParentFile().mkdirs();
   try {
	EuromarqueInformationCardServlet.createPdf(DEST,DEST1,objEuromarqueCarDetails);
} catch (DocumentException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
   response.setContentType("text/html");
   String st_SuccessMsg = "PDF created : M:\\InformationCard\\"+objEuromarqueCarDetails.getId()+".pdf";
   request.getSession().setAttribute("success", st_SuccessMsg);
   response.sendRedirect("http://192.168.2.169:8081/InformationCardDynamic/euromarqueinformationcard.jsp");
   
  
  	   }
   }
   
   catch(IOException io)
	  {
		  String error =io.getMessage();
		  request.getSession().setAttribute("error", error);
		  response.sendRedirect("http://192.168.2.169:8081/InformationCardDynamic/euromarqueinformationcard.jsp");
	  }
	  catch(DocumentException de)
	  {
		  String error =de.getMessage();
		  request.getSession().setAttribute("error", error);
		  response.sendRedirect("http://192.168.2.169:8081/InformationCardDynamic/euromarqueinformationcard.jsp"); 	  
	  }
	  catch(Exception e)
	  {	String error = "";
	  if(e.getMessage() == null)
			  error = Servleterror;
		  else
			  error = e.getMessage();
	  
	  if(error.contains("http://192.168.2.169:8081/InformationCardDynamic/images/"))
		  error="No image for the vehicle in F2";
	  request.getSession().setAttribute("error", error);
	  response.sendRedirect("http://192.168.2.169:8081/InformationCardDynamic/euromarqueinformationcard.jsp"); 	  
	  }
	  
	  }

  
  



// Method to handle POST method request.
public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {

  doGet(request, response);
}

public static void createPdf(String dest, String dest1, EuromarqueCarDetails objEuromarqueCarDetails) throws IOException, DocumentException {
	
	
      
		
		
		//adding background image to the pdf 
		Document document = new Document();
		Rectangle pagesize = new Rectangle(595,842);
		document.setPageSize(pagesize);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
		document.open();
		PdfContentByte canvas = writer.getDirectContentUnder();
		Image image = Image.getInstance(IMAGE1);
		image.setAbsolutePosition(0, 0);
		image.scaleToFit(595,842);
		canvas.addImage(image);
		
		
		//adding car image to PDF
		
	    Image img = Image.getInstance(objEuromarqueCarDetails.getSt_photo()); 
	    PdfTemplate t = writer.getDirectContent().createTemplate(510, 281);
	    t.clip();
	    t.newPath();
	    t.addImage(img,255, 0, 0, 141, 0, 0);
	    img = Image.getInstance(t);
	    //setting the cropped image to the object img defined before
	    image.scaleToFit(255,141);
	    img.setAbsolutePosition(170f, 590f);
	    document.add(img);
	       
	    document.add( Chunk.NEWLINE );
	    
	    //adding title to the pdf
	    Font f_car_title = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//FTR_.pfm", 12);
		f_car_title.setColor(0, 0, 0);
   		Paragraph p_car_name = new Paragraph(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_Name()),f_car_title);
   		p_car_name.setAlignment(Element.ALIGN_CENTER);
   		p_car_name.setSpacingBefore(233f);
   		document.add(p_car_name);
		
   		//adding first row features
   		
   		PdfPTable table = new PdfPTable(4);
	    table.setWidthPercentage(100);
	    PdfPCell cell1= new PdfPCell();
	    PdfPCell cell2= new PdfPCell();
	    PdfPCell cell3= new PdfPCell();
	    PdfPCell cell4= new PdfPCell();
	       
	    cell1 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_YearOfMake()), PdfPCell.ALIGN_CENTER);
	    cell1.setPaddingBottom(0f);
	    cell1.setPaddingTop(45f);
	    cell1.setPaddingRight(30f);
	    cell1.setPaddingLeft(10f);
	    table.addCell(cell1);
	    
	    
	    cell2 = getCell("$"+Integer.toString(objEuromarqueCarDetails.getIn_AskingPrice()), PdfPCell.ALIGN_CENTER);
	    cell2.setPaddingBottom(0f);
	    cell2.setPaddingTop(45f);
	    cell2.setPaddingLeft(10f);
	    if(Integer.toString(objEuromarqueCarDetails.getIn_AskingPrice()).length()==5)
	    cell2.setPaddingRight(20f);
	    if(Integer.toString(objEuromarqueCarDetails.getIn_AskingPrice()).length()==6)
		    cell2.setPaddingRight(17f);
	    if(Integer.toString(objEuromarqueCarDetails.getIn_AskingPrice()).length()==7)
		    cell2.setPaddingRight(15f);
	   
	    table.addCell(cell2);
	    
	    
	    cell3 = getCell(Integer.toString(objEuromarqueCarDetails.getIn_Odometer())+"km", PdfPCell.ALIGN_CENTER);
	    cell3.setPaddingBottom(0f);
	    cell3.setPaddingTop(45f);
	    if(Integer.toString(objEuromarqueCarDetails.getIn_Odometer()).length()==3)
	    	cell3.setPaddingRight(7f);
		    if(Integer.toString(objEuromarqueCarDetails.getIn_Odometer()).length()==4)
		    	cell3.setPaddingRight(5f);
		    if(Integer.toString(objEuromarqueCarDetails.getIn_Odometer()).length()==5)
		    	cell3.setPaddingRight(3f);
		    if(Integer.toString(objEuromarqueCarDetails.getIn_Odometer()).length()==6)
		    	cell3.setPaddingRight(1f);
		   
	    
	    cell3.setPaddingLeft(10f);
	    table.addCell(cell3);
	    
	    
	    cell4 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_EngineCapacity())+"L", PdfPCell.ALIGN_CENTER);
	    cell4.setPaddingBottom(0f);
	    cell4.setPaddingTop(45f);
	    cell4.setPaddingRight(0f);
	    cell4.setPaddingLeft(15f);
	    table.addCell(cell4);
	       
	    document.add(table);
	    
	    //adding second row features
	    
	    PdfPTable table1 = new PdfPTable(4);
	    table1.setWidthPercentage(100);
	    PdfPCell cell5= new PdfPCell();
	    PdfPCell cell6= new PdfPCell();
	    PdfPCell cell7= new PdfPCell();
	    PdfPCell cell8= new PdfPCell();
	       
	    cell5 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_FuelType()).toUpperCase(), PdfPCell.ALIGN_CENTER);
	    cell5.setPaddingBottom(0f);
	    cell5.setPaddingTop(45f);
	    cell5.setPaddingRight(30f);
	    cell5.setPaddingLeft(10f);
	    table1.addCell(cell5);
	    
	    
	    cell6 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_ChassisNumber()), PdfPCell.ALIGN_CENTER);
	    cell6.setPaddingBottom(0f);
	    cell6.setPaddingTop(45f);
	    cell6.setPaddingLeft(5f);
	    cell6.setPaddingRight(11f);
	  	table1.addCell(cell6);
	    
	    
	    cell7 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_WOF()).toUpperCase(), PdfPCell.ALIGN_CENTER);
	    cell7.setPaddingBottom(0f);
	    cell7.setPaddingTop(45f);
	    if(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_WOF()).length()==3)
	    	cell7.setPaddingRight(7f);
		 if(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_WOF()).length()==2)
		    cell7.setPaddingRight(5f);
		     
		   
	    
		    cell7.setPaddingLeft(10f);
	    table1.addCell(cell7);
	    
	    
	    cell8 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_WOFExpiry()), PdfPCell.ALIGN_CENTER);
	    cell8.setPaddingBottom(0f);
	    cell8.setPaddingTop(45f);
	    cell8.setPaddingRight(0f);
	    cell8.setPaddingLeft(22f);
	    table1.addCell(cell8);
	       
	    document.add(table1);
		
		//adding third row
	    
	    PdfPTable table2 = new PdfPTable(4);
	    table2.setWidthPercentage(100);
	    PdfPCell cell9= new PdfPCell();
	    PdfPCell cell10= new PdfPCell();
	    PdfPCell cell11= new PdfPCell();
	    PdfPCell cell12= new PdfPCell();
	       
	    cell9 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_RegisteredVehicle()).toUpperCase(), PdfPCell.ALIGN_CENTER);
	    cell9.setPaddingBottom(0f);
	    cell9.setPaddingTop(45f);
	    cell9.setPaddingRight(30f);
	    cell9.setPaddingLeft(10f);
	    table2.addCell(cell9);
	    
	    
	    cell10 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_RegistrationNumber()), PdfPCell.ALIGN_CENTER);
	    cell10.setPaddingBottom(0f);
	    cell10.setPaddingTop(45f);
	    cell10.setPaddingLeft(5f);
	    cell10.setPaddingRight(11f);
	    table2.addCell(cell10);
	    
	    
	    cell11 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_YearFirstRegNZ()), PdfPCell.ALIGN_CENTER);
	    cell11.setPaddingBottom(0f);
	    cell11.setPaddingTop(45f);
	    cell11.setPaddingLeft(10f);
	    table2.addCell(cell11);
	    
	    
	    cell12 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_ReRegisteredVehicle()).toUpperCase(), PdfPCell.ALIGN_CENTER);
	    cell12.setPaddingBottom(0f);
	    cell12.setPaddingTop(45f);
	    cell12.setPaddingRight(0f);
	    cell12.setPaddingLeft(22f);
	    table2.addCell(cell12);
	       
	    document.add(table2);
		
   		//adding fourth row
	    
//adding third row
	    
	    PdfPTable table3 = new PdfPTable(4);
	    table3.setWidthPercentage(100);
	    PdfPCell cell13= new PdfPCell();
	    PdfPCell cell14= new PdfPCell();
	    PdfPCell cell15= new PdfPCell();
	    PdfPCell cell16= new PdfPCell();
	       
	    cell13 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_VehicleLicense()).toUpperCase(), PdfPCell.ALIGN_CENTER);
	    cell13.setPaddingBottom(0f);
	    cell13.setPaddingTop(45f);
	    cell13.setPaddingRight(30f);
	    cell13.setPaddingLeft(10f);
	    table3.addCell(cell13);
	    
	    
	    cell14 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_LicenseExpriy()), PdfPCell.ALIGN_CENTER);
	    cell14.setPaddingBottom(0f);
	    cell14.setPaddingTop(45f);
	    cell14.setPaddingLeft(5f);
	    cell14.setPaddingRight(12f);
	    table3.addCell(cell14);
	    
	    
	    cell15 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_RoadUserCharges()).toUpperCase(), PdfPCell.ALIGN_CENTER);
	    cell15.setPaddingBottom(0f);
	    cell15.setPaddingTop(45f);
	    cell15.setPaddingLeft(10f);
		table3.addCell(cell15);
	    
	    
	    cell16 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_OutstandingRoadUserCharges()).toUpperCase(), PdfPCell.ALIGN_CENTER);
	    cell16.setPaddingBottom(0f);
	    cell16.setPaddingTop(45f);
	    if(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_OutstandingRoadUserCharges()).length()==3)
	    	cell15.setPaddingRight(7f);
		 if(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_OutstandingRoadUserCharges()).length()==2)
			 cell15.setPaddingRight(5f);
	    cell16.setPaddingLeft(22f);
	    table3.addCell(cell16);
	       
	    document.add(table3);
	    
	    PdfPTable table4 = new PdfPTable(2);
	    table4.setWidthPercentage(100);
	    PdfPCell cell17= new PdfPCell();
	    PdfPCell cell18= new PdfPCell();
	    	       
	    cell17 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_RadioReceiverCapability()).toUpperCase(), PdfPCell.ALIGN_CENTER);
	    cell17.setPaddingBottom(0f);
	    cell17.setPaddingTop(45f);
	    cell17.setPaddingRight(0f);
	    cell17.setPaddingLeft(200f);
	    table4.addCell(cell17);
	    
	    
	    cell18 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_SecurityInterest()).toUpperCase(), PdfPCell.ALIGN_CENTER);
	    cell18.setPaddingBottom(0f);
	    cell18.setPaddingTop(45f);
	    cell18.setPaddingLeft(225f);
	    cell18.setPaddingRight(0f);
	    table4.addCell(cell18);
	    document.add(table4);
	    
	    
	    PdfPTable table5 = new PdfPTable(3);
	    table5.setWidthPercentage(100);
	    PdfPCell cell19= new PdfPCell();
	    PdfPCell cell20= new PdfPCell();
	    PdfPCell cell21= new PdfPCell();
	    	       
	    cell19 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_YearFirstRegOverseas()), PdfPCell.ALIGN_CENTER);
	    cell19.setPaddingBottom(0f);
	    cell19.setPaddingTop(82f);
	    cell19.setPaddingRight(35f);
	    cell19.setPaddingLeft(20f);
	    table5.addCell(cell19);
	    
	    
	    cell20 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_CountryLastReg()).toUpperCase(), PdfPCell.ALIGN_CENTER);
	    cell20.setPaddingBottom(0f);
	    cell20.setPaddingTop(82f);
	    cell20.setPaddingLeft(22f);
	    cell20.setPaddingRight(30f);
	    table5.addCell(cell20);
	    
	    
	    cell21 = getCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_ImportedDamagedVehicle()).toUpperCase(), PdfPCell.ALIGN_CENTER);
	    cell21.setPaddingBottom(0f);
	    cell21.setPaddingTop(82f);
	    cell21.setPaddingRight(15f);
	    cell21.setPaddingLeft(20f);
	    table5.addCell(cell21);
	    document.add(table5);	    
	    document.close();
	    
	   
	    
	    //******************NEXT PAGE*********************************************************
	  
	    	//adding background image to the pdf 
	    	Document document1 = new Document();
	    	Rectangle page_size = new Rectangle(595,842);
	    	document1.setPageSize(page_size);
	  		PdfWriter writer1 = PdfWriter.getInstance(document1, new FileOutputStream(dest1));
	  		document1.open();
	  		
	  		PdfContentByte canvas1 = writer1.getDirectContentUnder();
			Image image1 = Image.getInstance(IMAGE2);
			image1.setAbsolutePosition(0, 0);
			image1.scaleToFit(595,842);
			canvas1.addImage(image1);
			
	  		
	  		document1.add( Chunk.NEWLINE );
	 	    
	 	    //adding title to the pdf
	 	   Font f_car_complete_title = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//FTUBL.pfm",22);
	 	   f_car_complete_title.setColor(0, 0, 0);
	       Paragraph p_complete_car_name = new Paragraph(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_YearOfMake())+" "+EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_Manufacturer()).toUpperCase()+" "+EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_VehicleFamily()).toUpperCase(),f_car_complete_title);
	       p_complete_car_name.setAlignment(Element.ALIGN_CENTER);
	       p_complete_car_name.setSpacingBefore(40f);
	       document1.add(p_complete_car_name);
	       
	       Font f_car_breif = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//FTR_.pfm",18);
	       f_car_breif.setColor(0, 0, 0);
	       String desc = "";
	       if(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_PricesincludesORC()).equalsIgnoreCase("Yes"))
	       {
	    	   desc = Integer.toString(objEuromarqueCarDetails.getIn_Odometer())+"KMS, $"+ Integer.toString(objEuromarqueCarDetails.in_AskingPrice)+"+ORC'S";
	       }
	       else
	       {
	    	   desc = Integer.toString(objEuromarqueCarDetails.getIn_Odometer())+"KMS, $"+ Integer.toString(objEuromarqueCarDetails.in_AskingPrice);
	       }
	       Paragraph p_car_brief = new Paragraph(desc,f_car_breif);
	       p_car_brief.setAlignment(Element.ALIGN_CENTER);
	       p_car_brief.setSpacingBefore(0f);
	       document1.add(p_car_brief);
	       
	       
	       PdfPTable table6 = new PdfPTable(3);
	       table6.setWidthPercentage(100);
		    PdfPCell cell22= new PdfPCell();
		    PdfPCell cell23= new PdfPCell();
		    PdfPCell cell24= new PdfPCell();
		    
		    String gen_features =  EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_GeneralFeatures()).replaceAll(",","\n\u2022 ");
		    int gen_feature_count = 1;
		    for( int i=1; i<gen_features.length(); i++ ) {
		        if( gen_features.charAt(i) == '\u2022' ) {
		        	gen_feature_count++;
		        } 
		    }
		    if(gen_feature_count<10)
		    cell22 = getFeaturesCell("\n\u2022 "+gen_features, PdfPCell.ALIGN_LEFT);
		    else if (gen_feature_count<15 && gen_feature_count>10)
		    cell22 = getFeaturesCellSmall("\n\u2022"+gen_features, PdfPCell.ALIGN_LEFT);
		    cell22.setPaddingBottom(0f);
		    cell22.setPaddingTop(40f);
		    cell22.setPaddingLeft(0f);
		    table6.addCell(cell22);
		    
		    String ext_features=EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_ExteriorFeatures()).replaceAll(",","\n\u2022 ");
		    int ext_feature_count = 1;
		    for( int i=1; i<ext_features.length(); i++ ) {
		        if( ext_features.charAt(i) == '\u2022' ) {
		        	ext_feature_count++;
		        } 
		    }
		    if(ext_feature_count<10)
		    cell23 = getFeaturesCell("\n\u2022 "+ext_features, PdfPCell.ALIGN_LEFT);
		    else if (ext_feature_count<15 && ext_feature_count>10)
		    cell23 = getFeaturesCellSmall("\n\u2022 "+ext_features, PdfPCell.ALIGN_LEFT);
		    cell23.setPaddingBottom(0f);
		    cell23.setPaddingTop(40f);
		    cell23.setPaddingLeft(2f);
		    table6.addCell(cell23);
		    
		    String int_features=EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_InteriorFeatures()).replaceAll(",","\n\u2022 ");
		    int int_features_count = 1;
		    for( int i=1; i<int_features.length(); i++ ) {
		        if( int_features.charAt(i) == '\u2022' ) {
		        	int_features_count++;
		        } 
		    }
		    if(int_features_count<10)
		    cell24 = getFeaturesCell("\n\u2022 "+int_features, PdfPCell.ALIGN_LEFT);
		    else if (int_features_count<15 && int_features_count>10)
		    cell24 = getFeaturesCellSmall("\n\u2022 "+int_features, PdfPCell.ALIGN_LEFT);
		    cell24.setPaddingBottom(0f);
		    cell24.setPaddingTop(40f);
		    cell24.setPaddingLeft(10f);
		    table6.addCell(cell24);
		    document1.add(table6);	
	       
		    
 
		       PdfPTable table7 = new PdfPTable(3);
		       table7.setWidthPercentage(100);
			    PdfPCell cell25= new PdfPCell();
			    PdfPCell cell26= new PdfPCell();
			    PdfPCell cell27= new PdfPCell();
			    
			    String safe_features =  EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_SafetyFeatures()).replaceAll(",","\n\u2022 ");
			    String[] safe_feature = safe_features.split("\n\u2022 ");
			    
			    int safe_features_count = 1;
			    for( int i=1; i<safe_features.length(); i++ ) {
			        if( safe_features.charAt(i) == '\u2022' ) {
			        	safe_features_count++;
			        } 
			    }
			    
			    System.out.println(safe_features_count);
			    String left_safe_feature = "",right_safe_feature="";
			    
			    for(int i=0; i<safe_features_count/2; i=i+1)
			    {
			    	left_safe_feature = left_safe_feature +"\n\u2022 "+safe_feature[i];
			    }
			    
			    
			    
			    
			    for(int i=safe_features_count/2; i<safe_features_count; i=i+1)
			    {
			    	right_safe_feature = right_safe_feature +"\n\u2022 "+safe_feature[i];
				}
			   
			   
			    cell25 = getFeaturesCell(left_safe_feature, PdfPCell.ALIGN_LEFT);
			    cell26 = getFeaturesCell(right_safe_feature, PdfPCell.ALIGN_LEFT);
			    
			    String audio_features = EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_AudioFeatures()).replaceAll(",","\n\u2022 ");
			    int audio_features_count = 1;
			    for( int i=1; i<audio_features.length(); i++ ) {
			        if( audio_features.charAt(i) == '\u2022' ) {
			        	audio_features_count++;
			        } 
			    }
			    if(audio_features_count<10)
			    cell27 = getFeaturesCell("\n\u2022 "+audio_features, PdfPCell.ALIGN_LEFT);
			    else
			    cell27 = getFeaturesCellSmall("\n\u2022 "+audio_features, PdfPCell.ALIGN_LEFT);	
			  
			    
			    cell25.setPaddingBottom(0f);
			    cell25.setPaddingTop(90f);
			    cell25.setPaddingLeft(0f);
			    
			    cell26.setPaddingBottom(0f);
			    cell26.setPaddingTop(90f);
			    cell26.setPaddingLeft(2f);
			    
			    cell27.setPaddingBottom(0f);
			    cell27.setPaddingTop(90f);
			    cell27.setPaddingLeft(10f);
			    
			    table7.addCell(cell25);
			    table7.addCell(cell26);
			    table7.addCell(cell27);
			    
			    document1.add(table7);	
			   
			    
			    Font f_car_weekly_price = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//FTR_.pfm",35);
			    f_car_weekly_price.setColor(205, 32, 48);
			    Paragraph p_car_weekly_price = new Paragraph("$"+EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_WeeklyFinancePrice()),f_car_weekly_price);
			    p_car_weekly_price.setSpacingBefore(100f);
			    p_car_weekly_price.setIndentationLeft(85f);
			    document1.add(p_car_weekly_price);
	       
			    
			  
			    if(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_FuelStar()).equalsIgnoreCase("6"))
			    {
			    	objEuromarqueCarDetails.setSt_photo(FUEL6);
			    }
			    else if(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_FuelStar()).equalsIgnoreCase("5"))
			    {
			    	objEuromarqueCarDetails.setSt_photo(FUEL5);
			    }
			    else if (EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_FuelStar()).equalsIgnoreCase("4"))
			    {
			    	objEuromarqueCarDetails.setSt_photo(FUEL4);
			    }
			    else if (EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_FuelStar()).equalsIgnoreCase("3"))
			    {
			    	objEuromarqueCarDetails.setSt_photo(FUEL3);
			    }
			    else if (EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_FuelStar()).equalsIgnoreCase("2"))
			    {
			    	objEuromarqueCarDetails.setSt_photo(FUEL2);
			    }
			    else
			    {
			    	objEuromarqueCarDetails.setSt_photo(FUEL1);
			    }
			    	
			    Image fuelimage = Image.getInstance(objEuromarqueCarDetails.getSt_photo()); 
			    PdfTemplate fueltemplate  = writer1.getDirectContent().createTemplate(510, 281);
			    fueltemplate.clip();
			    fueltemplate.newPath();
			    fueltemplate.addImage(fuelimage,618, 0, 0, 81, 0, 0);
			    fuelimage = Image.getInstance(fueltemplate);
			    //setting the cropped image to the object img defined before
			    fuelimage.scaleToFit(618,81);
			    fuelimage.setAbsolutePosition(220f,150f);
			    document1.add(fuelimage);
			    
			    PdfPTable table8 = new PdfPTable(3);
			    table8.setWidthPercentage(100);
				PdfPCell cell28= new PdfPCell();
				PdfPCell cell29= new PdfPCell();
				PdfPCell cell30= new PdfPCell();
			    
				cell28 = getFuelCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_FuelLitrePer100KM()), PdfPCell.ALIGN_CENTER);
				cell28.setPaddingBottom(0f);
				cell28.setPaddingTop(165f);
				cell28.setPaddingRight(0f);
				cell28.setPaddingLeft(50f);
				table8.addCell(cell28);
				
				cell29 = getFuelCell(EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_FuelStar())+" STAR", PdfPCell.ALIGN_CENTER);
				cell29.setPaddingBottom(0f);
				cell29.setPaddingTop(165f);
				cell29.setPaddingRight(30f);
				cell29.setPaddingLeft(30f);
				table8.addCell(cell29);
				
				cell30 = getFuelCell("$"+EuromarqueInformationCardServlet.handleNullValue(objEuromarqueCarDetails.getSt_FuelAnnualCost()), PdfPCell.ALIGN_CENTER);
				cell30.setPaddingBottom(0f);
				cell30.setPaddingTop(165f);
				cell30.setPaddingRight(50f);
				cell30.setPaddingLeft(0f);
				table8.addCell(cell30);
				
				document1.add(table8);
			    
				document1.close();
	  	    
	  	    //next page
	  	    
	  	   
	    
	       
				File file = new File(DESTNEW+objEuromarqueCarDetails.getId()+".pdf");
				file.getParentFile().mkdirs();
				new EuromarqueInformationCardServlet().manipulatePdf(DESTNEW+objEuromarqueCarDetails.getId()+".pdf");
	       
	       
	    
	    
	       
    
	
  
  
        
}

public static PdfPCell getCell(String text, int alignment) {
	Font f_car_details = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//FTR_.pfm", 11);
	f_car_details.setColor(0, 0, 0);
	PdfPCell cell = new PdfPCell(new Phrase(text,f_car_details));
	cell.setPadding(0f);
	cell.setHorizontalAlignment(alignment);
	cell.setBorder(PdfPCell.NO_BORDER);
    return cell;
}

public static PdfPCell getFuelCell(String text, int alignment) {
	Font f_car_details = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//FTR_.pfm", 25);
	f_car_details.setColor(205, 32, 48);
	PdfPCell cell = new PdfPCell(new Phrase(text,f_car_details));
	cell.setPadding(0f);
	cell.setHorizontalAlignment(alignment);
	cell.setBorder(PdfPCell.NO_BORDER);
    return cell;
}

public static PdfPCell getFeaturesCell(String text, int alignment) {
	Font f_car_details = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//FTR_.pfm", 9);
	f_car_details.setColor(0, 0, 0);
	PdfPCell cell = new PdfPCell(new Phrase(text,f_car_details));
	cell.setLeading(2f, 1f);
	cell.setPadding(1f);
	cell.setHorizontalAlignment(alignment);
	cell.setBorder(PdfPCell.NO_BORDER);
    return cell;
}

public static PdfPCell getFeaturesCellSmall(String text, int alignment) {
	Font f_car_details = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//FTR_.pfm", 7);
	f_car_details.setColor(0, 0, 0);
	PdfPCell cell = new PdfPCell(new Phrase(text,f_car_details));
	cell.setLeading(2f, 0);
	cell.setPadding(1f);
	cell.setHorizontalAlignment(alignment);
	cell.setBorder(PdfPCell.NO_BORDER);
    return cell;
}



public void manipulatePdf(String dest) throws IOException {
   PdfDocument pdfDoc = new PdfDocument(new com.itextpdf.kernel.pdf.PdfWriter(dest));
   PdfMerger merger = new PdfMerger(pdfDoc);
   PdfDocument resource = new PdfDocument(new com.itextpdf.kernel.pdf.PdfReader(DEST1));
   PdfDocument cover = new PdfDocument(new com.itextpdf.kernel.pdf.PdfReader(DEST));
   merger.merge(cover, 1, 1);
   merger.merge(resource, 1, resource.getNumberOfPages());
   cover.close();
   resource.close();
   //add function call to load second page contents
   pdfDoc.close();
}

public static String handleNullValue(String dest) throws IOException {
	    if(dest==null)
	    	dest="";
	    
	    return dest;
	}



}
