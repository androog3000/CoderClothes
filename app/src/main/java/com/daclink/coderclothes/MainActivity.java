package com.daclink.coderclothes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daclink.coderclothes.db.AppDatabase;
import com.daclink.coderclothes.db.CartDAO;
import com.daclink.coderclothes.db.UserLogDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CoderClothesApp";
    private static final String USER_ID_KEY = "com.daclink.coderclothes.userIdKey";
    private static final String PREFERENCES_KEY = "com.daclink.coderclothes.PREFERENCES_KEY";

    private Button buttonMainLogin;
    private Button buttonMainCreateAccount;
    private TextView mMainDisplay;

    private UserLogDAO mUserLogDAO;
    private CartDAO mCartDAO;

    private List<UserLog> mUserLogs;

    private int mUserId = -1;

    private UserLog mUser;

    private TextView mAdmin;
    private Menu mOptionsMenu;
    private SharedPreferences mPreferences = null;

    //sharedPreferences and keys
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //creating access to UserLog table to add default users
        mUserLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserLogDAO();

        mCartDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCartDAO();


        //adding admin2
        UserLog userAdmin2 = mUserLogDAO.getUserByUsername("admin2");

        if (userAdmin2 == null){
            userAdmin2 = new UserLog("admin2", "admin2", true);
            userAdmin2.setMIsAdmin(true);
            userAdmin2.setMPassword("admin2");
            mUserLogDAO.insert(userAdmin2);
        }

        //adding cart for Admin2
        Cart cartAdmin2 = mCartDAO.getCartByName("admin2");
        if (cartAdmin2 == null){
            cartAdmin2 = new Cart("admin2");
            mCartDAO.insert(cartAdmin2);
        }

        //adding testuser1
        UserLog userTestuser1 = mUserLogDAO.getUserByUsername("testuser1");

        if (userTestuser1 == null) {
            userTestuser1 = new UserLog("testuser1", "testuser1", false);
            userTestuser1.setMIsAdmin(false);
            userTestuser1.setMPassword("testuser1");
            mUserLogDAO.insert(userTestuser1);
        }

        //adding cart for testuser1
        Cart cartTestuser1 = mCartDAO.getCartByName("testuser1");
        if (cartTestuser1 == null){
            cartTestuser1 = new Cart("testuser1");
            mCartDAO.insert(cartTestuser1);
        }

        Log.i("CheckMain", mUserLogDAO.getAllUsers().toString());
        Log.i("CheckMain", mCartDAO.getAllCarts().toString());

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String prefUsername = sharedPreferences.getString(KEY_USERNAME, null);
        String prefPassword = sharedPreferences.getString(KEY_PASSWORD, null);

        //shared preference switching straight to LandingPageActivity
        if (prefUsername != null) {
            Intent intent = new Intent(MainActivity.this, LandingPageActivity.class);
            startActivity(intent);
        } else {
            Log.i("Checks", "shared pref username was null");
        }


        //if not already directed to landing page, Login button directs to LoginActivity
        buttonMainLogin = findViewById(R.id.mainLoginButton);
        buttonMainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        buttonMainCreateAccount = findViewById(R.id.mainCreateAccountButton);
        buttonMainCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });


    }

}