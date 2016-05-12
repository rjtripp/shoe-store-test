package com.rosalietripp.demo.sdet.shoestore.test;

import com.rosalietripp.demo.sdet.shoestore.utilities.CrossBrowserUtility;
import com.rosalietripp.demo.sdet.shoestore.pages.ShoeReleasePage;
import com.rosalietripp.demo.sdet.shoestore.utilities.AssertMessage;

import org.junit.Assert;

import org.openqa.selenium.WebDriver;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;



/*
 * Story: Submit email for reminder
 * 
 * As a user of the Shoe store
 * I want to be able to submit my email address 
 * so that I can be reminded of upcoming shoe releases
 * 
 * Acceptance Criteria:
 * 		1) There should be an area to submit email address
 * 		2) On successful submission of a valid email address, user should receive message:	
 * 			Thanks! We will notify you of our new shoes at this email: users email address
 */
public class SubmitEmailAddrForRemindersTest {
	
	// Instance variables
	private WebDriver driver;
	private String browser;
	private ShoeReleasePage objShoeRelease;
	
	// Constants
	private static final String POSITIVE_TEST = "+";
	private static final String NEGATIVE_TEST = "-";		
	
	private static final String REPLY_POS_ACCEPTED = "Thanks! We will notify you of our new shoes at this email: %1s";
	private static final String REPLY_NEG_NO_EMAIL_ADDR = "Please enter an email address";
	private static final String REPLY_NEG_INVALID_EMAIL_ADDR = "Invalid email format. Ex. name@example.com";
	private static final String REPLY_NEG_MISSING_DOMAIN = "Invalid email missing domain. Ex. name@example.com";
	
	/**
	 * This function will execute before each Test tag in testng.xml
	 * @param browser
	 * @throws Exception
	 */
	@BeforeTest
	@Parameters("browser")
	public void setup(String browser) throws Exception{
		this.browser = browser;
		this.driver = CrossBrowserUtility.setupWebDriver(this.browser, CONFIG.WAIT_SECONDS);
	}
	
	/**
	 * This function will execute after each Test tag in testng.xml
	 * @param browser
	 * @throws Exception
	 */
	@AfterTest
	public void teardown() throws Exception{
		this.driver.quit(); 
	}
	
	/*
	 * Provide the data to be used to test submit email address button
	 */
	@DataProvider
	public Object[][] getEmailDataAll() {
		// Fixx: This is a small sampling of test data which isn't conclusive of all tests which are needed.
		// Some more examples of valid/invalid email addresses can be found at: https://en.wikibooks.org/wiki/JavaScript/Best_practices#Email_validation
		return new Object[][]
				{{POSITIVE_TEST,"tripprosalie@gmail.com"}
				,{POSITIVE_TEST,"tripprosalie@gmail.net"}
				,{POSITIVE_TEST,"tripp.rosalie@gmail.com"} 		// case: dot allowed within name
				,{POSITIVE_TEST,"\"tripp rosalie\"@gmail.com"} 	// case: spaces may be quoted
				
				,{NEGATIVE_TEST,"tripprosalie"}			// case: missing @... text
				,{NEGATIVE_TEST,"tripprosalie@"}		// case: missing required text after char @
				,{NEGATIVE_TEST,"tripprosalie@gmail"}	// case: missing text (ex: .com, .net, etc.)
				,{NEGATIVE_TEST,"tripprosalie@gmail."}	// case: missing text after the dot (ex: com, net, etc)
				,{NEGATIVE_TEST,"tripp rosalie@gmail.com"} // case: imbedded space
				,{NEGATIVE_TEST,"tripprosalie@gmail.com.extra"} // case: extra text on end (.extra)
				,{NEGATIVE_TEST,"@gmail.com"} 				// case: missing text before @gmail.com
				,{NEGATIVE_TEST,"tripprosalie.@gmail.com."} // case: extraneous . char before @gmail.com
				,{NEGATIVE_TEST,".tripprosalie@gmail.com."} // case: extraneous . char before @gmail.com				
				,{NEGATIVE_TEST,"tripprosalie.@gmail..com."} // case: extraneous . char within @gmail.com
				,{NEGATIVE_TEST,"tripprosalie\\@gmail.com."} // case: \ char prior to @gmail.com
				
				,{NEGATIVE_TEST,""} // case: Blank email address
				};
	}
	
	/*
	 * This method will be reused by each test to implement principal: Don't Repeat Yourself (DRY)
	 */
	@Test(dataProvider="getEmailDataAll")
	public void testSubmitEmailReminder(String testType,String emailAddr) throws Exception {
		
		String eMsg;
		System.out.println(String.format("Running %1s test for submitting email address '%2s' using %3s",testType,emailAddr,this.browser));
				
		// Connect to URL
		driver.get(CONFIG.BASE_URL + "/");
		
		// Create Page object
		this.objShoeRelease = new ShoeReleasePage(this.driver);
		  
		// Submit email
		boolean wasSubmitted = this.objShoeRelease.submitEmailAddr(emailAddr);
		  
		eMsg = AssertMessage.MISSING_EMAIL_REMIND_INPUT_FIELD;
		Assert.assertTrue(eMsg,wasSubmitted);
		  		    
		// Verify reply
		String receivedReply = this.objShoeRelease.getSubmitEmailReply();
		String expectedReply;
		if (testType.equals(POSITIVE_TEST)) {
			expectedReply = String.format(REPLY_POS_ACCEPTED, emailAddr);
		} else if (emailAddr.trim().equals("")) {
			expectedReply = REPLY_NEG_NO_EMAIL_ADDR;
		} else if (emailAddr.trim().endsWith("@")) {
			expectedReply = REPLY_NEG_MISSING_DOMAIN;
		} else {
			expectedReply = REPLY_NEG_INVALID_EMAIL_ADDR;				
		}
		  
		eMsg = AssertMessage.MISSING_EMAIL_REMINDER_REPLY;
		Assert.assertNotNull(eMsg, receivedReply);
		  
		eMsg = String.format(AssertMessage.INVALID_EMAIL_REMINDER_REPLY, expectedReply, receivedReply);
		Assert.assertEquals(eMsg, expectedReply,receivedReply);
		    
	  }
	
}
