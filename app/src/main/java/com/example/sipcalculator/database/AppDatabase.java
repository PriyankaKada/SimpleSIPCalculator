package com.example.sipcalculator.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MutualFund.class, SIPRecord.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MutualFundDao mutualFundDao();
    public abstract SIPRecordDao sipRecordDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "mutual_fund_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
