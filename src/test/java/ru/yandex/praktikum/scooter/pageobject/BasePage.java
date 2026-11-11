package ru.yandex.praktikum.scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    // Базовый класс Page Object: хранит WebDriver и явное ожидание.
    // При создании страницы ожидает появления её уникального элемента.
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    public BasePage(WebDriver driver, By uniquePageLocator){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        wait.until(ExpectedConditions.visibilityOfElementLocated(uniquePageLocator));
    }
}