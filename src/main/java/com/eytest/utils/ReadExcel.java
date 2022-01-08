package com.eytest.utils;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel 
{
	public static Object[][] getTestData(String sheetName)
	{
		Object data[][]=null;
		try
		{
			Workbook workBook;
			Sheet sheet;
			Properties prop=new Properties();
			FileInputStream fs1=new FileInputStream("./src/main/java/com/eytest/config/config.properties");
			prop.load(fs1);
			FileInputStream fs=new FileInputStream(prop.getProperty("TestDataExcelPath"));
			workBook=new XSSFWorkbook(fs);
			sheet=workBook.getSheet(sheetName);
			data=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			for(int i=0;i<sheet.getLastRowNum();i++)
			{
				for(int j=0;j<sheet.getRow(0).getLastCellNum();j++)
				{
					try
					{
						data[i][j]=sheet.getRow(i+1).getCell(j).toString();
					}
					catch(NullPointerException e)
					{
						data[i][j]="null";
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
}
