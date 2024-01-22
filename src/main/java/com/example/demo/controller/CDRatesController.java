package com.example.demo.controller;

import com.example.demo.dao.CDHistoricalRatesRepo;
import com.example.demo.dao.CDRatesRepo;
import com.example.demo.dao.CDRatesStatusRepo;
import com.example.demo.exception.CustomBadRequestException;
import com.example.demo.model.CDRates;
import com.example.demo.model.CDRatesWithoutManagerRate;
import com.example.demo.service.ConversionUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
public class CDRatesController {

    @Autowired
    private CDRatesRepo rateRepo;

    @Autowired
    private CDRatesStatusRepo statusRepo;

    @Autowired
    private CDHistoricalRatesRepo historicalRatesRepo;

    @Autowired
    private ConversionUtility conversionUtility;

    private static final Logger LOGGER = LoggerFactory.getLogger(CDRatesController.class);

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
        //ResponseEntity<CDRates> rates = (ResponseEntity<CDRates>) rateRepo.findRatesExcludingManagerRate(state);
        //LOGGER.info(String.valueOf(rates));
        //return rates;
        return ResponseEntity.ok(rateRepo.findRatesExcludingManagerRate(state)) ;
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

        return ResponseEntity.ok(historicalRatesRepo.findRatesExcludingManagerRate(state, eightYearsAgo)) ;
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
    private ResponseEntity<List<CDRates>> getRates(@Parameter(description = "Please enter valid US zip code to view certificate of deposit interest rates", required = true) @PathVariable String zip){
        String state = conversionUtility.getState(zip);
        if (state.equals("")){
            throw new CustomBadRequestException("Invalid zip supplied");
        }
        return ResponseEntity.ok(rateRepo.findAllByStateCode(state)) ;
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
    private ResponseEntity<List<CDRates>> getHistoricalRates(@Parameter(description = "Please enter valid US zip code to view historical certificate of deposit interest rates", required = true) @PathVariable String zip){
        String state = conversionUtility.getState(zip);
        if (state.equals("")){
            throw new CustomBadRequestException("Invalid zip supplied");
        }
        Instant eightYearsAgo = Instant.now().minus(Duration.ofDays(365 * 8));
        return ResponseEntity.ok(historicalRatesRepo.findAllByStateCode(state, eightYearsAgo)) ;
    }

    /**
     * Endpoint for bank admins to create new certificate of deposit interest rate records.
     *
     * @return
     */
    @Operation(summary = "Endpoint to create new certificate of deposit interest rate records", description = "This end point is provided to bank admins to create new certificate of deposit interest rate records ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/v1/admin/currentrates")
    private CDRates addCurrentRates(@RequestBody CDRates newRate){
        return rateRepo.save(newRate);
    }

    /**
     * Endpoint for bank admins to update status of certificate of deposit interest rate records.
     *
     */
    @Operation(summary = "Endpoint to update status of certificate of deposit interest rate records", description = "This end point is provided to bank admins to update status of certificate of deposit interest rate records ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/v1/admin/rate/{id}")
    private Optional<CDRates> updateRate(@RequestBody CDRates newRate, @PathVariable String id){

        return rateRepo.findById(id)
                .map(rate -> {
                    rate.setStatus(newRate.getStatus());
                    return rateRepo.save(rate);
                });
    }

    /**
     * Endpoint to archive certificate of deposit interest rate records.
     *
     */
    @Operation(summary = "Endpoint to archive certificate of deposit interest rate records", description = "This end point is used to archive the certificate of deposit interest rate records ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/v1/admin/archiverate/{id}")
    private Optional<CDRates> archiveRate(@RequestBody CDRates oldRate, @PathVariable String id){

         return rateRepo.findById(id)
                .map(rate -> {
                    rateRepo.delete(rate);
//                    CDHistoricalRates historicalRate = new CDHistoricalRates();
//
//                    historicalRate.setAPY(oldRate.getAPY());
//                    historicalRate.setInterestRate(oldRate.getInterestRate());
//                    historicalRate.setManagerRate(oldRate.getManagerRate());
//                    historicalRate.setCdType(oldRate.getCdType());
//                    historicalRate.setStatus(oldRate.getStatus());
//                    historicalRate.setCdUniqueId(oldRate.getCdUniqueId());
//                    historicalRate.setStatus(oldRate.getStatus());
//                    historicalRate.setEndDate(oldRate.getEndDate());
//                    historicalRate.setCreatedBy(oldRate.getCreatedBy());

                    historicalRatesRepo.save(oldRate);
                    return null;
                });
    }
}
