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
import com.daclink.coderclothes.db.UserLogDAO;

public class LandingPageActivity extends AppCompatActivity {

    private Button adminButton;
    private Button mLogoutButton;
    private Button cartButton;

    private UserLog userLog;
    private UserLog userLogUsername;
    private UserLog userLogPassword;

    private UserLogDAO mUserLogDAO;

    private LoginActivity loginActivity;

    private String mUsername;

    private EditText mEditTextUsername;
    private TextView mTextViewUsername;
    private TextView logo;


    //sharedPreferences and keys as recommended from outside sources
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        //previously getDatabase();
        mUserLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserLogDAO();


        mEditTextUsername = findViewById(R.id.landingUsername);
        mLogoutButton = findViewById(R.id.logoutButton);
        adminButton = findViewById(R.id.buttonAdmin);
        cartButton = findViewById(R.id.landingCart);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String prefUsername = sharedPreferences.getString(KEY_USERNAME, null);
        String prefPassword = sharedPreferences.getString(KEY_PASSWORD, null);

        if (prefUsername != null) {
            mEditTextUsername.setText(prefUsername);
        }

        if (prefUsername.equals("admin2")){
            adminButton.setVisibility(View.VISIBLE);
        }

        //testing out results temporarily by clicking on cart
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LandingPageActivity.this, "Testing for toast", Toast.LENGTH_SHORT).show();
            }
        });



        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(LandingPageActivity.this, "Logout successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LandingPageActivity.this, MainActivity.class);
                startActivity(intent);
                //finish();
            }
        });

    }

    private void getDatabase() {
        mUserLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserLogDAO();
    }
}