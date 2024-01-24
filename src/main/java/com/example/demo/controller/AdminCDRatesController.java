package com.example.demo.controller;

import com.example.demo.model.CDRateUpdate;
import com.example.demo.model.CDRates;
import com.example.demo.service.RateUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.Generators;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.UUID;

@RestController
public class AdminCDRatesController {

    @Autowired
    private ObjectMapper objectMapper;  // Autowire ObjectMapper

    private final RestTemplate restTemplate;

    @Autowired
    private RateUtility conversionUtility;

    private final Environment env;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminCDRatesController.class);

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    public AdminCDRatesController( Environment env) {
        this.restTemplate = new RestTemplate(clientHttpRequestFactory());
        this.env = env;
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

        UUID timeUuid = Generators.timeBasedGenerator().generate();
        newRate.setId(timeUuid.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-cassandra-token", env.getProperty("ASTRA_DB_APPLICATION_TOKEN"));
        headers.set("Content-type", "application/json;charset=UTF-8");
        HttpEntity<CDRates> request =
                new HttpEntity<CDRates>(newRate, headers);

        CDRates rate = restTemplate.postForObject("https://" +
                env.getProperty("ASTRA_DB_ID") +
                "-" +
                env.getProperty("ASTRA_DB_REGION") +
                ".apps.astra.datastax.com/api/rest/v2/keyspaces/" +
                env.getProperty("ASTRA_DB_KEYSPACE") +
                "/cdrates" , request, CDRates.class);

         return rate;
    }

    /**
     * Endpoint for bank admins to update status of certificate of deposit interest rate records.
     *
     * @return
     */
    @Operation(summary = "Endpoint to update status of certificate of deposit interest rate records", description = "This end point is provided to bank admins to update status of certificate of deposit interest rate records ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/v1/admin/rate/{id}")
    private ResponseEntity<CDRateUpdate> updateCDRateStatus(@RequestBody CDRateUpdate newRate, @PathVariable String id){

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-cassandra-token", env.getProperty("ASTRA_DB_APPLICATION_TOKEN"));
        headers.set("Content-type", "application/json;charset=UTF-8");
        HttpEntity<CDRateUpdate> request =
                new HttpEntity<CDRateUpdate>(newRate, headers);

        ResponseEntity<CDRateUpdate> rate = restTemplate.exchange("https://" +
                env.getProperty("ASTRA_DB_ID") +
                "-" +
                env.getProperty("ASTRA_DB_REGION") +
                ".apps.astra.datastax.com/api/rest/v2/keyspaces/" +
                env.getProperty("ASTRA_DB_KEYSPACE") +
                "/cdrates/" + id , HttpMethod.PATCH, request, CDRateUpdate.class);

        return rate;
    }


    /**
     * Endpoint to archive certificate of deposit interest rate records.
     *
     */
//    @Operation(summary = "Endpoint to archive certificate of deposit interest rate records", description = "This end point is used to archive the certificate of deposit interest rate records ")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Processed successfully"),
//            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
//            @ApiResponse(responseCode = "500", description = "Internal server error")
//    })
//    @PutMapping("/v1/admin/archiverate/{id}")
//    private Optional<CDRates> archiveRate(@RequestBody CDRates oldRate, @PathVariable String id){
//
//         return rateRepo.findById(id)
//                .map(rate -> {
//                    rateRepo.delete(rate);
//                    historicalRatesRepo.save(oldRate);
//                    return null;
//                });
//    }
}
