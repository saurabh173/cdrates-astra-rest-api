package com.example.demo.controller;

import com.example.demo.dao.BankCDRatesDao;
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
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class GraphQLController {

    private final Environment env;

    @Autowired
    private RateUtility conversionUtility;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    BankCDRatesDao dao;

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
        List<CDRates> customList =
                dao.getCDRates().stream()
                        .filter(rate -> state.equals(rate.getStatecode()))
                        .collect(Collectors.toList());

        return customList;

    }

    @QueryMapping
    public List<CDRatesWithoutManagerRate> ratesByZip(@Argument String zip) {
        String state = conversionUtility.getState(zip);
        if (state.equals("")) {
            return null;
        }

        List<CDRatesWithoutManagerRate> customList =
                dao.getCDRatesWithoutManagerRate().stream()
                        .filter(rate -> state.equals(rate.getStatecode()))
                        .collect(Collectors.toList());

        return customList;
    }
}
