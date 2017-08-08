package com.example.laptop.sqlexampleapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.laptop.sqlexampleapp.R;
import com.example.laptop.sqlexampleapp.model.Customer;
import com.example.laptop.sqlexampleapp.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {

    Customer customerData;
    MainPresenter presenter;
    Unbinder unbinder;

    @BindView(R.id.etCustomerName) EditText etCustomerName;
    @BindView(R.id.etCustomerAddress) EditText etCustomerAddress;
    @BindView(R.id.etCustomerPhone) EditText etCustomerPhone;
    @BindView(R.id.btnSaveCustomer) Button btnSaveCustomer;


    public EditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRetainInstance(true);

        unbinder = ButterKnife.bind(this, view);
        setupViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
        presenter = null;
    }

    private void setupViews(){

        etCustomerName.setText(customerData.getCustomer_name());
        etCustomerAddress.setText(customerData.getCustomer_address());
        etCustomerPhone.setText(customerData.getCustomer_phone());
        btnSaveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerData.setCustomer_name(etCustomerName.getText().toString());
                customerData.setCustomer_address(etCustomerAddress.getText().toString());
                customerData.setCustomer_phone(etCustomerPhone.getText().toString());

                presenter.saveCustomer(customerData);
            }
        });
    }

    public void receiveCustomer(Customer customer, MainPresenter presenter){

        this.customerData = customer;
        this.presenter = presenter;
        Log.i("Debugging", "Edit displayed");
    }
}
