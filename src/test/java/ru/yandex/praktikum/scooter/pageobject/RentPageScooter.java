package ru.yandex.praktikum.scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.openqa.selenium.Keys.ENTER;

public class RentPageScooter extends BasePage {

    //--------------------ЛОКАТОРЫ----------------------------

    //ЛОКАТОРЫ ПОЛЕЙ НА СТРАНИЦЕ "РЕНТА"

    //локатор для уникального элемента "Про аренду"
    private static final By uniqueRentPageLocator = By.xpath(".//div[contains(text(), 'Про аренду')]");
    //для поля дата доставки
    private final By deliveryDateInput = By.xpath(".//input[contains(@placeholder, 'Когда привезти самокат')]");
    //для поля с выпадающим с списком с опциями длительности ренты
    private final By rentalPeriodDropdown = By.xpath(".//div[@class='Dropdown-control']");

    //в случае с выпадающим списком для выбора продолжительности аренды воспользуемся шаблонами
    private static final String RENTAL_PERIOD_DROPDOWN_OPTION_TEMPLATE = ".//div[@class='Dropdown-option' and text()='%s']";
    //для чекбокса с цветом тоже лучше сделать шаблон, т.к. нам нужно будет выбирать через параметризацию цвет самоката
    private static final String COLOR_CHECKBOX_TEMPLATE = ".//label[@for='%s']";

    //для поля комментарий
    private final By commentForDeliveryInput = By.xpath(".//input[contains(@placeholder, 'Комментарий для курьера')]");
    //кнопка "Заказать" внизу страницы рента
    private final By orderButtonOnRentPageLocator = By.xpath(".//button[text()='Заказать' and contains(@class, 'Button_Middle')]");

    //ЛОКАТОРЫ МОДАЛЬНЫХ ОКОН ПОДТВЕРЖДЕНИЯ ЗАКАЗА И УСПЕШНОГО ОФОРМЛЕНИЯ ЗАКАЗА

    //уникальный локатор для модалного окна подтверждения заказа
    private static final By uniqueOrderConfirmationModalLocator = By.xpath(".//div[contains(@class, 'Order_ModalHeader') and contains(text(), 'Хотите оформить заказ?')]");

    // Кнопка "Да" в модальном окне подтверждения заказа
    private final By yesButtonOrderConfirmationModalLocator = By.xpath(".//div[contains(@class, 'Order_Buttons')]//button[text()='Да']");

    // Заголовок модального окна об успешном создании заказа, его будем использовать для проверки в тесте
    private final By confirmationHeaderOrderModalLocator = By.xpath(".//div[contains(@class, 'Order_ModalHeader') and text()='Заказ оформлен']");

    //----------------------КОНСТРУКТОР-----------------------


    public RentPageScooter(WebDriver driver){
        super(driver, uniqueRentPageLocator);
    }

    //----------------------МЕТОДЫ----------------------------

    //МЕТОДЫ ДЛЯ СТРАНИЦЫ РЕНТЫ

    //заполняем поле с датой доставки
    public void fillDeliveryDateInput(String dateData) {
        WebElement deliveryDateInputElement = wait.until(ExpectedConditions.elementToBeClickable(deliveryDateInput));
        deliveryDateInputElement.clear();
        deliveryDateInputElement.sendKeys(dateData);
        deliveryDateInputElement.sendKeys(ENTER);
    }

    //клик на поле "срок аренды" с выпадающим списком
    public void clickOnRentalPeriodDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodDropdown)).click();
    }

    //клик на определенную опцию в выпадающем списке
    public void clickOnRentalPeriodDropdownOption(String rentalPeriod) {
        String rentalPeriodDropdownOptionXpath = String.format(RENTAL_PERIOD_DROPDOWN_OPTION_TEMPLATE, rentalPeriod);
        By rentalPeriodDropdownOptionLocator = By.xpath(rentalPeriodDropdownOptionXpath);

        WebElement dropdownOptionElement = wait.until(ExpectedConditions.presenceOfElementLocated(rentalPeriodDropdownOptionLocator));

        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", dropdownOptionElement);

        wait.until(ExpectedConditions.elementToBeClickable(dropdownOptionElement)).click();
    }

    //выбор цвета самоката в чекбоксе
    public void chooseColorCheckBox(String colorData) {
        String colorCheckBoxXpath = String.format(COLOR_CHECKBOX_TEMPLATE, colorData);
        By colorCheckBoxLocator = By.xpath(colorCheckBoxXpath);

        WebElement checkBoxElement = wait.until(ExpectedConditions.elementToBeClickable(colorCheckBoxLocator));
        checkBoxElement.click();
    }

    //комментарий для курьера
    public void fillDeliveryCommentInput(String deliveryCommentData) {
        WebElement deliveryCommentInput = wait.until(ExpectedConditions.elementToBeClickable(commentForDeliveryInput));

        deliveryCommentInput.clear();
        deliveryCommentInput.sendKeys(deliveryCommentData);
    }

    //клик на кнопку заказать на странице ренты
    public void clickOnOrderButtonOnRentPage () {
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonOnRentPageLocator)).click();
    }

    //заполняем поля формы на странице про ренту и кликаем на кнопку заказать
    public void fillRentPageForm(String date, String period, String color, String comment) {
        fillDeliveryDateInput(date);
        clickOnRentalPeriodDropdown();
        clickOnRentalPeriodDropdownOption(period);
        chooseColorCheckBox(color);
        fillDeliveryCommentInput(comment);
        clickOnOrderButtonOnRentPage();
    }

    //МЕТОДЫ ДЛЯ МОДАЛЬНЫХ ОКОН

    // Клик по кнопке "Да" в модалке подтверждения заказа
    public void clickOnYesButtonInOrderConfirmationModal() {
        wait.until(ExpectedConditions.presenceOfElementLocated(uniqueOrderConfirmationModalLocator));
        wait.until(ExpectedConditions.elementToBeClickable(yesButtonOrderConfirmationModalLocator)).click();
    }

    // Проверка, что финальное окно успешно отобразилось (возвращает true/false)
    public boolean isOrderConfirmationModalHeaderDisplayed () {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationHeaderOrderModalLocator)).isDisplayed();
    }
}
