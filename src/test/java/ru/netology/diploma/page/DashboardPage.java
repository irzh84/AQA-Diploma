package ru.netology.diploma.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement dashboardPageHeading = $(byText("Путешествие дня"));
    private static SelenideElement elementPayByCard = $(byText("Купить"));

    private static SelenideElement elementPayByCredit = $(byText("Купить в кредит"));

    public DashboardPage() {
        dashboardPageHeading
                .shouldBe(visible);
    }

    public static void selectPayByCard() {
        elementPayByCard.click();
        new PaymentGatePage();
    }

    public static void selectPayByCredit() {
        elementPayByCredit.click();
        new CreditGatePage();
    }
}

