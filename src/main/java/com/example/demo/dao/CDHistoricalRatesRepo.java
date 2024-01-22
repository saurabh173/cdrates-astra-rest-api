package com.example.demo.dao;

//import com.example.demo.model.CDHistoricalRates;
import com.example.demo.model.CDRates;
import com.example.demo.model.CDRatesWithoutManagerRate;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
//mport org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;


@Repository
public interface CDHistoricalRatesRepo extends CassandraRepository<CDRates, String> {

    //@Query("SELECT new com.example.demo.model.CDHistoricalRates(c.cdUniqueId, c.termLength, c.interestRate, c.APY, c.minimumDeposit, c.maximumDeposit, c.penaltyDays, c.penaltyPercentage, c.cdType, c.status, c.startDate, c.endDate, c.createdBy, c.updatedBy) FROM CDHistoricalRates c ")
    //CDHistoricalRates findRatesExcludingManagerRate();

//    @Query("SELECT new com.example.demo.model.CDHistoricalRates(c.cdUniqueId, c.termLength, c.interestRate, c.APY, c.stateCode, c.minimumDeposit, c.penaltyDays, c.penaltyPercentage, c.cdType, c.status, c.startDate, c.endDate, c.createdBy, c.updatedBy) FROM CDHistoricalRates c where c.stateCode = :stateCode ")
//    List<CDHistoricalRates> findRatesExcludingManagerRate(@Param("stateCode") String stateCode);
//
//    @Query("SELECT * FROM CDRates c WHERE c.stateCode = :stateCode")
//    List<CDHistoricalRates> findAllByStateCode(@Param("stateCode") String stateCode);


    //****DO NOT DELETE***Working with Cassandra****
    @Query("SELECT cdUniqueId, termLength, interestRate, APY, stateCode, minimumDeposit, maximumDeposit, penaltyDays, penaltyPercentage, cdType, status, startDate, endDate, createdBy, updatedBy FROM CDRates WHERE stateCode = :stateCode AND startDate >= :eightYearsAgo ALLOW FILTERING")
    List<CDRatesWithoutManagerRate> findRatesExcludingManagerRate(@Param("stateCode") String stateCode, @Param("eightYearsAgo") Instant eightYearsAgo);


    //****DO NOT DELETE***Working with Cassandra****
    @Query("SELECT * FROM CDRates  WHERE stateCode = :stateCode AND startDate >= :eightYearsAgo ALLOW FILTERING")
    List<CDRates> findAllByStateCode(@Param("stateCode") String stateCode, @Param("eightYearsAgo") Instant eightYearsAgo);
}
