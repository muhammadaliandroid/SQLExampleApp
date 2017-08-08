package com.example.laptop.sqlexampleapp.presenter;

import com.example.laptop.sqlexampleapp.contracts.MainContract;
import com.example.laptop.sqlexampleapp.database.CustomersDataSource;
import com.example.laptop.sqlexampleapp.model.Customer;

import javax.inject.Inject;

public class MainPresenter implements MainContract.IMainPresenter{

    MainContract.IMainView iMainView;
    CustomersDataSource dataSource;

    @Inject
    public MainPresenter(){}

    @Override
    public void bind(MainContract.IMainView view) {

        this.iMainView = view;

        if (dataSource != null){
            dataSource.open();
        }
    }

    @Override
    public void unbind() {

        iMainView = null;
        dataSource.close();
    }

    @Override
    public void populateDatabase(CustomersDataSource customersDataSource) {

        this.dataSource = customersDataSource;
        dataSource.open();

        int i = 1;

        while (i < 6) {

            dataSource.createCustomer("Customer " + String.valueOf(i),
                    "Address " + String.valueOf(i),
                    "Phone Number " + String.valueOf(i));
            i++;
        }

        iMainView.getListData(dataSource.getAllCustomers());
    }

    @Override
    public void deleteCustomer(Customer customer) {

        dataSource.deleteCustomer(customer);
        iMainView.getUpdatedData(dataSource.getAllCustomers());
    }

    @Override
    public void editCustomer(Customer customer) {
        iMainView.displayEditFragment(customer);
    }

    @Override
    public void saveCustomer(Customer customer) {
        dataSource.editCustomer(customer.getId(), customer.getCustomer_name(),
                customer.getCustomer_address(), customer.getCustomer_phone());

        iMainView.getUpdatedData(dataSource.getAllCustomers());

    }
}
