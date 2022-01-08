package com.eytest.testcases;

import java.sql.Timestamp;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.eytest.base.BasePage;
import com.eytest.pages.CareerPage;
import com.eytest.utils.ReadExcel;

public class CareerPageTests 
{
	CareerPage careerPage;
	String text="";
	BasePage baseCareer;

	public CareerPageTests()
	{
		baseCareer=new BasePage("Career");
	}

	@BeforeClass
	public void setup() throws Exception
	{
		try
		{
			Thread.sleep(3000);
			baseCareer.initialization("Career");
			baseCareer.scriptStartTime=new Timestamp(System.currentTimeMillis());
			careerPage=new CareerPage(baseCareer);
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	@AfterClass
	public void tearDown()
	{
		baseCareer.scriptEndTime=new Timestamp(System.currentTimeMillis());
		try
		{
			Thread.sleep(1000);
			baseCareer.docER.close();
			baseCareer.writerER.close();
			Thread.sleep(1000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		baseCareer.docTSR.add(baseCareer.tableTSR);
		baseCareer.pdfCreate.createPDF(baseCareer);
		baseCareer.docTSR.close();
		baseCareer.writerTSR.close();
		baseCareer.driver.quit();
	}

	@AfterSuite
	public void mergePdfs()
	{
		baseCareer.pdfMerge();
	}

	public void performAfter()
	{
		if(text.equals("Success"))
		{
			baseCareer.scriptsPassed++;
			System.out.println(careerPage.testName+" test case passed!");
		}
		else
		{
			baseCareer.scriptFailed++;
			System.out.println(careerPage.testName+" test case failed!");	
		}
		baseCareer.pdfCreate.createPDF(careerPage.testName,careerPage.startTime+"",careerPage.timestamp+"",baseCareer,"Career");
	}
	
	@Test(priority=1,dataProvider="getLaunchPageTestData")
	public void launchPage(String Test)
	{
		baseCareer.totalScripts++;
		try
		{
			//baseCareer.driver.navigate().refresh();
			text=careerPage.launchPage(Test);
			performAfter();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	@DataProvider
	public static Object[][] getLaunchPageTestData()
	{
		Object data[][]=ReadExcel.getTestData("LaunchPage");
		return data;
	}
}