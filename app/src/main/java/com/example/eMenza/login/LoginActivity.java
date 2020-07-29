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
import com.example.eMenza.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

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

    //    private void writeNewUser(String userId, String name, String surname, String college, String cardNumber, String dateOfBirth, String dateOfIssue, String dateOfExpire,
//                              String email, String password, Integer numberOfBreakfast, Integer numberOfLunch, Integer numberOfDinner) {
//
//        User user = new User(name, surname, college, cardNumber,
//                dateOfBirth, dateOfIssue, dateOfExpire, email, password, numberOfBreakfast, numberOfLunch, numberOfDinner);
//
//        reference.child("users").child(userId).setValue(user);
//    }

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
        reference.addValueEventListener(userListener);
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


    // CODE FOR SAVING

//    final String email = txtEmail.getText().toString().trim();
//    final String password = txtPassword.getText().toString().trim();
//
//        if(TextUtils.isEmpty(email)) {
//        txtEmail.setError("Email is required.");
//        return;
//    }
//        else if(TextUtils.isEmpty(password)) {
//        txtPassword.setError("Password is required.");
//        return;
//    }
//        else if(!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
//        txtEmail.setError("Email is not valid.");
//        return;
//    }
//
//    // Retrieve all data for the user from the firebase
//    // Authenticate the user
//
//    reference = FirebaseDatabase.getInstance().getReference("users");
//        reference.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            User user = snapshot.getValue(User.class);
//            Log.v("Login", user.getEmail());
//            Log.v("Login", user.getPassword());
//            Log.v("Login", user.getNumberOfBreakfast().toString());
//
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//            Log.e("Login", "Jbg druze");
//        }
//    });
//
//
//        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//        @Override
//        public void onComplete(@NonNull Task<AuthResult> task) {
//            if(task.isSuccessful()) {
//                progressBar.setVisibility(View.VISIBLE);
//                Toast.makeText(LoginActivity.this, "Logged in.", Toast.LENGTH_SHORT).show();
//
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if(user != null) {
//
//                } else {
//
//                }
//
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            } else {
//                progressBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    });
}
