package com.example.sipcalculator.reports;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipcalculator.R;
import com.example.sipcalculator.database.SIPRecord;

import java.util.List;

public class MutualFundAdapter extends RecyclerView.Adapter<MutualFundAdapter.ViewHolder> {
    private List<SIPRecord> mutualFundList;

    public MutualFundAdapter(List<SIPRecord> mutualFundList) {
        this.mutualFundList = mutualFundList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mutual_fund, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SIPRecord fund = mutualFundList.get(position);
        holder.fundName.setText(fund.getFundName());

        // Format the expected return and total amount
        holder.expectedReturn.setText(String.format("Expected Return: %.2f", fund.getExpectedReturn()));
        holder.totalAmount.setText(String.format("Total Amount: %.2f", fund.getTotalAmount()));
        holder.type.setText(String.format("Type: %s", fund.getType()));
    }

    @Override
    public int getItemCount() {
        return mutualFundList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fundName, expectedReturn, totalAmount, type;

        public ViewHolder(View itemView) {
            super(itemView);
            fundName = itemView.findViewById(R.id.fundName);
            expectedReturn = itemView.findViewById(R.id.expectedReturn);
            totalAmount = itemView.findViewById(R.id.totalAmount);
            type = itemView.findViewById(R.id.type);
        }
    }
}
