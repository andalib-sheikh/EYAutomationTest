package com.eytest.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TakeScreenshot
{
	public static void CaptureScreenshot(WebDriver driver, int index, String testName) throws Exception
	{
		try
		{
			String ScreenshotName="Step "+(index+1);
			String ScreenshotDirectory="./EYTestScreenshots";
			String ScreenshotTestFolder=ScreenshotDirectory+"/"+testName;
			String Screenshotname=ScreenshotTestFolder+"/"+ScreenshotName+".png";
			Path path1=Paths.get(ScreenshotDirectory);
			Path path2=Paths.get(ScreenshotTestFolder);
			if(!Files.exists(path1))
			{
				Files.createDirectory(path1);
				Files.createDirectory(path2);
			}
			if(!Files.exists(path2))
			{
				Files.createDirectory(path2);
			}
			TakesScreenshot ss=((TakesScreenshot)driver);
			File SrcFile=ss.getScreenshotAs(OutputType.FILE);
			File DestFile=new File(Screenshotname);
			FileUtils.copyFile(SrcFile, DestFile);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}
