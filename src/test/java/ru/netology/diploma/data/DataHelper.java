package ru.netology.diploma.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    public static CardNumber setValidCardNumberApproved() {
        return new CardNumber("4444 4444 4444 4441", "APPROVED");
    }

    private static String generateDate(String pattern) {

        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String generateValidMonth() {
        String validMonth = generateDate("MM");
        return validMonth;
    }

    public static String generateValidYear() {
        String validYear = generateDate("yy");
        return validYear;
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
        String status;
    }

    @Value
    public static class OperationStatus {
        String status;
    }
}

