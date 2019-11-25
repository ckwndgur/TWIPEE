package com.nslb.twipee.board;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nslb.twipee.R;
import com.nslb.twipee.TripTalkBoard;
import com.nslb.twipee.User.PostGetImage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardWrite extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("board");
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    TextView tv_board_publish, tv_title;
    ImageView backArrow;
    private Button btn_gallery;

    Intent intent = new Intent();
    ImageView boardcontentimage;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_write);

        initView();
        intent = getIntent();

        if(intent.hasExtra(getString(R.string.selected_image))){
            //mFirebaseMethods.uploadNewPhoto(getString(R.string.new_photo), caption, imageCount, imgUrl,null);
            String imgUrl = intent.getStringExtra(getString(R.string.selected_image));
            File imgFile = new  File(String.valueOf(imgUrl));

            Bitmap bm = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            boardcontentimage = findViewById(R.id.board_content_imaage);
            boardcontentimage.setImageBitmap(bm);
        }
    }

    private void initView(){
        backArrow = (ImageView) findViewById(R.id.backArrow);
        tv_title = (TextView)findViewById(R.id.setTitle);
        tv_board_publish = (TextView)findViewById(R.id.tvNext);
        btn_gallery = (Button)findViewById(R.id.btn_gallery);

        //뒤로가기
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Title 설정 : 게시물 작성
        tv_title.setText(getResources().getString(R.string.title_boardWrite));

        //게시
        tv_board_publish.setText(getResources().getString(R.string.btn_boardWrite));
        tv_board_publish.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                uploadimage();
                finish();
            }
        });

        btn_gallery.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardWrite.this, PostGetImage.class);
                intent.putExtra(getString(R.string.activity_name), getString(R.string.board_write));
                startActivity(intent);
            }
        });

    }
    private void setData(String Url){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        EditText board_content = (EditText) findViewById(R.id.board_content);

        TypedArray ResldUserImage=getResources().obtainTypedArray(R.array.resld_user_image);
        String[] username=getResources().getStringArray(R.array.user_name);

        String getTime = sdf.format(date);
        String boardcontent = board_content.getText().toString() ;
        //String[] boardimage = getResources().getStringArray(R.array.content_image);

        BoardDTO dto=new BoardDTO();

        dto.setResld_user_image(ResldUserImage.getResourceId(0,0));
        dto.setUser_name(username[0]);
        dto.setDay(getTime);
        dto.setContent_board(boardcontent);
        //dto.setResld_content_image(boardimage[0]);
        dto.setResld_content_image(Url);
        dto.setResld_BoardKind(TripTalkBoard.BoardKind);

        myRef.push().setValue(dto);

    }

    private  void uploadimage(){
        String imgUrl = intent.getStringExtra(getString(R.string.selected_image));
        File imgFile = new  File(String.valueOf(imgUrl));
        final ProgressDialog progressDialog = new ProgressDialog(this);
        //progressDialog.setTitle("업로드중...");
        //progressDialog.show();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
        Date now = new Date();
        String filename = formatter.format(now) + ".png";

        StorageReference storageRef = storage.getReferenceFromUrl("gs://twipee-test.appspot.com").child("boardimage/" + filename);

        storageRef.putFile(Uri.fromFile(imgFile))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        final Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                         downloadUri.addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                String generatedFilePath = downloadUri.getResult().toString();
                                setData(generatedFilePath);
                            }
                        });
                    }
                })
                //실패시
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        //Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                    }
                })
                //진행중
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐?
                                double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                        //dialog에 진행률을 퍼센트로 출력해 준다
                        progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                    }
                });
    }
}