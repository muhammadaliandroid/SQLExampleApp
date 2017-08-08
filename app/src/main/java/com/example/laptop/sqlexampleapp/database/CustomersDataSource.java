package com.example.laptop.sqlexampleapp.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.laptop.sqlexampleapp.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomersDataSource {

    private SQLiteDatabase database;
    private CustomerSQLiteHelper dbHelper;
    private String[] allColumns = {CustomerSQLiteHelper.COLUMN_ID,
            CustomerSQLiteHelper.COLUMN_NAME, CustomerSQLiteHelper.COLUMN_ADDRESS,
            CustomerSQLiteHelper.COLUMN_PHONE_NUMBER};

    public CustomersDataSource (Context context){
        dbHelper = new CustomerSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Customer createCustomer(String name, String address, String phone){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CustomerSQLiteHelper.COLUMN_NAME, name);
        contentValues.put(CustomerSQLiteHelper.COLUMN_ADDRESS, address);
        contentValues.put(CustomerSQLiteHelper.COLUMN_PHONE_NUMBER, phone);
        long insertId = database.insert(CustomerSQLiteHelper.TABLE_CUSTOMERS, null, contentValues);

        Cursor cursor = database.query(CustomerSQLiteHelper.TABLE_CUSTOMERS, allColumns,
                CustomerSQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Customer newCustomer = cursorToCustomer(cursor);
        cursor.close();
        
        return newCustomer;
    }

    public void editCustomer(long id, String name, String address, String phone){

        ContentValues contentValues = new ContentValues();
        contentValues.put(CustomerSQLiteHelper.COLUMN_NAME, name);
        contentValues.put(CustomerSQLiteHelper.COLUMN_ADDRESS, address);
        contentValues.put(CustomerSQLiteHelper.COLUMN_PHONE_NUMBER, phone);

        database.update(CustomerSQLiteHelper.TABLE_CUSTOMERS, contentValues,
                CustomerSQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public void deleteCustomer(Customer customer){
        long id = customer.getId();
        Log.i("Debugging", "Customer with id " + String.valueOf(id) + " deleted");
        database.delete(CustomerSQLiteHelper.TABLE_CUSTOMERS,
                CustomerSQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Customer> getAllCustomers(){

        List<Customer> customers = new ArrayList<>();

        Cursor cursor = database.query(CustomerSQLiteHelper.TABLE_CUSTOMERS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

            Customer customer = cursorToCustomer(cursor);
            customers.add(customer);
            cursor.moveToNext();
        }

        cursor.close();

        return customers;
    }

    private Customer cursorToCustomer(Cursor cursor) {

        Customer customer = new Customer();
        customer.setId(cursor.getLong(0));
        customer.setCustomer_name(cursor.getString(1));
        customer.setCustomer_address(cursor.getString(2));
        customer.setCustomer_phone(cursor.getString(3));

        return customer;
    }
}
