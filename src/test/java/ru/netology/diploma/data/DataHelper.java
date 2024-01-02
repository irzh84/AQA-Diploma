package ru.netology.diploma.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    public static CardNumber setValidCardNumberApproved() {
        return new CardNumber("4444 4444 4444 4441");
    }

    public static String generateValidMonthInValidYear() {
        var validMonth = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        return validMonth[new Random().nextInt(validMonth.length)];
    }

    public static String generateValidYear() {
        var validYear = new String[]{"24", "25", "26", "27", "28"};
        return validYear[new Random().nextInt(validYear.length)];
    }

    public static String generateValidName() {
        var faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generateValidCvc() {
        var faker = new Faker();
        return faker.regexify("[0-9]{3}");
    }

    @Value
    public static class CardNumber {
        String cardNumber;
    }
}

