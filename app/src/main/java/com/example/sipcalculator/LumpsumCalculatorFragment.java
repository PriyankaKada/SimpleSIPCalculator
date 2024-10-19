package com.example.sipcalculator;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipcalculator.database.AppDatabase;
import com.example.sipcalculator.database.SIPRecord;
import com.example.sipcalculator.reports.MutualFundAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class LumpsumCalculatorFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MutualFundAdapter adapter;
    private List<SIPRecord> mutualFundList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lumpsum_calculator, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        mutualFundList = new ArrayList<>();
        adapter = new MutualFundAdapter(mutualFundList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        fetchMutualFunds();

        return view;
    }

    private void fetchMutualFunds() {
        progressBar.setVisibility(View.VISIBLE);
        // Use Executors for background thread
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getContext());
            List<SIPRecord> funds = db.sipRecordDao().getAll();

            // Switch back to the main thread to update the UI
            new Handler(Looper.getMainLooper()).post(() -> {
                mutualFundList.clear(); // Clear the list before adding new data
                mutualFundList.addAll(funds);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            });
        });
    }


}
