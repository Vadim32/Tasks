package demidov.hh.selenium.tests;

import demidov.hh.selenium.AddItemsToCartValidation;
import demidov.hh.selenium.DeleteItemsFromCartValidation;
import demidov.hh.selenium.ForwardToPaymentValidation;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Created by Vadim on 23.05.2015.
 */
public class PaymentRedirectionTest {

    AddItemsToCartValidation addItem = new AddItemsToCartValidation();
    ForwardToPaymentValidation paymentValidation = new ForwardToPaymentValidation();


    //Тест сачала находит драйвер и поднимает соединение к браузеру
    @BeforeSuite
    public void beforeTestsDriverInit() {
        //Методу createWebConnection(...) достаточно указать путь к драйверу и страницу и "автоматизация" сама инстанциирует драйвер
        Assert.assertEquals(addItem.createWebConnection("D:\\Software_programming\\Selenium\\chromedriver.exe", "http://hh.ru/price"), true);
    }


    @AfterMethod
    public void freezExec() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test(priority = 1)
    public void save_Initial_Element_State_Before_Add_Item_To_Cart_Test() {
        //Методу saveInitialState(...) передаются аргументы:
        //1-й аргумент: Начало открывающего елемента(это может быть: div, button и т.д) контента для сохранения
        //2-й аргумент: Конец или закрывающий тег из html страницы контента для сохранеия
        //3-й аргумент: Ключ по которому потом вырезанный контент можно найти в кеше
        Assert.assertTrue(addItem.saveInitialState("<div class=\"HH-PriceCart g-hidden\">", "<div class=\"HH-PriceCart-Empty\">", "AddItem:HH-PriceCart"),
                "Saving initial content state for HH-PriceCart element");
        Assert.assertTrue(addItem.saveInitialState("<div class=\"HH-PriceCart-Empty\">", "</div></div></div>", "AddItem:HH-PriceCart-Empty"),
                "Saving initial content state for HH-PriceCart-Empty");
    }


    //Тест выполнения действия
    @Test(dependsOnMethods = {"save_Initial_Element_State_Before_Add_Item_To_Cart_Test"})
    public void addItemToCart() {
        WebElement elementTest = addItem.doAction("m-colspan2", "HH-Price-SpecialOffer-AddToCartButton");
        Assert.assertNotNull(elementTest);
        elementTest.click();
    }


    //Тест проверяет что состояние карзины изменилось
    @Test(dependsOnMethods = {"addItemToCart"})
    public void validate_Is_Cart_State_Changed_After_Insertion_Test() {
        Assert.assertEquals(addItem.validateAction("<div class=\"HH-PriceCart\">",
                "<div class=\"HH-PriceCart-Empty g-hidden\">",
                "AddItem:HH-PriceCart"), true);
    }


    /*Этот тест сохраняет адрес страницы оплаты на которую должен попасть пользователя после того, как он собрал заказ
    * Адрес берется из html кода страницы
    * */
    @Test(dependsOnMethods = {"validate_Is_Cart_State_Changed_After_Insertion_Test"})
    public void save_Initial_Url_Before_Payments()
    {
        Assert.assertTrue(paymentValidation.saveInitialState("</script><a href=\"", "/\" class=\"g-button m-button_green", "redirectedPageUrl"),
                "Saving initial url before redirect");

    }

    /*Этот тест переводит пользователя на страницу оформления оплаты*/
    @Test(dependsOnMethods = {"save_Initial_Url_Before_Payments"})
    public void makePayment()
    {
        WebElement element = paymentValidation.doAction("price-cart__proceed", "g-button");
        Assert.assertNotNull(element);
        element.click();
    }

    /*Этот тест проверяет url на который переходит пользователь совпадает с тем, что был указать на странице
    * добавления услуг в карзину*/
    @Test(dependsOnMethods = {"makePayment"})
    public void validate_url_after_Payment_Test()
    {
        /*Метод validationAction(...) переопредел в классе ForwardToPaymentValidation
        и поэтому можно только один аргумент - ключ к первоначатьному адресу страницы оплаты в кеше,
        куда должен перейти пользователь для оплаты выбранных услуг*/
        Assert.assertTrue(paymentValidation.validateAction(null, null, "redirectedPageUrl"), "Cheak for url expected and redirected to");
    }



}
