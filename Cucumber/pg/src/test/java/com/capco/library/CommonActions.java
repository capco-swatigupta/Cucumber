package com.capco.library;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.SoftAssertions;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.capco.reports.ExtentManager;
import com.capco.utilities.Configuration;


public class CommonActions {
	WebDriver driver;
	Properties properties;
	FileInputStream fis;
	public static String featurename;
	public static String Scenarioname;
	ExtentReports report;
	ExtentTest scenario;
	SoftAssertions softassertions;



	public CommonActions() {

		if (properties == null) {
			try {
				properties = new Properties();
				fis = new FileInputStream(
						System.getProperty("user.dir") + "/src/test/resources/ObjectRepository/Object.properties");
				properties.load(fis);
			} catch (Exception e) {
				e.printStackTrace();
			}
			softassertions=new SoftAssertions();
		}
	}
	public void initReports(String scenarioName) throws Exception
	{
		try
		{
			report=ExtentManager.getInstance(Configuration.reportPath);
			scenario=report.createTest(scenarioName);
			scenario.log(Status.INFO, "Starting"+scenarioName);
		}catch (Exception e) {
			throw e;
		}
	}

	public void setfeaturefilenameansscenario(String id,String name)
	{
		featurename=id;
		String[] d=featurename.split("/features/");
		String[] d2=d[1].split(".feature");
		featurename=d2[0];
		Scenarioname=name;
	}
	public void launchBrowser() {
		try {
			if ((Configuration.BrowserName).equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver", Configuration.chromeDriverPath);
				driver = new ChromeDriver();
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			

			if (Configuration.ExecutionEnvrt.equalsIgnoreCase("testdata")) {
				driver.get(Configuration.AppurlLoad);
			}

		} catch (Exception e) {
			System.out.println("Invalid browser name or configuration");
		}
	}

	public void enterText(String objectKey, String datakey) {
		try {
			getElement(objectKey).clear();
			getElement(objectKey).sendKeys(getKeyFromJson(datakey));

		} catch (Exception e) {
			System.out.println("Invalid browser name or configuration");
		}
	}

	public String getKeyFromJson(String datakey) throws Exception {
		try {
			String data=null;
			JSONParser parser=new JSONParser();
			if(Configuration.ExecutionEnvrt.equalsIgnoreCase("testdata"))
			{
				JSONObject getFeaturename=(JSONObject) parser.parse(new FileReader("./src/test/java/com/capco/testdata/"+ featurename+".json"));
				JSONObject featureName=(JSONObject) getFeaturename.get(featurename);
				Map<String,String> getScenarioName=(Map<String, String>) featureName.get(Scenarioname);
				Iterator it=getScenarioName.entrySet().iterator();
				while(it.hasNext())
				{
					Map.Entry pair=(Entry) it.next();
					if(pair.getKey().toString().equals(datakey))
					{
						data=pair.getValue().toString();
						break;
					}
				}
			}


			return data;
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	public String getValueFromJson(String dataKeyInJson)
	{

		String datakey=null;
		try {
			datakey=getKeyFromJson(dataKeyInJson);

		}catch (Exception e) {
			e.printStackTrace();
		}

		return datakey;
	}
	public WebElement getElement(String objectKey) {
		WebElement e = null;
		WebDriverWait wait = new WebDriverWait(driver, 40);
		try {
			e = driver.findElement(By.xpath(properties.getProperty(objectKey)));
			Thread.sleep(1000);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return e;
	}

	public void logInfoStatus(String msg)
	{
		scenario.log(Status.INFO, msg);
	}

	public void logPassStatus(String msg)
	{
		scenario.log(Status.PASS, msg);
		softassertions.assertThat(true);
	}

	public void logFailStatus(String msg)
	{
		scenario.log(Status.FAIL, msg);
		System.out.println("message="+msg );
		softassertions.assertThat(false);
		takesScreenshot(msg);
	}


	public void click(String objectKey) throws Exception
	{
		try {
			Thread.sleep(2000);
			getElement(objectKey).click();		
		}catch (Exception e) {
			throw e;
		}
	}

	public void quit() throws Exception 
	{
		try
		{ 
			if(report!=null)
				report.flush();
			softassertions.assertAll();
			if(softassertions.errorsCollected().size()!=0)
			{
				logAssertFail(Scenarioname+"Failed");
			}

		}
		catch(Exception e)
		{
			throw e;
		}

	}
	public void logAssertFail(String errorMsg) {

		scenario.log(Status.FAIL, errorMsg);
		if((Configuration.takeascreenshots).equalsIgnoreCase("Y"))
		{
			takesScreenshot(errorMsg);
		}
		Assert.fail();
	}

	public boolean isElementPresent(String datakey)
	{
		List<WebElement> e=null;
		e=driver.findElements(By.xpath(properties.getProperty(datakey)));
		if(e.size()==0)
		{
			System.out.println("Element is not presernt"+ datakey);
			return false;
		}

		else
		{
			System.out.println("Element is presernt:Count"+ datakey+e.size());
			return true;

		}
	}

	public String getText(String objectKey) throws Exception
	{
		try
		{

			String str="";
			str=getElement(objectKey).getAttribute("value");
			return str;

		}catch (Exception e) {
			throw e;
		}
	}
	public void takesScreenshot(String msg) {
		if(Configuration.takeascreenshots.equals("Y"))
		{
			Date d=new Date();
			try {
				String screenshortfile=d.toString().replace(":", "_").replace(" ", "_")+ ".png";
				File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils .copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+screenshortfile));
				String PathOfScreenShort=System.getProperty("user.dir")+"/"+ExtentManager.screenshotFolderPath+screenshortfile;
				
				scenario.info(msg,MediaEntityBuilder.createScreenCaptureFromPath(PathOfScreenShort).build());
	
			}catch (Exception e) {
				e.printStackTrace();
				Assert.fail();
			}
		}
		

	}

}
