package ru.netology.diploma.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentGatePage {
    private SelenideElement paymentGatePageHeading = $(byText("Оплата по карте"));

    private static ElementsCollection form = $$(".input__control");
    private static SelenideElement cardNumber = form.get(0);
    private static SelenideElement month = form.get(1);
    private static SelenideElement year = form.get(2);
    private static SelenideElement holder = form.get(3);
    private static SelenideElement cvc = form.get(4);

    public PaymentGatePage() {
        paymentGatePageHeading
                .shouldBe(visible);
    }

    public static void fillForm(String setCardNumber,
                                String setMonth,
                                String setYear,
                                String setHolder,
                                String setCvc) {
        cardNumber.setValue(setCardNumber);
        month.setValue(setMonth);
        year.setValue(setYear);
        holder.setValue(setHolder);
        cvc.setValue(setCvc);
    }
}
