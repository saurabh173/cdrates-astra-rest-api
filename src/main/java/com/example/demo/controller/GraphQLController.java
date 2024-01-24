package com.example.demo.controller;

import com.example.demo.model.CDRates;
import com.example.demo.model.CDRatesWithoutManagerRate;
import com.example.demo.service.RateUtility;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
public class GraphQLController {

    private final Environment env;

    @Autowired
    private RateUtility conversionUtility;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public GraphQLController(Environment env) {
        this.env = env;
    }

    @QueryMapping
    public List<CDRates> managerRatesByZip(@Argument String zip) {
        String state = conversionUtility.getState(zip);
        if (state.equals("")){
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-cassandra-token", env.getProperty("ASTRA_DB_APPLICATION_TOKEN"));
        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);


        //String whereCondition = "{\"statecode\" : {\"$eq\" : \"" + state + "\"}}";

        //JSONParser parser = new JSONParser();
        //JSONObject json = (JSONObject) parser.parse(whereCondition);

        String whereClause = "{\"statecode\": {\"$eq\": \"" + state + "\"}}";

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

        List<CDRates> customList = objectMapper.convertValue(responseEntity.getBody().get("data"), new TypeReference<List<CDRates>>() {});
        return customList;
    }

    @QueryMapping
    public List<CDRatesWithoutManagerRate> ratesByZip(@Argument String zip) {
        String state = conversionUtility.getState(zip);
        if (state.equals("")){
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-cassandra-token", env.getProperty("ASTRA_DB_APPLICATION_TOKEN"));
        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);


        //String whereCondition = "{\"statecode\" : {\"$eq\" : \"" + state + "\"}}";

        //JSONParser parser = new JSONParser();
        //JSONObject json = (JSONObject) parser.parse(whereCondition);

        String whereClause = "{\"statecode\": {\"$eq\": \"" + state + "\"}}";

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

        List<CDRatesWithoutManagerRate> customList = objectMapper.convertValue(responseEntity.getBody().get("data"), new TypeReference<List<CDRatesWithoutManagerRate>>() {});
        return customList;
    }

}
