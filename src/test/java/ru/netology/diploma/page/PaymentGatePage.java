package ru.netology.diploma.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.diploma.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentGatePage {
    private SelenideElement paymentGatePageHeading = $(byText("Оплата по карте"));

    private static ElementsCollection formWithCardDetails = $$(".input__control");
    private static SelenideElement cardNumber = formWithCardDetails.get(0);
    private static SelenideElement month = formWithCardDetails.get(1);
    private static SelenideElement year = formWithCardDetails.get(2);
    private static SelenideElement holder = formWithCardDetails.get(3);
    private static SelenideElement cvc = formWithCardDetails.get(4);

    private static SelenideElement buttonContinue = $(byText("Продолжить"));


    private static ElementsCollection notification = $$(".notification__content");
    private static SelenideElement notificationSuccessful = notification.get(0);
    private static SelenideElement notificationUnsuccessful = notification.get(1);
    private static SelenideElement notificationUnsuccessfulAlone = $(byText("Ошибка! Банк отказал в проведении операции."));


    private static ElementsCollection redNotificationUnderTheField = $$(".input__sub");
    private static SelenideElement cardNumberRedNotification = redNotificationUnderTheField.get(0);
    private static SelenideElement monthRedNotification = redNotificationUnderTheField.get(1);

    private static SelenideElement yearRedNotification = redNotificationUnderTheField.get(2);
    private static SelenideElement holderRedNotification = redNotificationUnderTheField.get(3);
    private static SelenideElement cvcRedNotification = redNotificationUnderTheField.get(4);

    private static SelenideElement redNotificationUnderTheFieldWrongFormat = $(byText("Неверный формат"));
    private static SelenideElement redNotificationValidityPeriod = $(byText("Неверно указан срок действия карты"));
    private static SelenideElement redNotificationTimeExpiredYear = $(byText("Истёк срок действия карты"));
    private static SelenideElement redNotificationRequiredField = $(byText("Поле обязательно для заполнения"));


    public PaymentGatePage() {
        paymentGatePageHeading
                .shouldBe(visible);
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

    public void checkNotificationUnsuccessful() {
        notificationUnsuccessful
                .shouldBe(Condition.visible, Duration.ofSeconds(60))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    public void checkCardNumberRedNotification() {
        cardNumberRedNotification
                .shouldBe(Condition.visible, Duration.ofSeconds(60))
                .shouldHave(Condition.text("Неверный формат"));
    }

    public void checkMonthRedNotification() {
        monthRedNotification
                .shouldBe(Condition.visible, Duration.ofSeconds(60))
                .shouldHave(Condition.text("Неверный формат"));
    }

    public void checkYearRedNotification() {
        yearRedNotification
                .shouldBe(Condition.visible, Duration.ofSeconds(60))
                .shouldHave(Condition.text("Неверный формат"));
    }

    public void checkHolderRedNotification() {
        holderRedNotification
                .shouldBe(Condition.visible, Duration.ofSeconds(60))
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    public void checkCvcRedNotification() {
        cvcRedNotification
                .shouldBe(Condition.visible, Duration.ofSeconds(60))
                .shouldHave(Condition.text("Неверный формат"));
    }

    public void checkRedNotificationUnderTheFieldWrongFormat() {
        redNotificationUnderTheFieldWrongFormat
                .shouldBe(Condition.visible, Duration.ofSeconds(60));
    }

    public void checkRedNotificationValidityPeriod() {
        redNotificationValidityPeriod
                .shouldBe(Condition.visible, Duration.ofSeconds(60));
    }

    public void checkRedNotificationTimeExpiredYear() {
        redNotificationTimeExpiredYear
                .shouldBe(Condition.visible, Duration.ofSeconds(60));
    }

    public void checkRedNotificationRequiredField() {
        redNotificationRequiredField
                .shouldBe(Condition.visible, Duration.ofSeconds(60));
    }
}
