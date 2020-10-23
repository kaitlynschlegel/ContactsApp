package edu.csp.csc315.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {

    private Button btnSave;
    private Button btnCancel;
    private String name;
    private String phone;
    private String email;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_activity);

        // when the save button is clicked, go back to the main activity
        btnSave = (Button)findViewById(R.id.btnUpdate);
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // get the information from the user
                name = ((EditText)findViewById(R.id.txtName)).getText().toString();
                phone = ((EditText)findViewById(R.id.txtPhone)).getText().toString();
                email = ((EditText)findViewById(R.id.txtEmail)).getText().toString();
                address = ((EditText)findViewById(R.id.txtAddress)).getText().toString();

                // add the contact information that has been entered by the user to the intent and pass it to the MainActivity
                // don't pass any fields that are left blank
                Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                if (!name.equals("")) {
                    intent.putExtra("name", name);
                }
                if (!phone.equals("")) {
                    intent.putExtra("phone", phone);
                }
                if (!email.equals("")) {
                    intent.putExtra("email", email);
                }
                if (!address.equals("")) {
                    intent.putExtra("address", address);
                }
                // tell the intent that it came from addcontactactivity
                intent.putExtra("source", "AddContactActivity");
                // start the intent, which sends the contact info to the MainActivity to be put into the database
                startActivity(intent);
            }
        });

        // when the cancel button is clicked, go back to the main activity
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(AddContactActivity.this, MainActivity.class));
            }
        });
    }

}