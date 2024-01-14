package ru.netology.diploma.data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    public static CardNumber setValidCardNumberApproved() {
        return new CardNumber("4444 4444 4444 4441", "APPROVED");
    }

    public static CardNumber setCardNumberDeclined() {
        return new CardNumber("4444 4444 4444 4442", "DECLINED");
    }

    public static CardNumber generateCardNumberInvalid() {
        var faker = new Faker();
        return new CardNumber(faker.regexify("[0-9]{16}"), "DECLINED");
    }

    public static CardNumber setEmptyCardNumber() {
        return new CardNumber("", "");
    }

    public static CardNumber setZeroCardNumber() {
        var faker = new Faker();
        return new CardNumber(faker.regexify("[0]{16}"), "DECLINED");
    }

    public static CardNumber setRandomLettersCardNumber() {
        var faker = new Faker(new Locale("en"));
        return new CardNumber(faker.regexify("[A-Z]{8}"), "DECLINED");
    }

    public static CardNumber setRandomSpecialSymbolsCardNumber() {
        var specialSymbols = new String[]{"~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", "="};
        return new CardNumber(specialSymbols[new Random().nextInt(specialSymbols.length)], "DECLINED");
    }

    private static String generateDate(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String generateValidMonth() {
        String validMonth = generateDate("MM");
        return validMonth;
    }

    public static String generateInvalidMonth() {
        var invalidMonth = new String[]{"13", "14", "15", "16"};
        return invalidMonth[new Random().nextInt(invalidMonth.length)];
    }

    public static String generateValidYear() {
        String validYear = generateDate("yy");
        return validYear;
    }

    public static String generateInvalidEarlyYear() {
        int validYear = Integer.parseInt(generateDate("yy"));
        int invalidEarlyYear = validYear - 1;
        return String.valueOf(invalidEarlyYear);
    }

    public static String generateInvalidMore5Year() {
        int validYear = Integer.parseInt(generateDate("yy"));
        int invalidMore5Year = validYear + 6;
        return String.valueOf(invalidMore5Year);
    }

    public static String generateValidName() {
        var faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generateCyrillicName() {
        var faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generate1SymbolName() {
        var faker = new Faker(new Locale("en"));
        return faker.regexify("[A-Z]{1}");
    }

    public static String generate101SymbolsName() {
        var faker = new Faker(new Locale("en"));
        return faker.regexify("[A-Z]{101}");
    }

    public static String generateValidCvc() {
        var faker = new Faker();
        return faker.regexify("[0-9]{3}");
    }


    public static String emptyField() {
        return "";
    }

    public static String zeroField() {
        var faker = new Faker();
        return faker.regexify("[0]{4}");
    }

    public static String generate1SymbolNumber() {
        var faker = new Faker();
        return faker.regexify("[0-9]{1}");
    }

    public static String generateRandomLetters() {
        var faker = new Faker(new Locale("en"));
        return faker.regexify("[A-Z]{4}");
    }

    public static String generateRandomSpecialSymbols() {
        var specialSymbols = new String[]{"~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", "="};
        int numberSpecialSymbols = 4;
        int randomIndex = (int) (Math.random() * numberSpecialSymbols);
        int i = 0;
        String result = "";
        while (i < specialSymbols.length) {
            result = result + specialSymbols[randomIndex];
            randomIndex = (int) (Math.random() * numberSpecialSymbols);
            i++;
        }
        return result;
    }

    public static APICardInfo postAPIValidCardNumberApproved() {
        return new APICardInfo("4444 4444 4444 4441", "24", "01", "Anna", "345");
    }

    public static APICardInfo postAPIInvalidCardNumberDeclined() {
        return new APICardInfo("4444 4444 4444 4442", "24", "01", "Anna", "345");
    }

    public static APICardInfo postAPIInvalidCardNumber() {
        return new APICardInfo("4444 4444 4444 4444", "24", "01", "Anna", "345");
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

    @Value
    public static class APICardInfo {
        String number;
        String year;
        String month;
        String holder;
        String cvc;
    }
}