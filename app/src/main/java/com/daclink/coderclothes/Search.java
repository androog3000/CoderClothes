package com.daclink.coderclothes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.daclink.coderclothes.db.AppDatabase;
import com.daclink.coderclothes.db.CartDAO;
import com.daclink.coderclothes.db.ProductDAO;

import java.util.HashMap;
import java.util.List;

public class Search extends AppCompatActivity {

    private Button pajamaMoreInfo;
    private Button pajamaAdd;
    private Button pantsMoreInfo;
    private Button pantsAdd;
    private Button glassesMoreInfo;
    private Button glassesAdd;
    private Button beverageMoreInfo;
    private Button beverageAdd;

    private Button goToCart;

    private ProductDAO mProductDAO;
    private CartDAO mCartDAO;

    private List<Product> productList;

    //sharedPreferences and keys as recommended from outside sources
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //shared preferences to help cart assignment
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String prefUsername = sharedPreferences.getString(KEY_USERNAME, null);

        //DAO objects for Product and Cart tables
        mProductDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getProductDAO();

        mCartDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCartDAO();

        //testing cart info making it through database
        Log.i("CheckSearch", mCartDAO.getAllCarts().toString());

        //'More Info' button functionality
        //Buttons trigger Toast messages for now to reveal Product Descriptions
        Product pajamas = mProductDAO.getProductByName("Programma's Pajamas");
        pajamaMoreInfo = findViewById(R.id.searchPajamasMoreInfo);
        pajamaMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Search.this, pajamas.toString(), Toast.LENGTH_LONG).show();
            }
        });

        Product pants = mProductDAO.getProductByName("Coderoy Pants");
        pantsMoreInfo = findViewById(R.id.searchPantsMoreInfo);
        pantsMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Search.this, pants.toString(), Toast.LENGTH_LONG).show();
            }
        });

        Product glasses = mProductDAO.getProductByName("See-Sharp Blue-Lens Glasses");
        glassesMoreInfo = findViewById(R.id.searchGlassesMoreInfo);
        glassesMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Search.this, glasses.toString(), Toast.LENGTH_LONG).show();
            }
        });

        Product beverage = mProductDAO.getProductByName("Memory Leak Alkaline Beverage");
        beverageMoreInfo = findViewById(R.id.searchBeverageMoreInfo);
        beverageMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Search.this, beverage.toString(), Toast.LENGTH_LONG).show();
            }
        });

        //wiring up Product 'Add to cart' buttons
        pajamaAdd = findViewById(R.id.searchPajamasAdd);
        pantsAdd = findViewById(R.id.searchPantsAdd);
        glassesAdd = findViewById(R.id.searchGlassesAdd);
        beverageAdd = findViewById(R.id.searchBeverageAdd);
        goToCart = findViewById(R.id.searchGoToCart);

        Cart cart = mCartDAO.getCartByName(prefUsername);

        pajamaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Search.this, "Adding Pajamas to Cart", Toast.LENGTH_SHORT).show();
                int pajamaCount = cart.getPajamasQuantity();
                pajamaCount++;
                cart.setPajamasQuantity(pajamaCount);
                Log.i("CheckCart", cart.toString());
            }
        });

        pantsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Search.this, "Adding Coderoys to Cart", Toast.LENGTH_SHORT).show();
                int pantsCount = cart.getPantsQuantity();
                pantsCount++;
                cart.setPantsQuantity(pantsCount);
                Log.i("CheckCart", cart.toString());
            }
        });

        glassesAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Search.this, "Adding Glasses to Cart", Toast.LENGTH_SHORT).show();
                int glassesCount = cart.getGlassesQuantity();
                glassesCount++;
                cart.setGlassesQuantity(glassesCount);
                Log.i("CheckCart", cart.toString());
            }
        });

        beverageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Search.this, "Adding Memory Leak to Cart", Toast.LENGTH_SHORT).show();
                int beverageCount = cart.getBeverageQuantity();
                beverageCount++;
                cart.setBeverageQuantity(beverageCount);
                Log.i("CheckCart", cart.toString());
            }
        });

        goToCart = findViewById(R.id.searchGoToCart);
        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCartDAO.update(cart);
            }
        });

        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCartDAO.update(cart);
                Intent intent = new Intent(Search.this, CartActivity.class);
                startActivity(intent);
            }
        });

    }
}