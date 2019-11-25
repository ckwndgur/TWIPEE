package com.nslb.twipee.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.nslb.twipee.R;
import com.nslb.twipee.UserInfoView;
import com.nslb.twipee.Utils.Permissions;
import com.nslb.twipee.board.BoardWrite;
import com.nslb.twipee.ui.MyEventListener;
import com.nslb.twipee.ui.main.PhotoAdapter;

public class PostCamera extends Fragment implements MyEventListener {

    private static final String TAG = "PhotoFragment";

    //constant
    private static final int PHOTO_FRAGMENT_NUM = 1;
    private static final int GALLERY_FRAGMENT_NUM = 2;
    private static final int CAMERA_REQUEST_CODE = 5;

    private Context mContext;

    public PostCamera(Context context){
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_camera, container, false);
        Log.d(TAG, "onCreateView: started.");

        ((PostGetImage)mContext).onTabSelected(this);

        return view;
    }


    private boolean isRootTask(){
        Intent intent = getActivity().getIntent();
        String activity_name = intent.getExtras().getString(getString(R.string.activity_name));

        if(activity_name.equals(getString(R.string.post_review))){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getExtras() != null) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Log.d(TAG, "onActivityResult: done taking a photo.");
                Log.d(TAG, "onActivityResult: attempting to navigate to final share screen.");

                Bitmap bitmap;
                bitmap = (Bitmap) data.getExtras().get("data");

                if (getActivity().getIntent().getExtras() != null) {
                    if (isRootTask()) {
                        try {
                            Log.d(TAG, "onActivityResult: received new bitmap from camera: " + bitmap);
                            Intent intent = new Intent(getActivity(), PostReview.class);
                            intent.putExtra(getString(R.string.selected_bitmap), bitmap);
                            intent.putExtra(getString(R.string.return_to_fragment), getString(R.string.post_review));
                            startActivity(intent);
                            getActivity().finish();
                        } catch (NullPointerException e) {
                            Log.d(TAG, "onActivityResult: NullPointerException: " + e.getMessage());
                        }
                    } else {
                        try {
                            Log.d(TAG, "onActivityResult: received new bitmap from camera: " + bitmap);
                            Intent intent = new Intent(getActivity(), BoardWrite.class);
                            intent.putExtra(getString(R.string.selected_bitmap), bitmap);
                            intent.putExtra(getString(R.string.return_to_fragment), getString(R.string.board_write));
                            startActivity(intent);
                            getActivity().finish();
                        } catch (NullPointerException e) {
                            Log.d(TAG, "onActivityResult: NullPointerException: " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == PostGetImage.FMPHOTO) {
            if (((PostGetImage) getActivity()).checkPermissions(Permissions.CAMERA_PERMISSION[0])) {
                Log.d(TAG, "onClick: starting camera");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
}
