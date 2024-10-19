package com.example.sipcalculator.reports;



import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class YearlyInvestment implements Parcelable {
    int year;
    double totalInvested;
    double totalReturns;

    public YearlyInvestment(int year, double totalInvested, double totalReturns) {
        this.year = year;
        this.totalInvested = totalInvested;
        this.totalReturns = totalReturns;
    }

    protected YearlyInvestment(Parcel in) {
        year = in.readInt();
        totalInvested = in.readDouble();
        totalReturns = in.readDouble();
    }

    public static final Creator<YearlyInvestment> CREATOR = new Creator<YearlyInvestment>() {
        @Override
        public YearlyInvestment createFromParcel(Parcel in) {
            return new YearlyInvestment(in);
        }

        @Override
        public YearlyInvestment[] newArray(int size) {
            return new YearlyInvestment[size];
        }
    };

    public int getYear() {
        return year;
    }

    public double getTotalInvested() {
        return totalInvested;
    }

    public double getTotalReturns() {
        return totalReturns;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeDouble(totalInvested);
        dest.writeDouble(totalReturns);
    }

    @NonNull
    @Override
    public String toString() {
        return "Year: " + year + ", Total Invested: " + totalInvested + ", Total Returns: " + totalReturns;
    }
}

