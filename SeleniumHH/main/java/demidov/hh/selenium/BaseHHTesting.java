package demidov.hh.selenium;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Vadim Demidov on 23.05.2015.
 * Class contains all base methods for creating driver instances and manipulating actions
 */
public abstract class BaseHHTesting {


    Logger log = Logger.getLogger(BaseHHTesting.class);


    protected static WebDriver webDriver; //Global driver shared between all implementations
    protected static WebElement element; //Global element shared between all implementations
    protected static Map<String, String> copiedContentStates = new HashMap(); //This var saves states of elements
    protected static String htmlPageSource; //Global html page source code shared between all implementations




    //Create full webDriver system argument string
    private String setWebDriverArg(String driverName)
    {
        log.info("Define driver argument name...");

        String webDriverArg = "webdriver.";
        webDriverArg = webDriverArg + driverName + "." + "driver";
        log.info("Driver argument name is: " + webDriverArg);
        return webDriverArg;
    }


    //Instanciate driver in case of passed argument
    private WebDriver getDriver(String webDriverPath) throws Exception
    {

        log.info("Getting path to the driver and making new instance of web driver...");
        log.info("Path to driver on hard driver: " + webDriverPath);

        int lastIdx = webDriverPath.lastIndexOf("\\");

        String driverName = webDriverPath.substring((lastIdx+1), webDriverPath.length());
        int driverIdx = driverName.indexOf("d");
        driverName = driverName.substring(0, driverIdx);

        log.info("Driver will be used " + driverName);

        String currentDriverName = setWebDriverArg(driverName);

        log.info("Setting system props with driver argument: " + currentDriverName + " and driver path: " + webDriverPath);
        System.setProperty(currentDriverName, webDriverPath);


        if(driverName.toLowerCase().startsWith("chrome")) {
            webDriver = new ChromeDriver();
        } else if(driverName.startsWith("firefox")) {
            webDriver = new FirefoxDriver();
        } else if(driverName.startsWith("safari")) {
            webDriver = new SafariDriver();
        } else {
            webDriver = new InternetExplorerDriver();
        }

        log.info("Driver instansiated: " + webDriver.getClass().getName());

        if(webDriver == null) {
            log.error("WebDriver is not instanciated. WebDriver specified name specified is: " + driverName);
            throw new Exception("WebDriver is not instanciated. WebDriver specified name specified is: " + driverName);
        }

            return webDriver;


    }


    //Uses getDriver(...) method to provide url for web driver and apply request within web browser
    protected boolean createWebConnection(String webDriverPath, String url)
    {
        try {

            log.info("Creating web driver connection and redirecting to url: " + url);

            getDriver(webDriverPath);
            webDriver.get(url);
        } catch (Exception e) {
            log.error("Exception during execution createWebConnection() method");
            return false;
        }

        return true;
    }


    //Mainly realise web driver
    protected boolean realiseWebDriver()
    {

        log.info("Realising driver connection");
        try {

            if(webDriver != null)
                webDriver.quit();

        } catch(Exception e) {
            log.error("Exception during execution realiseWebDriver() method");
            return false;
        }
        return true;
    }


    //Saves initial and changed content of particular element to this .class local cache
    protected boolean saveInitialState(String openString, String closeString, String keyForSavedInitialContent)
    {

        log.info("Saving initial content before action perform");
        htmlPageSource = webDriver.getPageSource();

        log.debug("Html code to get content from: " + htmlPageSource);

        if(htmlPageSource == null) {
            log.error("Html page is: " + htmlPageSource);
            return false;
        }

        //Finding and extract content of particular element
        String copiedElementContent = StringUtils.substringBetween(htmlPageSource, openString, closeString);
        log.debug("Initial content to be saved in the cache: " + copiedElementContent);

        if(copiedElementContent == null) {
            log.error("Content to save in the cache is: " + copiedElementContent);
            return false;
        }

        log.info("Initial content will be saved with key name: " + keyForSavedInitialContent);
        copiedContentStates.put(keyForSavedInitialContent, copiedElementContent); //Putting content to local cache
        log.debug("Initial content saved with key: " + keyForSavedInitialContent + " value: " + copiedElementContent);

        log.info("cache size is: " + copiedContentStates.size());

        return true;


    }


    //Each extended instance can create own implementation for elements manipulations
    protected abstract WebElement doAction(String rootElementName, String childElement);

    //Each extended instance can create own validation process
    protected abstract boolean validateAction(String openElementTag, String closeElementTag, String keyFromCopiedContentStates);

}
