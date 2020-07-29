package com.example.eMenza.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eMenza.MainActivity;
import com.example.eMenza.R;
import com.example.eMenza.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";

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

    // Database
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;

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
        reference = FirebaseDatabase.getInstance().getReference();

        btnRegister.setOnClickListener(this);
        txtAlreadyRegistered.setOnClickListener(this);
    }

    private void signUp() {
        Log.d(TAG, "signUp");

        final String email = txtEmail.getText().toString().trim();
        final String cardNumber = txtCardNumber.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();
        String passwordConfirm = txtPasswordConfirm.getText().toString().trim();

        if (!validateForm(email, cardNumber, password, passwordConfirm)) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                progressBar.setVisibility(View.INVISIBLE);

                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), cardNumber, email, password);
                } else {
                    Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onAuthSuccess(FirebaseUser user, String cardNumber, String email, String password) {
        String userId = user.getUid();

        // @TODO
        // ENCRYPT USER PASSWORD AND CREDIT CARD INFORMATION

        // Create an user object
        // Hardcoded values are from the menza database that I get with cardNumber value
        User u = new User("Pavle", "Pavlovic", "FTN", cardNumber,
                "1999/10/10", "2019/10/10", "2020/10/10", email, password, "IT30/2017");

        reference.child("users").child(userId).setValue(u);
        Toast.makeText(RegisterActivity.this, "Welcome " + u.getName(), Toast.LENGTH_SHORT).show();

        // Go to MainActivity
        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
        i.putExtra("user", u);
        startActivity(i);
        finish();
    }

    private boolean validateForm(String email, String cardNumber, String password, String passwordConfirm) {
        boolean result = true;

        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Email is required.");
            result = false;
        } else if (TextUtils.isEmpty(cardNumber)) {
            txtCardNumber.setError("Card number is required.");
            result = false;
        } else if (TextUtils.isEmpty(password)) {
            txtPassword.setError("Password is required.");
            result = false;
        } else if (TextUtils.isEmpty(passwordConfirm)) {
            txtPasswordConfirm.setError("Password Confirm is required.");
            result = false;
        } else if (!EMAIL_ADDRESS_PATTERN.matcher(email).matches()) {
            txtEmail.setError("Email is not valid.");
            result = false;
        } else if (!isPasswordMatching(password, passwordConfirm)) {
            txtPasswordConfirm.setError("Passwords are not matching.");
            result = false;
        } else if (password.length() < 6) {
            txtPassword.setError("Password length must be >= 6 characters.");
            result = false;
        }

        return result;
    }

    private boolean isPasswordMatching(String password, String confirmPassword) {
        Pattern pattern = Pattern.compile(password, Pattern.CASE_INSENSITIVE); // How to make case sensitive???
        Matcher matcher = pattern.matcher(confirmPassword);

        if (!matcher.matches())
            return false;
        return true;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnRegister) {
            signUp();
        } else if (i == R.id.textAlreadyRegistered) {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        }
    }
}
