package com.cmg.motorcycles;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.StringTokenizer;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmg.motorcycles.db.ConnectMotorCycleDB;
import com.cmg.motorcycles.ejb.EuromarqueCarDetails;
import com.cmg.motorcycles.ejb.MotorcycleDetails;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.utils.PdfMerger;
 


// Extend HttpServlet class
public class InformationCardServlet extends HttpServlet {
	   
	    public static final String IMAGE = "M://ZZ-SRUTHY//MotorcyclesInformationCard//images//ICTemplate.jpg";
	    public static final String BIKECAR_PATH = "http://192.168.2.169:8081/InformationCardDynamic/images/";
	    public static final String DEST = "M://ZZ-SRUTHY//MotorcyclesInformationCard//output//test.pdf";
	    
	    public static final String TERMS = "M://ZZ-SRUTHY//MotorcyclesInformationCard//output//SIN.pdf";
	    public static final String DESTNEW= "P://InformationCard//";
	    public static final String RESOURCE = "M://ZZ-SRUTHY//MotorcyclesInformationCard//output//test.pdf";
	    public static String Servleterror ="";
	
	      
   // Method to handle GET method request.
   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException 
      
      {
	   
	  try
	  {
       String stockId= (String) request.getParameter("stockid");
	   ConnectMotorCycleDB objConnectMotorCycleDB = new ConnectMotorCycleDB();
	   
	   MotorcycleDetails objMotorcycleDetails = new MotorcycleDetails();
	   objMotorcycleDetails = objConnectMotorCycleDB.getMotorcycleDetails(stockId);	   
	   if(objMotorcycleDetails.getId() == 0)
	   {
		   Exception e = null;
		   Servleterror = "No vehicle found for this stockNumber";
		   throw new Exception(e);
	   }
	   else if(!(objMotorcycleDetails.motorcycle_manufacturer.equalsIgnoreCase("Bike") || objMotorcycleDetails.motorcycle_manufacturer.equalsIgnoreCase("Ducati") || objMotorcycleDetails.motorcycle_manufacturer.equalsIgnoreCase("Harley Davidson") || objMotorcycleDetails.motorcycle_manufacturer.equalsIgnoreCase("Honda") ||objMotorcycleDetails.motorcycle_manufacturer.equalsIgnoreCase("Indian") || objMotorcycleDetails.motorcycle_manufacturer.equalsIgnoreCase("Kymco") || objMotorcycleDetails.motorcycle_manufacturer.equalsIgnoreCase("MotoGuzzi") || objMotorcycleDetails.motorcycle_manufacturer.equalsIgnoreCase("Piaggio") || objMotorcycleDetails.motorcycle_manufacturer.equalsIgnoreCase("Triumph") ||objMotorcycleDetails.motorcycle_manufacturer.equalsIgnoreCase("Vespa") ||objMotorcycleDetails.motorcycle_manufacturer.equalsIgnoreCase("BMW Bike") ||objMotorcycleDetails.motorcycle_manufacturer.equalsIgnoreCase("Yamaha")))
	   {
		   Exception e = null;
		   Servleterror = "No motorcycle found for this stockNumber";
		   throw new Exception(e);
	   }
	  	   else
	   {
	   objMotorcycleDetails.setMotorcycle_photo(BIKECAR_PATH+objMotorcycleDetails.getId()+"-1.jpg");
	   
	   File file = new File(DEST);
       file.getParentFile().mkdirs();
	   InformationCardServlet.createPdf(DEST,objMotorcycleDetails);
	   response.setContentType("text/html");
	   PrintWriter out = response.getWriter();
	   String title = "Using GET Method to Read Form Data";
	   String st_SuccessMsg = "PDF created : P:\\InformationCard\\"+objMotorcycleDetails.getId()+".pdf";
	   request.getSession().setAttribute("success", st_SuccessMsg);
	   response.sendRedirect("http://192.168.2.169:8081/InformationCardDynamic/index.jsp");
	   }
	  }
	  catch(IOException io)
	  {
		  String error =io.getMessage();
		  request.getSession().setAttribute("error", error);
		  response.sendRedirect("http://192.168.2.169:8081/InformationCardDynamic/index.jsp");
	  }
	  catch(DocumentException de)
	  {
		  String error =de.getMessage();
		  request.getSession().setAttribute("error", error);
		  response.sendRedirect("http://192.168.2.169:8081/InformationCardDynamic/index.jsp"); 	  
	  }
	  catch(Exception e)
	  {	String error = "";
	  if(e.getMessage() == null)
			  error = Servleterror;
		  else
			  error = e.getMessage();
	  
	  if(error.contains("http://192.168.2.169:8081/InformationCardDynamic/images/"))
		  error="No image set for the motorcycle in F2";
	  request.getSession().setAttribute("error", error);
	  response.sendRedirect("http://192.168.2.169:8081/InformationCardDynamic/index.jsp"); 	  
	  }
	  
	  }
   
   

   // Method to handle POST method request.
   public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      doGet(request, response);
   }
   
   public static void createPdf(String dest, MotorcycleDetails objMotorcycleDetails) throws IOException, DocumentException, Exception {
   	
   	
    
    	Font f_bike_type = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//OpenSans-Bold.ttf", 10);
   		f_bike_type.setColor(255, 255, 255);
   		Font f_bike_title = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//OpenSans-Bold.ttf", 20);
   		f_bike_title.setColor(255, 255, 255);
   		Font f_bike_summary = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//OpenSans-Regular.ttf", 7);
   		f_bike_summary.setColor(255, 255, 255);
   		Font f_reduced_bike_summary = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//OpenSans-Regular.ttf", 7);
   		f_reduced_bike_summary.setColor(255, 255, 255);
   		Font f_reduced1_bike_summary = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//OpenSans-Regular.ttf", 6);
   		f_reduced1_bike_summary.setColor(255, 255, 255);
   		Paragraph p_bike_summary = new Paragraph();
   		Document document = new Document();
  		Rectangle pagesize = new Rectangle(510,794);
   		document.setPageSize(pagesize);
   		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
   		document.open();
   		PdfContentByte canvas = writer.getDirectContentUnder();
   		Image image = Image.getInstance(IMAGE);
   		image.setAbsolutePosition(0, 0);
   		image.scaleToFit(510,794);
   		canvas.addImage(image);
   		
   		
   		
   		
       
   
   		document.add( Chunk.NEWLINE );
   		String bike_type = objMotorcycleDetails.getMotorcycle_type();
   		String bike_category = "New";
   		if(bike_type.toLowerCase().contains(bike_category.toLowerCase()))
   			objMotorcycleDetails.setMotorcycle_type("NEW");
   		else
   			objMotorcycleDetails.setMotorcycle_type("USED");
   		Paragraph p_bike_type = new Paragraph("  "+objMotorcycleDetails.getMotorcycle_type(),f_bike_type);
   		
   		String st_bikeTitle_1="", st_bikeTitle_2="";
   		StringTokenizer st = new StringTokenizer(objMotorcycleDetails.getMotorcycle_name());
   		st_bikeTitle_1 = " "+st.nextToken().toUpperCase();
   		while(st.hasMoreTokens())
   		{
   			st_bikeTitle_2 = st_bikeTitle_2 +" "+st.nextToken().toUpperCase();
   			
   		}
   		Paragraph p_bike_title1 = new Paragraph(st_bikeTitle_1,f_bike_title);
   		Paragraph p_bike_title2= new Paragraph(st_bikeTitle_2,f_bike_title);
   		
   		String st_bike_summary = objMotorcycleDetails.getMotorcycle_descrption();
   		int summary_size = st_bike_summary.length();
   		if(summary_size>1100 && summary_size<1500)
   		{
   			p_bike_summary = new Paragraph(st_bike_summary,f_reduced_bike_summary);
   		}
   		else if(summary_size>1500)
   		{
   			p_bike_summary = new Paragraph(st_bike_summary,f_reduced1_bike_summary);
   		}
   		else
   		{
   			p_bike_summary = new Paragraph(st_bike_summary,f_bike_summary);
   		}
   		
   		
   		PdfPTable tableprice = new PdfPTable(1);
        tableprice.setWidthPercentage(100);
        PdfPCell cell= new PdfPCell();
        cell = getCellPrice(objMotorcycleDetails.getMotorcycle_weekly_price(), PdfPCell.ALIGN_RIGHT);
        cell.setPaddingBottom(50f);
        cell.setPaddingTop(172f);
        cell.setPaddingRight(40f);
        cell.setPaddingLeft(5f);
        tableprice.addCell(cell);
        document.add(tableprice);
       
             
       //adding columns in a paragraph
        if(Integer.toString(objMotorcycleDetails.getMotorcycle_price()).equals("0") && objMotorcycleDetails.getMotorcycle_engine_cc() == null )
        {
        	System.out.println("Table not entered");
        	if(summary_size<200)
        	       p_bike_type.setSpacingBefore(-140f);
        	       else
        	       p_bike_type.setSpacingBefore(-240f);
        	       p_bike_summary.setSpacingBefore(10f);
        	       p_bike_type.setIndentationLeft(12);
        	       p_bike_title1.setIndentationLeft(12);
        	       p_bike_title2.setIndentationLeft(12);
        	       p_bike_summary.setIndentationLeft(16);
        	       p_bike_summary.setIndentationRight(150f);
        	       p_bike_title1.setLeading(20f);
        	       p_bike_title2.setLeading(20f);
        	       
        	       if(summary_size>1100)
        	       {
        	    	   System.out.println("Summary > 1100");
        	       p_bike_summary.setLeading(8f);
        	       }
        	       else
        	       {
        	    	   System.out.println("Summary < 1000");
        	       p_bike_summary.setLeading(9f);
        	       }
        	       
        	       document.add(p_bike_type);
        	       document.add(p_bike_title1);
        	       document.add(p_bike_title2);
        	       document.add(p_bike_summary);
        	      
        }
        else
        {
        String engineLitre = objMotorcycleDetails.getMotorcycle_engine_cc();
        float engcc = 0;
        int ecc = 0;
        if(engineLitre==null)
        	engineLitre="";
        else
        {
        engcc = Float.parseFloat(engineLitre);
        ecc = (int)engcc;
        System.out.println(ecc);
        }
       PdfPTable table = new PdfPTable(3);
       table.setWidthPercentage(100);
       PdfPCell cell1= new PdfPCell();
       PdfPCell cell2= new PdfPCell();
       PdfPCell cell3= new PdfPCell();
       if(engineLitre=="")
       cell1 = getCell(engineLitre+"cc", PdfPCell.ALIGN_LEFT);
       else
       cell1 = getCell(ecc+"cc", PdfPCell.ALIGN_LEFT);
       cell1.setPaddingBottom(0f);
       cell1.setPaddingTop(120f);
       cell1.setPaddingRight(0f);
       cell1.setPaddingLeft(45f);
       table.addCell(cell1);
       
        int rounded_odo = Math.round((objMotorcycleDetails.getMotorcycle_odometer_value()+49)/50) * 50;
       cell2 = getCell("<"+Integer.toString(rounded_odo)+"KM", PdfPCell.ALIGN_CENTER);
       if(Integer.toString(rounded_odo).length()<=2)
       {
    	   cell2.setPaddingRight(0f);
           cell2.setPaddingLeft(5f);
       }
       if(Integer.toString(rounded_odo).length()>3)
       {
    	   cell2.setPaddingRight(0f);
           cell2.setPaddingLeft(35f);
       }
       else
       {
    	   cell2.setPaddingRight(0f);
           cell2.setPaddingLeft(15f);
       }
       
       cell2.setPaddingBottom(0f);
       cell2.setPaddingTop(120f);
       
       table.addCell(cell2);
       cell3 =getCell(Integer.toString(objMotorcycleDetails.getMotorcycle_price()), PdfPCell.ALIGN_RIGHT);
       
       if(Integer.toString(objMotorcycleDetails.getMotorcycle_price()).length()<=4)
       {
    	   cell3.setPaddingRight(45f);
           cell3.setPaddingLeft(0f);
       }
       else
       {
       cell3.setPaddingRight(35f);
       cell3.setPaddingLeft(0f);
       }
       cell3.setPaddingBottom(0f);
       cell3.setPaddingTop(120f);
       
       table.addCell(cell3);
       document.add(table);
       if(summary_size<200)
           p_bike_type.setSpacingBefore(-300f);
           else
           p_bike_type.setSpacingBefore(-340f);
           p_bike_summary.setSpacingBefore(10f);
           p_bike_type.setIndentationLeft(12);
           p_bike_title1.setIndentationLeft(12);
           p_bike_title2.setIndentationLeft(12);
           p_bike_summary.setIndentationLeft(16);
           p_bike_summary.setIndentationRight(150f);
           p_bike_title1.setLeading(20f);
           p_bike_title2.setLeading(20f);
           
           if(summary_size>1100 && summary_size<1500)
           p_bike_summary.setLeading(8f);
           else if(summary_size>1500)
           p_bike_summary.setLeading(7f);
           else
           p_bike_summary.setLeading(10f);
           
           document.add(p_bike_type);
           document.add(p_bike_title1);
           document.add(p_bike_title2);
           document.add(p_bike_summary);
        }
       
       
       
       
    
   
       
      //cropping image to fit the size given
       Image img = Image.getInstance(objMotorcycleDetails.getMotorcycle_photo()); 
      // PdfTemplate t = writer.getDirectContent().createTemplate(2128, 1055);
       PdfTemplate t = writer.getDirectContent().createTemplate(510, 281);
       t.clip();
       t.newPath();
       t.addImage(img,510, 0, 0, 281, 0, 0);
       img = Image.getInstance(t);
       //setting the cropped image to the object img defined before
       image.scaleToFit(510,281);
       img.setAbsolutePosition(0f, 60f);
       document.add(img);
              
       document.close();
       
       File file = new File(DESTNEW+objMotorcycleDetails.getId()+".pdf");
       file.getParentFile().mkdirs();
       new InformationCardServlet().manipulatePdf(DESTNEW+objMotorcycleDetails.getId()+".pdf");
       
       
       File deletetempPDF = new File(RESOURCE);

		if(deletetempPDF.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");}
		
      
      
            
   }
   
   public static PdfPCell getCell(String text, int alignment) {
   	Font  f_spec = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//OpenSans-Bold.ttf", 20);
    	f_spec.setColor(255, 255, 255);
       PdfPCell cell = new PdfPCell(new Phrase(text,f_spec));
       cell.setPadding(0f);
       cell.setHorizontalAlignment(alignment);
       cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
   }
   
   public static PdfPCell getCellPrice(String text, int alignment) {
   	Font f_spec = FontFactory.getFont("M://ZZ-SRUTHY//MotorcyclesInformationCard//font//OpenSans-Bold.ttf", 40);
    	f_spec.setColor(255, 255, 255);
       PdfPCell cell = new PdfPCell(new Phrase(text,f_spec));
       cell.setPadding(0f);
       cell.setHorizontalAlignment(alignment);
       cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
   }
   
   public void manipulatePdf(String dest) throws IOException {
       PdfDocument pdfDoc = new PdfDocument(new com.itextpdf.kernel.pdf.PdfWriter(dest));
       PdfMerger merger = new PdfMerger(pdfDoc);
       PdfDocument resource = new PdfDocument(new com.itextpdf.kernel.pdf.PdfReader(TERMS));
       PdfDocument cover = new PdfDocument(new com.itextpdf.kernel.pdf.PdfReader(RESOURCE));
       merger.merge(cover, 1, 1);
       merger.merge(resource, 1, resource.getNumberOfPages());
       cover.close();
       resource.close();
       pdfDoc.close();
   }




}



