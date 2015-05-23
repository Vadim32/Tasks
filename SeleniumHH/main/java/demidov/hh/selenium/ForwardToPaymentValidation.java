package demidov.hh.selenium;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;



/**
 * Created by Vadim Demidov on 23.05.2015.
 * This class is responsible for redirect request to payment page by clicking on particular payments elements
 */
public class ForwardToPaymentValidation extends BaseHHTesting {


    public static Logger log = Logger.getLogger(ForwardToPaymentValidation.class);


    @Override
    public WebElement doAction(String rootElementName, String childElement) {

        log.info("Find and prepare element for forwarding with " + rootElementName + " and " + childElement);
        element = webDriver.findElement(By.xpath("//div[contains(@class, '" + rootElementName + "')]" +
                                                 "/a[contains(@class, '" + childElement + "')]"));
        return element;
    }


    @Override
    public boolean validateAction(String openElementTag, String closeElementTag, String keyFromCopiedContentStates) {

            log.info("Perform validate action within ForwardToPaymentValidation class");

            String redirectedUrl = webDriver.getCurrentUrl();
            log.info("Redirected url is: " + redirectedUrl);

            if(redirectedUrl.contains(copiedContentStates.get(keyFromCopiedContentStates))) {
                log.info("Redirect url is match with url from initial cache");
                return true;
            }

        return false;
    }

    @Override
    public boolean saveInitialState(String openString, String closeString, String keyForSavedInitialContent) {
        log.info("Save initial content in ForwardToPaymentValidation class");
        return super.saveInitialState(openString, closeString, keyForSavedInitialContent);
    }
}
