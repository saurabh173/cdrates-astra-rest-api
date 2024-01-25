package com.example.demo;

public class Routes {
    public static String base_url="http://localhost:8080/v1" ;

    public static String consumer_current_rates=base_url+"/consumer/currentrates/{zip}";
    public static String consumer_historical_rates=base_url+"/consumer/historicalrates/{zip}";
    public static String manager_current_rates=base_url+"/manager/currentrates/{zip}";
    public static String manager_historical_rates=base_url+"/manager/historicalrates/{zip}";
}
