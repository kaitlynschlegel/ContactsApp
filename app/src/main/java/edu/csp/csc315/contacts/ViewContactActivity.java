package edu.csp.csc315.contacts;

import android.content.Intent;
import android.graphics.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class ViewContactActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private Button btnUpdate;
    private Button btnCall;
    private Button btnEmail;
    private EditText txtName;
    private EditText txtPhone;
    private EditText txtEmail;
    private EditText txtAddress;
    private MapView map;
    private int id;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contact_activity);

        // initialize all of the fields
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);

        // get the id of the contact that was clicked in the recyclerview
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        // update the fields to reflect the contact info
        txtName.setText(intent.getStringExtra("name"));
        txtPhone.setText(intent.getStringExtra("phone"));
        txtEmail.setText(intent.getStringExtra("email"));
        txtAddress.setText(intent.getStringExtra("address"));

        // when the update button is clicked, go back to the main activity
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String name = txtName.getText().toString();
                String phone = txtPhone.getText().toString();
                String email = txtEmail.getText().toString();
                String address = txtAddress.getText().toString();

                // put all of the contact info into the intent to be passed to the MainActivity to update the contact
                Intent intent = new Intent(ViewContactActivity.this, MainActivity.class);
                intent.putExtra("source", "ViewContactActivity");

                // add the contact information that has been entered by the user to the intent and pass it to the MainActivity
                // don't pass any fields that are left blank
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

                // pass the id back to the intent to go back to the MainActivity
                intent.putExtra("id", id);

                // start the intent, which sends the contact info to the MainActivity to be updated
                startActivity(intent);
            }
        });

        // when the call button is clicked, open the phone app with the phone number pre-dialed
        btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + txtPhone.getText().toString()));
                startActivity(intent);
            }
        });

        // when the email button is clicked, open the email app with the email pre-entered
        btnEmail = findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + txtEmail.getText().toString()));
                startActivity(intent);
            }
        });

        // change the address to a latLng
        //geoLocate();
        // initialize the map
        //initGoogleMap(savedInstanceState);
    }

    /*
    the map shows an error in the run window but runs fine
    */

    // initialize the map that will show on the ViewContactActivity page
    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        map = findViewById(R.id.mapView);
        map.onCreate(mapViewBundle);

        map.getMapAsync(this);
    }

    // turn the address into latLng
    private void geoLocate() {
        String address = txtAddress.getText().toString();

        Geocoder geocoder = new Geocoder(ViewContactActivity.this);
        List<Address> list = new ArrayList<>();

        try {
            // give us the first search result when searching for the address from txtAddress
            list = geocoder.getFromLocationName(address, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // if we have a search result
        if(list.size() > 0) {
            // get the result from the geocoder
            Address addressResult = list.get(0);
            latLng = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());
        }
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if(mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        map.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        map.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        map.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // put the location marker on the map
        // map.addMarker(new MarkerOptions().position(new LatLng(0,0)));

        if (latLng == null) {
            latLng = new LatLng(0,0);
        }

        map.addMarker(new MarkerOptions().position(latLng));
        // move the camera so it zooms in and shows the marker
        map.moveCamera(CameraUpdateFactory.zoomTo(10.0f));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }
    /**/
}