package com.example.demo.dao;

import com.example.demo.model.CDRates;
import com.example.demo.model.CDRatesWithoutManagerRate;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CDRatesRepo extends CassandraRepository<CDRates, String> {

    //****DO NOT DELETE***Working with h2****
    //@Query("SELECT new com.example.demo.model.CDRates(c.cdUniqueId, c.termLength, c.interestRate, c.APY, c.stateCode, c.minimumDeposit, c.maximumDeposit, c.penaltyDays, c.penaltyPercentage, c.cdType, c.status, c.startDate, c.endDate, c.createdBy, c.updatedBy) FROM CDRates c where c.stateCode = :stateCode")
    //List<CDRates> findRatesExcludingManagerRate(@Param("stateCode") String stateCode);

    //@Query("SELECT c.cdUniqueId, c.termLength, c.interestRate, c.APY, c.stateCode, c.minimumDeposit, c.maximumDeposit, c.penaltyDays, c.penaltyPercentage, c.cdType, c.status, c.startDate, c.endDate, c.createdBy, c.updatedBy FROM CDRates c where c.stateCode = :stateCode")
    //List<CDRates> findRatesExcludingManagerRate(@Param("stateCode") String stateCode);

    //****DO NOT DELETE***Working with Cassandra****
    @Query("SELECT cdUniqueId, termLength, interestRate, APY, stateCode, minimumDeposit, maximumDeposit, penaltyDays, penaltyPercentage, cdType, status, startDate, endDate, createdBy, updatedBy FROM CDRates WHERE stateCode = :stateCode ALLOW FILTERING")
    List<CDRatesWithoutManagerRate> findRatesExcludingManagerRate(@Param("stateCode") String stateCode);


    //****DO NOT DELETE***Working with Cassandra****
    @Query("SELECT * FROM CDRates  WHERE stateCode = :stateCode ALLOW FILTERING")
    List<CDRates> findAllByStateCode(@Param("stateCode") String stateCode);

}
