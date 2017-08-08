package com.example.laptop.sqlexampleapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop.sqlexampleapp.R;
import com.example.laptop.sqlexampleapp.adapter.CustomersListAdapter;
import com.example.laptop.sqlexampleapp.model.Customer;
import com.example.laptop.sqlexampleapp.presenter.MainPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    MainPresenter presenter;
    Unbinder unbinder;
    List<Customer> customerData;

    @BindView(R.id.rvCustomers) RecyclerView rvCustomers;


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRetainInstance(true);

        unbinder = ButterKnife.bind(this, view);

        initialiseRecyclerView(view.getContext());
        setupRecyclerView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
        presenter = null;
    }

    private void initialiseRecyclerView(Context context){

        rvCustomers.setLayoutManager(new LinearLayoutManager(context));
    }

    private void setupRecyclerView(){

        rvCustomers.setAdapter(new CustomersListAdapter(presenter, customerData, R.layout.row_customers_list));
    }

    public void receiveCustomerData(List<Customer> customers, MainPresenter presenter){

        Log.i("Debugging", "Got customers, " + customers.get(0).getCustomer_name());
        this.customerData = customers;
        this.presenter = presenter;
    }
}
