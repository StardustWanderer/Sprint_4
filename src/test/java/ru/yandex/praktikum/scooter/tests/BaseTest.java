package ru.yandex.praktikum.scooter.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

//абстрактный класс, чтобы junit не пытался запустить его как тест
public abstract class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp(){
        //здесь создается браузер перед началом теста
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        // максимизируем окно, чтобы верстка сайта всегда была десктопной и стабильной
        driver.manage().window().maximize();
        //открываем страниц
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @After
    public void teardown() {
        driver.quit();
    }
}