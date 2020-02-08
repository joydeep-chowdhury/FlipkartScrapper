package com.wipro.joydeep.service;

import javax.annotation.PostConstruct;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;



public abstract class GenericService 
  {
	// Added to track changes
	  @Value("${chrome-driver.path}")
      protected String driverPath;
	  @Value("${chrome-driver.page-timeout}")
      protected int pageTimeOut;
	  @Value("${url.flipkart}")
	  protected String navigationUrl;
	  @Value("${driver.type}")
	  protected String driverType;
	  protected static final Logger LOGGER = LoggerFactory.getLogger(GenericService.class);

	/*
	 * protected static DesiredCapabilities CAPABILITIES; protected ChromeOptions
	 * options;
	 */
	  @PostConstruct
	  private void initialize()
	  {
		  System.setProperty(driverType, driverPath);
		/*
		 * CAPABILITIES=DesiredCapabilities.chrome(); options=new ChromeOptions();
		 * options.addArguments("start-maximized");
		 * CAPABILITIES.setCapability(ChromeOptions.CAPABILITY, options);
		 */
		  
	  }
	  
	  protected boolean wait(WebDriver driver) {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			ExpectedCondition<Boolean> jQueryLoad = (d) -> {
				try {
					return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					LOGGER.info("Jquery not present");
					return true;
				}
			};
			ExpectedCondition<Boolean> jsLoad = d -> ((JavascriptExecutor) d).executeScript("return document.readyState")
					.toString().equals("complete");
			return wait.until(jQueryLoad) && wait.until(jsLoad);
		}
	  
	  
	  
  }
