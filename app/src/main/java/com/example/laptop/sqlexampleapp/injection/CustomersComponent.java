package com.example.laptop.sqlexampleapp.injection;

import com.example.laptop.sqlexampleapp.MainActivity;

import dagger.Component;

@Component
public interface CustomersComponent {

    void inject(MainActivity mainActivity);
}
