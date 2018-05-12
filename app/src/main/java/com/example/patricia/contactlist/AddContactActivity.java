package com.example.patricia.contactlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddContactActivity extends AppCompatActivity {

    private static final int NO_ID = -99;
    private static final String NO_NAME = "Unknown";
    private static final String NO_FAMILY_NAME = "Unknown";
    private static final String NO_PHONE = "";
    private static final String NO_EMAIL = "";
    private static final String NO_ADDRESS = "";
    private static final String NO_ADD_PHONE = "";

    private Contact newContact;

    private EditText contactName;
    private EditText contactFamilyName;
    private EditText contactPhone;
    private EditText contactEmail;
    private EditText contactAddress;
    private EditText contactAdditionalPhone;

    private int mID;
    private String mName;
    private String mFamilyName;
    private String mPhone;
    private String mEmail;
    private String mAddress;
    private String mAdditionalPhone;

    private Button addContactBtn;

    private ContactHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Setting the button event
        addContactBtn = findViewById(R.id.addContactBtn);
        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewContact();
            }
        });

        // DB
        mDB = new ContactHelper(this);

        // Getting the edit texts
        contactName = findViewById(R.id.contactName);
        contactFamilyName = findViewById(R.id.contactFamilyName);
        contactPhone = findViewById(R.id.contactPhone);
        contactEmail = findViewById(R.id.contactEmail);
        contactAddress = findViewById(R.id.contactAddress);
        contactAdditionalPhone = findViewById(R.id.contactAdditionalPhone);

        // Get data sent from calling activity.
        Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            int id = extras.getInt(AdapterContact.EXTRA_ID, NO_ID);
            String name = extras.getString(AdapterContact.EXTRA_NAME, NO_NAME);
            String familyName = extras.getString(AdapterContact.EXTRA_FAMILY_NAME, NO_FAMILY_NAME);
            String phone = extras.getString(AdapterContact.EXTRA_PHONE, NO_PHONE);
            String email = extras.getString(AdapterContact.EXTRA_EMAIL, NO_EMAIL);
            String address = extras.getString(AdapterContact.EXTRA_ADDRESS, NO_ADDRESS);
            String additionalPhone = extras.getString(AdapterContact.EXTRA_ADDITIONAL_PHONE, NO_ADD_PHONE);
            if ((id != NO_ID) && (!name.equals(NO_NAME)) && (!familyName.equals(NO_FAMILY_NAME)) && (!phone.equals(NO_PHONE))
                    && (!email.equals(NO_PHONE)) && (!address.equals(NO_ADDRESS)) && (!additionalPhone.equals(NO_PHONE))) {
                mID = id;
                contactName.setText(name);
                contactFamilyName.setText(familyName);
                contactPhone.setText(phone);
                contactEmail.setText(email);
                contactAddress.setText(address);
                contactAdditionalPhone.setText(additionalPhone);
            }
        }
    }

    /*
        This method adds the necessary buttons to
        the page's ActionBar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    /*
        This method determines if the ActionBar item was selected.
        If true, does the corresponding action.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        // Handle presses on the action bar items
        switch (item.getItemId()){
            case R.id.exclamation_button:
                showInformationDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        Method that saves the contact information
        in the Firebase database
     */
    public void saveNewContact(){
        // Getting contact information
        this.getContactInformation();

        mDB.insert(newContact);

       Intent intent = new Intent(getBaseContext(), ContactListActivity.class);
       startActivity(intent);
    }

    /*
        Method that gets all the information about
        the new contact user is saving
     */
    public void getContactInformation(){
        // Convert information to strings to save it
        mName = contactName.getText().toString();
        if (mName.equals("")) { mName = NO_NAME; }
        mFamilyName = contactFamilyName.getText().toString();
        if (mFamilyName.equals("")) { mFamilyName = NO_FAMILY_NAME; }
        mPhone = contactPhone.getText().toString();
        if (mPhone.equals("")) { mPhone = NO_PHONE; }
        mEmail = contactEmail.getText().toString();
        if (mEmail.equals("")) { mEmail = NO_EMAIL; }
        mAddress = contactAddress.getText().toString();
        if (mAddress.equals("")) { mAddress = NO_ADDRESS; }
        mAdditionalPhone = contactAdditionalPhone.getText().toString();
        if (mAdditionalPhone.equals("")) { mAdditionalPhone = NO_ADD_PHONE; }

        // Create a new contact with the given information
        newContact = new Contact(mName, mFamilyName, mPhone, mEmail, mAddress, mAdditionalPhone);
    }

    public void showInformationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("FORM INFORMATION");
        builder.setMessage("In this form none of the fields are required, " +
                "but we suggest you at least fill the three first ones in order to " +
                "save helpful information.");

        // Neutral action - closes dialog.
        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
