package com.example.user.bike_shop;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created by user on 14/07/2017.
 */

public class SearchStoreActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    ListView list;
    String store_name,store_id;
    CursorAdapter listAdapter;
    DatabaseHelper dataB;
    RelativeLayout rl;
    PopupWindow pwindo;
    Boolean alpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_stores);

        //create an object of type DatabaseHelper
        dataB= new DatabaseHelper(this);


        rl=(RelativeLayout) findViewById(R.id.list_root);




        storeList();
        listview_Item_Listener();
        deleteItem();


    }

    private void storeList() {
        list=(ListView)findViewById(R.id.store_list);

        try {
         SQLiteOpenHelper bandDatabaseHelper = new DatabaseHelper(this);
         db = bandDatabaseHelper.getReadableDatabase();


         //The cursor  allows direct access to the names and description of the
         cursor = db.query("STORES",
         new String[]{"_id","Store_name","telephone"},
         null, null, null, null, null);

          /**
           * CursorAdapter is a fast and simple way to display SQLite data in a ListView.
           *
           * */

         listAdapter = new SimpleCursorAdapter(this,
         R.layout.list_row_layout,
         cursor,
         new String[]{"Store_name","telephone"},
         new int[]{R.id.nameOfStore,R.id.telephoneOfStore},
         0);
         list.setAdapter(listAdapter);

         } catch(SQLiteException e) {
         Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
         toast.show();
         }


    }

    //
    private void listview_Item_Listener(){
        /**
         * listener to the listView
         * when the user click on an item in the listView, the name of the store clicked
         * is sent to another activity using the method putExtra
         * and a the name of the store is shown using a toast
         */
       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                //Returns the value of the requested column(name) as a String.
                store_name =cursor.getString(1);
                Intent i=new Intent(SearchStoreActivity.this,Store_Info.class);
                i.putExtra("StoreName", store_name);
                Toast.makeText(SearchStoreActivity.this,""+ store_name,Toast.LENGTH_SHORT).show();
                startActivity(i);

           }
       });
    }


    private void deleteItem(){

        /**
         * when the user long press an item in the listView
         * a popup menu appears
         * the variavel store_id is used to store the _id of the Store clicked and then used to
         * delete the store when the used click the delete button in the showPopup method
         */
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub
                store_id =cursor.getString(0);
                showPopup();
                return true;
            }

        });
    }



    private void showPopup() {


        // Inflate the popup_layout.xml
        LayoutInflater inflater = (LayoutInflater) SearchStoreActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.pop_up,
                (ViewGroup) findViewById(R.id.popup));



        // Creating the PopupWindow
        pwindo = new PopupWindow(layout, 700, 470, true);

        // Displaying the popup at the specified location
        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //reference to the object DatabaseHelper allows the use of a method of that class
                dataB.deleteData(store_id);
                //by calling this method again, the data in the listview is refreshed
                storeList();
                pwindo.dismiss();


            }
        });





    }

}
