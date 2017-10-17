package com.cmg.motorcycles;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
  
import javax.servlet.*;
import javax.servlet.http.*;
  
public class SchedulerServlet extends HttpServlet {
     
    Timer timer = null;
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        System.out.println("[OrderChecker] loaded");
    }
     
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
     
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
     
    
    public String getServletInfo() {
        return "Order Monitor";
    }
  
    // Make sure we cancel the timer if the servlet is terminated
    public void destroy() {
        timer.cancel();
        super.destroy();
    }
  
     
    public void init() throws ServletException {
        System.out.println("[OrderChecker] init");        
         
        long executionPeriod = 1000 * 60 * 60 * 24;
         
        // 10 seconds for testing.
        //executionPeriod = 1000 * 60;
         
        timer = new Timer();
        Calendar date = Calendar.getInstance();
         
        // set date to today
        date.setTime(new Date());
        date.add(Calendar.HOUR,10);
        //date.set(Calendar.DAY_OF_WEEK,                Calendar.SUNDAY                );
        date.set(Calendar.HOUR, 10);
        date.set(Calendar.MINUTE, 00);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        // Schedule to run every day 
        timer.scheduleAtFixedRate(
                new ReportGenerator(),
                date.getTime(),
                executionPeriod
                );
    }
     
     
    class ReportGenerator extends TimerTask {
         
        public void run() {
            System.out.println("[OrderChecker] Ran @ " + new Date());
             
        }
         
    }
}

