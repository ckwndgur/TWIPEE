package com.nslb.twipee.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.nslb.twipee.R;

public class BoardBabycarriage extends Fragment{

    public BoardBabycarriage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_trip_talk_board, container , false);
    }



}
