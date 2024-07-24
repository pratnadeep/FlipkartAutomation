package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import demo.wrappers.Wrappers;

public class TestCases {
    static ChromeDriver driver;
    @BeforeSuite(enabled = true, alwaysRun = true)
    public static void openBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
    }
    @Test(priority = 1)
    public void testCase01(){
        try {
            int count = 0;
            System.out.println("Test Case 01 :Sort by popularity and print the count of items with rating less than or equal to 4 stars");
            driver.get("https://www.flipkart.com/");
            
            boolean text = Wrappers.sendtext(driver, By.xpath("//input[contains(@title,'Search for Products, Brands and More')]"), "washing machine");
            Thread.sleep(10000);
            Assert.assertTrue(text);
            
            boolean EC = Wrappers.clickonwebelement(driver, By.xpath("//div[@class ='zg-M3Z' and text()='Popularity']"));
            Assert.assertTrue(EC);
            
            List<WebElement> products = Wrappers.searchallwebelements(driver, By.xpath("//div[@class ='XQDdHH']"));
           
            for (WebElement e : products) {
                String rate = e.getText();
                double rating = Double.parseDouble(rate);
                if (rating <5) {
                    count++;
                }
            }
            System.out.println("Count of Washing Machines :" + count);
            System.out.println("Test Case 01 Passed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Testcase 01 Failed");
        }
    }

    @Test(priority = 2)
    public void testCase02() throws InterruptedException {
        try {
            System.out.println("Test Case 02: Search \"iPhone\", print the Titles and discount % of items with more than 17% discount ");
            driver.get("https://www.flipkart.com/");
            
            boolean text = Wrappers.sendtext(driver, By.xpath("//input[contains(@title,'Search for Products, Brands and More')]"), "iphone");
            
            List<WebElement> heading = Wrappers.searchallwebelements(driver, By.xpath("//div[@class ='KzDlHZ']"));
            List<WebElement> discounts = Wrappers.searchallwebelements(driver, By.xpath("//div[@class ='UkUFwK']"));
            
            for (int i = 0; i < discounts.size(); i++) {
                String str = discounts.get(i).getText();
               
                String str1 = (str.replaceAll("[\\D]", ""));
               
                int discount = Integer.parseInt(str1);
                if (discount > 17) {
                    System.out.println(heading.get(i).getText() + " " + str);
                }
            }
            System.out.println("Test Case 02 Passed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Testcase 02 Failed");
        }
    }
    @Test(priority = 3)
    public void testCase03() throws InterruptedException {
        try {
            System.out.println("Test Case 03 Search \"Coffee Mug\", select 4 stars and above, and print the Title and image URL of the 5 items with highest number of reviews");
            driver.get("https://www.flipkart.com/");
            boolean text = Wrappers.sendtext(driver, By.xpath("//input[contains(@title,'Search for Products, Brands and More')]"), "Coffee Mug");
            Thread.sleep(10000);
            Assert.assertTrue(text);
            WebElement checkboxratings = driver.findElement(By.xpath("(//div[@class = 'XqNaEv' ])[1]"));
            checkboxratings.click();
            Thread.sleep(10000);
            
            List<WebElement> reviews = driver.findElements(By.xpath("//span[@class='Wphh3N']"));
            ArrayList<Integer> reviewslist= new ArrayList<>();
           
            for (WebElement review : reviews) {
                String str = review.getText();
              
                String str1 = (str.replaceAll("[\\D]", ""));
                
                reviewslist.add(Integer.parseInt(str1));
            }
          
            Collections.sort(reviewslist, Collections.reverseOrder());
        
            for (int i = 0; i < 5; i++) {
               
                String  formatnumber = "";
                DecimalFormat format = new DecimalFormat("#,###");
               
                formatnumber = format.format(reviewslist.get(i));
                System.out.println( formatnumber);
                Thread.sleep(5000);
                WebElement title = driver.findElement(By.xpath("//span[@class='Wphh3N' and contains(text(),"+ "'"+formatnumber +"'"+")]/parent::div/preceding-sibling::div/preceding-sibling::a[1]"));
                String pt = title.getText();
                Thread.sleep(5000);
                WebElement image = driver.findElement(By.xpath("//span[@class='Wphh3N' and contains(text(),"+ "'"+formatnumber+"'"+")]/parent::div/preceding-sibling::div/preceding-sibling::a[1]"));
                String pi = image.getAttribute("href");
                System.out.println("Products with high reveiws :" + pt);
                System.out.println("Image link of the Products with high reveiws :" + pi);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Testcase 03 Failed");
        }
    }
}