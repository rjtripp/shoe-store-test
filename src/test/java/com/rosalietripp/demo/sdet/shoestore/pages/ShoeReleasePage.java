package com.rosalietripp.demo.sdet.shoestore.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.rosalietripp.demo.sdet.shoestore.utilities.WebElementUtility;

/**
 * This class is used when testing for interacting with web pages that contain:
 * 1) monthly links to shoe releases 
 * 2) submit email address to receive reminders for shoe releases
 * @author Rosalie Tripp
 *
 */
public class ShoeReleasePage {
	// Instance variables
	private WebDriver driver;
	
	// WebElements are identified by @FindBy annotation
	// 		Preferred selector order : id > name >links text> css > xpath
	
	// The following web elements are used when submitting user's email address for getting reminders
	@FindBy(id="remind_email_input")
	private WebElement fldEmailAddr;
	
	@FindBy(id="remind_email_submit")
	private WebElement btnSubmitEmailAddr;
	
	// The following web elements are used when displaying the shoe releases
	@FindBy(id="shoe_list")
	private WebElement listShoes;
	
	@FindBy(className="shoe_result")
	private List<WebElement> shoeResultList;
	
	@FindBy(className="shoe_price")
	private WebElement shoePrice;
	
	@FindBy(className="shoe_description")
	private WebElement shoeDescription;
	
	@FindBy(className="shoe_image")
	private WebElement shoeImage;
	
	@FindBy(className="navbar-nav")
	private WebElement shoeNavLinks;
	
	
	// Constructor
	public ShoeReleasePage(WebDriver driver) {
		this.driver = driver;
		//This initElements method will create all WebElements
		PageFactory.initElements(this.driver, this);
	}
	

	/**
	 * This method will click the link if it exists
	 * @param linkStr
	 * @return Returns false when link not found
	 */
	public boolean clickLink(String linkStr) {
		
		if (WebElementUtility.isElementPresent(this.shoeNavLinks, By.linkText(linkStr))) {
			WebElement linkWE = this.shoeNavLinks.findElement(By.linkText(linkStr));
			linkWE.click(); 
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * This method is used for getting the number of shoe results 
	 * @return
	 */
	public int getShoeResultsNumber() {
		if (this.shoeResultList == null) return 0;
		return this.shoeResultList.size();
	}
	
	/**
	 * This method is used for getting the shoe description for one of the items in the shoe results.
	 * Returns null when not found.
	 * @param indx
	 * @return
	 */
	public String getShoeResultDesc(int indx) {
		if (shoeResultList.size() == 0) {
			return null;
		} else if (indx > shoeResultList.size()) {
			return null;
		}		
		WebElement shoeDescWE = shoeResultList.get(indx).findElement(By.className("shoe_description"));
		if (shoeDescWE == null) {
			return null;
		}
    	return shoeDescWE.getText();
	}
	
	/**
	 * This method is used for getting the shoe price for one of the items in the shoe results.
	 * Returns null when not found.
	 * @param indx
	 * @return
	 */
	public String getShoeResultPrice(int indx) {
		if (shoeResultList.size() == 0) {
			return null;
		} else if (indx > shoeResultList.size()) {
			return null;
		}		
		WebElement shoePriceWE = shoeResultList.get(indx).findElement(By.className("shoe_price"));
		if (shoePriceWE == null) {
			return null;
		}
    	return shoePriceWE.getText();
	}
	
	/**
	 * This method is used for getting the shoe image source for one of the items in the shoe results.
	 * Returns null when not found.
	 * @param indx
	 * @return
	 */
	public String getShoeResultImage(int indx) {
		if (shoeResultList.size() == 0) {
			return null;
		} else if (indx > shoeResultList.size()) {
			return null;
		}	
		WebElement shoeImageWE = shoeResultList.get(indx).findElement(By.className("shoe_image"));
		if (shoeImageWE == null) {
			return null;
		}
		
    	return shoeImageWE.findElement(By.tagName("img")).getAttribute("src");
    	
	}
	
	
	/**
	 * Fill in email address in text box
	 * @param strEmailAddr
	 */
	public void setEmailAddr(String strEmailAddr) {
		this.fldEmailAddr.clear();
		this.fldEmailAddr.sendKeys(strEmailAddr);
	}

	/**
	 * This method will return the email address from text box
	 * @return
	 */
	public String getEmailAddr() {
		return this.fldEmailAddr.getText();
	}
	
	/**
	 * This method will submit the user's email address so user can receive email reminders of new shoes as they
	 * are released. Returns boolean to indicate if input box and button were found or not.
	 * @param strEmailAddr
	 */
	public boolean submitEmailAddr(String strEmailAddr) {
		// Verify field on page
		if (this.fldEmailAddr == null) return false;
		// Fill required input field 
		this.setEmailAddr(strEmailAddr);
		
		// Verify submit button on page
		if (this.btnSubmitEmailAddr == null) return false;
		// Click Submit button
		this.clickSubmitEmailAddr();
		return true;
	}
	
	/**
	 * This method will click the submit email address button
	 */
	public void clickSubmitEmailAddr() {
		this.btnSubmitEmailAddr.click();
	}
	
	/**
	 * This method will return the reply message from when user clicked the submit email address button.
	 * Returns null when message not found.
	 * @return
	 */
	public String getSubmitEmailReply() {
		
		// Return success message if it exists
		if (WebElementUtility.isElementPresent(this.driver, By.className("flash_success"))) {
			return this.driver.findElement(By.className("flash_success")).getText();
		}
		
		// Return alert message if it exists
		if (WebElementUtility.isElementPresent(this.driver, By.className("alert_danger"))) {
			return this.driver.findElement(By.className("alert_danger")).getText();
		}
		
		// Return null when no message found
		return null;
	
	}
	
}
