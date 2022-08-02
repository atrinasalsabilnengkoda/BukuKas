package com.example.financekasbkn;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailCashFlow extends AppCompatActivity {

    //Declare RecyclerView
    private RecyclerView recyclerViewCashFlow;
    //Declare List
    private List<CashFlow> cashFlowList;
    //Declare PatientRecyclerAdapter
    private DetailAdapter detailAdapter;
    //Declare DatabaseHelper
    private DatabaseHelper databaseHelper;

    Button kembaliCashFlowBtn;

    User us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cash_flow);

        Objects.requireNonNull(getSupportActionBar()).setSubtitle("Detail Cash Flow");

        try{

            if(getIntent().hasExtra("User")){
                us = getIntent().getParcelableExtra("User");
            }

            initViews();
            initObjects();

            cashFlowList.clear();
            cashFlowList.addAll(databaseHelper.getAllCashFlow());

            detailAdapter.notifyDataSetChanged();

            kembaliCashFlowBtn.setOnClickListener(v -> finish());
        }catch(Exception e){
            Log.d(" cash Flow activity", e.toString());
        }
    }

    private void initObjects() {
        cashFlowList = new ArrayList<CashFlow>();
        detailAdapter = new DetailAdapter(cashFlowList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCashFlow.setLayoutManager(mLayoutManager);
        recyclerViewCashFlow.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCashFlow.setHasFixedSize(true);
        recyclerViewCashFlow.setAdapter(detailAdapter);

        databaseHelper = new DatabaseHelper(getApplicationContext());

    }

    private void initViews() {
        kembaliCashFlowBtn = findViewById(R.id.buttonKembaliDetailCashFlow);
        recyclerViewCashFlow = findViewById(R.id.DetailsRv);
    }
}