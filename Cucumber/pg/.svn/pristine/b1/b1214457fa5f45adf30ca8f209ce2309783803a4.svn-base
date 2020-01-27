package com.capco.reports;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.capco.library.CommonActions;

public class ExtentManager {

	private static ExtentReports extent;
	public static String screenshotFolderPath;
	static CommonActions commonActions;
	ExtentTest scenario;

	public static ExtentReports getInstance(String reportPath) {
		System.out.println("Path  "+reportPath);
		if (extent == null) {
			String fileName = "Report.html";
			String folderName;
			Date d = new Date();
			folderName = d.toString().replace(":", "_");
			new File(reportPath + folderName + "/screenshots").mkdirs();
			reportPath = reportPath + folderName + "/";
			screenshotFolderPath = reportPath + "screenshots/";
			createInstance(reportPath + fileName);
			
		}
		return extent;
	}

	public static ExtentReports createInstance(String fileName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("Reports");
		htmlReporter.config().setEncoding("UTF-8");
		htmlReporter.config().setReportName("Reports Automation Testing");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		return extent;

	}
	
	

	
	
}
