package com.example.financekasbkn;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class Pengeluaran extends AppCompatActivity {

    EditText tglPengeluaranEdt, nominalPengeluaranEdt, keteranganPengeluaranEdt;
    Button kembaliPengeluaran, simpanPengeluaran;


    final Calendar myCalendarPengeluaran = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datePengeluaran = null;

    DatabaseHelper databaseHelper = null;
    CashFlow cf;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);

        Objects.requireNonNull(getSupportActionBar()).setSubtitle("Tambah Pengeluaran");

        if(getIntent().hasExtra("User")){
            user = getIntent().getParcelableExtra("User");
        }

        initViews();
        initObjects();


    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(getApplicationContext());

        datePengeluaran = (view, year, month, dayOfMonth) -> {
            myCalendarPengeluaran.set(Calendar.YEAR, year);
            myCalendarPengeluaran.set(Calendar.MONTH, month);
            myCalendarPengeluaran.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        tglPengeluaranEdt.setOnClickListener(v -> {
            new DatePickerDialog(Pengeluaran.this, datePengeluaran, myCalendarPengeluaran.get(Calendar.YEAR),myCalendarPengeluaran.get(Calendar.MONTH),myCalendarPengeluaran.get(Calendar.DAY_OF_MONTH)).show();
        });

        kembaliPengeluaran.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), BerandaActivity.class));
            finish();
        });


        simpanPengeluaran.setOnClickListener(v ->{

            cf = new CashFlow();

            cf.setNominal(Integer.parseInt(nominalPengeluaranEdt.getText().toString()));
            cf.setTanggal(String.valueOf(myCalendarPengeluaran.get(Calendar.DAY_OF_MONTH)));
            cf.setKeterangan(keteranganPengeluaranEdt.getText().toString());
            cf.setBulan(String.valueOf(myCalendarPengeluaran.get(Calendar.MONTH)+1));
            cf.setTahun(String.valueOf(myCalendarPengeluaran.get(Calendar.YEAR)));
            cf.setTipe("pengeluaran");

            databaseHelper.addCashFlow(cf);
            Toast.makeText(getApplicationContext(),"Pengeluaran berhasil diinput!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Pengeluaran.class));
            finish();
        });
    }

    private void updateLabel() {
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        tglPengeluaranEdt.setText(dateFormat.format(myCalendarPengeluaran.getTime()));
    }

    private void initViews(){
        tglPengeluaranEdt = findViewById(R.id.editTextDatePengeluaran);
        keteranganPengeluaranEdt = findViewById(R.id.editTextTextKeteranganPengeluaran);
        nominalPengeluaranEdt = findViewById(R.id.editTextNominalPengeluaran);

        kembaliPengeluaran = findViewById(R.id.buttonKembaliPengeluaran);
        simpanPengeluaran = findViewById(R.id.buttonSimpanPengeluaran);
    }
}