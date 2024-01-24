package com.example.demo.controller;

import com.example.demo.dao.BankCDRatesDao;
import com.example.demo.exception.CustomBadRequestException;
import com.example.demo.model.CDRates;
import com.example.demo.model.CDRatesWithoutManagerRate;
import com.example.demo.service.RateUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ManagerCDRatesController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    BankCDRatesDao dao;

    @Autowired
    private RateUtility rateUtility;

    private final Environment env;

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerCDRatesController.class);

    public ManagerCDRatesController(Environment env) {
        this.env = env;
    }

     /**
     * Get certificate of deposit interest rates for branch managers.
     *
     * @param zip is a valid US zip code.
     * @return list of deposit interest rates.
     */
    @Operation(summary = "Get certificate of deposit interest rates for a zip code", description = "This end point is provided to branch managers to view the certificate of deposit interest rates ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/v1/manager/currentrates/{zip}")
    @Cacheable(value = "managerCDRates", key = "#zip")
    private ResponseEntity<List<CDRates>> getRates(@Parameter(description = "Please enter valid US zip code to view certificate of deposit interest rates", required = true) @PathVariable String zip){
        String state = rateUtility.getState(zip);
        if (state.equals("")){
            throw new CustomBadRequestException("Invalid zip supplied");
        }

        List<CDRates> customList =
                dao.getCDRates().stream()
                        .filter(rate -> state.equals(rate.getStatecode()))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(customList, HttpStatus.OK);

    }

    /**
     * Get historical certificate of deposit interest rates for branch managers.
     *
     * @param zip is a valid US zip code.
     * @return list of historical deposit interest rates.
     */
    @Operation(summary = "Get historical certificate of deposit interest rates for a zip code", description = "This end point is provided to branch managers to view the historical certificate of deposit interest rates ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/v1/manager/hisoricalrates/{zip}")
    @Cacheable(value = "managerCDHistoryRates", key = "#zip")
    private ResponseEntity<List<CDRates>> getHistoricalRates(@Parameter(description = "Please enter valid US zip code to view historical certificate of deposit interest rates", required = true) @PathVariable String zip){
        String state = rateUtility.getState(zip);
        if (state.equals("")){
            throw new CustomBadRequestException("Invalid zip supplied");
        }
        List<CDRates> customList =
                dao.getHistoricalCDRates().stream()
                        .filter(rate -> state.equals(rate.getStatecode()))
                        .filter(rate -> rateUtility.isStartDateWithinLast8Years(rate.getStartdate()))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(customList, HttpStatus.OK);

    }


}
