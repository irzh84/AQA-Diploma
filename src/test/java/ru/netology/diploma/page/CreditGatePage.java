package ru.netology.diploma.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CreditGatePage {
    private SelenideElement creditGatePageHeading = $(byText("Кредит по данным карты"));

    public CreditGatePage() {
        creditGatePageHeading
                .shouldBe(visible);
    }
}
