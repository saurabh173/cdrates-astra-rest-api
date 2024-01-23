package com.example.demo.model;

//import org.springframework.data.annotation.Id;

import java.util.Date;

public class CDRatesWithoutManagerRate {

    //@Id
    private String id;

    private double apy;
    private String cdtype;
    private String cduniqueid;
    private String createdby;
    private Date createdon;
    private Date enddate;
    private double interestrate;
    private int maximumdeposit;
    private int minimumdeposit;
    private int penaltydays;
    private double penaltypercentage;
    private Date startdate;
    private String statecode;
    private String status;
    private int termlength;
    private String updatedby;
    private Date updatedon;

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

    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public double getInterestrate() {
        return interestrate;
    }

    public void setInterestrate(double interestrate) {
        this.interestrate = interestrate;
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

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
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

    public Date getUpdatedon() {
        return updatedon;
    }

    public void setUpdatedon(Date updatedon) {
        this.updatedon = updatedon;
    }
}
