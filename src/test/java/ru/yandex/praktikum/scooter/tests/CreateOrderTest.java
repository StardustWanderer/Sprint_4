package ru.yandex.praktikum.scooter.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.scooter.pageobject.MainPageScooter;
import ru.yandex.praktikum.scooter.pageobject.OrderPageScooter;
import ru.yandex.praktikum.scooter.pageobject.RentPageScooter;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest {
    //MainPage
    private final int orderButtonIndex;
    //OrderPage
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String stationName;
    private final String phone;
    //RentPage
    private final String date;
    private final String period;
    private final String color;
    private final String comment;

    public CreateOrderTest (int orderButtonIndex,
                            String firstName,
                            String lastName,
                            String address,
                            String stationName,
                            String phone,
                            String date,
                            String period,
                            String color,
                            String comment
                            ) {
        this.orderButtonIndex = orderButtonIndex;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.stationName = stationName;
        this.phone = phone;
        this.date = date;
        this.period = period;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters (name = "Проверка создания заказа через кнопку заказа {0}. Клиент: {1} {2}")
    public static Object[][] getOrderData() {
        return new Object[][] {
                {
                        1,
                        "Ян",
                        "Ли",
                        "ул. Мира, 1",
                        "Черкизовская",
                        "89991112233",
                        "30.06.2026",
                        "сутки",
                        "black",
                        "Позвоните за час"
                },
                {
                        2,
                        "Екатерина",
                        "Владимировна",
                        "наб. Академика Королева, д. 12, кв. 45",
                        "Сокольники",
                        "89995554422",
                        "01.07.2026",
                        "семеро суток",
                        "grey",
                        ""
                }
        };
    }

    @Test
    public void shouldDisplayConfirmationModalAfterOrder() {
        MainPageScooter mainPage = new MainPageScooter(driver);
        mainPage.clickOrderButton(orderButtonIndex);

        OrderPageScooter orderPage = new OrderPageScooter(driver);
        orderPage.fillFirstOrderForm(firstName, lastName, address, stationName, phone);

        RentPageScooter rentPage = new RentPageScooter(driver);
        rentPage.fillRentPageForm(date, period, color, comment);
        rentPage.clickOnYesButtonInOrderConfirmationModal();

        boolean isDisplayedActual = rentPage.isOrderConfirmationModalHeaderDisplayed();

        Assert.assertTrue("Окно 'Заказ оформлен' не появилось", isDisplayedActual);
    }
}

