package ru.netology.diploma.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.diploma.data.APIHelper;
import ru.netology.diploma.data.DataHelper;
import ru.netology.diploma.data.SQLHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class APITest {
    @Test
    @DisplayName("API, оплата по карте, валидный номер карты, ответ сервера 200")
    void should200ValidCardNumberApprovedPaymentGate() {
        var card = DataHelper.postAPIValidCardNumberApproved();
        String path = "/api/v1/pay";
        int apiStatus = 200;
        APIHelper.returnResponse(card, path, apiStatus);

        var actualVerifyOperationInDb = SQLHelper.getStatusPaymentGatePage();
        var expected = "APPROVED";
        assertEquals(expected, actualVerifyOperationInDb);
    }

    @Test
    @DisplayName("API, оплата по карте, невалидный номер карты, отклоненная карта, ответ сервера 400 - баг")
    void should400InvalidCardNumberDeclinedPaymentGate() {
        var card = DataHelper.postAPIInvalidCardNumberDeclined();
        String path = "/api/v1/pay";
        int apiStatus = 500;
        APIHelper.returnResponse(card, path, apiStatus);

        var actualVerifyOperationInDb = SQLHelper.getStatusPaymentGatePage();
        var expected = "DECLINED";
        assertEquals(expected, actualVerifyOperationInDb);
    }

    @Test
    @DisplayName("API, оплата по карте, невалидный номер карты, ответ сервера 400")
    void should400InvalidCardNumberPaymentGate() {
        var card = DataHelper.postAPIInvalidCardNumber();
        String path = "/api/v1/pay";
        int apiStatus = 500;
        var actual = APIHelper.returnResponse(card, path, apiStatus);
        var expected = "400 Bad Request";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("API, оплата в кредит, валидный номер карты, ответ сервера 200")
    void should200ValidCardNumberApprovedCreditGate() {
        var card = DataHelper.postAPIValidCardNumberApproved();
        String path = "/api/v1/credit";
        int apiStatus = 200;
        APIHelper.returnResponse(card, path, apiStatus);

        var actualVerifyOperationInDb = SQLHelper.getStatusCreditGatePage();
        var expected = "APPROVED";
        assertEquals(expected, actualVerifyOperationInDb);
    }

    @Test
    @DisplayName("API, оплата в кредит, невалидный номер карты, отклоненная карта, ответ сервера 400 - баг")
    void should400InvalidCardNumberDeclinedCreditGate() {
        var card = DataHelper.postAPIInvalidCardNumberDeclined();
        String path = "/api/v1/credit";
        int apiStatus = 500;
        APIHelper.returnResponse(card, path, apiStatus);

        var actualVerifyOperationInDb = SQLHelper.getStatusCreditGatePage();
        var expected = "DECLINED";
        assertEquals(expected, actualVerifyOperationInDb);
    }

    @Test
    @DisplayName("API, оплата в кредит, невалидный номер карты, ответ сервера 400")
    void should400InvalidCardNumberCreditGate() {
        var card = DataHelper.postAPIInvalidCardNumber();
        String path = "/api/v1/credit";
        int apiStatus = 500;
        var actual = APIHelper.returnResponse(card, path, apiStatus);
        var expected = "400 Bad Request";
        assertEquals(expected, actual);
    }
}
