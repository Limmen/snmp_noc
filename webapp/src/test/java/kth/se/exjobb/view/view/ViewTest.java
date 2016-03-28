/*
 * Royal Institute of Technology
 * 2016 (c) Kim Hammar Marcus Blom
 */
package kth.se.exjobb.view.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * This class contains acceptance-tests for the application.
 * It will run a firefox instance to tests usual use-cases and
 * edge-cases.
 * 
 * @author Kim Hammar
 */
public class ViewTest 
{
    private WebDriver driver;
    
    /**
     * Class constructor
     */
    public ViewTest() 
    {
    }
    
    /**
     * This method is called before the tests are executed
     */
    @Before
    public void setUp() 
    {
        driver  = new FirefoxDriver();
    }

    /**
     * This test need to be run after deployment to application server (@Ignore)
     */    
    @Ignore
    @Test
    public void testView()
    {
        testIndex();
        testRequest();
        testStatistics();
        testConfiguration();
    }
    
    /* Tests that home-page is rendered properly */
    private void testIndex(){
        driver.navigate().to("http://localhost:8080/webapp/");
        Assert.assertEquals("NOC Prototype", driver.getTitle());
        /* Check if jumbotron is visible */
        Assert.assertTrue(driver.findElements(By.id("j_idt7")).size() > 0);
        /* Check that main container is visible */
        Assert.assertTrue(driver.findElements(By.id("j_idt17")).size() > 0);
        /* Check that footer is visible */
        Assert.assertTrue(driver.findElements(By.id("j_idt62")).size() > 0);
        /* Check that the url is correct */
        Assert.assertEquals("http://localhost:8080/webapp/", driver.getCurrentUrl());
    }
    
    /* Tests that request-page is rendered properly */
    private void testRequest(){
        driver.navigate().to("http://localhost:8080/webapp/request.xhtml");
        Assert.assertEquals("NOC Prototype", driver.getTitle());
        /* Check if jumbotron is visible */
        Assert.assertTrue(driver.findElements(By.id("j_idt7")).size() > 0);
        /* Check that main container is visible */
        Assert.assertTrue(driver.findElements(By.id("j_idt17")).size() > 0);
        /* Check that footer is visible */
        Assert.assertTrue(driver.findElements(By.id("j_idt79")).size() > 0);
        /* Check that the url is correct */
        Assert.assertEquals("http://localhost:8080/webapp/request.xhtml", driver.getCurrentUrl());
    }
    
    /* Tests that statistics-page is rendered properly */
    private void testStatistics(){
        driver.navigate().to("http://localhost:8080/webapp/statistics.xhtml");
        Assert.assertEquals("NOC Prototype", driver.getTitle());
        /* Check if jumbotron is visible */
        Assert.assertTrue(driver.findElements(By.id("j_idt7")).size() > 0);
        /* Check that main container is visible */
        Assert.assertTrue(driver.findElements(By.id("j_idt17")).size() > 0);
        /* Check that footer is visible */
        Assert.assertTrue(driver.findElements(By.id("j_idt25")).size() > 0);
        /* Check that the url is correct */
        Assert.assertEquals("http://localhost:8080/webapp/statistics.xhtml", driver.getCurrentUrl());
    }
    
    /* Tests that config-page is rendered properly */
    private void testConfiguration(){
        driver.navigate().to("http://localhost:8080/webapp/configuration.xhtml");
        Assert.assertEquals("NOC Prototype", driver.getTitle());
        /* Check if jumbotron is visible */
        Assert.assertTrue(driver.findElements(By.id("j_idt7")).size() > 0);
        /* Check that main container is visible */
        Assert.assertTrue(driver.findElements(By.id("j_idt17")).size() > 0);
        /* Check that footer is visible */
        Assert.assertTrue(driver.findElements(By.id("j_idt32")).size() > 0);
        /* Check that the url is correct */
        Assert.assertEquals("http://localhost:8080/webapp/configuration.xhtml", driver.getCurrentUrl());
    }
  
}
