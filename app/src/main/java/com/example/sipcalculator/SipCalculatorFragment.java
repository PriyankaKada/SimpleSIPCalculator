package com.example.sipcalculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sipcalculator.database.AppDatabase;
import com.example.sipcalculator.database.MutualFund;
import com.example.sipcalculator.database.SIPRecord;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

public class SipCalculatorFragment extends Fragment {
    private final List<MutualFund> mutualFunds = new ArrayList<>();

    private Spinner spinnerMF;
    private TextView textViewRateOfInterest;
    private TextView textExpectedReturns;
    private TextView textTotalAmount;
    private SpinAdapter adapter;
    private Button calculate;
    private Button addSIP;
    MutualFund selectMF;
    EditText etAmount;
    EditText period;
    float totalAmountOnMaturity;
    float totalGain;
    PieChart pieChart;
    float totalInvestment;
    private RadioGroup radioGroupInvestmentType;
    String selectedInvestmentType = "SIP";
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sip_calculator, container, false);
        progressBar = view.findViewById(R.id.progressBar);

        spinnerMF = view.findViewById(R.id.spinnerMutualFunds);
        textViewRateOfInterest = view.findViewById(R.id.tvInterestRate);
        textExpectedReturns = view.findViewById(R.id.tvExpectedReturn);
        etAmount = view.findViewById(R.id.etAmount);
        period = view.findViewById(R.id.etYears);
        calculate =view.findViewById(R.id.btnCalculate);
        textTotalAmount =view.findViewById(R.id.tvTotalAmount);
        textTotalAmount =view.findViewById(R.id.tvTotalAmount);
        addSIP =view.findViewById(R.id.btnAddSIP);
        pieChart = view.findViewById(R.id.piechart);
        // Initialize mutual funds
        getMutualFundsFromDB();


        spinnerMF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                selectMF = adapter.getItem(position);
                textViewRateOfInterest.setText("Rate Of Interest: "+" "+selectMF.getReturnPercentage()+ "%");
                // Here you can do the action you want to...

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

        radioGroupInvestmentType = view.findViewById(R.id.radioGroupInvestmentType);

        // Set listener to the RadioGroup
        radioGroupInvestmentType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = view.findViewById(checkedId);
                selectedInvestmentType = selectedRadioButton.getText().toString();

            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int amount=Integer.parseInt(etAmount.getText().toString());
                int periodInt=Integer.parseInt(period.getText().toString());


                // Calculate total investment and maturity amount
                float maturityAmount = (float) calculateMaturityAmount(amount, selectMF.getReturnPercentage(), periodInt,selectedInvestmentType);
                totalInvestment = calculateTotalInvestment(amount,selectedInvestmentType,periodInt);
                 totalGain = maturityAmount - totalInvestment;
                 totalAmountOnMaturity=totalInvestment+totalGain;

                textExpectedReturns.setText("Expected Returns: "+ totalGain);
                textTotalAmount.setText("Total Amount on Maturity: "+totalAmountOnMaturity );

                pieChart.addPieSlice(
                        new PieModel(
                                "R",
                                Integer.parseInt(""+(int)totalGain),
                                Color.parseColor("#FFA726")));

                pieChart.addPieSlice(
                        new PieModel(
                                "R",
                                Integer.parseInt(""+(int)totalInvestment),
                                Color.parseColor("#66BB6A")));

                pieChart.startAnimation();
            }
        });

        addSIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Insert SIP record in a background thread using Executors
                SIPRecord sipRecord = new SIPRecord(selectMF.getName(), totalGain ,totalAmountOnMaturity, selectedInvestmentType);
                Executors.newSingleThreadExecutor().execute(() -> {
                    AppDatabase db = AppDatabase.getDatabase(getContext());
                    db.sipRecordDao().insert(sipRecord);
                });
            }
        });



        return view;
    }

    private void getMutualFundsFromDB() {
        progressBar.setVisibility(View.VISIBLE);
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getContext());
            List<MutualFund> funds = db.mutualFundDao().getAll();

            // Use a Set to eliminate duplicates
            Set<MutualFund> uniqueFunds = new HashSet<>(funds);

            // Switch back to the main thread to update the UI
            new Handler(Looper.getMainLooper()).post(() -> {
                mutualFunds.clear();
                mutualFunds.addAll(uniqueFunds); // Add unique funds to the list

                // Initialize the adapter after fetching data
                adapter = new SpinAdapter(getContext(), mutualFunds);
                spinnerMF.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);
            });
        });
    }


    private float calculateTotalInvestment(int amount, String selectedInvestmentType, int periodInt) {

        if(selectedInvestmentType.equals("Lumpsum")){
            totalInvestment= amount*periodInt;
        }else{
            totalInvestment = amount * periodInt * 12;
        }
        return  totalInvestment;
    }

    public static double calculateMaturityAmount(double investment, double annualRate, int years, String selectedInvestmentType) {
        double maturityAmount = 0;

        if (selectedInvestmentType.equalsIgnoreCase("SIP")) {
            // Calculate maturity amount for SIP
            double monthlyInvestment = investment; // Assuming this is the monthly SIP amount
            double monthlyRate = annualRate / 100 / 12; // Convert annual rate to monthly
            int totalMonths = years * 12;

            for (int i = 0; i < totalMonths; i++) {
                maturityAmount += monthlyInvestment * Math.pow(1 + monthlyRate, totalMonths - i);
            }
        } else if (selectedInvestmentType.equalsIgnoreCase("Lumpsum")) {
            // Calculate maturity amount for Lumpsum
            double principalAmount = investment; // Assuming this is the lumpsum amount
            double monthlyRate = annualRate / 100 / 12; // Convert annual rate to monthly
            int totalMonths = years * 12;

            maturityAmount = principalAmount * Math.pow(1 + monthlyRate, totalMonths);
        }

        return maturityAmount;
    }

}
