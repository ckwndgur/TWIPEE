package com.nslb.twipee.ui.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PostAapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    public PostAapter(FragmentManager fm,int numOfTabs){
        super(fm);
        this.mNumOfTabs=numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                UserPost tab1=new UserPost();
                return tab1;
            case 1:
                UserMap tab2=new UserMap();
                return tab2;
                default:
                    return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
//    @StringRes
//    private final int[]TAB_IMAGE=new int[]{R.drawable.ic_camera_alt_black_24dp, R.drawable.ic_child_friendly_black_24dp};
//    private final Context mContext;
//    public UserPost mUserPost=null;
//    public UserMap mUserMap=null;
//    public PostAapter(Context context,FragmentManager fm){
//        super(fm);
//        mContext=context;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                return mUserPost = new UserPost();
//            case 1:
//                return mUserMap = new UserMap();
//            default:
//                return null;
//        }
//    }
//
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position){
//        return (CharSequence) mContext.getResources().getDrawable(TAB_IMAGE[position]);
//    }
//
//    @Override
//    public int getCount() {
//        return 2;
//    }
}





