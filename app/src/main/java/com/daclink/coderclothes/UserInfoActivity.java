package com.daclink.coderclothes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

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

public class UserInfoActivity extends AppCompatActivity {

    private TextView userNameDisplay;
    private TextView passwordDisplay;

    private Button delete;
    private Button goBack;

    private UserLog mUserLog;
    private UserLogDAO mUserLogDAO;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        mUserLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserLogDAO();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String prefUsername = sharedPreferences.getString(KEY_USERNAME, null);
        String prefPassword = sharedPreferences.getString(KEY_PASSWORD, null);

        mUserLog = mUserLogDAO.getUserByUsername(prefUsername);

        userNameDisplay = findViewById(R.id.userInfoUsernameDisplay);
        userNameDisplay.setText(prefUsername);

        passwordDisplay = findViewById(R.id.userInfoPasswordDisplay);

        UserLog userLog = mUserLogDAO.getUserByUsername(prefUsername);
        String userPassword = userLog.getMPassword();

        String passwordMod = "";
        for (int i = 0; i < userPassword.length()-1; i++){
            passwordMod += "*";
        }
        passwordMod += userPassword.charAt(userPassword.length()-1);

        passwordDisplay.setText(passwordMod);

        delete = findViewById(R.id.userInfoDelete);
        goBack = findViewById(R.id.userInfoGoBack);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserInfoActivity.this, "Account deleted", Toast.LENGTH_LONG).show();
                mUserLogDAO.delete(mUserLog);
                sharedPreferences.edit().clear().apply();
                Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, LandingPageActivity.class);
                startActivity(intent);
            }
        });



    }

}