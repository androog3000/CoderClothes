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
import android.widget.Toast;

import com.daclink.coderclothes.db.AppDatabase;
import com.daclink.coderclothes.db.UserLogDAO;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText enterUsername;
    private EditText enterPassword;

    private Button confirm;

    //private UserLog user;

    private UserLogDAO mUserLogDAO;

    //sharedPreferences and keys
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String prefUsername = sharedPreferences.getString(KEY_USERNAME, null);

        mUserLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserLogDAO();

        enterUsername = findViewById(R.id.createUsername);
        enterPassword = findViewById(R.id.createPassword);
        confirm = findViewById(R.id.createConfirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(CreateAccountActivity.this, "Cinnamon Toast Crunch", Toast.LENGTH_SHORT).show();
                String username = enterUsername.getText().toString();
                String password = enterPassword.getText().toString();

                Log.i("Checks", mUserLogDAO.getAllUsers().toString());

                UserLog user = mUserLogDAO.getUserByUsername(username);

                if(user != null){
                    Toast.makeText(CreateAccountActivity.this, "Account already exists", Toast.LENGTH_SHORT).show();
                } else {
                    if ( !(username.isEmpty()) && !(password.isEmpty()) ) {
                        user = new UserLog(username, password, false);
                        mUserLogDAO.insert(user);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_USERNAME, username);
                        editor.apply();

                        Log.i("Checks", mUserLogDAO.getAllUsers().toString());

                        Intent intent = new Intent(CreateAccountActivity.this, LandingPageActivity.class);
                        startActivity(intent);

                        } else {
                        Toast.makeText(CreateAccountActivity.this, "Please enter information", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });




    }
}