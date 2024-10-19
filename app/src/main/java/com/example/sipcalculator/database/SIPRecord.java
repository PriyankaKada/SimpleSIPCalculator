package com.example.sipcalculator.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sip_record")
public class SIPRecord {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String fundName;
    private double expectedReturn;
    private double totalAmount;
    private String  type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SIPRecord(String fundName, double expectedReturn, double totalAmount, String type) {
        this.fundName = fundName;
        this.expectedReturn = expectedReturn;
        this.totalAmount = totalAmount;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public double getExpectedReturn() {
        return expectedReturn;
    }

    public void setExpectedReturn(double expectedReturn) {
        this.expectedReturn = expectedReturn;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
