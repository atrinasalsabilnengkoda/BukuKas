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

public class Pemasukan extends AppCompatActivity {

    final Calendar myCalendarPemasukan = Calendar.getInstance();
    EditText tglPemasukanEdt, nominalPemasukanEdt, keteranganPemasukanEdt;

    Button simpanpemasukan, kembaliPemasukan;

    DatePickerDialog.OnDateSetListener datePemasukan = null;

    DatabaseHelper databaseHelper = null;
    CashFlow cf;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasukan);

        if (getIntent().hasExtra("User")) {
            user = getIntent().getParcelableExtra("User");
        }

        Objects.requireNonNull(getSupportActionBar()).setSubtitle("Tambah Pemasukan");

        initViews();
        initObjects();

    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(getApplicationContext());

        datePemasukan = (view, year, month, dayOfMonth) -> {
            myCalendarPemasukan.set(Calendar.YEAR, year);
            myCalendarPemasukan.set(Calendar.MONTH, month);
            myCalendarPemasukan.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        tglPemasukanEdt.setOnClickListener(v -> {
            new DatePickerDialog(Pemasukan.this, datePemasukan, myCalendarPemasukan.get(Calendar.YEAR), myCalendarPemasukan.get(Calendar.MONTH), myCalendarPemasukan.get(Calendar.DAY_OF_MONTH)).show();
        });

        kembaliPemasukan.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), BerandaActivity.class));
            finish();
        });


        simpanpemasukan.setOnClickListener(v -> {

            cf = new CashFlow();

            cf.setNominal(Integer.parseInt(nominalPemasukanEdt.getText().toString()));
            cf.setKeterangan(keteranganPemasukanEdt.getText().toString());
            cf.setTanggal(String.valueOf(myCalendarPemasukan.get(Calendar.DAY_OF_MONTH)));
            cf.setBulan(String.valueOf(myCalendarPemasukan.get(Calendar.MONTH) + 1));
            cf.setTahun(String.valueOf(myCalendarPemasukan.get(Calendar.YEAR)));
            cf.setTipe("pemasukan");

            databaseHelper.addCashFlow(cf);
            Toast.makeText(getApplicationContext(), "Pemasukan berhasil diinput!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Pemasukan.class));
            finish();
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        tglPemasukanEdt.setText(dateFormat.format(myCalendarPemasukan.getTime()));
    }

    private void initViews() {
        tglPemasukanEdt = findViewById(R.id.editTextDatePemasukan);
        nominalPemasukanEdt = findViewById(R.id.editTextNominalPemasukan);
        keteranganPemasukanEdt = findViewById(R.id.editTextTextKeteranganPemasukan);

        kembaliPemasukan = findViewById(R.id.buttonKembaliPemasukan);
        simpanpemasukan = findViewById(R.id.buttonSimpanPemasukan);
    }
}