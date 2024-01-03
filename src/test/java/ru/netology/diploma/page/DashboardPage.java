package ru.netology.diploma.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.diploma.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement dashboardPageHeading = $(byText("Путешествие дня"));
    private static SelenideElement elementPayByCard = $(byText("Купить"));
    private static SelenideElement elementPayByCredit = $(byText("Купить в кредит"));

    public DashboardPage() {
        dashboardPageHeading
                .shouldBe(visible);
    }

    public PaymentGatePage selectPayByCard() {
        elementPayByCard.click();
        return new PaymentGatePage();
    }

    public CreditGatePage selectPayByCredit() {
        elementPayByCredit.click();
        return new CreditGatePage();
    }
}

