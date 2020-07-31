package com.example.eMenza.login;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eMenza.MainActivity;
import com.example.eMenza.R;
import com.example.eMenza.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView txtRegister;
    private ProgressBar progressBar;

    // Database
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;

    private final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);
        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        btnLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(firebaseAuth.getCurrentUser() != null) {
            onAuthSuccess(firebaseAuth.getCurrentUser());
        }
    }

    private void onAuthSuccess(FirebaseUser user) {
        // Read user from firebase and send as putExtra object
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                Toast.makeText(LoginActivity.this, "Welcome " + u.getName(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadUser:onCancelled", error.toException());
            }
        };

        reference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        reference.addListenerForSingleValueEvent(userListener);
    }

    private void signIn() {
        Log.d(TAG, "signIn");

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if(!validateForm(email, password)) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signIn:onComplete" + task.isSuccessful());
                progressBar.setVisibility(View.INVISIBLE);

                if(task.isSuccessful()) {
                    onAuthSuccess((task.getResult().getUser()));
                } else {
                    Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean validateForm(String email, String password) {
        boolean result = true;

        if(TextUtils.isEmpty(email)) {
            txtEmail.setError("Email is required.");
            result = false;
        }
        else if(TextUtils.isEmpty(password)) {
            txtPassword.setError("Password is required.");
            result = false;
        }
        else if(!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
            txtEmail.setError("Email is not valid.");
            result = false;
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnLogin) {
            signIn();
        } else if (i == R.id.txtRegister) {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        }
    }
}
