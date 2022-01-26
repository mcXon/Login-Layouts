package com.example.w2_assignment_2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class accountActivity extends AppCompatActivity {

    private static String TAG = "Account_Activity";
    private EditText email_field;
    private EditText pass_field;
    private EditText rep_pass_field;
    private TextView pass_info;
    private TextView mail_info;
    private Button create_acc;
    private ImageView back_im;

    private String emailRegex =  "^(.+)@(.+)$";
    private String passRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
    private Pattern pattern;
    private Matcher matcher;
    private Drawable err;
    private Drawable fine;

    private Boolean correctEmail = false;
    private Boolean correctPass = false;
    private Boolean correctRepPass = false;

    ArrayList <String> users = new ArrayList<String>();

    //TODO add functionality of return button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        email_field = findViewById(R.id.et_mail);
        pass_field = findViewById(R.id.et_pass);
        rep_pass_field = findViewById(R.id.et_rep_pass);
        create_acc = findViewById(R.id.btn_next);
        back_im = findViewById(R.id.iv_back);

        pass_info = findViewById(R.id.tv_pass_info);
        mail_info = findViewById(R.id.tv_mailReview);

        err = getResources().getDrawable(R.drawable.cross);
        fine = getResources().getDrawable(R.drawable.tick);

        err.setBounds(0,0,err.getIntrinsicWidth(), err.getIntrinsicHeight());
        fine.setBounds(0,0,fine.getIntrinsicWidth(), fine.getIntrinsicHeight());

        back_im.setOnClickListener(view -> {
            finish();
        });

        email_field.setOnFocusChangeListener((view, focus) -> {
            if(!focus){
                pattern = Pattern.compile(emailRegex);
                matcher = pattern.matcher(email_field.getText().toString());
                Log.d(TAG, "focus lost email - regex result: " + matcher.matches());
                if(matcher.matches()){
                    email_field.setCompoundDrawables(null,null, fine,null);
                    email_field.setBackgroundResource(R.drawable.fine_box);
                    correctEmail = true;
                    reviewFieldsToContinue();
                }else{
                    email_field.setBackgroundResource(R.drawable.err_box);
                    email_field.setCompoundDrawables(null,null,null,null);
                    if(users != null && users.contains(email_field.getText().toString())){
                        emailExisting();
                    }
                }
            }
        });

        pass_field.setOnFocusChangeListener((view, focus) -> {
            if(!focus){
                pattern = Pattern.compile(passRegex);
                matcher = pattern.matcher(pass_field.getText().toString());
                Log.d(TAG, "focus lost password - regex result: " + matcher.matches());
                if(matcher.matches()){
                    pass_field.setCompoundDrawables(null,null,fine,null);
                    pass_field.setBackgroundResource(R.drawable.fine_box);
                    pass_info.setVisibility(View.GONE);
                    correctPass = true;
                    reviewFieldsToContinue();
                }else{
                    pass_field.setBackgroundResource(R.drawable.err_box);
                    pass_field.setCompoundDrawables(null,null,null,null);
                    showPassInfoField();
                }
            }
        });

        rep_pass_field.setOnFocusChangeListener((view, focus) -> {
            if(!focus){
                if(!pass_field.getText().toString().trim().isEmpty()){
                    if(!pass_field.getText().toString().equals(rep_pass_field.getText().toString())){
                        rep_pass_field.setBackgroundResource(R.drawable.err_box);
                        rep_pass_field.setText(R.string.pass_match);
                        rep_pass_field.setCompoundDrawables(null,null,null,null);
                        showPassInfoField();
                    }else{
                        rep_pass_field.setBackgroundResource(R.drawable.fine_box);
                        rep_pass_field.setCompoundDrawables(null,null,fine,null);
                        pass_info.setVisibility(View.GONE);
                        correctRepPass = true;
                        reviewFieldsToContinue();
                    }
                }else{
                    rep_pass_field.setBackgroundResource(R.drawable.err_box);
                    rep_pass_field.setCompoundDrawables(null,null,null,null);
                    showPassInfoField();
                }
            }
        });
    }

    private void emailExisting() {
        mail_info.setVisibility(View.VISIBLE);
        mail_info.setBackgroundResource(R.drawable.err_text_box);
        mail_info.setCompoundDrawables(err,null,null,null);
    }

    private void reviewFieldsToContinue() {
        if(correctEmail && correctPass && correctRepPass){
            create_acc.setEnabled(true);
        }else{
            create_acc.setEnabled(false);
        }
    }

    public void displayUsersData(View v){
        users.add(email_field.getText().toString());
        Intent userData = new Intent();
        userData.setClass(this, dataDisplayActivity.class);
        userData.putExtra("Users", users);
        startActivityForResult(userData, 46290);
    }

    private void showPassInfoField(){
        pass_info.setVisibility(View.VISIBLE);
        pass_info.setCompoundDrawables(err,null,null,null);
        pass_info.setBackgroundResource(R.drawable.err_text_box);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 46290){
            if(resultCode == RESULT_OK){
                if(data != null){
                    //update data from search activity
                    users = data.getStringArrayListExtra("DataBack");
                }
            }
        }
    }
}