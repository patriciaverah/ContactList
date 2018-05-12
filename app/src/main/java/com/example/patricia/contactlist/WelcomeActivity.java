package com.example.patricia.contactlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Define the activity button
        startButton = this.findViewById(R.id.start_button);
    }

    /*
        This function adds the necessary buttons to
        the page's ActionBar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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
        "startButton" button click event.
        It starts the next - and main - activity:
            The proper contact list.
     */
    public void startApp(View view) {
        startActivity(new Intent(this, ContactListActivity.class));
    }
}
