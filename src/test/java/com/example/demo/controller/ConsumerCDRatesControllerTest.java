package com.example.demo.controller;

import com.example.demo.dao.BankCDRatesDao;
import com.example.demo.model.CDRatesWithoutManagerRate;
import com.example.demo.service.RateUtility;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Collections;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ConsumerCDRatesControllerTest {

    @Mock
    private BankCDRatesDao dao;

    @Mock
    private RateUtility rateUtility;

    @InjectMocks
    private ConsumerCDRatesController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        RestAssuredMockMvc.standaloneSetup(controller);
    }

    @Test
    public void testGetRatesForConsumer() {
        String zip = "03801";
        String state = "NH";

        when(rateUtility.getState(zip)).thenReturn(state);
        when(dao.getCDRatesWithoutManagerRate()).thenReturn(Collections.singletonList(createMockCDRate()));

        given()
                .when()
                .get("/v1/consumer/currentrates/{zip}", zip)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("[0].statecode", equalTo(state));
    }

    @Test
    public void testGetHistoricalRatesForConsumer() {
        String zip = "03801";
        String state = "NH";

        when(rateUtility.getState(zip)).thenReturn(state);
        when(dao.getHistoricalCDRatesWithoutManagerRate()).thenReturn(Collections.singletonList(createMockCDRate()));

        given()
                .when()
                .get("/v1/consumer/hisoricalrates/{zip}", zip)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("[0].statecode", equalTo(state));
    }

    private CDRatesWithoutManagerRate createMockCDRate() {
        return new CDRatesWithoutManagerRate();
    }
}
