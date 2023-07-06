package com.example.justwater;

import androidx.recyclerview.widget.ListAdapter;
import com.example.justwater.database.DailyData;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class DailyDataAdapter extends ListAdapter<DailyData, DailyDataViewHolder> {

    public DailyDataAdapter(@NonNull DiffUtil.ItemCallback<DailyData> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public DailyDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return DailyDataViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(DailyDataViewHolder holder, int position) {
        DailyData current = getDataAt(position);
        holder.bind(current.getTime(), current.getAmount());
    }

    public DailyData getDataAt(int position) {
        return getItem(position);
    }

    static class DailyDataDiff extends DiffUtil.ItemCallback<DailyData> {

        @Override
        public boolean areItemsTheSame(@NonNull DailyData oldItem, @NonNull DailyData newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DailyData oldItem, @NonNull DailyData newItem) {
            return oldItem.getTime().equals(newItem.getTime());
        }
    }
}
