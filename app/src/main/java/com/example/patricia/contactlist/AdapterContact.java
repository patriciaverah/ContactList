package com.example.patricia.contactlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactViewHolder> {

    private Activity activity;
    private ArrayList<Contact> contactArrayList;
    private static LayoutInflater inflater = null;

    private static final String TAG = AdapterContact.class.getSimpleName();

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_NAME = "NAME";
    public static final String EXTRA_FAMILY_NAME = "FAMILY NAME";
    public static final String EXTRA_PHONE = "PHONE";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static final String EXTRA_ADDRESS = "ADDRESS";
    public static final String EXTRA_ADDITIONAL_PHONE = "ADDITIONAL PHONE";
    public static final String EXTRA_POSITION = "POSITION";

    private final LayoutInflater mInflater;
    ContactHelper mDB;
    Context mContext;

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public final TextView contactName;
        public final TextView contactFamilyName;

        public ContactViewHolder(View itemView) {
            super(itemView);

            contactName = itemView.findViewById(R.id.item_name);
            contactFamilyName = itemView.findViewById(R.id.item_family_name);
        }
    }

    public AdapterContact (Context context, ContactHelper db) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDB = db;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        final Contact current = mDB.query(position);
        holder.contactName.setText(current.getContactName());
        holder.contactFamilyName.setText(current.getFamilyName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactInformation(current);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) mDB.count();
    }

    public void contactInformation(Contact currentContact) {
        Intent intent = new Intent(mContext.getApplicationContext(), ContactInfoActivity.class);
        intent.putExtra("CONTACT_ID", currentContact.id);
        intent.putExtra("CONTACT_NAME", currentContact.contactName);
        intent.putExtra("CONTACT_FAMILY_NAME", currentContact.familyName);
        intent.putExtra("CONTACT_PHONE", currentContact.contactPhone);
        intent.putExtra("CONTACT_EMAIL", currentContact.email);
        intent.putExtra("CONTACT_ADDRESS", currentContact.address);
        intent.putExtra("CONTACT_ADD_PHONE", currentContact.additionalPhone);
        mContext.getApplicationContext().startActivity(intent);
    }

}
