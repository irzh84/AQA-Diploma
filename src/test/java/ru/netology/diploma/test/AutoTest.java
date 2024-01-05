package ru.netology.diploma.test;

import org.junit.jupiter.api.*;
import ru.netology.diploma.data.DataHelper;
import ru.netology.diploma.data.SQLHelper;
import ru.netology.diploma.page.DashboardPage;
import ru.netology.diploma.page.PaymentGatePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.diploma.data.SQLHelper.cleanDatabase;


public class AutoTest {
    DashboardPage dashboardPage;
    PaymentGatePage paymentGatePage;

    @BeforeEach
    void setup() {
        cleanDatabase();
        dashboardPage = open("http://localhost:8080/", DashboardPage.class);
    }

    @Test
    @DisplayName("Успешная покупка с оплатой по карте, валидные данные")
    void shouldCardSuccessfulPayment() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkNotificationSuccessful();

        var actualVerifyOperationInDb = SQLHelper.getStatusPaymentGatePage();
        var expected = card.getStatus();
        assertEquals(expected, actualVerifyOperationInDb);
    }
}
