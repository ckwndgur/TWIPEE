package com.nslb.twipee.ui.main;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nslb.twipee.User.PostCamera;
import com.nslb.twipee.User.PostGallery;

public class PhotoAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;
    PostGallery tab1;
    PostCamera tab2;

    public static final int GALLERY = 0;
    public static final int PHOTO = 1;

    private Context mContext;

    public PhotoAdapter(FragmentManager fm, int numOfTabs, Context context) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                tab1 = new PostGallery(mContext);
                return tab1;
            case 1:
                tab2 = new PostCamera(mContext);
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return mNumOfTabs;
    }
}
