package com.example.patricia.contactlist;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    public static final int CONTACT_EDIT = 1;
    public static final int CONTACT_ADD = -1;

    private Button newContactButton;

    private ContactHelper mDB;
    private RecyclerView mRecyclerView;
    private AdapterContact mAdapter;
    private int mLastPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        newContactButton = this.findViewById(R.id.newContactButton);
        newContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });

        mDB = new ContactHelper(this);

        // Create recycler view.
        mRecyclerView = findViewById(R.id.contactlist);

        // Create an mAdapter and supply the data to be displayed.
        mAdapter = new AdapterContact(this, /* mDB.getAllEntries(),*/ mDB);

        // Connect the mAdapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);

        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /*
        This function adds the necessary buttons to
        the page's ActionBar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return true;
    }

    /*
        This function determines if the ActionBar item was selected.
        If true, does the corresponding action.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        // Handle presses on the action bar items
        switch (item.getItemId()){
            case R.id.information_button:
                startActivity(new Intent(this, InfoActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        "newContactButton" button click event.
        It starts the next activity:
            Add Contact page.
     */
    private void addContact() {
        Intent intent = new Intent(getBaseContext(), AddContactActivity.class);
        startActivity(intent);
    }

}
