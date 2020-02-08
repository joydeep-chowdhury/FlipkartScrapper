package com.wipro.joydeep.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.wipro.joydeep.exceptions.CustomExceptions;

@Service
public class FlipkartService extends GenericService {
	// Added to track changes 
	private WebDriver webdriver;

	public List<Map<String, String>> performNewAction(String name) {
		
		webdriver = new ChromeDriver();
		webdriver.manage().deleteAllCookies();
		 webdriver.manage().window().maximize();
		webdriver.manage().timeouts().pageLoadTimeout(pageTimeOut, TimeUnit.SECONDS);
		webdriver.navigate().to(navigationUrl);
		wait(webdriver);
		WebElement webelement = webdriver.findElement(By.xpath("/html/body/div[2]/div/div/button"));
		webelement.click();
		WebElement flipkartSearchElement = webdriver
				.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div[1]/div[2]/div[2]/form/div/div/input"));
		flipkartSearchElement.sendKeys(name);
		flipkartSearchElement.sendKeys(Keys.ENTER);
		String parsingUrl = webdriver.getCurrentUrl();
		List<Map<String, String>> items = new ArrayList<Map<String, String>>();
		System.out.println(parsingUrl);
		try {
			Document doc = Jsoup.connect(parsingUrl).get();
			//Document doc = Jsoup.parse(webdriver.getPageSource());
			Elements elements = doc.select("div._3O0U0u");
			System.out.println(elements.size()+"hi");
			
			elements.forEach(elem -> {
				Map<String, String> itemDetailsMap = new HashMap<String, String>();
				String title = elem.select("div._3wU53n").get(0).text();
				itemDetailsMap.put("Product Name", title);
				Element reviewRatingSeperator = elem.select("span._1VpSqZ").get(0);
				Element ratingElement = reviewRatingSeperator.previousElementSibling();
				Element reviewElement = reviewRatingSeperator.nextElementSibling();
				itemDetailsMap.put("No of ratings", ratingElement.text());
				itemDetailsMap.put("No of reviews", reviewElement.text());
				Elements priceColl = elem.select("div._1uv9Cb");
				priceColl.forEach(pc -> {
					Elements finalPriceElements = pc.select("div._1vC4OE");
					if (finalPriceElements.size() == 0) {

					} else {
						itemDetailsMap.put("Price", finalPriceElements.get(0).text());
					}
					Elements originalPriceElements = pc.select("div._3auQ3N");
					if (originalPriceElements.size() == 0) {

					} else {
						itemDetailsMap.put("Original Price", originalPriceElements.get(0).text());
					}
					Elements discountPercentageElements = pc.select("div.VGWI6T");
					if (discountPercentageElements.size() == 0) {

					} else {
						itemDetailsMap.put("Discount Details", discountPercentageElements.get(0).text());
					}
				});

				Elements list = elem.select("ul.vFw0gD");
				itemDetailsMap.put("ROM details", list.get(0).children().get(0).text());
				itemDetailsMap.put("Screen Details", list.get(0).children().get(1).text());
				itemDetailsMap.put("Camera Details", list.get(0).children().get(2).text());
				itemDetailsMap.put("Process Details", list.get(0).children().get(3).text());
				itemDetailsMap.put("Waranty Details", list.get(0).children().get(4).text());
				items.add(itemDetailsMap);
			});

//		} catch (IOException ie) {
//			System.out.println("hi");
//			new CustomExceptions("Unable to parse url");
//			return CompletableFuture.completedFuture(null);
			return items;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			new CustomExceptions("Unable to parse url");
			e.printStackTrace();
			return null;
		} 
		
	}
	
	@Async
	public CompletableFuture<List<Map<String, String>>> performAction(String name) {
		WebDriver webdriver;
		webdriver = new ChromeDriver();
		webdriver.manage().deleteAllCookies();
		 webdriver.manage().window().maximize();
		webdriver.manage().timeouts().pageLoadTimeout(pageTimeOut, TimeUnit.SECONDS);
		webdriver.navigate().to(navigationUrl);
		wait(webdriver);
		WebElement webelement = webdriver.findElement(By.xpath("/html/body/div[2]/div/div/button"));
		webelement.click();
		WebElement flipkartSearchElement = webdriver
				.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div[1]/div[2]/div[2]/form/div/div/input"));
		flipkartSearchElement.sendKeys(name);
		flipkartSearchElement.sendKeys(Keys.ENTER);
		String parsingUrl = webdriver.getCurrentUrl();
		List<Map<String, String>> items = new ArrayList<Map<String, String>>();
		try {
			Document doc = Jsoup.connect(parsingUrl).get();
			//Document doc = Jsoup.parse(webdriver.getPageSource());
			FileWriter f=new FileWriter(new File("test.html"));
			f.append(doc.html());
			f.close();
			Elements elements = doc.select("div._3O0U0u");
			System.out.println(elements.size());
			
			elements.forEach(elem -> {
				Map<String, String> itemDetailsMap = new HashMap<String, String>();
				String title = elem.select("div._3wU53n").get(0).text();
				itemDetailsMap.put("Product Name", title);
				Elements test=elem.select("span._1VpSqZ");
				if(test.size()>0)
				{
					Element reviewRatingSeperator = elem.select("span._1VpSqZ").get(0);
					Element ratingElement = reviewRatingSeperator.previousElementSibling();
					Element reviewElement = reviewRatingSeperator.nextElementSibling();
					itemDetailsMap.put("No of ratings", ratingElement.text());
					itemDetailsMap.put("No of reviews", reviewElement.text());
				}
				Elements priceColl = elem.select("div._1uv9Cb");
				priceColl.forEach(pc -> {
					Elements finalPriceElements = pc.select("div._1vC4OE");
					if (finalPriceElements.size() == 0) {

					} else {
						itemDetailsMap.put("Price", finalPriceElements.get(0).text());
					}
					Elements originalPriceElements = pc.select("div._3auQ3N");
					if (originalPriceElements.size() == 0) {

					} else {
						itemDetailsMap.put("Original Price", originalPriceElements.get(0).text());
					}
					Elements discountPercentageElements = pc.select("div.VGWI6T");
					if (discountPercentageElements.size() == 0) {

					} else {
						itemDetailsMap.put("Discount Details", discountPercentageElements.get(0).text());
					}
				});

				Elements list = elem.select("ul.vFw0gD");
				itemDetailsMap.put("ROM details", list.get(0).children().get(0).text());
				itemDetailsMap.put("Screen Details", list.get(0).children().get(1).text());
				itemDetailsMap.put("Camera Details", list.get(0).children().get(2).text());
				itemDetailsMap.put("Process Details", list.get(0).children().get(3).text());
				itemDetailsMap.put("Waranty Details", list.get(0).children().get(4).text());
				items.add(itemDetailsMap);
			});

//		} catch (IOException ie) {
//			System.out.println("hi");
//			new CustomExceptions("Unable to parse url");
//			return CompletableFuture.completedFuture(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			webdriver.close();
		}
		return CompletableFuture.completedFuture(items);
	}

}
