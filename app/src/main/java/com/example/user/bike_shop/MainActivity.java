package com.example.user.bike_shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Boolean addOrUpdate=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // create an obeject of type Button
        final Button searchStoreActivity=(Button)findViewById(R.id.searchStoreActivity);
        // listener to the button and when the button is pressed the user sent to another activity( SearchStoreActivity)
        searchStoreActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search=new Intent(MainActivity.this,SearchStoreActivity.class);
                startActivity(search);
            }
        });

        /**
         *declare the button to update a store
         * when the button is pressed the user is sent to AddOrUpdate activity
         */
        final Button addStore=(Button)findViewById(R.id.add_store);

        addStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add=new Intent(MainActivity.this,AddOrUpdate.class);
                add.putExtra("updateStore","add");
                startActivity(add);


            }
        });

        /**
         *declare the button to update a store
         * when the button is pressed the user is sent to AddOrUpdate activity
         */
        final Button updateStore=(Button)findViewById(R.id.update);

        updateStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update=new Intent(MainActivity.this,AddOrUpdate.class);
                // pass a value to another activity and retrieve it in another Activity using updateStore
                update.putExtra("updateStore","update");
                startActivity(update);

            }
        });

}}
