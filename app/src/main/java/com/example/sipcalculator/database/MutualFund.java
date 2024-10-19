package com.example.sipcalculator.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "mutual_fund")
public class MutualFund {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private float returnPercentage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MutualFund)) return false;
        MutualFund that = (MutualFund) o;
        return Float.compare(that.returnPercentage, returnPercentage) == 0 &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, returnPercentage);
    }
    public MutualFund(String name, float returnPercentage) {
        this.name = name;
        this.returnPercentage = returnPercentage;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getReturnPercentage() {
        return returnPercentage;
    }

    public void setReturnPercentage(float returnPercentage) {
        this.returnPercentage = returnPercentage;
    }
}
