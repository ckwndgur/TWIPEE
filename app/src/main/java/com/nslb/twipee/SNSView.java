package com.nslb.twipee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eschao.android.widget.elasticlistview.ElasticListView;
import com.eschao.android.widget.elasticlistview.LoadFooter;
import com.eschao.android.widget.elasticlistview.OnLoadListener;
import com.eschao.android.widget.elasticlistview.OnUpdateListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nslb.twipee.Utils.FirebaseMethods;
import com.nslb.twipee.Utils.MainFeedListAdapter;
import com.nslb.twipee.model.Comment;
import com.nslb.twipee.model.Photo;
import com.nslb.twipee.model.UserAccountSettings;
import com.nslb.twipee.sns.SNS_Search;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SNSView extends Fragment implements OnUpdateListener, OnLoadListener,View.OnClickListener {

    private static final String TAG = "SNS View";

    public SNSView() {
        // Required empty public constructor
    }

    @Override
    public void onUpdate() {
        Log.d(TAG, "ElasticListView: updating list view...");

        getFollowing();
    }

    @Override
    public void onLoad() {
        Log.d(TAG, "ElasticListView: loading...");

        // load 완료 알림
        mListView.notifyLoaded();
    }

    //vars
    private ArrayList<Photo> mPhotos;
    private ArrayList<Photo> mPaginatedPhotos;
    private ArrayList<String> mFollowing;
    private int recursionIterator = 0;
    //private ListView mListView;
    private ElasticListView mListView;
    private MainFeedListAdapter adapter;
    private int resultsCount = 0;
    private ArrayList<UserAccountSettings> mUserAccountSettings;
    private Button mBtnFeedSearch;
    public double latitude;
    public double longitude;

    private FirebaseMethods mFirebaseMethods;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snsview, container, false);
//        mListView = (ListView) view.findViewById(R.id.SNSListView);

        mListView = (ElasticListView) view.findViewById(R.id.SNSListView);
        mFirebaseMethods = new FirebaseMethods(getActivity());

        mPhotos = new ArrayList<>();
//        Photo pt = new Photo();
//        Comment cm = new Comment();
//        List<Comment> comments = new ArrayList<>();
//        cm.setComment("comment");
//        cm.setDate_created("20190930");
//        cm.setUser_id("Eunwha");
//        Like like = new Like();
//        like.setUser_id("jh");
//        List<Like> likes = new ArrayList<>();
//        likes.add(like);
//        cm.setLikes(likes);
//        comments.add(cm);
//        List<Comment> cms = new ArrayList<>();
//        pt.setCaption("123");
//        pt.setComments(comments);
//        pt.setDate_created("20190930");
//        pt.setImage_path("http://tong.visitkorea.or.kr/cms/resource/16/2373216_image2_1.jpg");
//        pt.setCaption("aa");
//        pt.setLikes(likes);
//        pt.setPhoto_id("photoid");
//        pt.setTags("dummy");
//        pt.setUser_id("UserID");
//        mPhotos.add(pt);
        initListViewRefresh();
        getFollowing();

        //        setData();
//        mListView.setAdapter(adapter);

        mBtnFeedSearch = (Button)view.findViewById(R.id.button_feedSearch);
        mBtnFeedSearch.setOnClickListener(this);

        return view;
    }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }


    private void displayPhotos(){
        mPaginatedPhotos = new ArrayList<>();
        if(mPhotos != null){

            try{

                //sort for newest to oldest
                Collections.sort(mPhotos, new Comparator<Photo>() {
                    public int compare(Photo o1, Photo o2) {
                        return o2.getDate_created().compareTo(o1.getDate_created());
                    }
                });

                //한번에 10 load. 10 이상이면  10 올려서 시작
                int iterations = mPhotos.size();
                if(iterations > 10){
                    iterations = 10;
                }
//
                resultsCount = 0;
                for(int i = 0; i < iterations; i++){
                    mPaginatedPhotos.add(mPhotos.get(i));
                    resultsCount++;
                    Log.d(TAG, "displayPhotos: adding a photo to paginated list: " + mPhotos.get(i).getPhoto_id());
                }

                adapter = new MainFeedListAdapter(getActivity(), R.layout.sns_view_post, mPaginatedPhotos);
                mListView.setAdapter(adapter);

                // 업데이트 완료
                mListView.notifyUpdated();

            }catch (IndexOutOfBoundsException e){
                Log.e(TAG, "displayPhotos: IndexOutOfBoundsException:" + e.getMessage() );
            }catch (NullPointerException e){
                Log.e(TAG, "displayPhotos: NullPointerException:" + e.getMessage() );
            }
        }
    }

    public void displayMorePhotos(){
        Log.d(TAG, "displayMorePhotos: displaying more photos");

        try{

            if(mPhotos.size() > resultsCount && mPhotos.size() > 0){

                int iterations;
                if(mPhotos.size() > (resultsCount + 10)){
                    Log.d(TAG, "displayMorePhotos: there are greater than 10 more photos");
                    iterations = 10;
                }else{
                    Log.d(TAG, "displayMorePhotos: there is less than 10 more photos");
                    iterations = mPhotos.size() - resultsCount;
                }

                //add the new photos to the paginated list
                for(int i = resultsCount; i < resultsCount + iterations; i++){
                    mPaginatedPhotos.add(mPhotos.get(i));
                }

                resultsCount = resultsCount + iterations;
                adapter.notifyDataSetChanged();
            }
        }catch (IndexOutOfBoundsException e){
            Log.e(TAG, "displayPhotos: IndexOutOfBoundsException:" + e.getMessage() );
        }catch (NullPointerException e){
            Log.e(TAG, "displayPhotos: NullPointerException:" + e.getMessage() );
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_feedSearch:
                String strnlo = Double.toString(longitude);
                String strnla = Double.toString(latitude);

                Intent intent=new Intent(getActivity(), SNS_Search.class);
                intent.putExtra("N_longitude", strnlo );
                intent.putExtra("N_latitude",strnla);

                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void initListViewRefresh(){
        mListView.setHorizontalFadingEdgeEnabled(true);
        mListView.setAdapter(adapter);
        mListView.enableLoadFooter(true)
                .getLoadFooter().setLoadAction(LoadFooter.LoadAction.RELEASE_TO_LOAD);
        mListView.setOnUpdateListener(this)
                .setOnLoadListener(this);
//        mListView.requestUpdate();
    }

    private void getFriendsAccountSettings() {
        Log.d(TAG, "getFriendsAccountSettings: getting friends account settings.");

        for (int i = 0; i < mFollowing.size(); i++) {
            Log.d(TAG, "getFriendsAccountSettings: user: " + mFollowing.get(i));
            final int count = i;
            Query query = FirebaseDatabase.getInstance().getReference()
                    .child(getString(R.string.dbname_user_account_settings))
                    .orderByKey()
                    .equalTo(mFollowing.get(i));

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "getFriendsAccountSettings: got a user: " + snapshot.getValue(UserAccountSettings.class).getDisplay_name());
                        mUserAccountSettings.add(snapshot.getValue(UserAccountSettings.class));

                        if (count == 0) {
                            JSONObject userObject = new JSONObject();
                            try {
                                userObject.put(getString(R.string.field_display_name), mUserAccountSettings.get(count).getDisplay_name());
                                userObject.put(getString(R.string.field_username), mUserAccountSettings.get(count).getUsername());
                                userObject.put(getString(R.string.field_profile_photo), mUserAccountSettings.get(count).getProfile_photo());
                                userObject.put(getString(R.string.field_user_id), mUserAccountSettings.get(count).getUser_id());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                    if (count == mFollowing.size() - 1) {
                        //스토리
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void clearAll(){
        if(mFollowing != null){
            mFollowing.clear();
        }
        if(mPhotos != null){
            mPhotos.clear();
            if(adapter != null){
                adapter.clear();
                adapter.notifyDataSetChanged();
            }
        }
        if(mUserAccountSettings != null){
            mUserAccountSettings.clear();
        }
        if(mPaginatedPhotos != null){
            mPaginatedPhotos.clear();
        }
        mFollowing = new ArrayList<>();
        mPhotos = new ArrayList<>();
        mPaginatedPhotos = new ArrayList<>();
        mUserAccountSettings = new ArrayList<>();
    }

    //사용자가 팔로우한 모든 사용자 ID 검색
    private void getFollowing() {
        Log.d(TAG, "getFollowing: searching for following");

        clearAll();
        //also add your own id to the list
        mFollowing.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
//         mFollowing.add("M3JHfoZJ1hbnhxkBcSX8odwgCEq2");
        Query query = FirebaseDatabase.getInstance().getReference()
                .child(getActivity().getString(R.string.dbname_following))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "getFollowing: found user: " + singleSnapshot
                            .child(getString(R.string.field_user_id)).getValue());

                    mFollowing.add(singleSnapshot
                            .child(getString(R.string.field_user_id)).getValue().toString());
                }

                getPhotos();
                //                getMyUserAccountSettings();
                getFriendsAccountSettings();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private void getPhotos() {
        Log.d(TAG, "getPhotos: getting list of photos");

        for (int i = 0; i < mFollowing.size(); i++) {
            final int count = i;
            Query query = FirebaseDatabase.getInstance().getReference()
                    .child(getActivity().getString(R.string.dbname_user_photos))
                    .child(mFollowing.get(i))
                    .orderByChild(getString(R.string.field_user_id))
                    .equalTo(mFollowing.get(i));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                        Photo newPhoto = new Photo();
                        Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();

                        newPhoto.setCaption(objectMap.get(getString(R.string.field_caption)).toString());
                        newPhoto.setTags(objectMap.get(getString(R.string.field_tags)).toString());
                        newPhoto.setPhoto_id(objectMap.get(getString(R.string.field_photo_id)).toString());
                        newPhoto.setUser_id(objectMap.get(getString(R.string.field_user_id)).toString());
                        newPhoto.setDate_created(objectMap.get(getString(R.string.field_date_created)).toString());
                        newPhoto.setImage_path(objectMap.get(getString(R.string.field_image_path)).toString());

                        Log.d(TAG, "getPhotos: photo: " + newPhoto.getPhoto_id());
                        List<Comment> commentsList = new ArrayList<Comment>();
                        for (DataSnapshot dSnapshot : singleSnapshot
                                .child(getString(R.string.field_comments)).getChildren()) {
                            Map<String, Object> object_map = (HashMap<String, Object>) dSnapshot.getValue();
                            Comment comment = new Comment();
                            comment.setUser_id(object_map.get(getString(R.string.field_user_id)).toString());
                            comment.setComment(object_map.get(getString(R.string.field_comment)).toString());
                            comment.setDate_created(object_map.get(getString(R.string.field_date_created)).toString());
                            commentsList.add(comment);
                        }
                        newPhoto.setComments(commentsList);
                        mPhotos.add(newPhoto);
                    }
                    if (count >= mFollowing.size() - 1) {
                        //display the photos
                        displayPhotos();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "onCancelled: query cancelled.");
                }
            });

        }
    }

     /*private void setData() {
        //Photo newPhoto = new Photo();
        List<Comment> commentsList = new ArrayList<Comment>();
        //Comment comment = new Comment();
        TypedArray profilePhoto=getResources().obtainTypedArray(R.array.profile_photo);
        String[] userName=getResources().getStringArray(R.array.user_names);
        TypedArray postedPhoto=getResources().obtainTypedArray(R.array.posted_photo);
        String[] likedUser=getResources().getStringArray(R.array.liked_user);
        String[] postedText=getResources().getStringArray(R.array.posted_text);
        String[] postedDate=getResources().getStringArray(R.array.posted_date);

        for (int i=0; i<((TypedArray) profilePhoto).length();i++){
            SNSDTO dto=new SNSDTO();
            dto.setProfilePhoto(profilePhoto.getResourceId(i,0));
            dto.setUserName(userName[i]);
            dto.setPostedPhoto(postedPhoto.getResourceId(i,2));
            dto.setLikedUser(likedUser[i]);
            dto.setPostedText(postedText[i]);
            dto.setPostedDate(postedDate[i]);

            adapter.addItem(dto);

        }

        //displayPhotos();

    }*/


}
