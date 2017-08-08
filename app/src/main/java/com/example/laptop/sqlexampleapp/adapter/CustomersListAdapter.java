package com.example.laptop.sqlexampleapp.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.laptop.sqlexampleapp.R;
import com.example.laptop.sqlexampleapp.model.Customer;
import com.example.laptop.sqlexampleapp.presenter.MainPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomersListAdapter extends RecyclerView.Adapter<CustomersListAdapter.CustomersViewHolder>{

    private List<Customer> listData;
    private int row_customers_list;
    private MainPresenter presenter;

    public CustomersListAdapter(MainPresenter presenter, List<Customer> listData,
                                int row_customers_list) {

        this.listData = listData;
        this.row_customers_list = row_customers_list;
        this.presenter = presenter;
    }

    @Override
    public CustomersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(row_customers_list, parent, false);

        return new CustomersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomersViewHolder holder, final int position) {

        holder.tvCustomerName.setText(listData.get(position).getCustomer_name());
        holder.tvCustomerAddress.setText(listData.get(position).getCustomer_address());
        holder.tvCustomerPhone.setText(listData.get(position).getCustomer_phone());

        holder.btnEditCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.editCustomer(listData.get(position));
            }
        });

        holder.btnDeleteCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.deleteCustomer(listData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class CustomersViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvCustomerName) TextView tvCustomerName;
        @BindView(R.id.tvCustomerAddress) TextView tvCustomerAddress;
        @BindView(R.id.tvCustomerPhone) TextView tvCustomerPhone;
        @BindView(R.id.btnEditCutomer) Button btnEditCustomer;
        @BindView(R.id.btnDeleteCustomer) Button btnDeleteCustomer;


        public CustomersViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(itemView);
        }
    }


}
