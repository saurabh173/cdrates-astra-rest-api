package com.example.demo.dao;

import com.example.demo.model.CDRatesStatus;
import org.springframework.data.cassandra.repository.CassandraRepository;
//import org.springframework.data.repository.CrudRepository;

public interface CDRatesStatusRepo extends CassandraRepository<CDRatesStatus, String > {
}
