package ru.yandex.praktikum.scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    //Создаем переменные с типом WebDriver и WebDriverWait, они будут наследоваться в OrderPageScooter и MainPageScooter
    protected WebDriver driver;
    protected WebDriverWait wait;

    //конструктор, в который из классов-наследников будет передаваться ссылка на драйвер в памяти и переменная с локатором уникального элемента страныцм (нужен для ожиданий при первичном открытии страницы)
    public BasePage(WebDriver driver, By uniquePageLocator){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        //теперь каждый наследник будет автоматически запускать ожидаение при первичном открытии страницы
        wait.until(ExpectedConditions.visibilityOfElementLocated(uniquePageLocator));
    }
}