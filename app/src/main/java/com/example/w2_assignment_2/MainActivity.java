package com.example.w2_assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createAccount = findViewById(R.id.btn_create_acc);
    }

    public void openAccountActivity(View v){
        Intent account_Activity = new Intent();
        account_Activity.setClass(this, accountActivity.class);
        startActivity(account_Activity);
    }

}