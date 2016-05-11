package com.rosalietripp.demo.sdet.shoestore.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class CustomListener extends TestListenerAdapter{
    private int m_count = 0;
	 
    @Override
    public void onTestFailure(ITestResult tr) {
        log(tr,"--Test method failed\n");
    }
	 
    @Override
    public void onTestSkipped(ITestResult tr) {
        log(tr,"--Test method skipped\n");
    }
	 
    @Override
    public void onTestSuccess(ITestResult tr) {
        log(tr,"--Test method success\n");
    }
	 
    private void log(ITestResult tr,String statusStr) {
    	// Build test status string
    	StringBuffer sb = new StringBuffer();
    	sb.append("*** ");
    	sb.append(getCurrentTime());
    	sb.append(" ");
    	sb.append(tr.getName());
    	sb.append(" Parameters [");
    	sb.append(tr.getParameters().toString()); // Fix: Fix later if there is time; low priority
    	sb.append("] ");
    	sb.append(statusStr);
    	// Write status
        System.out.print(sb.toString());
        // Add space to breakup messages when a lot of messages are written
        if (++m_count % 40 == 0) {
	    System.out.println("");
        }
     }
    
    private String getCurrentTime() {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = new Date();
    	return dateFormat.format(date);
    }

}
