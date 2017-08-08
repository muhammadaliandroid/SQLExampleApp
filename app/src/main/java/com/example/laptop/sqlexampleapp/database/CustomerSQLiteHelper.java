package com.example.laptop.sqlexampleapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomerSQLiteHelper extends SQLiteOpenHelper {
    //setting up the table and columns
    public static final String TABLE_CUSTOMERS = "customers";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "customer_name";
    public static final String COLUMN_ADDRESS = "customer_address";
    public static final String COLUMN_PHONE_NUMBER = "customer_phone";

    //setting up the database details
    private static final String DATABASE_NAME = "customers.db";
    private static final int DATABASE_VERSION = 1;

    //SQL statement for the database creation
    private static final String DATABASE_CREATE = "create table " + TABLE_CUSTOMERS
            + "( " + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_ADDRESS + " text not null, "
            + COLUMN_PHONE_NUMBER + " text not null);";

    //SQLite Helper constructor
    public CustomerSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //initialise the database upon the application's start
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    //recreate the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        onCreate(db);
    }
}
