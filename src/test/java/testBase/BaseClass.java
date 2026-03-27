package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass  {
	
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
     @BeforeClass(groups={"Regression","Sanity","Master"})
     @Parameters({"os","browser"})
	
	public void setup(String os, String br) throws IOException
	{
    	 
    	 //Loading config.properties file
    	 
    	 FileReader file = new FileReader("./src//test//resources//config.properties");
    	 p=new Properties();
    	 p.load(file);
    	 
    	 logger=LogManager.getLogger(this.getClass());
    	 
    	 if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
    	 {
    		 
    		 DesiredCapabilities capabilities = new DesiredCapabilities();
    		 
    		 if(os.equalsIgnoreCase("windows"))
    		 {
    			 capabilities.setPlatform(Platform.WIN11);
    		 }
    		 else if(os.equalsIgnoreCase("linux"))
    		 {
    			 capabilities.setPlatform(Platform.LINUX);
    		 }
    		 else if(os.equalsIgnoreCase("MAC"))
    		 {
    			 capabilities.setPlatform(Platform.MAC);
    		 }
    		 else
    		 {
    			 System.out.println("No matching os");
    			 return;
    		 }
    		 
    		 
    		 //Browser
    		 switch(br.toLowerCase())
    		 {
    		 case "chrome": capabilities.setBrowserName("chrome");break;
    		 case "edge": capabilities.setBrowserName("MicrosoftEdge");break;
    		 case "firefox": capabilities.setBrowserName("firefox");break;
    		 default: System.out.println("No matching browser");return;
    		 }
    		 
    		 driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub") ,capabilities);	 
    	 }

    	 if(p.getProperty("execution_env").equalsIgnoreCase("local"))
    	 {
    		 switch(br.toLowerCase())
    	 		{
    	 		case"chrome":driver=new ChromeDriver();break;
    	 		case"edge":driver=new EdgeDriver();break;
    	 		default: System.out.println("Invalid Browser");return;
    	 		}
    	 }
    	 
		
		driver.manage().deleteAllCookies();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("appURL"));
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups={"Regression","Sanity","Master"})
	public void teardown()
	{
		driver.quit();
	}
	
	public String randomString()
	{
		String generated_String=RandomStringUtils.randomAlphabetic(6);
		
		return generated_String;
	}
	
	public String randomNumber()
	{
		
		String generatednumber=RandomStringUtils.randomNumeric(10);		
		return generatednumber;
	}
	
	public String randomAlphaNumberic()
	{
		String generated_String=RandomStringUtils.randomAlphabetic(3);
		
		String generatednumber=RandomStringUtils.randomNumeric(5);		
		return (generated_String+"@"+generatednumber);
	}
	
	
	public String captureScreen(String tname)
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
		
		File sourceFile= takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp+".png";
		
		File targetFile=new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
		
	}


}
