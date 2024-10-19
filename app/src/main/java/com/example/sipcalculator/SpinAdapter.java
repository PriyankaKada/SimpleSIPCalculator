package com.example.sipcalculator;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sipcalculator.database.MutualFund;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sipcalculator.database.MutualFund;

import java.util.List;

public class SpinAdapter extends ArrayAdapter<MutualFund> {

    private Context context;
    private List<MutualFund> values;

    public SpinAdapter(Context context, List<MutualFund> values) {
        super(context, 0, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public MutualFund getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate custom layout
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        }

        TextView label = convertView.findViewById(R.id.tvSpinnerItem);
        label.setText(values.get(position).getName());
        label.setTextColor(Color.BLACK);

        // Show arrow in the initial view
        ImageView arrow = convertView.findViewById(R.id.ivArrow);
        arrow.setVisibility(View.VISIBLE);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Inflate custom layout for dropdown view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        }

        TextView label = convertView.findViewById(R.id.tvSpinnerItem);
        label.setText(values.get(position).getName());
        label.setTextColor(Color.BLACK);

        // Hide arrow in dropdown view
        ImageView arrow = convertView.findViewById(R.id.ivArrow);
        arrow.setVisibility(View.GONE);

        return convertView;
    }
}
