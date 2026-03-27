package testCases;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	
	
	
	
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registration() throws InterruptedException {
		
		
		
		logger.info("*******  Starting TC001_AccountRegistrationTest ********** ");
		try 
		{
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info( "Clicked on MyAccount Link ");
		hp.clickRegister();
		logger.info("Clicked on Register Link ");
		
		AccountRegistrationPage regpage= new AccountRegistrationPage(driver);
		
		logger.info("Providing Customer details..");
		
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString()+"@gmail.com");
		regpage.setTelephone(randomNumber());
		
		String Password= randomAlphaNumberic();
		
		regpage.setPassword(Password);
		regpage.setConfirmPassword(Password);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		Thread.sleep(5000);
		
		logger.info("Validating expected message..");
		
		String confmsg= regpage.getConfirmationMsg();
		
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test Failed..");
			logger.debug("Debug logs..");
			Assert.assertTrue(false);
		}
		
	    }
		catch(Exception e)
		{
		
			Assert.fail();
		}
		
		logger.info("*******  Finished TC001_AccountRegistrationTest ********** ");
		
	}
	
	
	
	
	
}
