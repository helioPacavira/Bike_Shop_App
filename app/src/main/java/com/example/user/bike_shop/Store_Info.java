package com.example.user.bike_shop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by user on 20/07/2017.
 */

public class Store_Info extends AppCompatActivity {

    Intent intent;
    Bundle bd;
    String getName;
    private SQLiteDatabase db;
    Cursor cursor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_information);

        //create an object of type DatabaseHelper
        DatabaseHelper dataB= new DatabaseHelper(this);


        intent = getIntent();
        bd = intent.getExtras();
        // retrieve the data using StoreName
        getName = (String) bd.get("StoreName");

        //cursor that contain the information of the store clicked
        cursor=dataB.selectAllData(getName);
        cursor.moveToNext();
        declareViews();
        }

    private void declareViews() {

        //declare the textViews
        TextView storeName=(TextView)findViewById(R.id.storeName);
        TextView storeAddress=(TextView)findViewById(R.id.store_address);
        TextView storeTelephone=(TextView)findViewById(R.id.store_telephone);
        TextView storeServices=(TextView)findViewById(R.id.store_services);


        //create variavels to store the data in the cursor
        String name= cursor.getString(1).toString();
        String address= cursor.getString(2).toString();
        String telephone= cursor.getString(3).toString();
        String services= cursor.getString(4).toString();

        //set the TextView with the values in the variavels with the data from the cursor
        storeName.setText(name);
        storeAddress.setText(address);
        storeTelephone.setText(telephone);
        storeServices.setText(services);


    }

}
