package com.example.sipcalculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sipcalculator.database.AppDatabase;
import com.example.sipcalculator.database.MutualFund;
import com.example.sipcalculator.database.SIPRecord;
import com.example.sipcalculator.reports.LumpSumCalculator;
import com.example.sipcalculator.reports.SIPCalculator;
import com.example.sipcalculator.reports.YearlyInvestment;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executors;

public class SipCalculatorFragment extends Fragment {
    private final List<MutualFund> mutualFunds = new ArrayList<>();

    private Spinner spinnerMF;
    private TextView textViewRateOfInterest,textExpectedReturns,textTotalAmount,tvTotalAmountInvested;

    private SpinAdapter adapter;
    private Button calculate,addSIP,showDetails,reset;

    MutualFund selectMF;
    EditText etAmount,period;

    double totalAmountOnMaturity,totalGain,totalInvestment;

    PieChart pieChart;
    List<YearlyInvestment> list;

    private RadioGroup radioGroupInvestmentType;
    String selectedInvestmentType = "SIP";
    private ProgressBar progressBar;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_sip_calculator, container, false);
        progressBar = view.findViewById(R.id.progressBar);

        spinnerMF = view.findViewById(R.id.spinnerMutualFunds);
        textViewRateOfInterest = view.findViewById(R.id.tvInterestRate);
        tvTotalAmountInvested = view.findViewById(R.id.tvTotalAmountInvested);
        textExpectedReturns = view.findViewById(R.id.tvExpectedReturn);
        etAmount = view.findViewById(R.id.etAmount);
        period = view.findViewById(R.id.etYears);
        calculate =view.findViewById(R.id.btnCalculate);
        textTotalAmount =view.findViewById(R.id.tvTotalAmount);
        textTotalAmount =view.findViewById(R.id.tvTotalAmount);
//        addSIP =view.findViewById(R.id.btnAddSIP);
        pieChart = view.findViewById(R.id.piechart);
        reset = view.findViewById(R.id.reset);
        showDetails= view.findViewById(R.id.showDetails);
        // Initialize mutual funds
        getMutualFundsFromDB();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetViews(view);
            }
        });
        showDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAmount.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Amount is Empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (period.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Period is Empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                int amount = Integer.parseInt(etAmount.getText().toString());
                int periodInt = Integer.parseInt(period.getText().toString());

                if (selectedInvestmentType.equalsIgnoreCase("SIP")) {
                    list = SIPCalculator.calculateSIPReturns(amount, periodInt, selectMF.getReturnPercentage());
                }else{
                    list = LumpSumCalculator.calculateLumpSumReturns(amount,periodInt,selectMF.getReturnPercentage());
                }

                FragmentManager fragmentManager = getParentFragmentManager();
                InvestmentListFragment investmentListFragment = new InvestmentListFragment();

                // Create a bundle and pass the investment list
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("investmentList", (ArrayList<? extends Parcelable>) list);
                investmentListFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, investmentListFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

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
                resetViews(view);
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (etAmount.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Amount is Empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (period.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Period is Empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                pieChart.clearChart();
                // Fetch inputs and calculate
                int amount = Integer.parseInt(etAmount.getText().toString());
                int periodInt = Integer.parseInt(period.getText().toString());

                if (selectedInvestmentType.equalsIgnoreCase("SIP")) {
                    list = SIPCalculator.calculateSIPReturns(amount, periodInt, selectMF.getReturnPercentage());
                }else{
                    list = LumpSumCalculator.calculateLumpSumReturns(amount,periodInt,selectMF.getReturnPercentage());
                }
                    int size=list.size();
                    YearlyInvestment yearlyInvestment = list.get(size-1);

                totalGain = yearlyInvestment.getTotalReturns();
                totalInvestment = yearlyInvestment.getTotalInvested();
                totalAmountOnMaturity = totalInvestment + totalGain;

                // Update UI
                tvTotalAmountInvested.setText("Invested Amount: " + formatCurrency(totalInvestment));
                textExpectedReturns.setText("Expected Returns: " + formatCurrency(totalGain));
                textTotalAmount.setText("Total Amount on Maturity: " + formatCurrency(totalAmountOnMaturity));



                pieChart.setVisibility(View.VISIBLE);
                pieChart.setValueTextColor(Color.BLACK);
                pieChart.setValueTextSize(24);
                pieChart.setUseInnerPadding(false);
                pieChart.addPieSlice(
                        new PieModel(
                                "Total Gain",
                                Integer.parseInt(""+(int)totalGain),
                                Color.parseColor("#FFA726")));

                pieChart.addPieSlice(
                        new PieModel(
                                "Total Investment",
                                Integer.parseInt(""+(int)totalInvestment),
                                Color.parseColor("#66BB6A")));

                pieChart.startAnimation();
            }
        });


//        addSIP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Insert SIP record in a background thread using Executors
//                SIPRecord sipRecord = new SIPRecord(selectMF.getName(), totalGain ,totalAmountOnMaturity, selectedInvestmentType);
//                Executors.newSingleThreadExecutor().execute(() -> {
//                    AppDatabase db = AppDatabase.getDatabase(getContext());
//                    db.sipRecordDao().insert(sipRecord);
//                });
//            }
//        });



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        resetViews(view);
    }

    private void resetViews(View view) {

        etAmount.setText("");
        period.setText("");
        tvTotalAmountInvested.setText("Invested Amount: ");

        textViewRateOfInterest.setText("Rate Of Interest: ");
        textExpectedReturns.setText("Expected Return");
        textTotalAmount.setText("Total amount on Maturity:");
        pieChart.setVisibility(View.GONE);
        spinnerMF.setAdapter(adapter);

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

    // Method to format currency
    private String formatCurrency(double amount) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        return numberFormat.format(amount);
    }

}
