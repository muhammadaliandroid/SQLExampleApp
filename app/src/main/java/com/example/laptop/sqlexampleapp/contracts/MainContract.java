package com.example.laptop.sqlexampleapp.contracts;


import com.example.laptop.sqlexampleapp.database.CustomersDataSource;
import com.example.laptop.sqlexampleapp.model.Customer;

import java.util.List;

public interface MainContract {

    interface IMainPresenter{

        void bind (IMainView view);
        void unbind();
        void populateDatabase(CustomersDataSource customersDataSource);
        void deleteCustomer(Customer customer);
        void editCustomer(Customer customer);
        void saveCustomer(Customer customer);
    }

    interface IMainView{

        void getListData(List<Customer> customers);
        void getUpdatedData(List<Customer> customers);
        void displayEditFragment(Customer customer);
    }
}
