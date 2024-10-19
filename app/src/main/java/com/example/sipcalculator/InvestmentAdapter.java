package com.example.sipcalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipcalculator.reports.YearlyInvestment;

import java.util.List;

public class InvestmentAdapter extends RecyclerView.Adapter<InvestmentAdapter.ViewHolder> {
    private List<YearlyInvestment> investmentList;

    public InvestmentAdapter(List<YearlyInvestment> investmentList) {
        this.investmentList = investmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YearlyInvestment investment = investmentList.get(position);
        holder.yearTextView.setText("Year: " + investment.getYear());
        holder.investedTextView.setText("Total Invested: " + investment.getTotalInvested());
        holder.returnsTextView.setText("Total Returns: " + investment.getTotalReturns());
    }

    @Override
    public int getItemCount() {
        return investmentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView yearTextView;
        TextView investedTextView;
        TextView returnsTextView;

        ViewHolder(View itemView) {
            super(itemView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            investedTextView = itemView.findViewById(R.id.investedTextView);
            returnsTextView = itemView.findViewById(R.id.returnsTextView);
        }
    }
}
