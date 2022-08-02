package com.example.financekasbkn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BerandaActivity extends AppCompatActivity {

    ImageButton pengaturanIcon;
    ImageButton pemasukanIcon;
    ImageButton pengeluaranIcon;
    ImageButton detailIcon;

    TextView pemasukanBulanIniTotal;
    TextView pengeluaranBulanIniTotal;

    DatabaseHelper databaseHelper = null;
    User us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        DateFormat dateFormat = new SimpleDateFormat("MM", Locale.US);
        Date date = new Date();

        String currentMonth = dateFormat.format(date);

        if(getIntent().hasExtra("User")){
            us = getIntent().getParcelableExtra("User");
        }

        initViews();
        initObjects(currentMonth);

    }

    private void initViews(){
        pengaturanIcon = findViewById(R.id.settings);
        pemasukanIcon = findViewById(R.id.addIncomeButton);
        pengeluaranIcon = findViewById(R.id.addExpenseButton);
        detailIcon = findViewById(R.id.detailsCashflow);

        pemasukanBulanIniTotal = findViewById(R.id.textPemasukan);
        pengeluaranBulanIniTotal = findViewById(R.id.textPengeluaran);
    }

    private void initObjects(String currentMonth){

        databaseHelper = new DatabaseHelper(getApplicationContext());

        pengaturanIcon.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Pengaturan.class);
            i.putExtra("User", us);
            startActivity(i);
        });

        pemasukanIcon.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Pemasukan.class);
            i.putExtra("User", us);
            startActivity(i);
        });

        pengeluaranIcon.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Pengeluaran.class);
            i.putExtra("User", us);
            startActivity(i);
        });

        detailIcon.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), DetailCashFlow.class);
            i.putExtra("User", us);
            startActivity(i);
        });

        if(databaseHelper.getNominalPemasukanByMonth(currentMonth) != null){

            int pemasukan = 0;
            List<CashFlow> list = databaseHelper.getNominalPemasukanByMonth(currentMonth);

            for (int i = 0; i < list.size(); i++) {
                pemasukan += list.get(i).getNominal();
            }
            pemasukanBulanIniTotal.setText("Pemasukan: Rp. "+ pemasukan);
        }else{
            pemasukanBulanIniTotal.setText("Pemasukan: Rp. 0");
        }

        if(databaseHelper.getNominalPengeluaranByMonth(currentMonth) != null) {

            int pengeluaran = 0;
            List<CashFlow> list = databaseHelper.getNominalPengeluaranByMonth(currentMonth);

            for (int i = 0; i < list.size(); i++) {
                pengeluaran += list.get(i).getNominal();
            }

            pengeluaranBulanIniTotal.setText("Pengeluaran: Rp. "+pengeluaran);

        }else{
            pengeluaranBulanIniTotal.setText("Pengeluaran: Rp. 0");
        }

    }
}