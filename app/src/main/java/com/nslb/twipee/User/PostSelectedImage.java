package com.nslb.twipee.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.nslb.twipee.R;

public class PostSelectedImage extends Fragment {

    ImageButton btn_cancel;
    ImageView iv_selected_photo;

    public PostSelectedImage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_selected_image , container , false);


        btn_cancel = (ImageButton) view.findViewById(R.id.btn_cancel);
        iv_selected_photo = (ImageView) view.findViewById(R.id.iv_selected_photo);

        if (getArguments() != null) {
            Bundle args = getArguments();
            //받아온 Resource를 ImageView로
            iv_selected_photo.setImageResource(args.getInt("imgRes"));
        }

        return view;
    }
}