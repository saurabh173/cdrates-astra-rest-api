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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/v1/thirdparty")
public class ThirdPartyCDRatesController {

    @Autowired
    private ObjectMapper objectMapper;

    private final WebClient webClient;
    private final ConversionUtility conversionUtility;
    private final Environment env;

    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartyCDRatesController.class);

    public ThirdPartyCDRatesController(Environment env, ConversionUtility conversionUtility) {
        this.env = env;
        this.conversionUtility = conversionUtility;
        this.webClient = WebClient.builder()
                .baseUrl("https://" + env.getProperty("ASTRA_DB_ID") + "-" + env.getProperty("ASTRA_DB_REGION") +
                        ".apps.astra.datastax.com/api/rest/v2/keyspaces/" + env.getProperty("ASTRA_DB_KEYSPACE"))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("x-cassandra-token", env.getProperty("ASTRA_DB_APPLICATION_TOKEN"))
                .build();
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
            @ApiResponse(responseCode = "429", description = "Too many requests"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/currentrates/{zip}")
    private Mono<ResponseEntity<List<CDRatesWithoutManagerRate>>> getRatesForThirdParty(@Parameter(description = "Please enter valid US zip code to view certificate of deposit interest rates", required = true) @PathVariable String zip){
        String state = conversionUtility.getState(zip);
        LOGGER.info("getRatesForThirdParty request received for Zip " + zip);

        if (state.equals("")){
            throw new CustomBadRequestException("Invalid zip supplied");
        }

        return webClient.get()
                .uri("/cdrates?where={}", zip)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .map(responseBody -> {
                    List<CDRatesWithoutManagerRate> customList = objectMapper.convertValue(responseBody.get("data"), new TypeReference<List<CDRatesWithoutManagerRate>>() {});
                    return ResponseEntity.ok(customList);
                });
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
            @ApiResponse(responseCode = "429", description = "Too many requests"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/hisoricalrates/{zip}")
    private Mono<ResponseEntity<List<CDRatesWithoutManagerRate>>> getHistoricalRatesForThirdParty(@Parameter(description = "Please enter valid US zip code to view historical certificate of deposit interest rates", required = true) @PathVariable String zip){
        String state = conversionUtility.getState(zip);
        LOGGER.info("getHistoricalRatesForThirdParty request received for Zip " + zip);

        if (state.equals("")){
            throw new CustomBadRequestException("Invalid zip supplied");
        }

        Instant eightYearsAgo = Instant.now().minus(Duration.ofDays(365 * 8));

        return webClient.get()
                .uri("/cd_historical_rates?where={}", zip)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .map(responseBody -> {
                    List<CDRatesWithoutManagerRate> customList = objectMapper.convertValue(responseBody.get("data"), new TypeReference<List<CDRatesWithoutManagerRate>>() {});
                    return ResponseEntity.ok(customList);
                });
    }

 }
