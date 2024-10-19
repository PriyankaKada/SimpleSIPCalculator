package com.example.sipcalculator.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MutualFundDao {
    @Insert
    void insert(MutualFund mutualFund);

    @Query("SELECT * FROM mutual_fund")
    List<MutualFund> getAll();
}
