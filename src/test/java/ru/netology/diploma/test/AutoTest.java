package ru.netology.diploma.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.diploma.page.DashboardPage;
import ru.netology.diploma.page.PaymentGatePage;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AutoTest {

    @BeforeEach
    void setup() {
        open("http://localhost:8080/");
    }

//    @Test
//    void shouldSelectPayByCard() {
//        DashboardPage.selectPayByCard();
//        $(byText("Оплата по карте")).shouldBe(Condition.visible);
//    }
//
//    @Test
//    void shouldSelectPayByCredit() {
//        DashboardPage.selectPayByCredit();
//        $(byText("Кредит по данным карты")).shouldBe(Condition.visible);
//    }

    @Test
    @DisplayName("Успешная покупка с оплатой по карте, валидные данные")
    void shouldCardSuccessfulPayment() {
        DashboardPage.selectPayByCard();
        PaymentGatePage.fillForm("4444 4444 4444 4441",
                "12",
                "23",
                "Anna",
                "244");
        $(byText("Продолжить")).click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(60))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }
}
