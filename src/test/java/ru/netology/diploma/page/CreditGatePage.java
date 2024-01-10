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

public class CreditGatePage {
    private SelenideElement creditGatePageHeading = $(byText("Кредит по данным карты"));

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


    private static SelenideElement redNotificationUnderTheFieldWrongFormat = $(byText("Неверный формат"));
    private static SelenideElement redNotificationValidityPeriod = $(byText("Неверно указан срок действия карты"));
    private static SelenideElement redNotificationTimeExpiredYear = $(byText("Истёк срок действия карты"));
    private static SelenideElement redNotificationRequiredField = $(byText("Поле обязательно для заполнения"));

    public CreditGatePage() {
        creditGatePageHeading
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
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    public void checkNotificationUnsuccessful() {
        notificationUnsuccessful
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    public void checkRedNotificationUnderTheFieldWrongFormat() {
        redNotificationUnderTheFieldWrongFormat
                .shouldBe(Condition.visible);
    }

    public void checkRedNotificationValidityPeriod() {
        redNotificationValidityPeriod
                .shouldBe(Condition.visible);
    }

    public void checkRedNotificationTimeExpiredYear() {
        redNotificationTimeExpiredYear
                .shouldBe(Condition.visible);
    }

    public void checkRedNotificationRequiredField() {
        redNotificationRequiredField
                .shouldBe(Condition.visible);
    }
}
