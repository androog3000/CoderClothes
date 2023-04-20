package com.daclink.coderclothes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.daclink.coderclothes.db.AppDatabase;
import com.daclink.coderclothes.db.ProductDAO;

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

    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mProductDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getProductDAO();


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



    }
}