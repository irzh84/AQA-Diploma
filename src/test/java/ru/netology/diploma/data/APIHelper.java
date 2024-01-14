package ru.netology.diploma.data;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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

public static String returnResponsePaymentGate200(DataHelper.CardNumber card) {
    Response response = given()
            .spec(requestSpec)
            .body(card)
            .when()
            .post("/api/v1/pay")
            .then()
            .statusCode(200)
            .extract().response();
    return response.asString();
}

}
