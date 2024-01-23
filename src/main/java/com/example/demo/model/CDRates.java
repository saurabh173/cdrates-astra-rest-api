package com.example.demo.model;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.cassandra.core.mapping.Table;


//@Table
public class CDRates {

    //@Id
    private String id;

    private double apy;
    private String cdtype;
    private String cduniqueid;
    private String createdby;
    private String createdon;
    private String enddate;
    private double interestrate;
    private double managerrate;
    private int maximumdeposit;
    private int minimumdeposit;
    private int penaltydays;
    private double penaltypercentage;
    private String startdate;
    private String statecode;
    private String status;
    private int termlength;
    private String updatedby;
    private String updatedon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getApy() {
        return apy;
    }

    public void setApy(double apy) {
        this.apy = apy;
    }

    public String getCdtype() {
        return cdtype;
    }

    public void setCdtype(String cdtype) {
        this.cdtype = cdtype;
    }

    public String getCduniqueid() {
        return cduniqueid;
    }

    public void setCduniqueid(String cduniqueid) {
        this.cduniqueid = cduniqueid;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public double getInterestrate() {
        return interestrate;
    }

    public void setInterestrate(double interestrate) {
        this.interestrate = interestrate;
    }

    public double getManagerrate() {
        return managerrate;
    }

    public void setManagerrate(double managerrate) {
        this.managerrate = managerrate;
    }

    public int getMaximumdeposit() {
        return maximumdeposit;
    }

    public void setMaximumdeposit(int maximumdeposit) {
        this.maximumdeposit = maximumdeposit;
    }

    public int getMinimumdeposit() {
        return minimumdeposit;
    }

    public void setMinimumdeposit(int minimumdeposit) {
        this.minimumdeposit = minimumdeposit;
    }

    public int getPenaltydays() {
        return penaltydays;
    }

    public void setPenaltydays(int penaltydays) {
        this.penaltydays = penaltydays;
    }

    public double getPenaltypercentage() {
        return penaltypercentage;
    }

    public void setPenaltypercentage(double penaltypercentage) {
        this.penaltypercentage = penaltypercentage;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTermlength() {
        return termlength;
    }

    public void setTermlength(int termlength) {
        this.termlength = termlength;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public String getUpdatedon() {
        return updatedon;
    }

    public void setUpdatedon(String updatedon) {
        this.updatedon = updatedon;
    }
}
