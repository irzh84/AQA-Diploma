package ru.netology.diploma.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diploma.data.DataHelper;
import ru.netology.diploma.data.SQLHelper;
import ru.netology.diploma.page.CreditGatePage;
import ru.netology.diploma.page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.netology.diploma.data.SQLHelper.cleanDatabase;

public class CreditGatePageAutoTest {

    DashboardPage dashboardPage;
    CreditGatePage creditGatePage;

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
    @DisplayName("Успешная покупка с оплатой в кредит, валидные данные")
    void shouldSuccessfulCreditGateApprovedCard() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkNotificationSuccessful();

        var actualVerifyOperationInDb = SQLHelper.getStatusCreditGatePage();
        var expected = card.getStatus();
        assertEquals(expected, actualVerifyOperationInDb);
    }

    @Test
    @DisplayName("Отказ в проведении операции с оплатой в кредит, отклоненная карта - баг")
    void shouldUnsuccessfulCreditGateDeclinedCard() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setCardNumberDeclined();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkNotificationUnsuccessful();

        var actualVerifyOperationInDb = SQLHelper.getStatusCreditGatePage();
        var expected = card.getStatus();
        assertEquals(expected, actualVerifyOperationInDb);
    }

    @Test
    @DisplayName("Отказ в проведении операции с оплатой в кредит, невалидный номер карты")
    void shouldUnsuccessfulCreditGateInvalidCardNumber() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.generateCardNumberInvalid();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkNotificationUnsuccessful();

        var actualVerifyOperationInDb = SQLHelper.getStatusPaymentGatePage();
        assertNull(actualVerifyOperationInDb);
    }

    @Test
    @DisplayName("Пустые поля ввода, оплата в кредит")
    void shouldCreditGateEmptyInputField() {
        creditGatePage = dashboardPage.selectPayByCredit();
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
        creditGatePage.checkRedNotificationRequiredField();
    }

    @Test
    @DisplayName("Пустое поле «Номер карты», оплата в кредит")
    void shouldCreditGateEmptyCardNumber() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setEmptyCardNumber();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Номер карты», заполненное нулями, оплата в кредит")
    void shouldCreditGateZeroCardNumber() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setZeroCardNumber();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkNotificationUnsuccessful();
    }

    @Test
    @DisplayName("Поле «Номер карты», заполненное буквами, оплата в кредит")
    void shouldCreditGateRandomLettersCardNumber() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setRandomLettersCardNumber();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Номер карты», заполненное спец. символами, оплата в кредит")
    void shouldCreditGateRandomSpecialSymbolsCardNumber() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setRandomSpecialSymbolsCardNumber();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Пустое поле «Месяц», оплата в кредит")
    void shouldCreditGateEmptyMonth() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.emptyField();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Месяц», заполненное нулями, оплата в кредит")
    void shouldCreditGateZeroMonth() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.zeroField();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationValidityPeriod();
    }

    @Test
    @DisplayName("Поле «Месяц», заполненное невалидными данными, оплата в кредит")
    void shouldCreditGateInvalidMonth() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateInvalidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationValidityPeriod();
    }

    @Test
    @DisplayName("Поле «Месяц», заполненное невалидными данными, короче указанного значения, оплата в кредит")
    void shouldCreditGateShortValueMonth() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generate1SymbolNumber();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Месяц», заполненное буквами, оплата в кредит")
    void shouldCreditGateRandomLettersMonth() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateRandomLetters();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Месяц», заполненное спец. символами, оплата в кредит")
    void shouldCreditGateRandomSpecialSymbolsMonth() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateRandomSpecialSymbols();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Пустое поле «Год», оплата в кредит")
    void shouldCreditGateEmptyYear() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.emptyField();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Год», заполненное нулями, оплата в кредит")
    void shouldCreditGateZeroYear() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.zeroField();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationTimeExpiredYear();
    }

    @Test
    @DisplayName("Поле «Год», заполненное невалидными данными раньше текущего года, оплата в кредит")
    void shouldCreditGateInvalidEarlyYear() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateInvalidEarlyYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationTimeExpiredYear();
    }

    @Test
    @DisplayName("В поле «Год» указано значение, которое больше срока действия карты (5 лет), оплата в кредит")
    void shouldCreditGateInvalidMore5Year() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateInvalidMore5Year();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationValidityPeriod();
    }

    @Test
    @DisplayName("Поле «Год», заполненное буквами, оплата в кредит")
    void shouldCreditGateRandomLettersYear() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateRandomLetters();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Год», заполненное спец. символами, оплата в кредит")
    void shouldCreditGateRandomSpecialSymbolsYear() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateRandomSpecialSymbols();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Пустое поле «Владелец», оплата в кредит")
    void shouldCreditGateEmptyName() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.emptyField();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationRequiredField();
    }

    @Test
    @DisplayName("Поле «Владелец», заполненное нулями, оплата в кредит - баг")
    void shouldCreditGateZeroName() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.zeroField();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Владелец», заполненное числами, оплата в кредит - баг")
    void shouldCreditGateNumbersName() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidCvc();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Владелец», заполненное именем на кириллице, оплата в кредит - баг")
    void shouldCreditGateCyrillicName() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateCyrillicName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Владелец», заполненное спец. символами, оплата в кредит - баг")
    void shouldCreditGateSpecialSymbolsName() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateRandomSpecialSymbols();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Владелец», заполненное 1 символом на латинице, оплата в кредит - баг")
    void shouldCreditGate1SymbolName() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generate1SymbolName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «Владелец», заполненное 101 символом на латинице, оплата в кредит - баг")
    void shouldCreditGate101SymbolsName() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generate101SymbolsName();
        var cvc = DataHelper.generateValidCvc();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Пустое поле «CVC/CVV», оплата в кредит")
    void shouldCreditGateEmptyCvc() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.emptyField();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «CVC/CVV», заполненное нулями, оплата в кредит - баг")
    void shouldCreditGateZeroCvc() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.zeroField();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «CVC/CVV», заполненное 1 символом, оплата в кредит")
    void shouldCreditGate1SymbolCvc() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generate1SymbolNumber();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «CVC/CVV», заполненное буквами, оплата в кредит")
    void shouldCreditGateRandomLettersCvc() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateRandomLetters();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }

    @Test
    @DisplayName("Поле «CVC/CVV», заполненное спец. символами, оплата в кредит")
    void shouldCreditGateRandomSpecialSymbolsCvc() {
        creditGatePage = dashboardPage.selectPayByCredit();
        var card = DataHelper.setValidCardNumberApproved();
        var month = DataHelper.generateValidMonth();
        var year = DataHelper.generateValidYear();
        var name = DataHelper.generateValidName();
        var cvc = DataHelper.generateRandomSpecialSymbols();
        creditGatePage.fillForm(card, month, year, name, cvc);
        creditGatePage.clickButtonContinue();
        creditGatePage.checkRedNotificationUnderTheFieldWrongFormat();
    }
}
