package com.example.financekasbkn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    // Declare EditText
    EditText usernameEdt;
    EditText passwordEdt;

    // Declare Button
    Button loginBtn;

    TextView directToRegister;

    DatabaseHelper databaseHelper;
    User user = null;

    LinearLayout linearLayoutLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        initViews();

        loginBtn.setOnClickListener(v-> {

            if (usernameEdt.getText() != null && passwordEdt.getText() != null) {

                String userName = usernameEdt.getText().toString();
                String passWord = passwordEdt.getText().toString();

                if (validate(userName, passWord)) {

                    user = new User();

                    //Authenticate user
                    user.setUsername(userName);
                    user.setPassword(passWord);
                    User currentUser = databaseHelper.checkUser(user);

                    if (currentUser != null) {
                        Intent i = new Intent(getApplicationContext(), BerandaActivity.class);
                        i.putExtra("User", currentUser);
                        startActivity(i);
                    } else {
                        Snackbar.make(linearLayoutLogin, "User tidak ada, tolong registrasi terlebih dahulu!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(linearLayoutLogin, "Input dengan benar!", Snackbar.LENGTH_LONG).show();
                }
            }else{
                Snackbar.make(linearLayoutLogin, "Input dengan benar!", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private boolean validate(String username, String password) {

        boolean valid;

        //Handling validation for Email field
        if (username.isEmpty()) {
            valid = false;
            Snackbar.make(linearLayoutLogin, "Username tidak boleh kosong!", Snackbar.LENGTH_LONG).show();
        } else {
            if(username.contains(" ")){
                valid = false;
                Snackbar.make(linearLayoutLogin, "Masukkan tidak boleh memiliki spasi!", Snackbar.LENGTH_LONG).show();
            }else{
                valid = true;
            }
        }

        //Handling validation for Password field
        if (password.isEmpty()) {
            valid = false;
            Snackbar.make(linearLayoutLogin, "Password tidak boleh kosong!", Snackbar.LENGTH_LONG).show();
        } else {
            valid = true;
        }

        return valid;

    }

    private void initViews(){
        usernameEdt = findViewById(R.id.username_login);
        passwordEdt = findViewById(R.id.password_login);

        loginBtn = findViewById(R.id.button_login);
        linearLayoutLogin = findViewById(R.id.loginLinear);
    }
}