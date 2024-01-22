package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
import java.util.Date;

//@Entity
@Table
public class CDHistoricalRates {
    @Id
    private String cdUniqueId;

    private String termLength;
    private double interestRate;
    private double managerRate;
    private double APY;
    private String stateCode;
    private int minimumDeposit;
    private int penaltyDays;
    private double penaltyPercentage;
    private String cdType;
    private String status;
    private Date startDate;
    private Date endDate;
    private String createdBy;
    private String updatedBy;

    public CDHistoricalRates() {
    }

    public CDHistoricalRates(String cdUniqueId, String termLength, double interestRate, double managerRate, double APY, String stateCode, int minimumDeposit, int penaltyDays, double penaltyPercentage, String cdType, String status, Date startDate, Date endDate, String createdBy, String updatedBy) {
        this.cdUniqueId = cdUniqueId;
        this.termLength = termLength;
        this.interestRate = interestRate;
        this.managerRate = managerRate;
        this.APY = APY;
        this.stateCode = stateCode;
        this.minimumDeposit = minimumDeposit;
        this.penaltyDays = penaltyDays;
        this.penaltyPercentage = penaltyPercentage;
        this.cdType = cdType;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    //Excluding Manager rate
    public CDHistoricalRates(String cdUniqueId, String termLength, double interestRate, double APY, String stateCode, int minimumDeposit, int penaltyDays, double penaltyPercentage, String cdType, String status, Date startDate, Date endDate, String createdBy, String updatedBy) {
        this.cdUniqueId = cdUniqueId;
        this.termLength = termLength;
        this.interestRate = interestRate;
        this.APY = APY;
        this.stateCode = stateCode;
        this.minimumDeposit = minimumDeposit;
        this.penaltyDays = penaltyDays;
        this.penaltyPercentage = penaltyPercentage;
        this.cdType = cdType;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
    public String getCdUniqueId() {
        return cdUniqueId;
    }

    public void setCdUniqueId(String cdUniqueId) {
        this.cdUniqueId = cdUniqueId;
    }

    public String getTermLength() {
        return termLength;
    }

    public void setTermLength(String termLength) {
        this.termLength = termLength;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getManagerRate() {
        return managerRate;
    }

    public void setManagerRate(double managerRate) {
        this.managerRate = managerRate;
    }

    public double getAPY() {
        return APY;
    }

    public void setAPY(double APY) {
        this.APY = APY;
    }

    public int getMinimumDeposit() {
        return minimumDeposit;
    }

    public void setMinimumDeposit(int minimumDeposit) {
        this.minimumDeposit = minimumDeposit;
    }

    public int getPenaltyDays() {
        return penaltyDays;
    }

    public void setPenaltyDays(int penaltyDays) {
        this.penaltyDays = penaltyDays;
    }

    public double getPenaltyPercentage() {
        return penaltyPercentage;
    }

    public void setPenaltyPercentage(double penaltyPercentage) {
        this.penaltyPercentage = penaltyPercentage;
    }

    public String getCdType() {
        return cdType;
    }

    public void setCdType(String cdType) {
        this.cdType = cdType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
}
