package ru.netology.diploma.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diploma.data.DataHelper;
import ru.netology.diploma.data.SQLHelper;
import ru.netology.diploma.page.DashboardPage;
import ru.netology.diploma.page.PaymentGatePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.netology.diploma.data.SQLHelper.cleanDatabase;

public class PaymentGatePageAutoTest {
    DashboardPage dashboardPage;
    PaymentGatePage paymentGatePage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        cleanDatabase();
        dashboardPage = open("http://localhost:8080/", DashboardPage.class);
    }

    @Test
    @DisplayName("Успешная покупка с оплатой по карте, валидные данные")
    void shouldSuccessfulPaymentGateApprovedCard() {
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

    @Test
    @DisplayName("Отказ в проведении операции с оплатой по карте, отклоненная карта - баг")
    void shouldUnsuccessfulPaymentGateDeclinedCard() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setCardNumberDeclined();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkNotificationUnsuccessful();

        var actualVerifyOperationInDb = SQLHelper.getStatusPaymentGatePage();
        var expected = card.getStatus();
        assertEquals(expected, actualVerifyOperationInDb);
    }

    @Test
    @DisplayName("Отказ в проведении операции с оплатой по карте, невалидный номер карты")
    void shouldUnsuccessfulPaymentGateInvalidCardNumber() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.generateCardNumberInvalid();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkNotificationUnsuccessful();

        var actualVerifyOperationInDb = SQLHelper.getStatusPaymentGatePage();
        assertNull(actualVerifyOperationInDb);
    }

    @Test
    @DisplayName("Пустые поля ввода, оплата по карте")
    void shouldPaymentGateEmptyInputField() {
        paymentGatePage = dashboardPage.selectPayByCard();
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
        paymentGatePage.checkRedNotificationRequiredField();
    }

    @Test
    @DisplayName("Пустое поле «Номер карты», оплата по карте")
    void shouldPaymentGateEmptyCardNumber() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setEmptyCardNumber();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Номер карты», заполненное нулями, оплата по карте")
    void shouldPaymentGateZeroCardNumber() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setZeroCardNumber();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkNotificationUnsuccessful();
    }

    @Test
    @DisplayName("Поле «Номер карты», заполненное буквами, оплата по карте")
    void shouldPaymentGateRandomLettersCardNumber() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setRandomLettersCardNumber();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Номер карты», заполненное спец. символами, оплата по карте")
    void shouldPaymentGateRandomSpecialSymbolsCardNumber() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setRandomSpecialSymbolsCardNumber();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Пустое поле «Месяц», оплата по карте")
    void shouldPaymentGateEmptyMonth() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.emptyField();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Месяц», заполненное нулями, оплата по карте")
    void shouldPaymentGateZeroMonth() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.zeroField();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationValidityPeriod();
    }

    @Test
    @DisplayName("Поле «Месяц», заполненное невалидными данными, оплата по карте")
    void shouldPaymentGateInvalidMonth() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateInvalidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationValidityPeriod();
    }

    @Test
    @DisplayName("Поле «Месяц», заполненное невалидными данными, короче указанного значения, оплата по карте")
    void shouldPaymentGateShortValueMonth() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generate1SymbolNumber();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Месяц», заполненное буквами, оплата по карте")
    void shouldPaymentGateRandomLettersMonth() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateRandomLetters();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Месяц», заполненное спец. символами, оплата по карте")
    void shouldPaymentGateRandomSpecialSymbolsMonth() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateRandomSpecialSymbols();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Пустое поле «Год», оплата по карте")
    void shouldPaymentGateEmptyYear() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.emptyField();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Год», заполненное нулями, оплата по карте")
    void shouldPaymentGateZeroYear() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.zeroField();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationTimeExpiredYear();
    }

    @Test
    @DisplayName("Поле «Год», заполненное невалидными данными раньше текущего года, оплата по карте")
    void shouldPaymentGateInvalidEarlyYear() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateInvalidEarlyYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationTimeExpiredYear();
    }

    @Test
    @DisplayName("В поле «Год» указано значение, которое больше срока действия карты (5 лет), оплата по карте")
    void shouldPaymentGateInvalidMore5Year() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateInvalidMore5Year();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationValidityPeriod();
    }

    @Test
    @DisplayName("Поле «Год», заполненное буквами, оплата по карте")
    void shouldPaymentGateRandomLettersYear() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateRandomLetters();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Год», заполненное спец. символами, оплата по карте")
    void shouldPaymentGateRandomSpecialSymbolsYear() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateRandomSpecialSymbols();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Пустое поле «Владелец», оплата по карте")
    void shouldPaymentGateEmptyName() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.emptyField();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationRequiredField();
    }

    @Test
    @DisplayName("Поле «Владелец», заполненное нулями, оплата по карте - баг")
    void shouldPaymentGateZeroName() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.zeroField();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Владелец», заполненное числами, оплата по карте - баг")
    void shouldPaymentGateNumbersName() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidCvc();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Владелец», заполненное именем на кириллице, оплата по карте - баг")
    void shouldPaymentGateCyrillicName() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateCyrillicName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Владелец», заполненное спец. символами, оплата по карте - баг")
    void shouldPaymentGateSpecialSymbolsName() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateRandomSpecialSymbols();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Владелец», заполненное 1 символом на латинице, оплата по карте - баг")
    void shouldPaymentGate1SymbolName() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generate1SymbolName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Владелец», заполненное 101 символом на латинице, оплата по карте - баг")
    void shouldPaymentGate101SymbolsName() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generate101SymbolsName();
        var cvc = DataHelper.generateValidCvc();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Пустое поле «CVC/CVV», оплата по карте")
    void shouldPaymentGateEmptyCvc() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.emptyField();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «CVC/CVV», заполненное нулями, оплата по карте - баг")
    void shouldPaymentGateZeroCvc() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.zeroField();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «CVC/CVV», заполненное 1 символом, оплата по карте")
    void shouldPaymentGate1SymbolCvc() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generate1SymbolNumber();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «CVC/CVV», заполненное буквами, оплата по карте")
    void shouldPaymentGateRandomLettersCvc() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateRandomLetters();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «CVC/CVV», заполненное спец. символами, оплата по карте")
    void shouldPaymentGateRandomSpecialSymbolsCvc() {
        paymentGatePage = dashboardPage.selectPayByCard();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateRandomSpecialSymbols();
        paymentGatePage.fillForm(card, month, year, name, cvc);
        paymentGatePage.clickButtonContinue();
        paymentGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }
}
