package com.example.demo;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ReadResponse {
    public static Response getConsumerCurrentRates(String zip){
        Response response=given()
                .pathParam("zip",zip)
                .when()
                .get(Routes.consumer_current_rates);
        return response;
    }


    public static Response getConsumerHistoricalRates(String zip){
        Response response=given()
                .pathParam("zip",zip)
                .when()
                .get(Routes.consumer_historical_rates);
        return response;
    }


    public static Response getManagerCurrentRates(String zip){
        Response response=given()
                .pathParam("zip",zip)
                .when()
                .get(Routes.manager_current_rates);
        return response;
    }

    public static Response getManagerHistoricalRates(String zip){
        Response response=given()
                .pathParam("zip",zip)
                .when()
                .get(Routes.manager_historical_rates);
        return response;
    }
}
