package uitests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class Assignment4 {
    WebDriver driver;

    @Test
    public void orderFood() throws Exception {
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
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.get(properties.getProperty("url"));
        driver.findElement(By.cssSelector(properties.getProperty("country_dropdown"))).click();
        driver.findElement(By.cssSelector(properties.getProperty("country_search"))).sendKeys(properties.getProperty("country"));
        driver.findElement(By.cssSelector(properties.getProperty("country_search"))).sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector(properties.getProperty("contact"))).sendKeys(properties.getProperty("number"));
        driver.findElement(By.cssSelector(properties.getProperty("login_button"))).click();

        driver.findElement(By.partialLinkText(properties.getProperty("dine_in"))).click();
        WebElement element = driver.findElement(By.cssSelector(properties.getProperty("add_item")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);


        WebDriverWait w = new WebDriverWait(driver,5);
        w.until(ExpectedConditions.presenceOfElementLocated (By.cssSelector(properties.getProperty("add_to_cart"))));

        driver.findElement(By.cssSelector(properties.getProperty("add_to_cart"))).click();

        WebElement element1 = driver.findElement(By.cssSelector(properties.getProperty("add_qty")));
        JavascriptExecutor executor1 = (JavascriptExecutor)driver;
        executor1.executeScript("arguments[0].click();", element1);

        driver.findElement(By.cssSelector(properties.getProperty("checkout"))).click();
        driver.findElement(By.cssSelector(properties.getProperty("add_qty_from_cart"))).click();
        Thread.sleep(2);
        String cartQuantity = driver.findElement(By.cssSelector(properties.getProperty("cart_qty"))).getText();
        int quantity = parseInt(cartQuantity);
        Assert.assertEquals(quantity,3, "3 quantities of item not added to cart!");

        driver.findElement(By.cssSelector(properties.getProperty("make_payment"))).click();
        String OrderId = driver.findElement(By.cssSelector(properties.getProperty("order_id"))).getText();

        if(OrderId != null){
            System.out.println("Order is placed successfully with OrderID: " +OrderId);
        }
        else{
            throw new Exception("Order is not placed!");
        }


    }

    @AfterSuite
    public void close_browser(){
        driver.quit();
    }
}
