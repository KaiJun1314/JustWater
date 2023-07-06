package com.example.justwater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddWater extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.justwater.extra.REPLY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water);

        GridLayout grid = (GridLayout) findViewById(R.id.grid_layout);
        int childCount = grid.getChildCount();

        for (int i= 0; i < childCount; i++){
            CardView container = (CardView) grid.getChildAt(i);
            container.setId(i + 1);

            LinearLayout LL = (LinearLayout) container.getChildAt(0);
            TextView textView = (TextView)LL.getChildAt(1);
            textView.setText(Integer.toString((i + 1) * 250) + "ml");

            container.setOnClickListener(view -> {
                int id = view.getId() ;
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY, id*250);
                setResult(RESULT_OK, replyIntent);
                finish();
            });
        }
    }
}