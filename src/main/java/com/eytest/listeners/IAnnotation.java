package com.eytest.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import com.eytest.utils.ReadExcel;

public class IAnnotation implements IAnnotationTransformer 
{
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod)
	{
		Object data[][]=ReadExcel.getTestData("SetupTests");
		try
		{
			String methodName=testMethod.getName();
			int counterRow=0;
			int counterColumn=0;
			switch(methodName)
			{
				case "":
					if(data[counterRow][counterColumn].toString().equalsIgnoreCase("TRUE"))
						annotation.setEnabled(true);
					else
						annotation.setEnabled(false);
					break;
			}
		}
		catch(Exception e) {}
	}
}
