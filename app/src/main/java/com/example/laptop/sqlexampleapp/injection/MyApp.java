package com.example.laptop.sqlexampleapp.injection;


import android.app.Application;

public class MyApp extends Application {

    CustomersComponent customersComponent;

    public CustomersComponent getCustomersComponent(){

        return customersComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        customersComponent = DaggerCustomersComponent.create();
    }
}
