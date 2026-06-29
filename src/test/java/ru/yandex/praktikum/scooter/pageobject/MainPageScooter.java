package ru.yandex.praktikum.scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageScooter extends BasePage {

    //--------------------ЛОКАТОРЫ----------------------------
    //уникальный локатор для главной страницы, передаем его в BasePage
    private static final By uniqueMainPageLocator = By.xpath(".//div[contains(@class, 'Home_Header')]");

    //Локаторы для кнопок заказа

    private final String orderButtonTemplate = "(.//button[text()='Заказать'])[%d]";

    private final By orderButtonHeader = By.xpath(".//div[contains(@class, 'Header_Nav')]//button[text()='Заказать']");
    private final By orderButtonMiddle = By.xpath(".//div[contains(@class, 'Home_FinishButton')]//button[text()='Заказать']");

    //Локаторы для
    //т.к. у нас 8 одинаковых элементов в аккордионе (хэдингов и панелей с текстом), пришлось подставлять в xpath значения через шаблоны
    private final String headingLocatorXpathTemplate = ".//div[@id='accordion__heading-%d']";
    private final String panelLocatorXpathTemplate = ".//div[@id='accordion__panel-%d']/p";

    //----------------------КОНСТРУКТОР-----------------------

    //конструктор в который мы будем передавать driver из тестового класса и в котором обращаемся к BasePage
    public MainPageScooter(WebDriver driver) {
        super(driver, uniqueMainPageLocator);
        this.driver = driver;
    }

    //----------------------МЕТОДЫ----------------------------

    //клик на кнопку заказа в хэдэре или посередине страницы, в зависимости от переданного индекса
    public void clickOrderButton(int buttonIndex) {
        String orderButtonXpath = String.format(orderButtonTemplate, buttonIndex);
        By orderButtonLocator = By.xpath(orderButtonXpath);

        // Ждем появления в DOM
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(orderButtonLocator));

        // Скроллим (для верхней кнопки это ничему не повредит, а для нижней — поднимет в центр экрана)
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        // Проверяем кликабельность и нажимаем
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    //клик по хэдингу аккордиона
    public void clickAccordionHeading(int index) {
        //собираем локатор
        String headingLocatorXpathFinal = String.format(headingLocatorXpathTemplate, index);
        By headingLocator = By.xpath(headingLocatorXpathFinal);

        //подстраховка, чтобы прогрузился хэдинг
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(headingLocator));

        //скролл до хэдинга
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);

        //ожидание, чтобы хэдинг был кликабельный и кликаем
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    //получение текста из панели аккордиона
public String getAccordionPanelText(int index) {

        //собираем локатор
        String panelLocatorXpathFinal = String.format(panelLocatorXpathTemplate, index);
        By panelLocator = By.xpath(panelLocatorXpathFinal);

        //ждем чтобы элемент был виден и возвращаем текст
        return wait.until(ExpectedConditions.visibilityOfElementLocated(panelLocator)).getText();
    }
}
