package uitests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Assignment6 {

    WebDriver driver;

    @Test
    public void bookAppointment() throws Exception {
        File file = new File("Config/config.properties");

        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();

        try {
            assert fileInput != null;
            properties.load(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setProperty("webdriver.chrome.driver", properties.getProperty("driverpath"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(properties.getProperty("url2"));
        WebElement treatment = driver.findElement(By.linkText(properties.getProperty("treatments")));
        Actions action = new Actions(driver);
        action.moveToElement(treatment).perform();

        driver.findElement(By.cssSelector(properties.getProperty("child_health"))).click();
        driver.findElement(By.cssSelector(properties.getProperty("immunity"))).click();
        driver.findElement(By.cssSelector(properties.getProperty("chat_button"))).click();
        driver.findElement(By.linkText(properties.getProperty("consult_now"))).click();

        driver.findElement(By.cssSelector(properties.getProperty("name"))).sendKeys("Test");
        driver.findElement(By.cssSelector(properties.getProperty("mobile"))).sendKeys("1234567890");
        driver.findElement(By.cssSelector(properties.getProperty("email"))).sendKeys("test@consult.com");
        driver.findElement(By.cssSelector(properties.getProperty("tnc_chkbox"))).click();
        driver.findElement(By.cssSelector(properties.getProperty("next"))).click();
        Thread.sleep(5000);
        boolean payNow =  driver.findElement(By.cssSelector(properties.getProperty("pay_now"))).isDisplayed();
        Assert.assertTrue(payNow, "Pay Now button is expected but not displayed!");

    }

    @AfterSuite
    public void close_browser(){
        driver.quit();
    }
}
