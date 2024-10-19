package com.example.sipcalculator.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SIPRecordDao {
    @Insert

    void insert(SIPRecord sipRecord);

    @Query("SELECT * FROM sip_record")
    List<SIPRecord> getAll();
}
