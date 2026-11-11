package ru.yandex.praktikum.scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrderPageScooter extends BasePage {

    //--------------------ЛОКАТОРЫ----------------------------

    //уникальный локатор для страницы заказа "Для кого самокат", передаем его в BasePage
    private static final By uniqueOrderPageLocator = By.xpath(".//div[contains(text(), 'Для кого самокат')]");

    private final By firstNameInputLocator = By.xpath(".//input[contains(@placeholder, 'Имя')]");
    private final By lastNameInputLocator = By.xpath(".//input[contains(@placeholder, 'Фамилия')]");
    private final By addressInputLocator = By.xpath(".//input[contains(@placeholder, 'Адрес')]");
    private final By metroDropdownLocator = By.xpath(".//input[contains(@placeholder, 'Станция метро')]");
    private final By phoneInputLocator = By.xpath(".//input[contains(@placeholder, 'Телефон')]");
    private final By nextButtonLocator = By.xpath(".//button[contains(text(), 'Далее')]");

    //будем передавать название станции церез шаблон и в методе формировать xpath
    private static final String METRO_STATION_TEMPLATE = ".//ul//button//div[text()='%s']/parent::button";

    //----------------------КОНСТРУКТОР-----------------------

    //конструктор в который мы будем передавать driver из тестового класса CreateOrderTest и в котором обращаемся к BasePage
    public OrderPageScooter(WebDriver driver) {
        super(driver, uniqueOrderPageLocator);
    }

    //----------------------МЕТОДЫ-----------------------------

    //Заполнить поле имя
    public void fillFirstNameInput(String firstName){
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(firstNameInputLocator));

        input.clear();
        input.sendKeys(firstName);
    }

    //Заполнить фамилия
    public void fillLastNameInput(String lastName){
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(lastNameInputLocator));

        input.clear();
        input.sendKeys(lastName);
    }

    //Заполнить поле адрес
    public void fillAddressInput(String address){
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(addressInputLocator));

        input.clear();
        input.sendKeys(address);
    }

    //клик на поле с выпадающим списком станций
    public void clickOnMetroDropdown(){
        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(metroDropdownLocator));
        dropdownElement.click();
    }

    //Клик на станцию метро в выпадающем списке
    public void clickOnMetroStationInDropdown(String stationName) {
        //подготавливаем xpath для кнопки конкретной станции
        String metroStationXpathFinal = String.format(METRO_STATION_TEMPLATE, stationName);
        By metroStationLocator = By.xpath(metroStationXpathFinal);

        //страхуемся, чтобы нужная кнопка в списке отрендерилась + забираем ее в переменную
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(metroStationLocator));

        //скроллим до элемента внутри выпадающего списка, по идее scrollIntoView там тоже должен сработать
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);

        //ждем чтобы закончился скролл и кликаем на станцию
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    //Заполнить поле телефон
    public void fillPhoneInput(String phone){
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(phoneInputLocator));

        input.clear();
        input.sendKeys(phone);
    }

    //Клик по кнопке далее
    public void clickOnNextButton() {
        WebElement nextButtonElement = wait.until(ExpectedConditions.elementToBeClickable(nextButtonLocator));
        nextButtonElement.click();
    }

    // объединили в один шаг, который заполняет все поля и кликает на кнопку далее
    public void fillFirstOrderForm(String firstName, String lastName, String address, String stationName, String phone){
        fillFirstNameInput(firstName);
        fillLastNameInput(lastName);
        fillAddressInput(address);
        clickOnMetroDropdown();
        clickOnMetroStationInDropdown(stationName);
        fillPhoneInput(phone);
        clickOnNextButton();
    }
}
