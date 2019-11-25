package com.nslb.twipee.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.nslb.twipee.GUIParameter.LoginInfo;
import com.nslb.twipee.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Login_1 extends AppCompatActivity {

    public static int APP_REQUEST_CODE = 99;
    private Button mBtnCode, mBtnUserJoin, mBtnConfirm;
    public EditText mETUserName, mETPromptId, mETPromptPassword, mETPromptPasswordCheck, mETBirthday;
    private TextView birthday;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private Spinner spinner_gender;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private boolean bPWCheck = false;
    private LoginInfo PLI;

    //특수문자 입력 제한
    public InputFilter filterAlphaNum = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]*$");
            if (!ps.matcher(source).matches()) {
                Toast.makeText(getApplicationContext(), "한글, 영문, 숫자만 입력 가능합니다", Toast.LENGTH_LONG).show();
                return "";
            }
            return null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_1);

        this.InitializeView();
        this.InitializeListener();

        PLI = new LoginInfo();
        initObject();

        AccessToken accessToken = AccountKit.getCurrentAccessToken();
        if (accessToken != null) {
            //Handle Returning User
        } else {
            //Handle new or logged out user
        }
    }

    //생일선택달력
    private void InitializeListener() {
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear+1;
                birthday.setText(year + "년"+monthOfYear+"월"+dayOfMonth+"일");
            }
        };
    }

    public void onClickHandler(View view){
        DatePickerDialog dialog = new DatePickerDialog(this,callbackMethod,2019,9,24);

        dialog.show();
    }

    private void InitializeView() {
        birthday = (TextView)findViewById(R.id.birthday);
    }
//

    public void onLoginBtnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.code:
                Intent intent = new Intent(Login_1.this, AccountKitActivity.class);
                AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                        new AccountKitConfiguration.AccountKitConfigurationBuilder(
                                LoginType.PHONE,
                                AccountKitActivity.ResponseType.TOKEN);
                intent.putExtra(
                        AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                        configurationBuilder.build());
                startActivityForResult(intent, APP_REQUEST_CODE);
                break;
            case R.id.user_join:
                if (!(mETPromptId.getText().toString().length() == 0 || mETUserName.getText().toString().length() < 2 || bPWCheck == false))
                {
                    String strarray[] = new String[4];
                    strarray[PLI.USERNAME] = mETUserName.getText().toString();
                    strarray[PLI.LOGINID] = mETPromptId.getText().toString();
                    strarray[PLI.LOGINPW] = mETPromptPassword.getText().toString();
                    strarray[PLI.BIRTHDAY] = mETBirthday.getText().toString();

                    Intent intent1 = new Intent();
                    intent1.putExtra("LoginInfo", strarray);
                    setResult(RESULT_OK, intent1);
                    finish();

                }
                else {Toast.makeText(this, "이름은 두글자 이상 입력해야합니다.", Toast.LENGTH_LONG).show();}
                break;
            case R.id.confirm:
                if (mETPromptPassword.getText().toString().equals(mETPromptPasswordCheck.getText().toString())) {
                    bPWCheck = true;
                    Toast.makeText(this, "일치", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    private void initObject()
    {
        mBtnCode = findViewById(R.id.code);
        mBtnUserJoin = findViewById(R.id.user_join);
        mBtnConfirm = findViewById(R.id.confirm);
        mETPromptId = findViewById(R.id.prompt_id);
        mETUserName = findViewById(R.id.username);
        mETPromptPassword = findViewById(R.id.prompt_password);
        mETPromptPasswordCheck = findViewById(R.id.prompt_password_check);
        mETBirthday = findViewById(R.id.birthday);

        //필터적용
        mETUserName.setFilters(new InputFilter[]{filterAlphaNum});
        mETPromptId.setFilters(new InputFilter[]{filterAlphaNum});
        mETPromptPassword.setFilters(new InputFilter[]{filterAlphaNum});
        mETPromptPasswordCheck.setFilters(new InputFilter[]{filterAlphaNum});

//비밀번호가리기
        mETPromptPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mETPromptPasswordCheck.setTransformationMethod(PasswordTransformationMethod.getInstance());



//성별선택
        arrayList = new ArrayList<>();
        arrayList.add("여자");
        arrayList.add("남자");
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList);

        spinner_gender = findViewById(R.id.spinner_gender);
        spinner_gender.setAdapter(arrayAdapter);
        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),arrayList.get(i)+"가 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
//


    @Override
    public void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                //showErrorActivity(loginResult.getError());
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10));
                }
            }
            Toast.makeText(
                    this,
                    toastMessage,
                    Toast.LENGTH_LONG)
                    .show();
        }
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                // Get Account Kit ID
                String accountKitId = account.getId();
                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                if (phoneNumber != null) {
                    String phoneNumberString = phoneNumber.toString();
                }
                mBtnUserJoin.setEnabled(true);
            }

            @Override
            public void onError(final AccountKitError error) {
                // Handle Error
            }
        });
    }

}
