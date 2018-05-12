package com.example.patricia.contactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactHelper extends SQLiteOpenHelper {

    private static final String TAG = ContactHelper.class.getSimpleName();

    // Declaring all these as constants makes code a lot more readable, and looking like SQL.

    // Versions has to be 1 first time or app will crash.
    private static final int DATABASE_VERSION = 2;
    private static final String CONTACTS_TABLE = "contact";
    private static final String DATABASE_NAME = "contactlist";

    // Column names...
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_FAMILY_NAME = "family_name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_ADD_PHONE = "additional_phone";

    // ... and a string array of columns.
    private static final String[] COLUMNS =
            {KEY_ID, KEY_NAME, KEY_FAMILY_NAME, KEY_PHONE, KEY_EMAIL, KEY_ADDRESS, KEY_ADD_PHONE};

    // Build the SQL query that creates the table.
    private static final String CONTACTS_TABLE_CREATE =
            "CREATE TABLE " + CONTACTS_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_NAME + " TEXT, " +
                    KEY_FAMILY_NAME + " TEXT, " +
                    KEY_PHONE + " TEXT, " +
                    KEY_EMAIL + " TEXT, " +
                    KEY_ADDRESS + " TEXT, " +
                    KEY_ADD_PHONE + " TEXT);";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public ContactHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Construct WordListOpenHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CONTACTS_TABLE_CREATE);
        fillDatabaseWithData(db);
        // We cannot initialize mWritableDB and mReadableDB here, because this creates an infinite
        // loop of on Create being repeatedly called.
    }

    /*
     * Called when a database needs to be upgraded. The most basic version of this method drops
     * the tables, and then recreates them. All data is lost, which is why for a production app,
     * you want to back up your data first. If this method fails, changes are rolled back.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ContactHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE);
        onCreate(db);
    }

    /*
     * Adds the initial data set to the database.
     * According to the docs, onCreate for the open helper does not run on the UI thread.
     *
     * @param db Database to fill with data since the member variables are not initialized yet.
     */
    private void fillDatabaseWithData(SQLiteDatabase db) {
        Contact initialContact = new Contact("Patricia", "Vera", "+34666666666", "patricia.vera.hernandez@student.laurea.fi", "", "");

        // Create a container for the data.
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, initialContact.contactName);
        values.put(KEY_FAMILY_NAME, initialContact.familyName);
        values.put(KEY_PHONE, initialContact.contactPhone);
        values.put(KEY_EMAIL, initialContact.email);
        values.put(KEY_ADDRESS, initialContact.address);
        values.put(KEY_ADD_PHONE, initialContact.additionalPhone);

        db.insert(CONTACTS_TABLE, null, values);
    }

    /*
     * Queries the database for an entry at a given position.
     *
     * @param position The Nth row in the table.
     * @return a WordItem with the requested database entry.
     */
    public Contact query(int position) {
        String query = "SELECT  * FROM " + CONTACTS_TABLE +
                " ORDER BY " + KEY_NAME + " ASC " +
                "LIMIT " + position + ",1";

        Cursor cursor = null;
        Contact entry = new Contact();

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            entry.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            entry.setContactName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            entry.setFamilyName(cursor.getString(cursor.getColumnIndex(KEY_FAMILY_NAME)));
            entry.setContactPhone(cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            entry.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            entry.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
            entry.setAdditionalPhone(cursor.getString(cursor.getColumnIndex(KEY_ADD_PHONE)));
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return entry;
        }
    }

    /*
     * Gets the number of rows in the word list table.
     *
     * @return The number of entries in WORD_LIST_TABLE.
     */
    public long count() {
        if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
        return DatabaseUtils.queryNumEntries(mReadableDB, CONTACTS_TABLE);
    }

    /*
     * Adds a single word row/entry to the database.
     *
     * @param  newContact New contact.
     * @return The id of the inserted word.
     */
    public long insert(Contact newContact) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, newContact.contactName);
        values.put(KEY_FAMILY_NAME, newContact.familyName);
        values.put(KEY_PHONE, newContact.contactPhone);
        values.put(KEY_EMAIL, newContact.email);
        values.put(KEY_ADDRESS, newContact.address);
        values.put(KEY_ADD_PHONE, newContact.additionalPhone);
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            newId = mWritableDB.insert(CONTACTS_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }

    /*
     * Updates the word with the supplied id to the supplied value.
     *
     * @param id Id of the contact to update.
     * @param word The new value of the word.
     * @return The number of rows affected or -1 of nothing was updated.
     */
    public int update(int id, Contact editedContact) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, editedContact.contactName);
            values.put(KEY_FAMILY_NAME, editedContact.familyName);
            values.put(KEY_PHONE, editedContact.contactPhone);
            values.put(KEY_EMAIL, editedContact.email);
            values.put(KEY_ADDRESS, editedContact.address);
            values.put(KEY_ADD_PHONE, editedContact.additionalPhone);

            mNumberOfRowsUpdated = mWritableDB.update(CONTACTS_TABLE, //table to change
                    values, // new values to insert
                    KEY_ID + " = ?", // selection criteria for row (in this case, the _id column)
                    new String[]{String.valueOf(id)}); //selection args; the actual value of the id

        } catch (Exception e) {
            Log.d (TAG, "UPDATE EXCEPTION! " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }

    /*
     * Deletes one entry identified by its id.
     *
     * @param id ID of the entry to delete.
     * @return The number of rows deleted. Since we are deleting by id, this should be 0 or 1.
     */
    public int delete(int id) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            deleted = mWritableDB.delete(CONTACTS_TABLE, //table name
                    KEY_ID + " = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());        }
        return deleted;
    }
}
