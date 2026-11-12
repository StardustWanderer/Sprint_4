package ru.yandex.praktikum.scooter.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

//абстрактный класс, чтобы junit не пытался запустить его как тест
public abstract class BaseTest {
    protected WebDriver driver;

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private static final String BROWSER = System.getProperty("browser", "firefox");

    @Before
    public void setUp(){
        switch(BROWSER.toLowerCase()){
            case "chrome":
                driver = new ChromeDriver();
                break;

            case "firefox":
                driver = new FirefoxDriver();
                break;
        }

        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}