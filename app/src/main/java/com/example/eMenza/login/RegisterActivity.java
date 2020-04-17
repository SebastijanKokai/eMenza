package com.example.eMenza.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eMenza.MainActivity;
import com.example.eMenza.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    private Button btnRegister;
    private EditText txtEmail;
    private EditText txtCardNumber;
    private EditText txtPassword;
    private EditText txtPasswordConfirm;
    private TextView txtAlreadyRegistered;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    private boolean isPasswordMatching(String password, String confirmPassword) {
        Pattern pattern = Pattern.compile(password, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(confirmPassword);

        if (!matcher.matches())
            return false;

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtCardNumber = (EditText) findViewById(R.id.txtCardNumber);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPasswordConfirm = (EditText) findViewById(R.id.txtPasswordConfirm);
        txtAlreadyRegistered = (TextView) findViewById(R.id.textAlreadyRegistered);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        // If exists get current user
        if(firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtEmail.getText().toString().trim();
                String cardNumber = txtCardNumber.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String passwordConfirm = txtPasswordConfirm.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    txtEmail.setError("Email is required.");
                    return;
                }
                else if(TextUtils.isEmpty(cardNumber)) {
                    txtCardNumber.setError("Card number is required.");
                    return;
                }
                else if(TextUtils.isEmpty(password)) {
                    txtPassword.setError("Password is required.");
                    return;
                }
                else if(TextUtils.isEmpty(passwordConfirm)) {
                    txtPasswordConfirm.setError("Password Confirm is required.");
                    return;
                }
                else if(!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
                    txtEmail.setError("Email is not valid.");
                    return;
                }
                else if(!isPasswordMatching(password, passwordConfirm)){
                    txtPasswordConfirm.setError("Passwords are not matching.");
                    return;
                }

                if(password.length() < 6) {
                    txtPassword.setError("Password length must be >= 6 characters.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Register the user in firebase

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        txtAlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}
