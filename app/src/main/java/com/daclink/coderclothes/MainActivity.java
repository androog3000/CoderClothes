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

        mUserLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserLogDAO();

        //adding admin2
        UserLog userAdmin2 = mUserLogDAO.getUserByUsername("admin2");

        if (userAdmin2 == null){
            userAdmin2 = new UserLog("admin2", "admin2", true);
            userAdmin2.setMIsAdmin(true);
            userAdmin2.setMPassword("admin2");
            mUserLogDAO.insert(userAdmin2);
        }

        //adding testuser1
        UserLog userTestuser1 = mUserLogDAO.getUserByUsername("testuser1");

        if (userTestuser1 == null) {
            userTestuser1 = new UserLog("testuser1", "testuser1", false);
            userTestuser1.setMIsAdmin(false);
            userTestuser1.setMPassword("testuser1");
            mUserLogDAO.insert(userTestuser1);
        }

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

        //TODO: from Dr. C videos commenting out for now
        //checkForUser();

        //TODO: from Dr. C videos commenting out for now
        //loginUser(mUserId);

        //TODO: check later to add these
        //mMainDisplay = findViewById(R.id.mainCoderClothesDisplay);
        //mMainDisplay.setMovementMethod(new ScrollingMovementMethod());

        //if not already directed to landing page, directed now
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


    //end of onCreate
    }

//    private void loginUser(int userId) {
//        //check if user Id is valid
//        mUser = mUserLogDAO.getUserLogsById(userId);
//        //check if mUser is not null
//        addUserToPreference(mUserId);
//        invalidateOptionsMenu();
//    }



    //--------methods below may or may not be needed, here for reference-------

    private void getDatabase() {
        mUserLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserLogDAO();
    }

    private void checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        //do we have a user in the preferences?
        if(mUserId != -1){
            return;
        }

        if(mPreferences == null){
            getPrefs();
        } else {
            mUserId = mPreferences.getInt(USER_ID_KEY, -1);
        }

        //do we have any users at all?
        List<UserLog> users = mUserLogDAO.getAllUsers();
        if(users.size() <= 0){
            UserLog admin2 = new UserLog("admin2", "admin2", true);
            UserLog testuser1 = new UserLog("testuser1", "testuser1", false);
            mUserLogDAO.insert(admin2, testuser1);
        }

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);

    }

    private void getPrefs(){
        //check this preferences object to see if we have someone logged in
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref() {
        addUserToPreference(-1);
    }

    private void addUserToPreference(int userId){
        if (mPreferences == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

    //methods dealing with logout menu
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        //TODO: maybe watch codingflow, figure out userMenuLogout
//        if(mUser != null){
//            MenuItem item = menu.findItem(R.id.userMenuLogout);
//            item.setTitle(mUser.getMUsername());
//
//            mOptionsMenu = menu;
//        }
//        return super.onPrepareOptionsMenu(menu);
//    }

//    //TODO: return to check on menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.user_menu, menu);
//        return true;
//    }

//      taken from "more details"
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.userMenuLogout:
//                logoutUser();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    //      testing out recommended alternatives
//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.userMenuLogout:
//                logoutUser();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    //TODO: logoutUser()
    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clearUserFromIntent();
                        clearUserFromPref();
                        mUserId = -1;
                        checkForUser();
                    }
                });
        alertBuilder.setNegativeButton(getString(R.string.no),
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //shrug?
                }
            });
           alertBuilder.create().show();
    }


}