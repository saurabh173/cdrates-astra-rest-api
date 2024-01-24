package com.example.demo.dao;

import com.example.demo.controller.ConsumerCDRatesController;
import com.example.demo.model.CDRates;
import com.example.demo.model.CDRatesWithoutManagerRate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class BankCDRatesDao {
    @Autowired
    RestTemplate restTemplate;

    private final Environment env;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerCDRatesController.class);

    public BankCDRatesDao(Environment env) {
        this.env = env;
    }

    @Cacheable(value = "CDNonMgrRates")
    public List<CDRatesWithoutManagerRate> getCDRatesWithoutManagerRate(){
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
        return customList;
        }

    @Cacheable(value = "historicalNonMgrCDRates")
    public List<CDRatesWithoutManagerRate> getHistoricalCDRatesWithoutManagerRate(){
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
        return customList;
    }



    @Cacheable(value = "CDRates")
    public List<CDRates> getCDRates(){
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
        List<CDRates> customList = objectMapper.convertValue(responseEntity.getBody().get("data"), new TypeReference<List<CDRates>>() {});
        return customList;
    }

    @Cacheable(value = "historicalCDRates")
    public List<CDRates> getHistoricalCDRates(){
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
        List<CDRates> customList = objectMapper.convertValue(responseEntity.getBody().get("data"), new TypeReference<List<CDRates>>() {});
        return customList;
    }
}
