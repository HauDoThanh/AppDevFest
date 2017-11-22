package com.example.phamh.devfest.Activitys;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phamh.devfest.Constantttt;
import com.example.phamh.devfest.R;
import com.example.phamh.devfest.Team;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, SeekBar.OnSeekBarChangeListener {
    String ID_TEAM;
    private Dialog dialog;
    private EditText edtTeamName, leaderName, edtSDT, edtMota, edtIdThanhVien;
    private Button btnCancel, btnCreateTeam;
    private SeekBar seek;
    private GoogleMap mMap;
    private double latitudeGPS, longtitudeGPS;
    private DatabaseReference database;
    View.OnClickListener onLick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnCancel:
                    dialog.dismiss();
                    break;
                case R.id.btnCreateTeam:
                    if (isTrungDataIDMember(ID_TEAM)) {
                        Toast.makeText(MapsActivity.this, "Bạn đã là leader team khác rồi", Toast.LENGTH_SHORT).show();
                    } else {
                        Team team = new Team();
                        ID_TEAM = "6473164731641_quoc";
                        team.setID_TEAM(ID_TEAM);
                        team.setTeamName(edtTeamName.getText().toString());
                        team.setLeaderName(leaderName.getText().toString());
                        team.setSdt(edtSDT.getText().toString());
                        team.setMota(edtMota.getText().toString());
                        team.setImageUrl("abcd.com");
                        team.setDoTuongTac(50.0);
                        ArrayList<String> dsMember = new ArrayList<>();
                        dsMember.add(edtIdThanhVien.getText().toString());
                        team.setListIdMember(dsMember);
                        final ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this);
                        progressDialog.setTitle("Posting....");
                        progressDialog.show();
                        database.child(Constantttt.ROOT_TEAMS).child(ID_TEAM).setValue(team).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                Toast.makeText(MapsActivity.this, "Post thanh cong!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                    }

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        database = FirebaseDatabase.getInstance().getReference();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        seek = (SeekBar) findViewById(R.id.seekbarAround);
        seek.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            moveCameraMyLoc(latitudeGPS, longtitudeGPS, 17);
        } else {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mMap != null) {
                        moveCameraMyLoc(latitudeGPS, longtitudeGPS, 17);
                    } else {
                        handler.postDelayed(this, 300);
                    }
                }
            }, 300);
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
        mMap.setMyLocationEnabled(true);
        //mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    public void moveCameraMyLoc(double lat, double lng, float zoom) {
        LatLng loc = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(loc).title("Here!"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        TextView txt = (TextView) findViewById(R.id.testTextSeek);
        txt.setText(i + " km");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void ThemGroup(View view) {
        dialog = new Dialog(this);
        dialog.setTitle("Update Team");
        dialog.setContentView(R.layout.dialog_create_team);
        dialog.setCancelable(false);
        dialog.show();

        edtIdThanhVien = (EditText) dialog.findViewById(R.id.edtIDmember);
        edtTeamName = (EditText) dialog.findViewById(R.id.edtTeamName);
        leaderName = (EditText) dialog.findViewById(R.id.edtLeaderName);
        edtSDT = (EditText) dialog.findViewById(R.id.edtPhoneNumber);
        edtMota = (EditText) dialog.findViewById(R.id.edtDescription);
        btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCreateTeam = (Button) dialog.findViewById(R.id.btnCreateTeam);


        btnCancel.setOnClickListener(onLick);
        btnCreateTeam.setOnClickListener(onLick);
    }

    public boolean isTrungDataIDMember(String id) {
        final ArrayList ids = new ArrayList();
        database.child(Constantttt.ROOT_TEAMS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Team team = dataSnapshot.getValue(Team.class);
                ids.addAll(team.getListIdMember());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (ids.contains(id)) {
            return true;
        }
        return false;
    }

    //lam tiep moi vao map thi location set lat long ngay

    // lay data tu nguyen tim map
}
