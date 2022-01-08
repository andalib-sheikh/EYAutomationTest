package com.eytest.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtil 
{
	private WebDriver driver;
	
	public ElementUtil(WebDriver driver)
	{
		this.driver=driver;
	}
	
	public WebElement getElement(By locator)
	{
		WebElement element=null;
		try
		{
			element=driver.findElement(locator);
		}
		catch(Exception e)
		{
			System.out.println("Element could not be created.."+locator);
		}
		return element;
	}
	
	 //drop down utils
	public void doSelectValuesByVisibleText(WebElement el, String value)
	{
		Select select=new Select(el);
		select.selectByVisibleText(value);
	}
	
	public void doSelectValuesByIndex(WebElement el, int index)
	{
		Select select=new Select(el);
		select.selectByIndex(index);
	}
	
	public void doSelectValuesByVisibleValue(WebElement el, String value)
	{
		Select select=new Select(el);
		select.selectByValue(value);
	}
	
	public List<String> getDropDownOptionsValues(By locator)
	{
		List<String> optionsList=new ArrayList<String>();
		Select select=new Select(getElement(locator));
		List<WebElement> dropList=select.getOptions();
		for(int i=0;i<dropList.size();i++)
		{
			String text=dropList.get(i).getText();
			optionsList.add(text);
		}
		return optionsList;
	}
		
	public void selectValuesFromDropDown(By locator, String value)
	{
		List<WebElement> list=driver.findElements(locator);
		int flag=0;
		for(int i=0; i<list.size();i++)
		{
			String text=list.get(i).getText();
			if(text.contains(value))
			{
				list.get(i).click();
				flag=1;
				break;
			}
		}
		if(flag==0)
			throw new NoSuchElementException("Element Not Found");		
	}
	
	
	// wait utils

	public WebElement waitForElementPresent(By locator, long timeOut)
	{
		WebDriverWait wait=new WebDriverWait(driver,timeOut);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public WebElement waitForElementToBeClickable(By locator, long timeOut)
	{
		WebDriverWait wait=new WebDriverWait(driver,timeOut);
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	public WebElement waitForElementToBeClickable(WebElement element, long timeOut)
	{
		WebDriverWait wait=new WebDriverWait(driver,timeOut);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public WebElement waitForElementToBeVisible(WebElement element)
	{
		WebDriverWait wait=new WebDriverWait(driver,TimeOut.EXPLICIT_WAIT);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public List<WebElement> visibilityOfAllElements(By locator, long timeOut)
	{
		WebDriverWait wait=new WebDriverWait(driver,timeOut);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	public void clickWhenReady(WebElement el)
	{
		WebDriverWait wait=new WebDriverWait(driver,TimeOut.EXPLICIT_WAIT);
		WebElement element=wait.until(ExpectedConditions.elementToBeClickable(el));
		element.click();
	}
	
	public Alert waitForAlertPresent(int timeOut)
	{
		WebDriverWait wait=new WebDriverWait(driver,timeOut);
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public boolean isElementDisplayed(By locator, long timeout)
	{
		WebElement element=null;
		boolean flag=false;
		for(long i=0;i<timeout;i++)
		{
			try
			{
				element=driver.findElement(locator);
				flag=element.isDisplayed();
				break;
			}
			catch(Exception e) 
			{
				System.out.println("Waiting for element to be present on the page "+i+" secs");
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e1) {}
			}
		}
		return flag;
	}
}
