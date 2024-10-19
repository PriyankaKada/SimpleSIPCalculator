package com.example.sipcalculator.reports;

import java.util.ArrayList;
import java.util.List;

public class LumpSumCalculator {

    public static List<YearlyInvestment> calculateLumpSumReturns(double investmentAmount, int noOfYears, double annualReturnRate) {
        double totalInvested = investmentAmount; // Lump sum investment
        List<YearlyInvestment> results = new ArrayList<>();

        // Calculate total returns for each year
        for (int year = 1; year <= noOfYears; year++) {
            // Calculate future value using compound interest formula
            double totalReturns = totalInvested * Math.pow((1 + annualReturnRate / 100), year);
            results.add(new YearlyInvestment(year, totalInvested, totalReturns - totalInvested));
        }

        return results;
    }


}
