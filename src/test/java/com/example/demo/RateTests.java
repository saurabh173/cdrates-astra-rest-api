package com.example.demo;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.testng.Assert;


public class RateTests {

    @Test
    public void testConsumerCurrentRates(){
        Response response=ReadResponse.getConsumerCurrentRates("03801");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
    }


    @Test
    public void testConsumerHistoricalRates(){
        Response response=ReadResponse.getConsumerHistoricalRates("03801");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
    }


    @Test
    public void testManagerCurrentRates(){
        Response response=ReadResponse.getManagerCurrentRates("03801");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test
    public void testManagerHistoricalRates(){
        Response response=ReadResponse.getManagerHistoricalRates("03801");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
    }
}
