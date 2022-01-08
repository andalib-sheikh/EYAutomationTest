package com.eytest.utils;

import com.eytest.base.BasePage;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class PDFCreate 
{
	public static void createPDF(String testName, int tempIndex, String timestamp, Object[][] data, String status, boolean imgFlag, int counter, Document doc, Font font)
	{
		if(tempIndex!=0)
			doc.newPage();
		PdfPTable table=new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setWidths(new float[] {6.0f,6.0f,6.0f,6.0f,6.0f});
		String text="Step Number";
		PdfPCell cell=new PdfPCell();
		cell=new PdfPCell();
		cell.setPaddingBottom(10f);
		cell.setPaddingTop(10f);
		cell.setPaddingRight(5f);
		cell.setPaddingLeft(5f);
		cell.setPhrase(new Phrase("Timestamp", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Description", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Expected Result", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Actual Result", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Status", font));
		table.addCell(cell);
		text="Step "+(tempIndex+1);
		font=new Font(Font.TIMES_ROMAN,12,Font.NORMAL);
		cell.setPhrase(new Phrase(text, font));
		table.addCell(cell);
		cell.setPhrase(new Phrase(data[tempIndex][0]+"", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase(data[tempIndex][1]+"", font));
		table.addCell(cell);
		if(status.equals("Pass"))
		{
			cell.setPhrase(new Phrase(data[tempIndex][2]+"", font));
			table.addCell(cell);
		}
		else
		{
			cell.setPhrase(new Phrase(data[tempIndex][2]+"", font));
			table.addCell(cell);
		}
		cell.setPhrase(new Phrase(status, font));
		table.addCell(cell);
		doc.add(table);
		Paragraph para=new Paragraph("\n");
		doc.add(para);
		if(imgFlag==true)
		{
			String tempImgPath="./EYTestScreenshots/"+testName+counter+"/"+text+".png";
			Image image;
			try
			{
				image=Image.getInstance(tempImgPath);
				image.scaleAbsoluteWidth(table.getTotalWidth());
				image.scaleAbsoluteHeight(320);
				doc.add(image);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			doc.newPage();
		}
	}


	public static void createPDF(String testName, String startTime, String endTime, BasePage basePageObj, String module)
	{
		basePageObj.cellTSR.setPhrase(new Phrase(testName, basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase(startTime, basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase(endTime, basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase(basePageObj.totalSteps+"", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase((basePageObj.tempIndex+1)+"", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase(basePageObj.stepsPassed+"", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase(basePageObj.stepsFailed+"", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
	}


	public static void createPDF(BasePage basePageObj)
	{
		Paragraph para=new Paragraph("\n\n\n");
		basePageObj.docTSR.add(para);
		basePageObj.tableTSR=new PdfPTable(5);
		basePageObj.tableTSR.setWidthPercentage(100);
		basePageObj.tableTSR.setWidths(new float[] {6.0f,6.0f,6.0f,6.0f,6.0f});
		basePageObj.cellTSR=new PdfPCell();
		basePageObj.cellTSR.setPaddingBottom(10f);
		basePageObj.cellTSR.setPaddingTop(10f);
		basePageObj.cellTSR.setPaddingRight(5f);
		basePageObj.cellTSR.setPaddingLeft(5f);
		basePageObj.cellTSR.setPhrase(new Phrase("Execution Start Time", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase("Execution End Time", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase("Total Number of Test Cases", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase("Number of Test Cases Passed", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase("Number of Test Cases Failed", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.fontER=new Font(Font.TIMES_ROMAN,12,Font.NORMAL);
		basePageObj.cellTSR.setPhrase(new Phrase(basePageObj.scriptStartTime+"", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase(basePageObj.scriptEndTime+"", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase(basePageObj.totalSteps+"", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase(basePageObj.stepsPassed+"", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.cellTSR.setPhrase(new Phrase(basePageObj.stepsFailed+"", basePageObj.fontER));
		basePageObj.tableTSR.addCell(basePageObj.cellTSR);
		basePageObj.docTSR.add(basePageObj.tableTSR);
	}
}