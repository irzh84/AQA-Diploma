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

    private static ElementsCollection formWithCardDetails = $$(".input__control");
    private static SelenideElement cardNumber = formWithCardDetails.get(0);
    private static SelenideElement month = formWithCardDetails.get(1);
    private static SelenideElement year = formWithCardDetails.get(2);
    private static SelenideElement holder = formWithCardDetails.get(3);
    private static SelenideElement cvc = formWithCardDetails.get(4);

    private static SelenideElement buttonContinue = $(byText("Продолжить"));
    private static SelenideElement notificationSuccessful = $(".notification__content");

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

    public void fillForm(DataHelper.CardNumber info,
                         String setMonth,
                         String setYear,
                         String setHolder,
                         String setCvc) {
        cardNumber.setValue(info.getCardNumber());
        month.setValue(setMonth);
        year.setValue(setYear);
        holder.setValue(setHolder);
        cvc.setValue(setCvc);
    }

    public void clickButtonContinue() {
        buttonContinue.click();
    }

    public void checkNotificationSuccessful() {
        notificationSuccessful
                .shouldBe(Condition.visible, Duration.ofSeconds(60))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }
}

