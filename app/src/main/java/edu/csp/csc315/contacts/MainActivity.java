/*
All work is my own, but I used the following resources, in addition to the official
Android and Google documentation, to learn how to accomplish what I thought would work for the app.

RESOURCES:

FAB button linking to another activity: user Mohammad C on StackOverflow
https://stackoverflow.com/questions/45643872/start-activity-via-floating-action-button

Setting up RecyclerView: Stevdza-Stan on Youtube
https://www.youtube.com/watch?v=18VcnYN5_LM

Adding the recycler view to the fragment: James Liu on Medium
https://medium.com/swlh/create-recyclerview-in-android-fragment-c0f0b151125f

Passing data from one activity to another using Intents: Naved_Alam on GeeksforGeeks
https://www.geeksforgeeks.org/android-how-to-send-data-from-one-activity-to-second-activity/

Make a phone call when the call button is clicked: Shaista Naaz on StackOverflow
https://stackoverflow.com/questions/5403308/make-a-phone-call-click-on-a-button

Setting up a MapView: CodingWithMitch on Youtube
https://www.youtube.com/watch?v=118wylgD_ig

Getting the latlng from an address using geocoding: CodingWithMitch on Youtube
https://www.youtube.com/watch?v=MWowf5SkiOE
 */

package edu.csp.csc315.contacts;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Contact> contacts= new ArrayList<Contact>();
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            // start the AddContactActivity when the FAB is clicked
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddContactActivity.class));
            }
        });

        dbHelper = new DatabaseHelper(this);
        Intent intent = getIntent();

        // check which intent started the activity so we know what to do with the data
        if (intent.hasExtra("source") && intent.getStringExtra("source").equals("AddContactActivity")) {
            // add the contact from the AddContactActivity to the database as long as some contact info is entered
            if (intent.hasExtra("name") || intent.hasExtra("phone") || intent.hasExtra("email") || intent.hasExtra("address")) {
                dbHelper.addContact(new Contact(
                        dbHelper.getContacts().size(),
                        intent.getStringExtra("name"),
                        intent.getStringExtra("phone"),
                        intent.getStringExtra("email"),
                        intent.getStringExtra("address")));
            }
        } else if (intent.hasExtra("source") && intent.getStringExtra("source").equals("ViewContactActivity")) {
            int id = intent.getIntExtra("id", -1);
            // get the contact info from the intent
            String name = intent.getStringExtra("name");
            String phone = intent.getStringExtra("phone");
            String email = intent.getStringExtra("email");
            String address = intent.getStringExtra("address");

            // if the user left any fields blank, send the database an empty string
            if (name == null) {
                name = "";
            }
            if (phone == null) {
                phone = "";
            }
            if (email == null) {
                email = "";
            }
            if (address == null) {
                address = "";
            }

            dbHelper.updateContact(id, name, phone, email, address);
        }

        // get the contents of the database and put them into the arraylist
        try {
            contacts = dbHelper.getContacts();
        } catch (Exception e) {
            // if there is an error loading the contacts from the database (such as no database exists),
            // start with a blank arraylist
            contacts = new ArrayList<Contact>();
        }
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }
}