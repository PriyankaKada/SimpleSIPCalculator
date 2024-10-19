package com.example.sipcalculator;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sipcalculator.reports.YearlyInvestment;

import java.text.DecimalFormat;
import java.util.List;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sipcalculator.reports.YearlyInvestment;
import java.util.List;

public class InvestmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<YearlyInvestment> investmentList;

    public InvestmentAdapter(List<YearlyInvestment> investmentList) {
        this.investmentList = investmentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investment_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investment, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00"); // Format for two decimal places

        if (holder instanceof HeaderViewHolder) {
            // Do nothing for the header
        } else if (holder instanceof ItemViewHolder) {
            YearlyInvestment investment = investmentList.get(position - 1); // Offset by 1 for the header
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.yearTextView.setText(String.valueOf(investment.getYear()));
            itemHolder.investedTextView.setText(decimalFormat.format(investment.getTotalInvested()));
            itemHolder.returnsTextView.setText(decimalFormat.format(investment.getTotalReturns()));

            // Calculate total value
            double totalValue = investment.getTotalInvested() + investment.getTotalReturns();
            itemHolder.totalValueTextView.setText(decimalFormat.format(totalValue));
        }
    }

    @Override
    public int getItemCount() {
        return investmentList.size() + 1; // Add 1 for the header
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView yearTextView;
        TextView investedTextView;
        TextView returnsTextView;
        TextView totalValueTextView;

        ItemViewHolder(View itemView) {
            super(itemView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            investedTextView = itemView.findViewById(R.id.investedTextView);
            returnsTextView = itemView.findViewById(R.id.returnsTextView);
            totalValueTextView = itemView.findViewById(R.id.totalValueTextView);
        }
    }
}
