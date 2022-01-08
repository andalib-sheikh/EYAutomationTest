package com.eytest.pages;

import java.sql.Timestamp;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;

import com.eytest.base.BasePage;
import com.eytest.utils.ElementUtil;

public class CareerPage 
{
	
	ElementUtil elementUtil;
	public String text;
	public Timestamp timestamp, startTime, endTime;
	public String testName="";
	JavascriptExecutor js;
	BasePage baseCareer;
	
	public CareerPage(BasePage baseCareer) 
	{
		this.baseCareer=baseCareer;
		PageFactory.initElements(baseCareer.driver, this);
		elementUtil=new ElementUtil(baseCareer.driver);
		js=(JavascriptExecutor)baseCareer.driver;
	}
	
	public String launchPage(String Test)
	{
		baseCareer.launchPageCounter++;
		testName="Launch Page";
		baseCareer.initVars(testName);
		//Step 1
		try
		{
			timestamp=new Timestamp(System.currentTimeMillis());
			startTime=timestamp;
			baseCareer.pass(testName, baseCareer.launchPageCounter, baseCareer.docER, baseCareer.fontER);
		}
		catch(Exception e)
		{
			System.out.println("Exception in "+testName+" test case at Step "+(baseCareer.tempIndex+1)+". Details: "+e.getMessage());
			e.printStackTrace();
			timestamp=new Timestamp(System.currentTimeMillis());
			startTime=timestamp;
			return baseCareer.fail(testName, baseCareer.launchPageCounter, baseCareer.docER, baseCareer.fontER);
		}
		text="Success";
		return text;
	}
}
