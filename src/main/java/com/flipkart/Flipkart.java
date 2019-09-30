package com.flipkart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.android.dx.util.FileUtils;

public class Flipkart {
	
	//Reference of WebDriver
	public static WebDriver chromedriver;
	
	//This test will be executed first
	@Test(priority=0,enabled=true)
	public void browser() throws IOException{
		
		String path = System.getProperty("user.dir");
		FileInputStream fis = new FileInputStream("D:\\javaprojects\\TestingProject\\config.properties");
		
		String genericPath= path+"\\drivers\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", genericPath);
		chromedriver = new ChromeDriver();
		
		Properties prop = new Properties();
		prop.load(fis);
		String URL = prop.getProperty("URL");
		
		System.out.println(URL);
		chromedriver.get(URL);
		
		
		chromedriver.manage().window().maximize();
		chromedriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				
	}
	@Test(priority=1,enabled=true,dependsOnMethods="browser")
//	@Parameters({"username"})
	public void login() throws IOException, InterruptedException{

		FileInputStream fis=new FileInputStream(new File("D:\\javaprojects\\TestingProject\\resources\\Book.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		XSSFRow row = sheet.getRow(0);
		String userid = row.getCell(0).getStringCellValue();
		String pass = row.getCell(1).getStringCellValue();
		
		
		//Finding element by defining absolute xpath
		chromedriver.findElement(By.xpath("//input[@class='_2zrpKA _1dBPDZ']")).sendKeys(userid);
		
		//Finding element by defining relative xpath
		chromedriver.findElement(By.xpath("//input[@type='password']")).sendKeys(pass);
		
		chromedriver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div/form/div[3]/button")).click();
		
		
		
		//Alert---------------------------------------------------------
		Alert alert = chromedriver.switchTo().alert();		    
        String Message= chromedriver.switchTo().alert().getText();		
        		
        // Displaying alert message		
        System.out.println(Message);	
        Thread.sleep(5000);
        
        alert.accept();
		
		
		//Actions----------------------------------------------------------
		Actions builder = new Actions(chromedriver);
        Action mouseOverHome = builder.moveToElement(chromedriver.findElement(By.xpath("//input[@class='_2zrpKA _1dBPDZ']"))).sendKeys("erthrht").build();
        mouseOverHome.perform();
        
		Alert alert=chromedriver.switchTo().
		
		WebDriverWait wait = new WebDriverWait(chromedriver, 10);
		Thread.sleep(3000);
	}
	
	@Test(priority=2,enabled=true,dependsOnMethods="login")
	public void cart() throws IOException, InterruptedException{
		
		chromedriver.findElement(By.xpath("//input[@name='q']")).sendKeys("laptop"+Keys.ENTER);
		Thread.sleep(6000);
		/*chromedriver.findElement(By.className("vh79eN")).click();
		Thread.sleep(6000);*/
		chromedriver.findElement(By.xpath("//img[@alt='HP 14q APU Dual Core A6 - (4 GB/256 GB SSD/Windows 10 Home) 14q-cy0004AU Thin and Light Laptop'][1]")).click();
		Thread.sleep(5000);
		ArrayList<String> tabs = new ArrayList<String>(chromedriver.getWindowHandles());
		chromedriver.switchTo().window(tabs.get(1));
		Thread.sleep(2000);
		System.out.println(chromedriver.getCurrentUrl());
		chromedriver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[1]/div[1]/div[2]/div/ul/li[1]/button")).click();
		Thread.sleep(5000);
	}
	
	
	@Test(priority=3,enabled=true)
	public void testSikuli() throws InterruptedException, FindFailed
	{
		Screen screen=new Screen();
		Pattern pattern=new Pattern("D:\\javaprojects\\TestingProject\\Capture.PNG");
		screen.type(pattern,"laptop");
		screen.click();
		Thread.sleep(10000);
	}
	
	@AfterMethod
	public void screenshot(){
		TakesScreenshot ts=(TakesScreenshot) chromedriver;
		File file=ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(genericPath+"abc.png");
		System.out.println(genericPath+"abc.png");
		
	}
	@AfterTest
	public void closeBrowser() {
		
		chromedriver.close();
		chromedriver.quit();
	}
		
	
}
