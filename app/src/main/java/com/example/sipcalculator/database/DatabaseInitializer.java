package com.example.sipcalculator.database;

import android.content.Context;
import androidx.room.Room;
import java.util.Arrays;

public class DatabaseInitializer {

    public static void populateDatabase(final MutualFundDao dao) {
        new Thread(() -> {
            MutualFund[] mutualFunds = new MutualFund[] {
                new MutualFund("Fund A", 8.5f),
                new MutualFund("Fund B", 10.0f),
                new MutualFund("Fund C", 7.2f),
                new MutualFund("Fund D", 9.3f),
                new MutualFund("Fund E", 11.5f)
            };

            for (MutualFund fund : mutualFunds) {
                dao.insert(fund);
            }
        }).start();
    }
}
