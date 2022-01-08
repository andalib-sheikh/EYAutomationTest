package com.eytest.base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.eytest.utils.PDFCreate;
import com.eytest.utils.ReadExcel;
import com.eytest.utils.TakeScreenshot;
import com.eytest.utils.TimeOut;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import io.netty.util.Timeout;

public class BasePage 
{
	public WebDriver driver;
	public Properties prop;
	public String platformName="";
	public String browserName="";
	public String browserVersion="";
	public Timestamp scriptStartTime, scriptEndTime;
	public int scriptsPassed=0, scriptFailed=0,totalScripts=0;
	public int tempIndex=0, totalSteps=0,stepsPassed=0,stepsFailed=0;
	public Object[][] data=null;
	public PDFCreate pdfCreate;
	public Document docER, docTSR;
	public Font fontER, fontTSR;
	public PdfWriter writerER, writerTSR;
	public PdfPTable tableTSR;
	public PdfPCell cellTSR;
	public int TSRFlag=0;
	public static int launchPageCounter=0;

	public BasePage(String module)
	{
		try
		{
			prop=new Properties();
			FileInputStream fs = new FileInputStream("./src/main/java/com/eytest/config/config.properties");
			prop.load(fs);
			fontER=new Font(Font.TIMES_ROMAN,16, Font.UNDERLINE);
			docER=new Document();
			writerER=PdfWriter.getInstance(docER, new FileOutputStream(prop.getProperty("pdfFilePath"+module)));
			docER.open();
			Paragraph para =  new Paragraph("EY Test Execution Report- Module: "+module, fontER);
			para.setAlignment(1);
			docER.add(para);
			fontER=new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			para=new Paragraph("\n\n");
			docER.add(para);
			if(TSRFlag==0)
			{
				TSRFlag=1;
				fontTSR=new Font(Font.TIMES_ROMAN,16, Font.UNDERLINE);
				docTSR=new Document();
				try
				{
					writerTSR=PdfWriter.getInstance(docTSR, new FileOutputStream(prop.getProperty("pdfFilePathTSR"+module)));
				}
				catch(Exception e)
				{
					System.out.println("Error in TSR");
				}
				docTSR.open();
				para=new Paragraph("EY Test Summary Report - Module:"+module, fontER);
				para.setAlignment(1);
				docTSR.add(para);
				para=new Paragraph("\n\n\n");
				docTSR.add(para);
				tableTSR=new PdfPTable(7);
				tableTSR.setWidthPercentage(100);
				tableTSR.setWidths(new float[] {6.0f,6.0f,6.0f,6.0f,6.0f,6.0f,6.0f});
				cellTSR=new PdfPCell();
				cellTSR.setPaddingBottom(10f);
				cellTSR.setPaddingTop(10f);
				cellTSR.setPaddingRight(5f);
				cellTSR.setPaddingLeft(5f);
				cellTSR.setPhrase(new Phrase("Test Case Name", fontER));
				tableTSR.addCell(cellTSR);
				cellTSR.setPhrase(new Phrase("Start Time", fontER));
				tableTSR.addCell(cellTSR);
				cellTSR.setPhrase(new Phrase("End Time", fontER));
				tableTSR.addCell(cellTSR);
				cellTSR.setPhrase(new Phrase("Total Number of Steps", fontER));
				tableTSR.addCell(cellTSR);
				cellTSR.setPhrase(new Phrase("Number Of Steps Executed", fontER));
				tableTSR.addCell(cellTSR);
				cellTSR.setPhrase(new Phrase("Number of Steps Passed", fontER));
				tableTSR.addCell(cellTSR);
				cellTSR.setPhrase(new Phrase("Number of Steps Failed", fontER));
				tableTSR.addCell(cellTSR);
				fontER=new Font(Font.TIMES_ROMAN,12, Font.NORMAL);
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// for running on LOCAL
	/*public void initialization(String module)
	{
		this.TSRFlag=0;
		System.setProperty("webdriver.edge.driver", prop.getProperty("edgeDriverPath"));
		driver=new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TimeOut.PAGE_LOAD_TIMEOUT,TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));	
	}*/
	
	// for running on SERVER
	public void initialization(String module) throws MalformedURLException
	{
		this.TSRFlag=0;
		MutableCapabilities sauceOptions = new MutableCapabilities();
		sauceOptions.setCapability("name", "EY Automation Tests "+module);
		Object data[][]=ReadExcel.getTestData("Setup");
		platformName=data[0][0].toString();
		browserName=data[0][1].toString();
		browserVersion=data[2][0].toString();
		DesiredCapabilities cap=new DesiredCapabilities();
		cap.setCapability("sauce:options", sauceOptions);
		cap.setCapability("platformName", platformName);
		cap.setCapability("browserName",browserName);
		cap.setCapability("browserVersion", browserVersion);
		String username=System.getenv("SAUCE_USERNAME");
		String key=System.getenv("SAUCE_KEY");
		String huburl="https://"+username+":"+key+"@ondemand.eu-central-1.saucelabs.com:443/wd/hub";
		driver=new RemoteWebDriver(new URL(huburl),cap);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(TimeOut.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.navigate().to(prop.getProperty("url"));
		pdfCreate=new PDFCreate();
	}
	
	// Method for initializing variables and setting pdf headings
	public void initVars(String testName)
	{
		tempIndex=0;
		stepsPassed=0;
		stepsFailed=0;
		totalSteps=0;
		fontER=new Font(Font.TIMES_ROMAN,14,Font.BOLD);
		Paragraph para=new Paragraph(testName,fontER);
		docER.add(para);
		fontER=new Font(Font.TIMES_ROMAN,12,Font.NORMAL);
		para=new Paragraph("This is to test the functionality of '"+testName+"' test case\n\n",fontER);
		docER.add(para);
		int len=testName.split(" ").length;
		String temp="";
		for(int i=0;i<len;i++)
		{
			temp=temp+testName.split(" ")[i];
		}
		data=ReadExcel.getTestData(""+temp+"Script");
		totalSteps=data.length;
	}
	
	public void pass(String testName, int tempCounter, Document doc, Font font)
	{
		try
		{
			Timestamp timestamp=new Timestamp(System.currentTimeMillis());
			TakeScreenshot.CaptureScreenshot(driver,tempIndex,testName+tempCounter);
			pdfCreate.createPDF(testName,tempIndex,timestamp+"",data,"Pass",true,tempCounter,doc,font);
			stepsPassed++;
		}
		catch(Exception e)
		{
			System.out.println("Exception in Pass method "+testName+" after step +tempIndex");
			e.printStackTrace();
		}
	}
	
	public String fail(String testName, int tempCounter, Document doc, Font font)
	{
		try
		{
			Timestamp timestamp=new Timestamp(System.currentTimeMillis());
			TakeScreenshot.CaptureScreenshot(driver,tempIndex,testName+tempCounter);
			pdfCreate.createPDF(testName,tempIndex,timestamp+"",data,"Fail",true,tempCounter,doc,font);
			stepsFailed++;
			return tempIndex+"";
		}
		catch(Exception e)
		{
			System.out.println("Exception in Fail method "+testName+" after step +tempIndex");
			e.printStackTrace();
			return tempIndex+"";
		}
	}
	
	public void pdfMerge()
	{
		final String MERGED_PDF="EYTestExecutionReport.pdf";
		try
		{
			List<String> fileList=Arrays.asList("EYTestExecutionReport_CareerModule.pdf");
			Document doc=new Document();
			PdfCopy copy=new PdfCopy(doc, new FileOutputStream(MERGED_PDF));
			doc.open();
			for(String filePath: fileList)
			{
				try
				{
					PdfReader pdfReader = new PdfReader(filePath);
					int n=pdfReader.getNumberOfPages();
					PdfImportedPage page;
					for(int i=1;i<=n;i++)
					{
						page=copy.getImportedPage(pdfReader, i);
						copy.addPage(page);
					}
					copy.freeReader(pdfReader);
				}
				catch(Exception e) {}
			}
			doc.close();
			copy.close();	
		}
		catch(DocumentException | IOException e)
		{
			e.printStackTrace();
		}

		final String MERGED_PDF_TSR="EYTestSummaryReport.pdf";
		try
		{
			List<String> fileList=Arrays.asList("EYTestSummaryReport_CareerModule.pdf");
			Document doc=new Document();
			PdfCopy copy=new PdfCopy(doc, new FileOutputStream(MERGED_PDF_TSR));
			doc.open();
			for(String filePath: fileList)
			{
				try
				{
					PdfReader pdfReader = new PdfReader(filePath);
					int n=pdfReader.getNumberOfPages();
					PdfImportedPage page;
					for(int i=1;i<=n;i++)
					{
						page=copy.getImportedPage(pdfReader, i);
						copy.addPage(page);
					}
					copy.freeReader(pdfReader);
				}
				catch(Exception e) {}
			}
			doc.close();
			copy.close();	
		}
		catch(DocumentException | IOException e)
		{
			e.printStackTrace();
		}
	}
}
