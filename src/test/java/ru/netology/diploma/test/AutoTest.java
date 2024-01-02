package ru.netology.diploma.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.diploma.data.DataHelper;
import ru.netology.diploma.page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;

public class AutoTest {
    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        dashboardPage = open("http://localhost:8080/", DashboardPage.class);
    }

    @Test
    @DisplayName("Успешная покупка с оплатой по карте, валидные данные")
    void shouldCardSuccessfulPayment() {
        dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonthInValidYear();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        dashboardPage.fillForm(card, month, year, name, cvc);
        dashboardPage.clickButtonContinue();
        dashboardPage.checkNotificationSuccessful();
    }
}
