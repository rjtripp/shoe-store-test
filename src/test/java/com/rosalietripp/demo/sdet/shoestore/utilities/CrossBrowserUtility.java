package com.rosalietripp.demo.sdet.shoestore.utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class CrossBrowserUtility {

	public static WebDriver setupWebDriver(String browser,int waitSeconds) throws Exception{
		WebDriver driver = null;
		
		//Check if parameter passed from TestNG is 'firefox'
		if(browser.equalsIgnoreCase("firefox")){
		//create firefox instance
			driver = new FirefoxDriver();
		}
		//Check if parameter passed as 'chrome'
		else if(browser.equalsIgnoreCase("chrome")){
			//set path to chromedriver.exe 
			//You may need to download it from https://sites.google.com/a/chromium.org/chromedriver/downloads
			System.setProperty("webdriver.chrome.driver",".\\chromedriver.exe");
			//create chrome instance
			driver = new ChromeDriver();
		}
		//Check if parameter passed as 'IE'
		else if(browser.equalsIgnoreCase("ie")){
			//set path to IE.exe
			//You man need to download it from https://sites.google.com/a/chromium.org/chromedriver/downloads
			System.setProperty("webdriver.ie.driver",".\\IEDriverServer.exe");
			//create IE instance
			driver = new InternetExplorerDriver();
		}
		else{
			//If no browser passed throw exception
			throw new Exception("Browser is not correct");
		}
		
		
		driver.manage().timeouts().implicitlyWait(waitSeconds, TimeUnit.SECONDS);
		
		// Return WebDriver object
		return driver;
	}
}
