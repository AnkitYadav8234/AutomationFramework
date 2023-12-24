package com.automation.amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class LoginTest {
    WebDriver driver;
    String baseURL;
    WebDriverWait wait;

    @BeforeClass
    public void beforeClass(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver,Duration.ofSeconds(20));

        baseURL = "https://www.amazon.in/";
        driver.get(baseURL);

    }

    @Test
    public void testLogin() throws InterruptedException {
        driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys("iphone");
        driver.findElement(By.xpath("(//div[@class='left-pane-results-container']//div[@class='s-suggestion s-suggestion-ellipsis-direction' ])[2]")).click();
        driver.findElement(By.xpath("//span[text()='iPhone 15 Pro (128 GB) - Blue Titanium']")).click();
        String ParentWindow = driver.getWindowHandle();
        Set<String> allWind= driver.getWindowHandles();
        for(String cd : allWind){
            if(!ParentWindow.equals(cd)){
                driver.switchTo().window(cd);
            }
        }
        Thread.sleep(5000);
        String priceValue = driver.findElement(By.xpath("(//span[@class='a-price-whole'])[1]")).getText();
        System.out.println("Price Value is:- "+priceValue);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='add-to-cart-button']"))).click();
        driver.findElement(By.xpath("//input[@id='add-to-cart-button']")).click();
        Thread.sleep(5000);

        WebElement addToCard_e = null;
        try {
            addToCard_e = driver.findElement(By.xpath("(//h4[text()='Added to Cart'])[2]"));
        }catch (Exception e){
            e.printStackTrace();
        }

        Assert.assertNotNull(addToCard_e);
        Thread.sleep(10000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@id='attach-sidesheet-view-cart-button-announce']"))).click();


       boolean proceedButton = driver.findElement(By.xpath("//input[@data-feature-id='proceed-to-checkout-action']")).isDisplayed();

       Assert.assertTrue(proceedButton);


    }


//    @AfterClass
//    public void AfterClass(){
//        driver.quit();
//    }
}
