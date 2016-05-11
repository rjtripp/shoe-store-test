package com.rosalietripp.demo.sdet.shoestore.test;

import com.rosalietripp.demo.sdet.shoestore.pages.ShoeReleasePage;
import com.rosalietripp.demo.sdet.shoestore.utilities.AssertMessage;
import com.rosalietripp.demo.sdet.shoestore.utilities.CrossBrowserUtility;

import org.junit.Assert;

import org.openqa.selenium.WebDriver;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/*
 * Story: Monthly display of new releases
 * 
 * As a user of the Shoe store
 * I want to be able to visit a link for each month
 * so that I can see the shoes being released each month
 * 
 * Acceptance Criteria:
 * 		1) Month should display a small blur of each shoe
 * 		2) Month should display an image of each shoe being released
 * 		3) Each shoe should have a suggested price pricing
 */
public class MonthlyShoesNewlyReleasedTest {
	// Instance variables
	private WebDriver driver;
	private String browser;
	private ShoeReleasePage objShoeRelease;
	
	/**
	 * This method will execute before each Test tag in testng.xml
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
	 * This method will execute after each Test tag in testng.xml
	 * @param browser
	 * @throws Exception
	 */
	@AfterTest
	public void teardown() throws Exception{
		this.driver.quit(); 
	}
	
	/**
	 * This method will execute each of the tests needed for testing the links for each of the 12 monthly new shoe releases
	 * @param mm		Integer representation of a month (1 - 12)
	 * @param linkStr	String representation of a month ("January", "February", etc.)
	 */
	@Test(groups="linkAllMonths",dataProvider="getMonthDataAll")
	public void testDisplayMonth(int mm, String linkStr) throws Exception {
		System.out.println("Running test for month " + mm + " for testing link " + linkStr + " using " + this.browser);
		testDisplayShoes(linkStr);
	}
	
	/**
	 * This method will execute test for testing the January link. 
	 * @param mm		Integer representation of a month (1 - 12)
	 * @param linkStr	String representation of a month ("January", "February", etc.)
	 */
	@Test(groups="linkJanuary", dataProvider="getMonthDataJanuary")
	public void testDisplayJanuary(int mm, String linkStr) throws Exception {
		System.out.println("Running test for month " + mm + " for testing link " + linkStr + " using " + this.browser);
		testDisplayShoes(linkStr);
	}
		
	/*
	 * Provide the data to be used to test each link to a month's new shoe release
	 */
	@DataProvider
	public Object[][] getMonthDataAll() {
		return new Object[][]
				{{1,"January"},{2,"February"},{3,"March"}
				,{4,"April"},{5,"May"},{6,"June"}
				,{7,"July"},{8,"August"},{9,"September"}
				,{10,"October"},{11,"November"},{12,"December"}
				};
	}
	
	/*
	 * Provide the data to be used to test link to January's new shoe release
	 */
	@DataProvider
	public Object[][] getMonthDataJanuary() {
		return new Object[][]{{1,"January"}};
	}
		
	
	/**
	 * This method will be used when testing a link which shows shoes released for a particular month. 
	 * @param linkStr String representation of a month ("January", "February", etc.)
	 */
	public void testDisplayShoes(String linkStr) throws Exception {
	 
		String eMsg;
		
		// Connect to URL
	    this.driver.get(CONFIG.BASE_URL + "/"); 
		
	    // Create Page object
	    this.objShoeRelease = new ShoeReleasePage(this.driver);
	        
	    // Click on link
	    eMsg = String.format(AssertMessage.MISSING_LINK_TO_MONTH,linkStr);
	    Assert.assertTrue(eMsg,this.objShoeRelease.clickLink(linkStr));
	        
	    // Verify title of page just loaded
	    eMsg = String.format(AssertMessage.INVALID_LINK_TO_MONTH,linkStr);
	    Assert.assertTrue(this.driver.getTitle().toLowerCase().contains("shoe store: " + linkStr.toLowerCase()));
	    
	    // Verify shoe results found on page
	    int shoeResultsNumber = this.objShoeRelease.getShoeResultsNumber();
	    eMsg = String.format(AssertMessage.MISSING_SHOE_RESULTS,linkStr);
	    Assert.assertFalse(eMsg,shoeResultsNumber==0);
    
	    // Verify content contains:
	    // 		Description of shoe
	    // 		Price of shoe	    
	    // 		Image of shoe
	    for (int i = 0; i < shoeResultsNumber;i++) {
	    	
	    	// Acceptance Criteria: Verify Description
	    	String shoeDescStr = this.objShoeRelease.getShoeResultDesc(i);
	    	eMsg = String.format(AssertMessage.MISSING_SHOE_DESC_IN_MONTH,i+1,linkStr);	    
	    	Assert.assertNotNull(eMsg,shoeDescStr);
	    	Assert.assertFalse(eMsg,shoeDescStr.trim().equals(""));
    	
	    	// Acceptance Criteria: Verify Price
	    	String shoePriceStr = this.objShoeRelease.getShoeResultPrice(i);
	    	eMsg = String.format(AssertMessage.MISSING_SHOE_PRICE_IN_MONTH,i+1,linkStr);
	    	Assert.assertNotNull(eMsg,shoePriceStr);    
	    	Assert.assertFalse(eMsg,shoePriceStr.trim().equals(""));
    	
	    	// Acceptance Criteria: Verify Image
	    	String shoeImageStr = this.objShoeRelease.getShoeResultImage(i);
	    	eMsg = String.format(AssertMessage.MISSING_SHOE_IMAGE_IN_MONTH,i+1,linkStr);
	    	Assert.assertNotNull(eMsg,shoeImageStr);	    	
	    	Assert.assertFalse(eMsg,shoeImageStr.trim().equals(""));
    		    	
	    }
  }
	
}
