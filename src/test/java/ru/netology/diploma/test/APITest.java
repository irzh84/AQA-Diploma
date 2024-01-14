package ru.netology.diploma.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.diploma.data.APIHelper;
import ru.netology.diploma.data.DataHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class APITest {
    @Test
    @DisplayName("API, оплата по карте, валидный номер карты, ответ сервера 200")
    void should200ValidCardNumberApprovedPaymentGate() {
        var card = DataHelper.postAPIValidCardNumberApproved();
        var actual = APIHelper.returnResponsePaymentGate200(card);
        var cardStatus = DataHelper.setValidCardNumberApproved();
        var expected = cardStatus.getStatus();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("API, оплата по карте, невалидный номер карты, отклоненная карта, ответ сервера 500 - баг")
    void should500InvalidCardNumberDeclinedPaymentGate() {
        var card = DataHelper.postAPIInvalidCardNumberDeclined();
        APIHelper.PaymentGate500(card);
    }

    @Test
    @DisplayName("API, оплата по карте, невалидный номер карты, ответ сервера 500")
    void should500InvalidCardNumberPaymentGate() {
        var card = DataHelper.postAPIInvalidCardNumber();
        APIHelper.PaymentGate500(card);
    }

    @Test
    @DisplayName("API, оплата в кредит, валидный номер карты, ответ сервера 200")
    void should200ValidCardNumberApprovedCreditGate() {
        var card = DataHelper.postAPIValidCardNumberApproved();
        var actual = APIHelper.returnResponseCreditGate200(card);
        var cardStatus = DataHelper.setValidCardNumberApproved();
        var expected = cardStatus.getStatus();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("API, оплата в кредит, невалидный номер карты, отклоненная карта, ответ сервера 500 - баг")
    void should500InvalidCardNumberDeclinedCreditGate() {
        var card = DataHelper.postAPIInvalidCardNumberDeclined();
        APIHelper.CreditGate500(card);
    }

    @Test
    @DisplayName("API, оплата в кредит, невалидный номер карты, ответ сервера 500")
    void should500InvalidCardNumberCreditGate() {
        var card = DataHelper.postAPIInvalidCardNumber();
        APIHelper.CreditGate500(card);
    }
}
