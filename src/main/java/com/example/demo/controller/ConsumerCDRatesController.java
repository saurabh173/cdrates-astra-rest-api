package com.example.demo.controller;

import com.example.demo.exception.CustomBadRequestException;
import com.example.demo.model.CDRatesWithoutManagerRate;
import com.example.demo.service.ConversionUtility;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
public class ConsumerCDRatesController {

    @Autowired
    private ObjectMapper objectMapper;  // Autowire ObjectMapper

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ConversionUtility conversionUtility;

    private final Environment env;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerCDRatesController.class);

    public ConsumerCDRatesController(Environment env) {
        this.env = env;
    }

    /**
     * Get certificate of deposit interest rates for consumers.
     *
     * @param zip is a valid US zip code.
     * @return list of deposit interest rates.
     */
    @Operation(summary = "Get certificate of deposit interest rates for a zip code", description = "This end point is provided to consumers to view the certificate of deposit interest rates ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/v1/consumer/currentrates/{zip}")
    private ResponseEntity<List<CDRatesWithoutManagerRate>> getRatesForConsumer(@Parameter(description = "Please enter valid US zip code to view certificate of deposit interest rates", required = true) @PathVariable String zip){
        String state = conversionUtility.getState(zip);
        LOGGER.info("getRatesForConsumer request received for Zip " + zip);

        if (state.equals("")){
            throw new CustomBadRequestException("Invalid zip supplied");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-cassandra-token", env.getProperty("ASTRA_DB_APPLICATION_TOKEN"));
        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);


        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                "https://" +
                        env.getProperty("ASTRA_DB_ID") +
                        "-" +
                        env.getProperty("ASTRA_DB_REGION") +
                        ".apps.astra.datastax.com/api/rest/v2/keyspaces/" +
                        env.getProperty("ASTRA_DB_KEYSPACE") +
                        "/cdrates?where={}" ,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        // Extract the "data" array from the response
        List<CDRatesWithoutManagerRate> customList = objectMapper.convertValue(responseEntity.getBody().get("data"), new TypeReference<List<CDRatesWithoutManagerRate>>() {});

        return new ResponseEntity<>(customList, responseEntity.getStatusCode());

    }

    /**
     * Get historical certificate of deposit interest rates for consumers.
     *
     * @param zip is a valid US zip code.
     * @return list of historical deposit interest rates.
     */
    @Operation(summary = "Get historical certificate of deposit interest rates for a zip code", description = "This end point is provided to consumers to view the historical certificate of deposit interest rates ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/v1/consumer/hisoricalrates/{zip}")
    private ResponseEntity<List<CDRatesWithoutManagerRate>> getHistoricalRatesForConsumer(@Parameter(description = "Please enter valid US zip code to view historical certificate of deposit interest rates", required = true) @PathVariable String zip){
        String state = conversionUtility.getState(zip);
        if (state.equals("")){
            throw new CustomBadRequestException("Invalid zip supplied");
        }

        Instant eightYearsAgo = Instant.now().minus(Duration.ofDays(365 * 8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-cassandra-token", env.getProperty("ASTRA_DB_APPLICATION_TOKEN"));
        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);


        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                "https://" +
                        env.getProperty("ASTRA_DB_ID") +
                        "-" +
                        env.getProperty("ASTRA_DB_REGION") +
                        ".apps.astra.datastax.com/api/rest/v2/keyspaces/" +
                        env.getProperty("ASTRA_DB_KEYSPACE") +
                        "/cd_historical_rates?where={}" ,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        // Extract the "data" array from the response
        List<CDRatesWithoutManagerRate> customList = objectMapper.convertValue(responseEntity.getBody().get("data"), new TypeReference<List<CDRatesWithoutManagerRate>>() {});

        return new ResponseEntity<>(customList, responseEntity.getStatusCode());
    }

 }
