package com.example.sipcalculator.database;

import android.content.Context;
import androidx.room.Room;
import java.util.Arrays;

public class DatabaseInitializer {

    public static void populateDatabase(final MutualFundDao dao) {
        new Thread(() -> {
            MutualFund[] mutualFunds = new MutualFund[] {
                new MutualFund("Aditya Birla", 8.5f),
                new MutualFund("SBI Mutual Fund", 10.0f),
                new MutualFund("Canera Roboco", 7.2f),
                new MutualFund("HDFC Securities", 9.3f),
                new MutualFund("Nippon Small Cap Funds", 11.5f)
            };

            for (MutualFund fund : mutualFunds) {
                dao.insert(fund);
            }
        }).start();
    }
}
