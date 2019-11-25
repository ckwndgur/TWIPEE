package com.nslb.twipee.sns;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.nslb.twipee.R;

public class SNS_Search extends AppCompatActivity implements View.OnClickListener {

    private Button btn_align1, btn_align2, btn_align3, btn_align4, btn_align5, btn_align6, btn_choice1, btn_choice2,btn_choice3,btn_choice4,btn_choice5,btn_choice6,btn_choice7,btn_choice8,btn_choice9,btn_choice10,btn_choice11,btn_choice12;
    private SeekBar seek_travelExpenses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_view_feed_search);

        initView();
    }

    @Override
    public void onClick(View view) {

    }

    private void initView()
    {
        btn_align1 = findViewById(R.id.btn_align1);
        btn_align2 = findViewById(R.id.btn_align2);
        btn_align3 = findViewById(R.id.btn_align3);
        btn_align4 = findViewById(R.id.btn_align4);
        btn_align5 = findViewById(R.id.btn_align5);
        btn_align6 = findViewById(R.id.btn_align6);

        seek_travelExpenses = findViewById(R.id.seek_travelExpenses);

        btn_choice1 = findViewById(R.id.btn_choice1);
        btn_choice2 = findViewById(R.id.btn_choice2);
        btn_choice3 = findViewById(R.id.btn_choice3);
        btn_choice4 = findViewById(R.id.btn_choice4);
        btn_choice5 = findViewById(R.id.btn_choice5);
        btn_choice6 = findViewById(R.id.btn_choice6);
        btn_choice7 = findViewById(R.id.btn_choice7);
        btn_choice8 = findViewById(R.id.btn_choice8);
        btn_choice9 = findViewById(R.id.btn_choice9);
        btn_choice10 = findViewById(R.id.btn_choice10);
        btn_choice11 = findViewById(R.id.btn_choice11);
        btn_choice12 = findViewById(R.id.btn_choice12);

        btn_align1.setOnClickListener(this);
        btn_align2.setOnClickListener(this);
        btn_align3.setOnClickListener(this);
        btn_align4.setOnClickListener(this);
        btn_align5.setOnClickListener(this);
        btn_align6.setOnClickListener(this);

        btn_choice1.setOnClickListener(this);
        btn_choice2.setOnClickListener(this);
        btn_choice3.setOnClickListener(this);
        btn_choice4.setOnClickListener(this);
        btn_choice5.setOnClickListener(this);
        btn_choice6.setOnClickListener(this);
        btn_choice7.setOnClickListener(this);
        btn_choice8.setOnClickListener(this);
        btn_choice9.setOnClickListener(this);
        btn_choice10.setOnClickListener(this);
        btn_choice11.setOnClickListener(this);
        btn_choice12.setOnClickListener(this);

    }
}
