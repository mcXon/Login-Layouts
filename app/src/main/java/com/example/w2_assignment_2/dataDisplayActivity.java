package com.example.w2_assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class dataDisplayActivity extends AppCompatActivity {

    ArrayList<String> users;
    private ImageView backImage;
    private ListView displayUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);

        backImage = findViewById(R.id.iv_data_back);
        displayUsers = findViewById(R.id.lv_usersAdded);

        backImage.setOnClickListener(view -> {
            Intent passDataBack = new Intent();
            passDataBack.putExtra("DataBack" , users);
            setResult(RESULT_OK, passDataBack);
            finish();
        });

        //Get users from previous activity
        Intent intent = getIntent();
        intent.getExtras();
        users = intent.getStringArrayListExtra("Users");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users);
        displayUsers.setAdapter(adapter);
    }
}