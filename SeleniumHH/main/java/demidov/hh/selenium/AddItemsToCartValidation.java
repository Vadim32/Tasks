package demidov.hh.selenium;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


/**
 * Created by Vadim Deimidov on 23.05.2015.
 * This class is responsible for addItem to cart and validate is state of cart changed after all
 */
public class AddItemsToCartValidation extends BaseHHTesting {


    public static  Logger log = Logger.getLogger(AddItemsToCartValidation.class);


    @Override
    public boolean createWebConnection(String webDriverPath, String url) {
        log.info("Create web connection with driver in AddItemsToCartValidation class");
        return super.createWebConnection(webDriverPath, url);
    }



    //This xpath exp. checks first on root element in html dom model and in case if root element exist then
    // looks for child element in the root element
    @Override
    public WebElement doAction(String rootElementName, String childElement) {

        log.info("Find and prepare element for adding item with " + rootElementName + " and " + childElement);

        element = webDriver.findElement(By.xpath("//div[contains(@class, '" + rootElementName +
                                                 "')]//button[contains(@class, '" + childElement + "')]"));
        return element;
    }




    @Override
    public boolean validateAction(String openElementTag, String closeElementTag, String keyFromCopiedContentStates) {

        log.info("Perform validate action within AddItemsToCartValidation class");

        String changedPageContent =  webDriver.getPageSource();
        log.debug("Html page code from AddItemsToCartValidation class: \n" + changedPageContent);

        log.debug("Get content beetween tags: " + openElementTag + " and " + closeElementTag);
        changedPageContent = StringUtils.substringBetween(changedPageContent, openElementTag, closeElementTag);
        log.debug("Extracted content is: \n" + changedPageContent);

        //This case cheaks whether state of Cart is changed after addItem manipulations
        if(copiedContentStates.get(keyFromCopiedContentStates).equals(changedPageContent)) {
            log.info("After adding element contents equals then noting happend");
            return false;
        }

        return true;
    }


    @Override
    public boolean saveInitialState(String openString, String closeString, String extractedStringKey) {
        log.info("Starting saveInitialState in AddItemsToCartValidation class");
        return super.saveInitialState(openString, closeString, extractedStringKey);
    }


    @Override
    public boolean realiseWebDriver() {

        log.info("Realizing web driver within AddItemsToCartValidation class");

        return super.realiseWebDriver();
    }

}
