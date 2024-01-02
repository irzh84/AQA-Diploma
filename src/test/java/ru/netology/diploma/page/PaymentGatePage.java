package ru.netology.diploma.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentGatePage {
    private SelenideElement paymentGatePageHeading = $(byText("Оплата по карте"));

    public PaymentGatePage() {
        paymentGatePageHeading
                .shouldBe(visible);
    }
}
