package ru.netology.diploma.data;

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

    public static String returnResponse(DataHelper.APICardInfo card, String path, int apiStatus) {
        Response response = given()
                .spec(requestSpec)
                .body(card)
                .when()
                .post(path)
                .then()
                .statusCode(apiStatus)
                .extract().response();
        return response.path("message");
    }
}
