package ru.netology.diploma.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class APIHelper {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static DataHelper.CardNumber sendRequest(DataHelper.CardNumber card) {
        given()
                .spec(requestSpec)
                .body(card)
                .when()
                .post()
                .then()
                .statusCode(200);
        return card;
    }
}
