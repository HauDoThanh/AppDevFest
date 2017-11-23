package com.example.phamh.devfest.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.phamh.devfest.Constantttt;
import com.example.phamh.devfest.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;

/**
 * Created by buimi on 11/22/2017.
 */

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private String name, id;
    private URL imageURL;
    private CallbackManager callbackManager;
    private LoginButton loginButton;


    private String TAG = "fb";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        getSupportActionBar().hide();
        //config facebook
        FacebookSdk.sdkInitialize(getApplicationContext());

        //anh xa
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.btn_signin);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(SignInActivity.this, "Successful", Toast.LENGTH_LONG).show();
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {
                                //Application code
                                name = object.optString(getString(R.string.name));
                                id = object.optString(getString(R.string.id));
                                imageURL = extractFacebookIcon(id);
                                Log.i(TAG, name);
                                Log.i(TAG, id);
                                Log.i(TAG, imageURL.toString());

                                //luu data facebook vao share
                                SharedPreferences ref = getSharedPreferences(Constantttt.DATA_LOCGIN, MODE_PRIVATE);
                                SharedPreferences.Editor edit_login = ref.edit();
                                edit_login.putString("idFb", id);
                                edit_login.putString("name", name);
                                edit_login.putString("imageURL", imageURL.toString());
                                //cho nay
                                edit_login.putBoolean("isLeader", false);
                                edit_login.apply();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString(getString(R.string.fields), getString(R.string.fields_name));
                request.setParameters(parameters);
                request.executeAsync();
                checkOnFirebase(id);
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignInActivity.this, "Login  canceled.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                //Log.e(Constanttt.TAG_TAG,e.toString());
                Toast.makeText(SignInActivity.this, "Login failed.", Toast.LENGTH_LONG).show();
            }
        });

    }

    //lay url image
    public URL extractFacebookIcon(String id) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL imageURL = new URL("http://graph.facebook.com/" + id
                    + "/picture?type=large");
            return imageURL;
        } catch (Throwable e) {
            return null;
        }
    }

    //callback
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        LoginManager.getInstance().logInWithReadPermissions(
                this
                , Arrays.asList("public_profile", "user_friends", "email"));
    }

    //Kiem tra co so du lieu tren Firebase
    private void checkOnFirebase(final String id){
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = root.child("USER");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(id).exists()) {
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SignInActivity.this, SetupNewUserActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
