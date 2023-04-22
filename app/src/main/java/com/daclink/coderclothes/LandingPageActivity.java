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
import com.daclink.coderclothes.db.ProductDAO;
import com.daclink.coderclothes.db.UserLogDAO;

import java.util.HashMap;
import java.util.List;

public class LandingPageActivity extends AppCompatActivity {

    private Button adminButton;
    private Button mLogoutButton;
    private Button cartButton;
    private Button searchButton;

    private UserLog userLog;
    private UserLog userLogUsername;
    private UserLog userLogPassword;

    private UserLogDAO mUserLogDAO;
    private ProductDAO mProductDAO;
    private CartDAO mCartDAO;

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

        //DAOs
        mUserLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserLogDAO();

        mCartDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCartDAO();



        mEditTextUsername = findViewById(R.id.landingUsername);
        mLogoutButton = findViewById(R.id.logoutButton);
        adminButton = findViewById(R.id.buttonAdmin);
        cartButton = findViewById(R.id.landingCart);
        searchButton = findViewById(R.id.landingSearch);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String prefUsername = sharedPreferences.getString(KEY_USERNAME, null);
        String prefPassword = sharedPreferences.getString(KEY_PASSWORD, null);


        if (prefUsername != null) {
            mEditTextUsername.setText(prefUsername);
        }

        if (prefUsername.equals("admin2")){
            adminButton.setVisibility(View.VISIBLE);
        }

        //building product catalog
        mProductDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getProductDAO();

        Product pajamas = mProductDAO.getProductByName("Programma's Pajamas");
        if (pajamas == null){
            pajamas = new Product("Programma's Pajamas", "Comfortable and stylish. Perfect for the programmer who has no intentions of leaving the house today.", 19.99);
            mProductDAO.insert(pajamas);
        }

        Product coderoys = mProductDAO.getProductByName("Coderoy Pants");
        if (coderoys == null){
            coderoys = new Product("Coderoy Pants", "Sure corduroys haven't been in style in over 20 years, but you're a coder, since when did you care about what's in style? They're comfortable and they have ridges.", 14.99);
            mProductDAO.insert(coderoys);
        }

        Product glasses = mProductDAO.getProductByName("See-Sharp Blue-Lens Glasses");
        if (glasses == null){
            glasses = new Product("See-Sharp Blue-Lens Glasses", "Perfect for the coder who stares at the computer all day. These glasses provide contrast and clarity, so give your eyes a break.", 24.99);
            mProductDAO.insert(glasses);
        }

        Product memoryLeak = mProductDAO.getProductByName("Memory Leak Alkaline Beverage");
        if (memoryLeak == null){
            memoryLeak = new Product("Memory Leak Alkaline Beverage", "Illegal in 9 countries, Memory Leak has occasionally been shown to improve the human brain's capacity for memory management. However, some consumers have complained of a sensation similar to a complete wiping of the hard drive. But with the release of the new Cherry Berry flavor, it's probably worth the risk", 4.99);
            mProductDAO.insert(memoryLeak);
        }


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPageActivity.this, Search.class);
                startActivity(intent);
            }
        });

        //testing out results temporarily by clicking on cart
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Check", mCartDAO.getCartByName(prefUsername).toString());
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

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Cart> carts = mCartDAO.getAllCarts();
                for (Cart c : carts){
                    Log.i("CheckingCarts", c.toString());
                }
            }
        });

        //note that cartButton is in fact button to UserInfoActivity, I was too lazy to rename it and redo constraint ID assignments in layout file
        cartButton = findViewById(R.id.landingCart);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("CheckLanding", "User Info button all good");
                Intent intent = new Intent(LandingPageActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });

    }
}