package com.example.laptop.sqlexampleapp;


import android.content.Context;

import com.example.laptop.sqlexampleapp.contracts.MainContract;
import com.example.laptop.sqlexampleapp.database.CustomersDataSource;
import com.example.laptop.sqlexampleapp.model.Customer;
import com.example.laptop.sqlexampleapp.presenter.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PresenterUnitTest {

    @Mock
    MainContract.IMainView iMainView;

    @Mock
    Context context;

    @InjectMocks
    MainPresenter presenter;

    @Mock
    CustomersDataSource dataSource;

    @Mock
    Customer customer;

    @Mock
    List<Customer> customerList;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        iMainView = mock(MainContract.IMainView.class);

        presenter = new MainPresenter();

        customer = new Customer();

        customer.setId(1);
        customer.setCustomer_name("Customer 1");
        customer.setCustomer_address("Address 1");
        customer.setCustomer_phone("Phone 1");

        customerList = new ArrayList<>();

        customerList.add(customer);
    }

    @Test
    public void testPresenterPopulatesDatabase(){

        when(dataSource.createCustomer("Customer 1", "Address 1", "Phone 1")).thenReturn(customer);
        when(dataSource.createCustomer("Customer 2", "Address 2", "Phone 2")).thenReturn(customer);
        when(dataSource.createCustomer("Customer 3", "Address 3", "Phone 3")).thenReturn(customer);
        when(dataSource.createCustomer("Customer 4", "Address 4", "Phone 4")).thenReturn(customer);
        when(dataSource.createCustomer("Customer 5", "Address 5", "Phone 5")).thenReturn(customer);
        when(dataSource.getAllCustomers()).thenReturn(customerList);

        presenter.bind(iMainView);

        presenter.populateDatabase(dataSource);

        Mockito.verify(iMainView).getListData(customerList);
    }

    @Test
    public void testEditScreenIsShown(){

        presenter.bind(iMainView);

        presenter.editCustomer(customer);

        Mockito.verify(iMainView).displayEditFragment(customer);
    }

    @Test
    public void testCustomerDataIsEdited(){

        when(dataSource.createCustomer("Customer 1", "Address 1", "Phone 1")).thenReturn(customer);
        when(dataSource.createCustomer("Customer 2", "Address 2", "Phone 2")).thenReturn(customer);
        when(dataSource.createCustomer("Customer 3", "Address 3", "Phone 3")).thenReturn(customer);
        when(dataSource.createCustomer("Customer 4", "Address 4", "Phone 4")).thenReturn(customer);
        when(dataSource.createCustomer("Customer 5", "Address 5", "Phone 5")).thenReturn(customer);
        when(dataSource.getAllCustomers()).thenReturn(customerList);

        presenter.bind(iMainView);
        presenter.populateDatabase(dataSource);
        presenter.saveCustomer(customer);

        Mockito.verify(iMainView).getUpdatedData(customerList);

    }

    @Test
    public void testCustomerDataIsDeleted(){

        when(dataSource.createCustomer("Customer 1", "Address 1", "Phone 1")).thenReturn(customer);
        when(dataSource.createCustomer("Customer 2", "Address 2", "Phone 2")).thenReturn(customer);
        when(dataSource.createCustomer("Customer 3", "Address 3", "Phone 3")).thenReturn(customer);
        when(dataSource.createCustomer("Customer 4", "Address 4", "Phone 4")).thenReturn(customer);
        when(dataSource.createCustomer("Customer 5", "Address 5", "Phone 5")).thenReturn(customer);
        when(dataSource.getAllCustomers()).thenReturn(customerList);

        presenter.bind(iMainView);
        presenter.populateDatabase(dataSource);
        presenter.deleteCustomer(customer);

        presenter.unbind();

        Mockito.verify(iMainView).getUpdatedData(customerList);
    }

}

