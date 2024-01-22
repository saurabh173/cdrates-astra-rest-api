package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
import java.util.Date;

//@Entity
@Table
public class CDRatesStatus {

    @Id
    private String cdUniqueId;

    private long amountCollected;
    private long maximumAmountAllowed;
    private long totalCDCount;
    private long averageDepositSize;
    private Date startDate;
    private Date endDate;
    private String createdBy;
    private String updatedBy;

    public CDRatesStatus(){

    }
    public CDRatesStatus(String cdUniqueId, long amountCollected, long maximumAmountAllowed, long totalCDCount, long averageDepositSize, Date startDate, Date endDate, String createdBy, String updatedBy) {
        this.cdUniqueId = cdUniqueId;
        this.amountCollected = amountCollected;
        this.maximumAmountAllowed = maximumAmountAllowed;
        this.totalCDCount = totalCDCount;
        this.averageDepositSize = averageDepositSize;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
