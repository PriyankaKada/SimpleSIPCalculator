package com.example.sipcalculator.reports;

import java.util.ArrayList;
import java.util.List;

public class SIPCalculator {

    public static List<YearlyInvestment> calculateSIPReturns(double investmentAmount, int noOfYears, double annualReturnRate) {
        double monthlyRate = annualReturnRate / 12 / 100;
        double monthlyInvestment = investmentAmount;
        List<YearlyInvestment> results = new ArrayList<>();

        for (int year = 1; year <= noOfYears; year++) {
            double totalInvested = 0.0;
            double totalReturns = 0.0;

            // For each year, calculate the total invested and returns
            for (int month = 1; month <= (year * 12); month++) {
                totalInvested += monthlyInvestment;
                int monthsRemaining = year * 12 - month + 1;
                totalReturns += monthlyInvestment * Math.pow((1 + monthlyRate), monthsRemaining);
            }

            results.add(new YearlyInvestment(year, totalInvested, totalReturns - totalInvested));
        }

        return results;
    }

}
