package com.nslb.twipee.board;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class BoardListAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public BoardListAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BoardCamera tab1 = new BoardCamera();
                return tab1;
            case 1:
                BoardLuggage tab2 = new BoardLuggage();
                return tab2;
            case 2:
                BoardBabycarriage tab3 = new BoardBabycarriage();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

