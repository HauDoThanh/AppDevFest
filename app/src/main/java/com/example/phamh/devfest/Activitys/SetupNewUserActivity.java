package com.example.phamh.devfest.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phamh.devfest.Class.UserClass;
import com.example.phamh.devfest.Constantttt;
import com.example.phamh.devfest.R;
import com.example.phamh.devfest.Service.GPSTracker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URL;

import static java.security.AccessController.getContext;

/**
 * Created by buimi on 11/23/2017.
 */

public class SetupNewUserActivity extends AppCompatActivity{


    private double x = 0.0;
    private double y = 0.0;

    private String name, id, imageURL;
    private UserClass userClass;
    private ImageView imgAvatarUser;
    private TextView tvNameUser;
    private EditText edtSDT,edtDiaChi,edtMoTa;

    private DatabaseReference dbRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setupnewuser);




        turnGPSOn();
        getLocation();

        //Khoi tao view
        imgAvatarUser = (ImageView) findViewById(R.id.imgAvatarUser);
        edtSDT = (EditText) findViewById(R.id.editTextSDT);
        edtDiaChi = (EditText) findViewById(R.id.editTextDiaChi);
        edtMoTa = (EditText) findViewById(R.id.editTextMoTa);



        //Lay du lieu ve Preferences
        SharedPreferences dataSignIn = getSharedPreferences (Constantttt.DATA_LOCGIN,MODE_PRIVATE);
        id = dataSignIn.getString("idFb","");
        name = dataSignIn.getString("name","");
        imageURL = dataSignIn.getString("imageURL","");

        userClass = new UserClass(id,name,x,y,false,edtSDT.getText().toString(),imageURL, edtMoTa.getText().toString(),edtDiaChi.getText().toString());

        Picasso.with(this).load(imageURL).into(imgAvatarUser);

    }



    private void getLocation(){
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        GPSTracker gpsTracker = new GPSTracker();
        if (gpsTracker != null && manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = gpsTracker.getLocation();
            x = location.getLatitude();
            y = location.getLongitude();
        }
    }
    private void turnGPSOn() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder
                    .setMessage(getResources().getString(R.string.enable_GPS))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.open),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    Intent callGPSSettingIntent = new Intent(
                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(callGPSSettingIntent);
                                }
                            });
            alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
    }


    //Up Firebase
    public void upLoadtoFirebase(View view) {
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("USER").setValue(userClass);
    }
}
