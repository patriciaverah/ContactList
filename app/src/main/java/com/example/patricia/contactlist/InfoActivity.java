package com.example.patricia.contactlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    public void goToList(View view) {
        Intent intent = new Intent(getBaseContext(), ContactListActivity.class);
        startActivity(intent);
    }
}
