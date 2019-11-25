package com.nslb.twipee;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nslb.twipee.board.BoardAdapter;
import com.nslb.twipee.board.BoardComment;
import com.nslb.twipee.board.BoardDTO;
import com.nslb.twipee.board.BoardWrite;
import com.nslb.twipee.ui.main.PostAapter;
import com.google.android.material.tabs.TabLayout;
import com.nslb.twipee.ui.main.UserMap;
import com.nslb.twipee.ui.main.UserPost;

import java.util.ArrayList;
import java.util.List;

public class TripTalkBoard extends AppCompatActivity implements View.OnClickListener {
    private List<BoardDTO> boardDTOS;
    public static int BoardKind;


    //    private ViewPager viewPager;
    public UserPost mUserPost=null;
    public UserMap mUserMap=null;
    private BoardAdapter adapter;
    private ListView listView;
    TextView tv_board_write, tv_title;
    Button btn_board_comment;
    ImageView backArrow;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("board");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trip_talk_board);

        listView=(ListView)findViewById(R.id.listboard);
        boardDTOS = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent , View view , int position , long id) {
                Intent it=new Intent(TripTalkBoard.this,BoardComment.class);
                startActivity(it);

            }
        });
        initView();


//        final ViewPager viewPager=view.findViewById(R.id.viewboard);

//        viewPager.setAdapter(postAapter);
//        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
//        viewPager.addOnPageChangeListener(new  TabLayout.TabLayoutOnPageChangeListener(tabs));
    }

    private void initView() {
        setBoardDTOS(BoardKind);

        backArrow = (ImageView) findViewById(R.id.backArrow);
        tv_title = (TextView)findViewById(R.id.setTitle);
        tv_board_write = (TextView)findViewById(R.id.tvNext);

        TabLayout tabs=findViewById(R.id.tabs_board);
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_camera_alt_black_24dp));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_card_travel_black_24dp));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_child_friendly_black_24dp));
        tabs.setTabGravity(tabs.GRAVITY_FILL);

        final PostAapter postAapter=new PostAapter(getSupportFragmentManager(),3);
        adapter=new BoardAdapter();

        initDatabase();

        //뒤로가기
        backArrow.setOnClickListener(this);

        //Title 설정 : 게시판
        tv_title.setText(getResources().getString(R.string.title_triptalkBoard));

        //작성하기
        tv_board_write.setText(getResources().getString(R.string.btn_triptalkBoard));
        tv_board_write.setOnClickListener(this);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        adapter=new BoardAdapter();
                        setBoardDTOS(0);
                        break;
                    case 1:
                        adapter=new BoardAdapter();
                        setBoardDTOS(1);
                        break;
                    case 2:
                        adapter=new BoardAdapter();
                        setBoardDTOS(2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initDatabase() {
        boardDTOS.clear();

        myRef.addChildEventListener(new ChildEventListener() {
            TabLayout tabs=findViewById(R.id.tabs_board);
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BoardDTO boardDTO = dataSnapshot.getValue(BoardDTO.class);
                boardDTOS.add(boardDTO);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                finish();
                break;
            case R.id.tvNext:
                Intent intent=new Intent(getApplicationContext(), BoardWrite.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    private  void setBoardDTOS(int BoardKind1){
        BoardKind = BoardKind1;
        for (int i=0; i<boardDTOS.size();i++){
            BoardDTO boardDTO = (BoardDTO)boardDTOS.get(i);
            int boardkindnum = boardDTO.getResld_BoardKind();
            if (BoardKind == boardkindnum){

                adapter.addItem(boardDTO);

            }
            else{

            }
        }listView.setAdapter(adapter);
    }
}