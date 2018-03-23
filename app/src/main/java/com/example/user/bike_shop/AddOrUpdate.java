package com.example.user.bike_shop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14/07/2017.
 */

public class AddOrUpdate extends AppCompatActivity {

    private SQLiteDatabase db;
    DatabaseHelper dataB;
    private Cursor cursor;

    TextView address, telephone, store_name, servicesOffered;
    ArrayList allDataToText;
    String storeSelected;
    ContentValues values;
    Spinner sp;
    String addOrUpDate;
    String store_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);


        SQLiteOpenHelper bandDatabaseHelper = new DatabaseHelper(this);
        db = bandDatabaseHelper.getReadableDatabase();

        dataB = new DatabaseHelper(this);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        // retrieve the data using updateStore
        addOrUpDate = (String) bd.get("updateStore");

        TextView title = (TextView) findViewById(R.id.title);

        //declare the spinner
        sp = (Spinner) findViewById(R.id.spinner);

        /**
         * if data received from the other activity  is equals to "add"
         * the textView title is set to  "ADD NEW STORE"
         * and the spinner is desabled
         */
        if (addOrUpDate.equals("add")) {
            title.setText("ADD NEW STORE");
            sp.removeViewInLayout(sp);
            sp.setEnabled(false);

        } else {
            title.setText("UPDATE STORE");
            addDataToSpinner();
        }

        declareView();
        buttons();


    }

    private void declareView() {
        store_name = (TextView) findViewById(R.id.store_name);
        telephone = (TextView) findViewById(R.id.telephone);
        address = (TextView) findViewById(R.id.store_address);
        servicesOffered = (TextView) findViewById(R.id.servicesOffered);
    }

    private void addDataToSpinner() {
        /**
         * in this method, data is added to the spinner using a cursor  and a arrayList that will contain the names of the all store in the database
         */
        cursor = db.query("STORES",
                new String[]{"Store_name"},
                null, null, null, null, null);

        List<String> list = new ArrayList<String>();
        list.add("Select store...");
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);

        /**
         * when an item in the spinner is clicked,
         * the storeSelected variavel store it value
         *
         */
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //get the value of the item that was clicked in the spinner
                storeSelected = String.valueOf(sp.getItemAtPosition(position));
                setFields();

                Toast.makeText(AddOrUpdate.this, storeSelected,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    private void setFields() {




        //cursor that contain the data relative to the store clicked in the spinner
        //this cursor will be used to set the texts in the textViews
        Cursor cursor = dataB.selectAllData(storeSelected);
        while (cursor.moveToNext()) {
            store_name.setText(cursor.getString(1));
            telephone.setText(cursor.getString(2));
            address.setText(cursor.getString(3));
            servicesOffered.setText(cursor.getString(4));


            store_ID = cursor.getString(0);

        }
    }

    public void buttons() {
        Button addStore = (Button) findViewById(R.id.submit);

        addStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStore();
            }
        });

    }

    public void saveStore() {

        /**
         *if the user clicked in the main activity to add a new store,
         * when clicked the save button
         * the data in the the textView, will be used to create a new store , by adding those details o the database
         * else a store is updated
         */

        if (addOrUpDate.equals("add")) {
            dataB.insertStore(db, store_name.getText().toString(), telephone.getText().toString(), address.getText().toString(),
                    servicesOffered.getText().toString());

        } else {
            dataB.updateStore(store_ID, store_name.getText().toString(), telephone.getText().toString(), address.getText().toString(),
                    servicesOffered.getText().toString());

        }
        Toast.makeText(AddOrUpdate.this, "SAVED",
                Toast.LENGTH_SHORT).show();

    }


}
