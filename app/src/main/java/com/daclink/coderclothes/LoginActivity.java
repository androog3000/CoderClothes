package com.daclink.coderclothes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daclink.coderclothes.db.AppDatabase;
import com.daclink.coderclothes.db.CartDAO;
import com.daclink.coderclothes.db.UserLogDAO;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.daclink.coderclothes.userIdKey";

    private Button mLoginEnterButton;

    private EditText mUsernameField;
    private EditText mPasswordField;

    private UserLogDAO mUserLogDAO;
    private CartDAO mCartDAO;

    private String mUsername;
    private String mPassword;

    private UserLog userLogUsername;
    private UserLog userLogPassword;

    private boolean adminIsAdmin;

    public String getMUsername() {
        return mUsername;
    }

    public String getMPassword() {
        return mPassword;
    }

    public void setMUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public void setMPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    //sharedPreferences and keys
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

//not sure if following fields are needed on this activity
//    private List<UserLog> mUserLogs;
//    private int mUserId = -1;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //connectDisplay();
        //
        mUsernameField = findViewById(R.id.editTextUsername);
        mPasswordField = findViewById(R.id.editTextPassword);
        mLoginEnterButton = findViewById(R.id.loginEnter);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String prefUsername = sharedPreferences.getString(KEY_USERNAME, null);
        //String prefPassword = sharedPreferences.getString(KEY_PASSWORD, null);

        //loginButton onClickListener alternate
//        mLoginEnterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString(KEY_USERNAME, mUsernameField.getText().toString());
//                editor.putString(KEY_PASSWORD, mPasswordField.getText().toString());
//                editor.apply();
//                if ((mUsernameField.getText().toString().equals("admin2") && mPasswordField.getText().toString().equals("admin2")) ||
//                    (mUsernameField.getText().toString().equals("testuser1") && mPasswordField.getText().toString().equals("testuser1"))) {
//
//                    Intent intent = new Intent(LoginActivity.this, LandingPageActivity.class);
//                    startActivity(intent);
//
//                    Toast.makeText(LoginActivity.this, "Avocado Toast!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(LoginActivity.this, "Account not found", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

        //getDatabase();
        mUserLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserLogDAO();

        mLoginEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_USERNAME, mUsernameField.getText().toString());
                    editor.putString(KEY_PASSWORD, mPasswordField.getText().toString());
                    editor.apply();

//                  //getting the input fields converted into strings
                    mUsername = mUsernameField.getText().toString();
                    mPassword = mPasswordField.getText().toString();

                    //assigning UserLog object to DataAccessObject method for String mUsername
                    //UserLog object = UserLogDAO object.method()
                    userLogUsername = mUserLogDAO.getUserByUsername(mUsername);



                    Log.i("CheckLogin", mUserLogDAO.getAllUsers().toString());

                    if (userLogUsername == null) {
                        Toast.makeText(LoginActivity.this, "Account not found", Toast.LENGTH_SHORT).show();
                    } else {
                        if (mPassword.equals(userLogUsername.getMPassword())){
                            Intent intent = new Intent(LoginActivity.this, LandingPageActivity.class);
                            startActivity(intent);
                        }
                    }


                }
            });

    }


    //connectDisplay()
    private void connectDisplay(){
        mUsernameField = findViewById(R.id.editTextUsername);
        mPasswordField = findViewById(R.id.editTextPassword);
        mLoginEnterButton = findViewById(R.id.loginEnter);


        mLoginEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getValuesFromDisplay();

                //getting the input fields converted into strings
                mUsername = mUsernameField.getText().toString();
                mPassword = mPasswordField.getText().toString();

                //assigning UserLog object to DataAccessObject method for String mUsername
                userLogUsername = mUserLogDAO.getUserByUsername(mUsername);

                //did anything get assigned to mUserLog when you checked the database for the input string username
                if(userLogUsername == null){
                    Toast.makeText(LoginActivity.this, "Account not found", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, LandingPageActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void toast(){
        Toast.makeText(this, "made it this far", Toast.LENGTH_SHORT).show();
    }

    private boolean validatePassword(){
            return userLogUsername.getMPassword().equals(mPassword);
    }

    private void getValuesFromDisplay(){
            mUsername = mUsernameField.getText().toString();
            mPassword = mPasswordField.getText().toString();
    }

    private boolean checkForUserInDatabase(){
        userLogUsername = mUserLogDAO.getUserByUsername(mUsername);
            if(userLogUsername == null){
                Toast.makeText(this, "no user " + mUsername + " found", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
    }

    private void getDatabase(){
            mUserLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                    .allowMainThreadQueries()
                    .build()
                    .getUserLogDAO();
    }

    public static Intent intentFactory(Context context){
            Intent intent = new Intent(context, LoginActivity.class);
            return intent;
    }



}