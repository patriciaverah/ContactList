package com.example.patricia.contactlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditContactActivity extends AppCompatActivity {

    private static final int NO_ID = -99;
    private static final String NO_NAME = "UNKNOWN";
    private static final String NO_FAMILY_NAME = "UNKNOWN";
    private static final String NO_PHONE = "UNKNOWN";
    private static final String NO_EMAIL = "UNKNOWN";
    private static final String NO_ADDRESS = "UNKNOWN";
    private static final String NO_ADD_PHONE = "UNKNOWN";

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

    private Button editContactBtn;

    private ContactHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        // Setting the button event
        editContactBtn = findViewById(R.id.addContactBtn);
        editContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editContact();
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
            int id = extras.getInt("CONTACT_ID");
            String name = extras.getString("CONTACT_NAME");
            String familyName = extras.getString("CONTACT_FAMILY_NAME");
            String phone = extras.getString("CONTACT_PHONE");
            String email = extras.getString("CONTACT_EMAIL");
            String address = extras.getString("CONTACT_ADDRESS");
            String additionalPhone = extras.getString("CONTACT_ADD_PHONE");
            // Set the information in the edit texts
            mID = id;
            contactName.setText(name);
            contactFamilyName.setText(familyName);
            contactPhone.setText(phone);
            contactEmail.setText(email);
            contactAddress.setText(address);
            contactAdditionalPhone.setText(additionalPhone);
        }
    }

    public void editContact(){
        // Getting contact information
        this.getContactInformation();

        mDB.update(mID, newContact);

        Intent intent = new Intent(getBaseContext(), ContactListActivity.class);
        startActivity(intent);
    }

    /*
        Function that gets all the information about
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
}
