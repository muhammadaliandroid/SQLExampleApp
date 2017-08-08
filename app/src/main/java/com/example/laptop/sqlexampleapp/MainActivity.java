package com.example.laptop.sqlexampleapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.laptop.sqlexampleapp.contracts.MainContract;
import com.example.laptop.sqlexampleapp.database.CustomersDataSource;
import com.example.laptop.sqlexampleapp.fragments.EditFragment;
import com.example.laptop.sqlexampleapp.fragments.ListFragment;
import com.example.laptop.sqlexampleapp.injection.MyApp;
import com.example.laptop.sqlexampleapp.model.Customer;
import com.example.laptop.sqlexampleapp.presenter.MainPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements MainContract.IMainView{

    @Inject MainPresenter presenter;

    private boolean presenter_check;
    private boolean edit_check;
    private boolean large_screen;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private EditFragment editFragment;
    private ListFragment listFragment;
    List<Customer> customerData;
    Unbinder unbinder;

    @BindView(R.id.fragment_container_2) RelativeLayout rlBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApp)getApplication()).getCustomersComponent().inject(this);

        unbinder = ButterKnife.bind(this);
        presenter.bind(this);
        presenter_check = false;
        edit_check = true;

        if (((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) ||
                ((getResources().getConfiguration().screenLayout &
                        Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE )){

            large_screen = true;
        }

        else {
            large_screen = false;
            rlBackground.setVisibility(GONE);
        }

        fragmentManager = getSupportFragmentManager();

        CustomersDataSource customersDataSource = new CustomersDataSource(getApplicationContext());

        presenter.populateDatabase(customersDataSource);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (presenter_check) {
            presenter.bind(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.unbind();
        presenter_check = true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

    @Override
    public void getListData(List<Customer> customers) {

        this.customerData = customers;

        fragmentTransaction = fragmentManager.beginTransaction();

        this.listFragment = new ListFragment();

        if (large_screen){
            fragmentTransaction.add(R.id.fragment_container_1_big, listFragment, "LIST_FRAGMENT");
        }

        else {
            fragmentTransaction.add(R.id.fragment_container_1, listFragment, "LIST_FRAGMENT");
        }
        fragmentTransaction.commit();

        listFragment.receiveCustomerData(customerData, presenter);
    }

    @Override
    public void getUpdatedData(List<Customer> customers) {

        this.customerData = customers;

        fragmentTransaction = fragmentManager.beginTransaction();

        if (edit_check){
            fragmentTransaction.remove(listFragment);
        }

        else if (large_screen){

            fragmentTransaction.remove(listFragment);
            fragmentTransaction.remove(editFragment);
            edit_check = true;
        }

        else {
            fragmentTransaction.remove(editFragment);
            edit_check = true;
        }

        if (customerData.size() == 0){

            Toast.makeText(this, "No Entries To Show", Toast.LENGTH_LONG).show();

            fragmentTransaction.commit();

            return;
        }

        this.listFragment = new ListFragment();

        if (large_screen) {
            fragmentTransaction.add(R.id.fragment_container_1_big, listFragment, "LIST_FRAGMENT");
        }

        else {
            fragmentTransaction.add(R.id.fragment_container_1, listFragment, "LIST_FRAGMENT");
        }
        fragmentTransaction.commit();

        listFragment.receiveCustomerData(customerData, presenter);
    }

    @Override
    public void displayEditFragment(Customer customer) {

        fragmentTransaction = fragmentManager.beginTransaction();



        if (large_screen){

            if (!edit_check){
                fragmentTransaction.remove(editFragment);
            }

            this.editFragment = new EditFragment();

            fragmentTransaction.add(R.id.fragment_container_2, editFragment, "EDIT_FRAGMENT");

        }

        else{

            this.editFragment = new EditFragment();

            fragmentTransaction.remove(listFragment);

            fragmentTransaction.add(R.id.fragment_container_1, editFragment, "EDIT_FRAGMENT");
        }

        fragmentTransaction.commit();

        edit_check = false;

        editFragment.receiveCustomer(customer, presenter);
    }
}
