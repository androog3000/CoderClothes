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
import com.daclink.coderclothes.db.CartDAO;
import com.daclink.coderclothes.db.UserLogDAO;

import java.util.ArrayList;
import java.util.Collections;

public class CartActivity extends AppCompatActivity {

    private TextView usernameDisplay;

    private TextView cart1Product;
    private TextView cart1Price;
    private TextView cart1Quantity;

    private TextView cart2Product;
    private TextView cart2Price;
    private TextView cart2Quantity;

    private TextView cart3Product;
    private TextView cart3Price;
    private TextView cart3Quantity;

    private TextView cart4Product;
    private TextView cart4Price;
    private TextView cart4Quantity;

    private TextView cartTotal;

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


        String productsArray[] = new String[] {"Programma's Pajamas", "Coderoy Pants", "See-Sharp Blue-Lens Glasses", "Memory Leak Alkaline Beverage"};


        //sorting arrayLists together
        //sorting cart quantities to help populate CartActivity
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

        //Log.i("CheckCartActivity", productsArray[0] + " " + productsArray[1] + " " + productsArray[2] + " " + productsArray[3]);
        //Log.i("CheckCartActivity", quantityArray[0] + " " + quantityArray[1] + " " + quantityArray[2] + " " + quantityArray[3]);

        //now I have productArray and quantityArray sorted descending together


        //assigning values to CartActivity layout
        cart1Product = findViewById(R.id.cart1Product);
        cart1Price = findViewById(R.id.cart1Price);
        cart1Quantity = findViewById(R.id.cart1Quantity);

        cart2Product = findViewById(R.id.cart2Product);
        cart2Price = findViewById(R.id.cart2Price);
        cart2Quantity = findViewById(R.id.cart2Quantity);

        cart3Product = findViewById(R.id.cart3Product);
        cart3Price = findViewById(R.id.cart3Price);
        cart3Quantity = findViewById(R.id.cart3Quantity);

        cart4Product = findViewById(R.id.cart4Product);
        cart4Price = findViewById(R.id.cart4Price);
        cart4Quantity = findViewById(R.id.cart4Quantity);


        Log.i("CheckCartActivity", "quantityArray[0]: " + quantityArray[0]);

        if (quantityArray[0] > 0) {
            cart1Product.setText(productsArray[0]);
            cart1Price.setText(getPriceString(productsArray[0]));
            String quantity = String.valueOf(quantityArray[0]);
            cart1Quantity.setText(quantity);
        } else {
            cart1Product.setText(" ");
            cart1Price.setText(" ");
            cart1Quantity.setText(" ");
        }

        if (quantityArray[1] > 0) {
            cart2Product.setText(productsArray[1]);
            cart2Price.setText(getPriceString(productsArray[1]));
            String quantity = String.valueOf(quantityArray[1]);
            cart2Quantity.setText(quantity);
        } else {
            cart2Product.setText(" ");
            cart2Price.setText(" ");
            cart2Quantity.setText(" ");
        }

        if (quantityArray[2] > 0) {
            cart3Product.setText(productsArray[2]);
            cart3Price.setText(getPriceString(productsArray[2]));
            String quantity = String.valueOf(quantityArray[2]);
            cart3Quantity.setText(quantity);
        } else {
            cart3Product.setText(" ");
            cart3Price.setText(" ");
            cart3Quantity.setText(" ");
        }

        if (quantityArray[3] > 0) {
            cart4Product.setText(productsArray[3]);
            cart4Price.setText(getPriceString(productsArray[3]));
            String quantity = String.valueOf(quantityArray[3]);
            cart4Quantity.setText(quantity);
        } else {
            Log.i("CheckCartActivity", "quantityArray[3] <= 0");
            cart4Product.setText(" ");
            cart4Price.setText(" ");
            cart4Quantity.setText(" ");
        }

        //calculating and displaying cart total
        cartTotal = findViewById(R.id.cartTotalDisplay);

        double total = quantityArray[0] * getPriceDouble(productsArray[0]) +
                        quantityArray[1] * getPriceDouble(productsArray[1]) +
                        quantityArray[2] * getPriceDouble(productsArray[2]) +
                        quantityArray[3] * getPriceDouble(productsArray[3]);

        //String totalString = String.valueOf(total);
        String formatTotal = String.format("%.2f", total);

        cartTotal.setText("$" + formatTotal);

        //wiring up and adding functionality to buttons 'Empty Cart' and 'Complete Purchase'
        emptyCart = findViewById(R.id.buttonEmpty);
        completePurchase = findViewById(R.id.buttonComplete);

        emptyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CartActivity.this, "Cart emptied!", Toast.LENGTH_LONG).show();
                cart.setPajamasQuantity(0);
                cart.setPantsQuantity(0);
                cart.setGlassesQuantity(0);
                cart.setBeverageQuantity(0);
                mCartDAO.update(cart);
                Intent intent = new Intent(CartActivity.this, LandingPageActivity.class);
                startActivity(intent);
            }
        });

        completePurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CartActivity.this, "Thank you for your purchase!", Toast.LENGTH_LONG).show();
                cart.setPajamasQuantity(0);
                cart.setPantsQuantity(0);
                cart.setGlassesQuantity(0);
                cart.setBeverageQuantity(0);
                mCartDAO.update(cart);
                Intent intent = new Intent(CartActivity.this, LandingPageActivity.class);
                startActivity(intent);
            }
        });


    }

    public double getPriceDouble (String name){
        double price = 0;
        if (name.equals("Programma's Pajamas")){
            price = 19.99;
        } else if (name.equals("Coderoy Pants")) {
            price = 14.99;
        } else if (name.equals("See-Sharp Blue-Lens Glasses")){
            price = 24.99;
        } else if (name.equals("Memory Leak Alkaline Beverage")) {
            price = 4.99;
        } else {
            price = 0;
        }
        return price;
    }

    public String getPriceString (String name){
        String price = "";
        if (name.equals("Programma's Pajamas")){
            price = "$19.99";
        } else if (name.equals("Coderoy Pants")) {
            price = "$14.99";
        } else if (name.equals("See-Sharp Blue-Lens Glasses")){
            price = "$24.99";
        } else if (name.equals("Memory Leak Alkaline Beverage")) {
            price = "$4.99";
        } else {
            price = "";
        }
        return price;
    }
}