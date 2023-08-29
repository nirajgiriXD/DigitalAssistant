package automation;

import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Automation {
	
	@SuppressWarnings("deprecation")
	public static String getWeather() {
		try {
			System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");
			System.setProperty("webdriver.chrome.silentOutput", "true");
			LogManager.getLogManager().reset();
			ChromeOptions options =  new ChromeOptions();
			
			options.addArguments("window-size=1920,1080");
			options.addArguments("no-sandbox");
			options.addArguments("headless");
			options.addArguments("disable-gpu");
			options.addArguments("disable-crash-reporter");
			options.addArguments("disable-extensions");
			options.addArguments("disable-in-process-stack-traces");
			options.addArguments("disable-logging");
			options.addArguments("disable-dev-shm-usage");
			options.addArguments("log-level=3");
			options.addArguments("output=/dev/null");
			
			WebDriver driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(55, TimeUnit.SECONDS);
			driver.navigate().to("https://www.google.com/search?q=weather");
			String weather = driver.findElement(By.xpath("//*[@id=\"wob_tm\"]")).getText();
			driver.close();
			return weather + " degree celcius";
		}
		catch (Exception e) {
			// TODO: handle exception
			return "Could not retrive data";
		}
	}
	
	@SuppressWarnings("deprecation")
	public static String getLocation() {
		try {
			System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");
			System.setProperty("webdriver.chrome.silentOutput", "true");
			LogManager.getLogManager().reset();
			ChromeOptions options =  new ChromeOptions();
			
			options.addArguments("window-size=1920,1080");
			options.addArguments("no-sandbox");
			options.addArguments("headless");
			options.addArguments("disable-gpu");
			options.addArguments("disable-crash-reporter");
			options.addArguments("disable-extensions");
			options.addArguments("disable-in-process-stack-traces");
			options.addArguments("disable-logging");
			options.addArguments("disable-dev-shm-usage");
			options.addArguments("log-level=3");
			options.addArguments("output=/dev/null");
			
			WebDriver driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(55, TimeUnit.SECONDS);
			driver.navigate().to("https://www.iplocation.net/");
			String ipAddress = driver.findElement(By.xpath("//*[@id=\"ip-placeholder\"]/p/span[1]")).getText();
			driver.navigate().to("https://whatismyipaddress.com/ip/" + ipAddress);
			String latitude = driver.findElement(By.xpath("//div[@class='right']//p[@class='information'][1]//span[2]")).getText();
			String longitude = driver.findElement(By.xpath("//div[@class='right']//p[@class='information'][2]//span[2]")).getText();
			String continent = driver.findElement(By.xpath("//div[@class='left']//p[@class='information'][9]//span[2]")).getText();
			String country = driver.findElement(By.xpath("//div[@class='left']//p[@class='information'][10]//span[2]")).getText();
			String state = driver.findElement(By.xpath("//div[@class='left']//p[@class='information'][11]//span[2]")).getText();
			String city = driver.findElement(By.xpath("//div[@class='left']//p[@class='information'][12]//span[2]")).getText();
			System.out.println("----------------------------------------------------------------------------\n");
			System.out.println("\tIP Address\t:\t" + ipAddress);
			System.out.println("\tLatitude\t:\t" + latitude);
			System.out.println("\tLongitude\t:\t" + longitude);
			System.out.println("\tContinent\t:\t" + continent);
			System.out.println("\tState\t\t:\t" + state);
			System.out.println("\tCountry\t\t:\t" + country);
			System.out.println("\tCity\t\t:\t" + city);
			System.out.println("\n----------------------------------------------------------------------------\n");
			driver.close();
			return city;
		}
		catch (Exception e) {
			// TODO: handle exception
			return "Could not retrieve data";
		}
	}

}
