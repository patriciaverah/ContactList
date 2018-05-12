package com.example.patricia.contactlist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class ContactInfoActivity extends AppCompatActivity {

    private TextView eName;
    private TextView eFamilyName;
    private TextView ePhone;
    private TextView eEmail;
    private TextView eAddress;
    private TextView eAdditionalPhone;

    private Contact selectedContact;

    private int mID;
    private String mName;
    private String mFamilyName;
    private String mPhone;
    private String mEmail;
    private String mAddress;
    private String mAdditionalPhone;

    private ContactHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        // Initalize edit texts
        eName = findViewById(R.id.contact_name);
        eFamilyName = findViewById(R.id.contact_family_name);
        ePhone = findViewById(R.id.contact_phone);
        eEmail = findViewById(R.id.contact_email);
        eAddress = findViewById(R.id.contact_address);
        eAdditionalPhone = findViewById(R.id.contact_additional_phone);

        /*
            Get intent extras:
                Contact full information
        */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mID = extras.getInt("CONTACT_ID");
            mName = extras.getString("CONTACT_NAME");
            mFamilyName = extras.getString("CONTACT_FAMILY_NAME");
            mPhone = extras.getString("CONTACT_PHONE");
            mEmail = extras.getString("CONTACT_EMAIL");
            mAddress = extras.getString("CONTACT_ADDRESS");
            mAdditionalPhone = extras.getString("CONTACT_ADD_PHONE");
        }

        // Create the selected contact object
        selectedContact = new Contact(mName, mFamilyName, mPhone, mEmail, mAddress, mAdditionalPhone);
        selectedContact.setId(mID);

        // Initialize helper
        mDB = new ContactHelper(this);

        showContactInformation(selectedContact);
    }

    /*
        This function adds the necessary buttons to
        the page's ActionBar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_contact_info, menu);
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
            case R.id.edit_button:
                // Goes to edit page
                Intent intent = new Intent(this, EditContactActivity.class);
                intent.putExtra("CONTACT_ID", selectedContact.id);
                intent.putExtra("CONTACT_NAME", selectedContact.contactName);
                intent.putExtra("CONTACT_FAMILY_NAME", selectedContact.familyName);
                intent.putExtra("CONTACT_PHONE", selectedContact.contactPhone);
                intent.putExtra("CONTACT_EMAIL", selectedContact.email);
                intent.putExtra("CONTACT_ADDRESS", selectedContact.address);
                intent.putExtra("CONTACT_ADD_PHONE", selectedContact.additionalPhone);
                startActivity(intent);
                return true;

            case R.id.delete_button:
                // Shows dialogue to confirm delete
                showAlertDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showContactInformation(Contact selectedContact) {
        eName.setText(selectedContact.getContactName());
        eFamilyName.setText(selectedContact.getFamilyName());
        ePhone.setText(selectedContact.getContactPhone());
        eEmail.setText(selectedContact.getEmail());
        eAddress.setText(selectedContact.getAddress());
        eAdditionalPhone.setText(selectedContact.getAdditionalPhone());
    }

    /*
        Create the dialog to delete contact.
        Options:
            NO - does nothing, goes back to contact info.
            YES - deletes the contact from database.
     */
    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DELETE CONTACT");
        builder.setMessage("Are you sure you want to delete this contact completly?");

        // Positive button - delete contact
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Database call to delete the contact
                        mDB.delete(mID);

                        // Goes back to contact list
                        Intent intent = new Intent(getBaseContext(), ContactListActivity.class);
                        startActivity(intent);
                    }
                }
        );

        // Negative button - does nothing
        builder.setNegativeButton(
                "No",
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
