package com.example.sipcalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipcalculator.reports.YearlyInvestment;

import java.util.ArrayList;
import java.util.List;

public class InvestmentListFragment extends Fragment {
    private RecyclerView recyclerView;
    private InvestmentAdapter adapter;
    private List<YearlyInvestment> investmentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_investment_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Retrieve the list of investments from the arguments
        investmentList = getArguments() != null ? getArguments().getParcelableArrayList("investmentList") : new ArrayList<>();

        adapter = new InvestmentAdapter(investmentList);
        recyclerView.setAdapter(adapter);

        // Set up the back button
        ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pop the current fragment from the back stack
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
