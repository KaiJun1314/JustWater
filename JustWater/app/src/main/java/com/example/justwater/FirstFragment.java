package com.example.justwater;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.justwater.databinding.FragmentFirstBinding;
import com.github.lzyzsd.circleprogress.CircleProgress;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class FirstFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private FragmentFirstBinding binding;
    private TextView dateTV, totalTV, countTV;
    private String todayDate;
    private CircleProgress progress;
    private int Year, Month, Day;
    private ImageView menuIcon;

    private DailyDataViewModel dailyDataViewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void observeData() {
        dailyDataViewModel.getCount().observe(getViewLifecycleOwner(), integer -> {
            if (integer != 0) {
                countTV.setVisibility(View.VISIBLE);
                progress.setVisibility(View.VISIBLE);
                menuIcon.setVisibility(View.VISIBLE);
                countTV.setText(Integer.toString(integer));
            } else {
                countTV.setVisibility(View.INVISIBLE);
                progress.setVisibility(View.INVISIBLE);
                menuIcon.setVisibility(View.INVISIBLE);
                totalTV.setText("No Data Recorded");
            }
        });
        dailyDataViewModel.getTotal().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                countTV.setVisibility(View.VISIBLE);
                progress.setVisibility(View.VISIBLE);
                menuIcon.setVisibility(View.VISIBLE);
                int progress_percentage = (int) Math.ceil(data / 1000.0 * 100);
                if (progress_percentage > 100) {
                    progress.setProgress(100);
                    totalTV.setText(Integer.toString(data) + "ml\nCongratulations !\nyou hit the target");
                } else {
                    progress.setProgress(progress_percentage);
                    totalTV.setText(Integer.toString(data) + "ml / 1l");
                }
            }
        });
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        totalTV = (TextView) view.findViewById(R.id.totalTV);
        countTV = (TextView) view.findViewById(R.id.countTV);
        progress = (CircleProgress) view.findViewById(R.id.circle_progress);
        dateTV = (TextView) view.findViewById(R.id.dateTV);
        menuIcon = (ImageView) view.findViewById(R.id.imageButton);

        Calendar myCalendar = Calendar.getInstance();
        Year = myCalendar.get(Calendar.YEAR);
        Month = myCalendar.get(Calendar.MONTH);
        Day = myCalendar.get(Calendar.DAY_OF_MONTH);
        Date today = new Date(Year, Month, Day);
        todayDate = DateFormat.getDateInstance(DateFormat.FULL).format(myCalendar.getTime());

        dailyDataViewModel = new ViewModelProvider(requireActivity()).get(DailyDataViewModel.class);
        dailyDataViewModel.readDailyData(today);
        binding.imageButton.setOnClickListener(view1 -> NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment));

        binding.linearLayout.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    FirstFragment.this, Year, Month, Day);
            datePickerDialog.show();
        });
        observeData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        if (todayDate.equals(selectedDate)) {
            dateTV.setText("Today");
        } else {
            dateTV.setText(selectedDate);
        }
        dailyDataViewModel.readDailyData(new Date(year, month, dayOfMonth));
        observeData();
    }
}