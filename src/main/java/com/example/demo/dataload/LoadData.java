package com.example.demo.dataload;

import com.example.demo.dao.CDRatesRepo;
import com.example.demo.dao.CDRatesStatusRepo;
import com.example.demo.model.CDRates;
import com.example.demo.model.CDRatesStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class LoadData implements ApplicationRunner {

    @Autowired
    CDRatesRepo rateRepo;

    @Autowired
    CDRatesStatusRepo rateStatusRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // ********** DO NOT DELETE*********

        rateRepo.save(new CDRates("20231201m12S10kCA",1,4.5,4.7, 4.6, "CA", 1000,100000,10, 5, "S","Y",parseDate("2024-01-15"), parseDate("2024-03-31"),"TestUser", "AdminUser"));
        rateRepo.save(new CDRates("20231201m12S10kAL",2,4.5,4.7, 4.6, "AL", 1000,100000,10, 5, "S","Y",parseDate("2024-01-15"), parseDate("2024-03-31"),"TestUser", "AdminUser"));
        rateRepo.save(new CDRates("20231201m12S10kAK",3,4.5,4.7, 4.6, "AK", 1000,100000,10, 5, "S","Y",parseDate("2024-01-15"), parseDate("2024-03-31"),"TestUser", "AdminUser"));
        rateRepo.save(new CDRates("20231201m12S10kAR",4,4.5,4.7, 4.6, "AR", 1000,100000,10, 5, "S","Y",parseDate("2024-01-15"), parseDate("2024-03-31"),"TestUser", "AdminUser"));
        rateRepo.save(new CDRates("20231201m12S10kFL",1,4.5,4.7, 4.6, "FL", 1000,100000,10, 5, "S","Y",parseDate("2024-01-15"), parseDate("2024-03-31"),"TestUser", "AdminUser"));
        rateRepo.save(new CDRates("20231201m12S10kIL",2,4.5,4.7, 4.6, "IL", 1000,100000,10, 5, "S","Y",parseDate("2024-01-15"), parseDate("2024-03-31"),"TestUser", "AdminUser"));
        rateRepo.save(new CDRates("20231201m12S10kLA",3,4.5,4.7, 4.6, "LA", 1000,100000,10, 5, "S","Y",parseDate("2024-01-15"), parseDate("2024-03-31"),"TestUser", "AdminUser"));
        rateRepo.save(new CDRates("20231201m12S10kME",4,4.5,4.7, 4.6, "ME", 1000,100000,10, 5, "S","Y",parseDate("2024-01-15"), parseDate("2024-03-31"),"TestUser", "AdminUser"));
        rateRepo.save(new CDRates("20231201m12S10kNH",5,4.5,4.7, 4.6, "NH", 1000,100000,10, 5, "S","Y",parseDate("2024-01-15"), parseDate("2024-03-31"),"TestUser", "AdminUser"));
        rateRepo.save(new CDRates("20231201m12S10kNJ",6,4.5,4.7, 4.6, "NJ", 1000,100000,10, 5, "S","Y",parseDate("2024-01-15"), parseDate("2024-03-31"),"TestUser", "AdminUser"));



//        rateStatusRepo.save(new CDRatesStatus("20231201m12S10kCA",12000, 1000000,12, 1000,  parseDate("2024-01-15"), parseDate("2024-03-31"), "TestUser", "AdminUser"));
//        rateStatusRepo.save(new CDRatesStatus("20231201m12S10kAL",12000, 1000000,12, 1000,  parseDate("2024-01-15"), parseDate("2024-03-31"), "TestUser", "AdminUser"));
//        rateStatusRepo.save(new CDRatesStatus("20231201m12S10kAK",12000, 1000000,12, 1000,  parseDate("2024-01-15"), parseDate("2024-03-31"), "TestUser", "AdminUser"));
//        rateStatusRepo.save(new CDRatesStatus("20231201m12S10kAR",12000, 1000000,12, 1000,  parseDate("2024-01-15"), parseDate("2024-03-31"), "TestUser", "AdminUser"));
//        rateStatusRepo.save(new CDRatesStatus("20231201m12S10kFL",12000, 1000000,12, 1000,  parseDate("2024-01-15"), parseDate("2024-03-31"), "TestUser", "AdminUser"));
//        rateStatusRepo.save(new CDRatesStatus("20231201m12S10kIL",12000, 1000000,12, 1000,  parseDate("2024-01-15"), parseDate("2024-03-31"), "TestUser", "AdminUser"));
//        rateStatusRepo.save(new CDRatesStatus("20231201m12S10kLA",12000, 1000000,12, 1000,  parseDate("2024-01-15"), parseDate("2024-03-31"), "TestUser", "AdminUser"));
//        rateStatusRepo.save(new CDRatesStatus("20231201m12S10kME",12000, 1000000,12, 1000,  parseDate("2024-01-15"), parseDate("2024-03-31"), "TestUser", "AdminUser"));
//        rateStatusRepo.save(new CDRatesStatus("20231201m12S10kNH",12000, 1000000,12, 1000,  parseDate("2024-01-15"), parseDate("2024-03-31"), "TestUser", "AdminUser"));
//        rateStatusRepo.save(new CDRatesStatus("20231201m12S10kNJ",12000, 1000000,12, 1000,  parseDate("2024-01-15"), parseDate("2024-03-31"), "TestUser", "AdminUser"));

    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
