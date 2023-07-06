package com.example.justwater;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.justwater.database.DailyData;
import com.example.justwater.databinding.ActivityMain2Binding;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMain2Binding binding;
    private DailyDataViewModel dailyDataViewModel;
    public static final int TEXT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dailyDataViewModel = new ViewModelProvider(this).get(DailyDataViewModel.class);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.idFABAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddWater.class);
            startActivityForResult(intent, TEXT_REQUEST);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Calendar myCalendar = Calendar.getInstance();
                Date today = new Date(myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                Time currentTime = new Time(myCalendar.getTime().getHours(), myCalendar.getTime().getMinutes(),
                        myCalendar.getTime().getSeconds());
                int amount = data.getIntExtra(AddWater.EXTRA_REPLY, 0);
                dailyDataViewModel.insert(new DailyData( today,  currentTime,  amount));
            }
        }
    }
}