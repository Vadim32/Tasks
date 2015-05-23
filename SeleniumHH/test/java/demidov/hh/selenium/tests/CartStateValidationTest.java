package demidov.hh.selenium.tests;

import demidov.hh.selenium.AddItemsToCartValidation;
import demidov.hh.selenium.DeleteItemsFromCartValidation;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Created by Vadim on 23.05.2015.
 */
public class CartStateValidationTest {


    AddItemsToCartValidation addItem = new AddItemsToCartValidation();
    DeleteItemsFromCartValidation deleteItem = new DeleteItemsFromCartValidation();


    //Тест сачала находит драйвер и поднимает соединение к брайзеру
    @BeforeSuite
    public void beforeTestDriverInit()
    {
        //Методу createWebConnection(...) достаточно указать путь к драйверу и страницу и "автоматизация" сама инстанциирует драйвер
        Assert.assertEquals(addItem.createWebConnection("D:\\Software_programming\\Selenium\\chromedriver.exe", "http://hh.ru/price"), true);
    }


    @AfterMethod
    public void freezExec()
    {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    /*
     * Этот тест используя методы частичной автоматизации сохраняет контент до выполнения действия(в данном случае вставки)
     * для последующей простой валидацией на равенство контента
     * Сохранения первоначального контента происходит в кеш(HashMap) абстрактного класса BaseHHTesting
     *  */
    @Test(priority = 1)
    public void save_Initial_Element_State_Before_Add_Item_To_Cart_Test()
    {
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
    public void addItemToCart()
    {
        WebElement elementTest = addItem.doAction("m-colspan2", "HH-Price-SpecialOffer-AddToCartButton");
        Assert.assertNotNull(elementTest);
        elementTest.click();
    }

    //Тест проверяет что состояние карзины изменилось
    @Test(dependsOnMethods = {"addItemToCart"})
    public void validate_Is_Cart_State_Changed_After_Insertion_Test()
    {
        Assert.assertEquals(addItem.validateAction("<div class=\"HH-PriceCart\">",
                                                   "<div class=\"HH-PriceCart-Empty g-hidden\">",
                                                    "AddItem:HH-PriceCart"), true);
    }


    /*
     * Этот тест используя методы частичной автоматизации сохраняет контент до выполнения действия(в данном случае удаления)
     * для последующей простой валидации на равенство контента
     * Сохранения первоначального контента происходит в кеш(HashMap) абстрактного класса BaseHHTesting
     *  */
    @Test(dependsOnMethods = {"validate_Is_Cart_State_Changed_After_Insertion_Test"})
    public void save_Initial_Element_State_Before_Delete_Item_To_Cart_Test()
    {
        //Методу saveInitialState(...) передаются аргументы:
        //1-й аргумент: Начало открывающего елемента(это может быть: div, button и т.д) контента для сохранения
        //2-й аргумент: Конец или закрывающий тег из html страницы контента для сохранеия
        //3-й аргумент: Ключ по которому потом вырезанный контент можно найти в кеше
        Assert.assertTrue(deleteItem.saveInitialState("<div class=\"HH-PriceCart\">", "<div class=\"HH-PriceCart-Empty g-hidden\">", "DeleteItem:HH-PriceCart"),
                "Saving initial content state for HH-PriceCart element");
        Assert.assertTrue(deleteItem.saveInitialState("<div class=\"HH-PriceCart-Empty g-hidden\">", "</div></div></div>", "DeleteItem:HH-PriceCart-Empty"),
                "Saving initial content state for HH-PriceCart-Empty g-hidden element");
    }


    //Тест выполняет действие
    @Test(dependsOnMethods = {"save_Initial_Element_State_Before_Delete_Item_To_Cart_Test"})
    public void deleteItemFromCart()
    {
        WebElement elementTest = deleteItem.doAction("price-cart", "HH-PriceCart-ItemRemover");
        Assert.assertNotNull(elementTest);
        elementTest.click();
    }

    //Этот тест падает!!!
    //Тест помог обнаружить, что вставленные элементы после удаления частично остаются в корзине.
    //Метод vlidateAction() сравнивает измененное состояние контента после вставки и после удаления.
    @Test(dependsOnMethods = {"deleteItemFromCart"})
    public void validate_Is_Cart_State_Changed_After_Deletion_Test()
    {
        //Метод validateAction(...) сравнивает новое(данное в этот момент) состояние карзины с запрошеным из кеша состоянием, которое было сохранено раньше
        //1-й аргумент: Начало открывающего елемента(это может быть: div, button и т.д) контента для проверки с сохраненым ранее в кеше
        //2-й аргумент: Конец или закрывающий тег из html страницы контента для проверки с сохраненым ранее в кеше
        //3-й аргумент: Ключ по которому можно найти и получить ранее сохраненый контент можно найти в кеше
        Assert.assertEquals(deleteItem.validateAction("<div class=\"HH-PriceCart-Empty\">", "</div></div></div>", "DeleteItem:HH-PriceCart-Empty"), true);
        Assert.assertEquals(deleteItem.validateAction("<div class=\"HH-PriceCart\">", "<div class=\"HH-PriceCart-Empty g-hidden\">", "AddItem:HH-PriceCart"), true);
    }


    @AfterSuite
    public void quitExec()
    {
        addItem.realiseWebDriver();
    }

}
