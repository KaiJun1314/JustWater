package com.example.justwater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.text.SimpleDateFormat;

public class DailyDataViewHolder extends RecyclerView.ViewHolder {

    private final TextView timeItemView;
    private final TextView amountItemView;
    private final SimpleDateFormat sdf = new SimpleDateFormat("hh.mm aa");

    private DailyDataViewHolder(View itemView) {
        super(itemView);
        timeItemView = itemView.findViewById(R.id.idTime);
        amountItemView = itemView.findViewById(R.id.idWaterAmount);
    }

    public void bind(Time time, int amount) {
        timeItemView.setText(sdf.format(time));
        amountItemView.setText(Integer.toString(amount) + " ml");
    }

    static DailyDataViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_rv_item, parent, false);
        return new DailyDataViewHolder(view);
    }
}
