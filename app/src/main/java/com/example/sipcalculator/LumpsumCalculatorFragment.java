package com.example.sipcalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LumpsumCalculatorFragment extends Fragment {

    private RadioGroup radioGroupInvestmentType;
    private RadioButton radioLumpsum, radioSIP;
    private Spinner spinnerMutualFunds;
    private EditText etAmount, etYears;
    private TextView tvExpectedReturn, tvTotalAmount;
    private Button btnCalculate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sip_calculator, container, false);

        radioGroupInvestmentType = view.findViewById(R.id.radioGroupInvestmentType);
        radioLumpsum = view.findViewById(R.id.radioLumpsum);
        radioSIP = view.findViewById(R.id.radioSIP);
        spinnerMutualFunds = view.findViewById(R.id.spinnerMutualFunds);
        etAmount = view.findViewById(R.id.etAmount);
        etYears = view.findViewById(R.id.etYears);
        tvExpectedReturn = view.findViewById(R.id.tvExpectedReturn);
        tvTotalAmount = view.findViewById(R.id.tvTotalAmount);
        btnCalculate = view.findViewById(R.id.btnCalculate);

        setupSpinner();
        setupRadioGroupListener();

        btnCalculate.setOnClickListener(v -> calculateReturns());

        return view;
    }

    private void setupSpinner() {
        // Replace this with your DB fetching logic
        String[] mutualFunds = {"Fund A", "Fund B", "Fund C"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mutualFunds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMutualFunds.setAdapter(adapter);
    }

    private void setupRadioGroupListener() {
        radioGroupInvestmentType.setOnCheckedChangeListener((group, checkedId) -> {
            // Reset fields when selection changes
            etAmount.setText("");
            etYears.setText("");
            tvExpectedReturn.setText("Expected Return: ");
            tvTotalAmount.setText("Total Amount on Maturity: ");
        });
    }

    private void calculateReturns() {
        String amountString = etAmount.getText().toString();
        String yearsString = etYears.getText().toString();

        if (amountString.isEmpty() || yearsString.isEmpty()) {
            // Handle empty fields (e.g., show a Toast)
            return;
        }

        double amount = Double.parseDouble(amountString);
        int years = Integer.parseInt(yearsString);
        double expectedReturn = 0; // Your calculation logic here
        double totalAmount = 0; // Your calculation logic here

        if (radioLumpsum.isChecked()) {
            expectedReturn = calculateLumpsumReturns(amount, years);
        } else if (radioSIP.isChecked()) {
            expectedReturn = calculateSIPReturns(amount, years);
        }

        totalAmount = amount + expectedReturn;

        tvExpectedReturn.setText("Expected Return: " + expectedReturn);
        tvTotalAmount.setText("Total Amount on Maturity: " + totalAmount);
    }

    private double calculateLumpsumReturns(double amount, int years) {
        // Example calculation for Lumpsum (modify as needed)
        double interestRate = 0.08; // 8% assumed rate
        return amount * Math.pow(1 + interestRate, years) - amount;
    }

    private double calculateSIPReturns(double amount, int years) {
        // Example calculation for SIP (modify as needed)
        double interestRate = 0.08; // 8% assumed rate
        int months = years * 12;
        double futureValue = amount * ((Math.pow(1 + interestRate / 12, months) - 1) / (interestRate / 12)) * (1 + interestRate / 12);
        return futureValue - (amount * months);
    }
}
