package com.daclink.coderclothes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.daclink.coderclothes.db.AppDatabase;
import com.daclink.coderclothes.db.CartDAO;
import com.daclink.coderclothes.db.UserLogDAO;

import java.util.ArrayList;
import java.util.Collections;

public class CartActivity extends AppCompatActivity {

    private TextView usernameDisplay;

    private EditText cart1Product;
    private EditText cart1Price;
    private EditText cart1Quantity;

    private EditText cart2Product;
    private EditText cart2Price;
    private EditText cart2Quantity;

    private EditText cart3Product;
    private EditText cart3Price;
    private EditText cart3Quantity;

    private EditText cart4Product;
    private EditText cart4Price;
    private EditText cart4Quantity;

    private EditText cartTotal;

    private Button emptyCart;
    private Button completePurchase;

    private UserLogDAO mUserLogDAO;
    private CartDAO mCartDAO;

    private int quantityPjs;
    private int quantityPants;
    private int quantityGlasses;
    private int quantityBeverage;

    //sharedPreferences and keys as recommended from outside sources
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //DAOs
        mUserLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserLogDAO();

        mCartDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCartDAO();

        //displaying username in CartActivity
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String prefUsername = sharedPreferences.getString(KEY_USERNAME, null);

        usernameDisplay = findViewById(R.id.cartUsernameDisplay);
        usernameDisplay.setText(prefUsername);

        Cart cart = mCartDAO.getCartByName(prefUsername);
        Log.i("CheckCartActivity", cart.toString());

        //accessing quantities added to cart
        quantityPjs = cart.getPajamasQuantity();
        quantityPants = cart.getPantsQuantity();
        quantityGlasses = cart.getGlassesQuantity();
        quantityBeverage = cart.getBeverageQuantity();



        int quantityArray[] = new int[] {quantityPjs, quantityPants, quantityGlasses, quantityBeverage};

//        ArrayList<Integer> cartQuantities = new ArrayList<>();
//        cartQuantities.add(quantityPjs);
//        cartQuantities.add(quantityPants);
//        cartQuantities.add(quantityGlasses);
//        cartQuantities.add(quantityBeverage);

        String productsArray[] = new String[] {"Programma's Pajamas", "Coderoy Pants", "See-Sharp Blue-Lens Glasses", "Memory Leak Alkaline Beverage"};

//        ArrayList<String> cartProducts = new ArrayList<>();
//        cartProducts.add("Programma's Pajamas");
//        cartProducts.add("Coderoy Pants");
//        cartProducts.add("See-Sharp Blue-Lens Glasses");
//        cartProducts.add("Memory Leak Alkaline Beverage");

        //sorting arrayLists together
        //sorting cart quantities to popoulate CartActivity
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3 - i; j++) {
                if (quantityArray[j] < quantityArray[j+1]) {
                    int temp = quantityArray[j];
                    String tempString = productsArray[j];
                    quantityArray[j] = quantityArray[j+1];
                    productsArray[j] = productsArray[j+1];
                    quantityArray[j+1] = temp;
                    productsArray[j+1] = tempString;
                }
            }
        }

        Log.i("CheckCartActivity", productsArray[0] + " " + productsArray[1] + " " + productsArray[2] + " " + productsArray[3]);
        Log.i("CheckCartActivity", quantityArray[0] + " " + quantityArray[1] + " " + quantityArray[2] + " " + quantityArray[3]);





    }
}