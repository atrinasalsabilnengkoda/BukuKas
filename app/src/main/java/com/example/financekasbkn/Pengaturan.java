package com.example.financekasbkn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class Pengaturan extends AppCompatActivity {

    EditText passwordLama;
    EditText passwordBaru;
    LinearLayout linearLayoutPengaturan;
    Button simpanPengaturanBtn, kembaliPengaturanBtn;

    User us;

    DatabaseHelper databaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        Objects.requireNonNull(getSupportActionBar()).setSubtitle("Tambah Pengaturan");

        if(getIntent().hasExtra("User")){
            us = getIntent().getParcelableExtra("User");
        }

        initViews();
        initObjects();

    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(getApplicationContext());

        simpanPengaturanBtn.setOnClickListener(v->{

            if(validate(passwordLama.getText().toString(), passwordBaru.getText().toString())){

                boolean checkOldPass = databaseHelper.checkOldPassword(passwordLama.getText().toString());
                if(checkOldPass){
                    us = databaseHelper.updatePassword(us, passwordBaru.getText().toString());

                    Toast.makeText(getApplicationContext(),"Password berhasil diupdate!", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplicationContext(), Pengaturan.class);
                    i.putExtra("User", us);
                    startActivity(i);

                }else{
                    Toast.makeText(getApplicationContext(),"Inputan password saat ini salah!", Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(getApplicationContext(),"Semua inputan tidak sesuai!", Toast.LENGTH_LONG).show();
            }
        });


        kembaliPengaturanBtn.setOnClickListener(v->{
            Intent i = new Intent(getApplicationContext(), BerandaActivity.class);
            i.putExtra("User", us);
            startActivity(i);
            finish();
        });
    }

    private void initViews() {

        passwordLama = findViewById(R.id.editTextOldPassword);
        passwordBaru = findViewById(R.id.editTextNewPassword);

        simpanPengaturanBtn = findViewById(R.id.buttonSimpanPengaturan);
        kembaliPengaturanBtn = findViewById(R.id.buttonKembaliPengaturan);
        linearLayoutPengaturan = findViewById(R.id.linearPengaturan);

    }

    private boolean validate(String oldPass, String newPass) {

        boolean valid;

        //Handling validation for Email field
        if (oldPass.isEmpty()) {
            valid = false;
            Snackbar.make(linearLayoutPengaturan, "Password saat ini tidak boleh kosong!", Snackbar.LENGTH_LONG).show();
        } else {
            valid = true;
        }

        //Handling validation for Password field
        if (newPass.isEmpty()) {
            valid = false;
            Snackbar.make(linearLayoutPengaturan, "Password baru tidak boleh kosong!", Snackbar.LENGTH_LONG).show();
        } else {
            valid = true;
        }
        return valid;
    }
}