package edu.csp.csc315.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "contactsdb";
    private final static int VERSION = 1;
    private final static String TABLE_NAME = "contact_table";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    // create a table to store the contacts in
    public void onCreate(SQLiteDatabase db) {
        // create a table named contact_table with columns id, name, phone, email, and address
        // where each field contains text
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PHONE TEXT, EMAIL TEXT, ADDRESS TEXT)");
    }

    @Override
    // update the table
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // if there is a new version, drop the old table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // create the table again
        onCreate(db);
    }

    public void addContact(Contact contact) {
        // put the contact info into ContentValues because that's what the insert method expects
        ContentValues val = new ContentValues();
        val.put("id", contact.getId());

        val.put("name", contact.getName());
        val.put("phone", contact.getPhone());
        val.put("email", contact.getEmail());
        val.put("address", contact.getAddress());
        // get the database that the tasks will be stored in
        SQLiteDatabase db = this.getWritableDatabase();
        // insert the contact into the table
        db.insert(TABLE_NAME, null, val);
        db.close();
    }

    public void updateContact(int id, String name, String phone, String email, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME +
                " SET NAME=" + "'" + name + "'" +
                " ,PHONE=" + "'" + phone + "'" +
                " ,EMAIL=" + "'" + email + "'" +
                " ,ADDRESS=" + "'" + address + "'" +
                " WHERE ID=" + "'" + id + "'");
        db.close();
    }

    public ArrayList<Contact> getContacts() {
        // get the database that the contacts are stored in
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            contacts.add(new Contact(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
        }
        db.close();
        return contacts;
    }
}
