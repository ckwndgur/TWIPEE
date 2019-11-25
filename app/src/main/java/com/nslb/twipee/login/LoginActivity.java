package com.nslb.twipee.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.minew.beacon.BluetoothState;
import com.minew.beacon.MinewBeacon;
import com.minew.beacon.MinewBeaconManager;
import com.minew.beacon.MinewBeaconManagerListener;
import com.nslb.twipee.GUIParameter.LoginInfo;
import com.nslb.twipee.MainActivity;
import com.nslb.twipee.R;
import com.nslb.twipee.communication.Connect;
import com.nslb.twipee.communication.GUIInterface_Sender;
import com.nslb.twipee.communication.Receiver;

import java.io.IOException;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private Button join, loginButton;
    private EditText useridEditText, passwordEditText;
    private LoginInfo PLI;
    private String userinfo[];
    private final int LOGIN_JOIN = 100;
    private final int PERMISSION_REQUEST_COARSE_LOCATION = 100;
    private MinewBeaconManager mMinewBeaconManager;

    //firebase
    private static final Boolean CHECK_IF_VERIFIED = false;
    private static final String TAG = "LoginActivity";
    private Context mContext = LoginActivity.this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setPermission();
        setupFirebaseAuth();
        initView();

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

    private void initView()
    {
        useridEditText = findViewById(R.id.userid);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        join = findViewById(R.id.join);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to log in.");

                String email = useridEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(isStringNull(email) && isStringNull(password)){
                    Toast.makeText(mContext, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }else{

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());

                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        try{
                                            if(CHECK_IF_VERIFIED){
                                                if(user.isEmailVerified()){
                                                    Log.d(TAG, "onComplete: success. email is verified.");
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                }else{
                                                    Toast.makeText(mContext, "Email is not verified \n check your email inbox.", Toast.LENGTH_SHORT).show();
                                                    mAuth.signOut();
                                                }
                                            }
                                            else{
                                                Log.d(TAG, "onComplete: success. email is verified.");
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                        }catch (NullPointerException e){
                                            Log.e(TAG, "onComplete: NullPointerException: " + e.getMessage() );
                                        }
                                    }
                                }
                            });
                }
            }
        });

        join.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this, Login_1.class);
                startActivityForResult(intent, LOGIN_JOIN);
            }
        });

        mMinewBeaconManager.setDeviceManagerDelegateListener(new MinewBeaconManagerListener() {
            @Override
            public void onAppearBeacons(List<MinewBeacon> list) {

            }

            @Override
            public void onDisappearBeacons(List<MinewBeacon> list) {

            }

            @Override
            public void onRangeBeacons(final List<MinewBeacon> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (list.size() > 0) {
                            for (int i = 0; i<list.size(); i++)
                            {
                                //mTV.setText(list.get(i).toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onUpdateState(BluetoothState bluetoothState) {

            }
        });
    }

    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string if null.");

        if(string.equals("")){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            if (requestCode == LOGIN_JOIN)
            {
                PLI = new LoginInfo();
                if(data.getExtras() != null) {
                    userinfo = new String[4];
                    userinfo = data.getExtras().getStringArray("LoginInfo");
                    useridEditText.setText(userinfo[PLI.LOGINID]);
                    passwordEditText.setText(userinfo[PLI.LOGINPW]);
                }
            }
        }
    }

    private void setPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
        mMinewBeaconManager = MinewBeaconManager.getInstance(this);
    }

    private void getInsertInfo()
    {

    }
}
