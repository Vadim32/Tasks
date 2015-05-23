package demidov.hh.selenium;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


/**
 * Created by Vadim Demidov on 23.05.2015.
 * This class is responsible for delete item from cart and validate state of cart element after all
 */
public class DeleteItemsFromCartValidation extends BaseHHTesting {


    public static Logger log = Logger.getLogger(DeleteItemsFromCartValidation.class);


    //This xpath exp. checks first on root element in html dom model and in case if root element exist then
    // looks for child element in the root element
    @Override
    public WebElement doAction(String rootElementName, String childElement) {

        log.info("Find and prepare element for deletion with " + rootElementName + " and " + childElement);

        element = webDriver.findElement(By.xpath("//div[contains(@class, '" + rootElementName +
                                                     "')]//small[contains(@class, '" + childElement + "')]"));
        return element;
    }



    @Override
    public boolean validateAction(String openElementtag, String closeElementTag, String keyFromCopiedContentStates) {

        log.info("Perform validate action within DeleteItemsFromCartValidation class");

        String changedPageContent =  webDriver.getPageSource();
        log.debug("After delete action html page content is : \n" + changedPageContent);

        String presentCartContentTemp = StringUtils.substringBetween(changedPageContent, openElementtag, closeElementTag);
        log.debug("Present cart content: \n" + presentCartContentTemp);

        //This case test whether the state of item cart is changes back to it's initial state
        // after deleteItem manipulations
        if(copiedContentStates.get(keyFromCopiedContentStates).equals(presentCartContentTemp)) {
            log.info("Cart contents for empty cart are equal after delete");
            return true;
        }

        return false;
    }

    @Override
    public boolean saveInitialState(String openString, String closeString, String keyForSavedInitialContent) {
        log.info("Save initial content in DeleteItemsFromCartValidation class");
        return super.saveInitialState(openString, closeString, keyForSavedInitialContent);
    }
}
