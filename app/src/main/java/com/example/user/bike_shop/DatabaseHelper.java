package com.example.user.bike_shop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "stores"; // the name of our database
    private static final int DB_VERSION = 20; // the version of the database

    // the constructor
    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);


    }

    /*
     a compulsory method because of the superclass - this is called when the DB is created.
     this is turn calls the updateBandDB which creates the database and inserts some bands
      */
    public void onCreate(SQLiteDatabase db) {
        updateStoresDB(db, 0, DB_VERSION);
    }

    // a compulsory method because of the superclass
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        updateStoresDB(db, oldVersion, newVersion);
    }


    private void updateStoresDB(SQLiteDatabase db, int oldVersion, int newVersion) {


        if (oldVersion <7) {
            db.execSQL("DROP TABLE IF EXISTS STORES");
            db.execSQL("CREATE TABLE STORES (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "Store_name TEXT, "
                    + "telephone TEXT, "
                    + "Address TEXT, "
                    + "services TEXT);");
            insertStore(db, "Evans Cycles Manchester Deansgate", "44 161 834 6732" , "01 Deansgate, Manchester M3 3NW, UK", "Whether you are new to cycling or a seasoned veteran, you are sure to find everything you need at our Manchester Deansgate store, along with great service and expertise to help you get the most out of your bike. The store has over 160 bikes on display and can rapidly order for you any one of the thousands of bikes and accessories available online. Alternatively, why not order and collect at a time convenient to you, using our Click & Collect service. As with all Evans Cycles stores, it has a fully-equipped workshop to help with anything from a puncture to a comprehensive service, so whatever your needs our friendly team of experts will keep you rolling.");  //insertStore defined below
            insertStore(db, "Eddy Bike Shop", "(330) 666-2453", "3991 Medina Rd, Akron, OH 44333", "Thinking of buying a new bicycle? One of your first choices > and not necessarily an easy one, will be where to shop. It can be a challenge because there are a myriad of places that sell bicycles. Specialty retailers (like us), giant department stores, and varied online operations.");
            insertStore(db, "Adam Bikes Store", "077263093", "10 Goole st Manchester ", "We sell any kind of bicycle and tools!");
            insertStore(db, "Happy Bike", "077947836", "23 Liverpool st, Manchester", "Any tool for any Bike... we sell and repair bikes.");
        }

    }

    // method to Add a new Store using ContentValues object
    public static void insertStore(SQLiteDatabase db, String name,
                                   String telephone,String address, String services) {
        ContentValues storeValues = new ContentValues();
        storeValues.put("Store_name", name);
        storeValues.put("telephone ", telephone);
        storeValues.put("Address", address);
        storeValues.put("services", services);
        db.insert("STORES", null, storeValues);
    }



    //public method that return a cursor  that contain data from all
    // the fields available in the table STORES where the name is equals to "storeName" variable
    public Cursor selectAllData(String storeName){
         //SQLiteDatabase initialization
         SQLiteDatabase db = this.getWritableDatabase();
         String select ="SELECT * FROM  STORES where Store_name='"+storeName+"'" ;
         Cursor cursor=db.rawQuery(select , null);

        return cursor;
    }

    //method to update a store in the database
    //this method receive five parameters
    public void updateStore(String id, String name,
                            String telephone, String address, String services){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues storeValues = new ContentValues();
        storeValues.put("Store_name", name);
        storeValues.put("telephone ", telephone);
        storeValues.put("Address", address);
        storeValues.put("services", services);
        db.update("STORES", storeValues,"_id="+id, null);

    }

    //method to delete a store
    public void deleteData(String store_id){

        SQLiteDatabase db = this.getWritableDatabase();
        String select ="DELETE FROM  STORES where _id='"+store_id+"'" ;
        db.execSQL(select );


    }
}
