package com.example.eMenza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eMenza.classes.CreditCard;
import com.example.eMenza.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.eMenza.MainActivity.USER_KEY;

public class CreditCardActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtCreditCardNumber;
    private EditText txtDateExpiration;
    private EditText txtCVC;
    private Button btnConfirm;

    // Database
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;

    // User
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        txtCreditCardNumber = findViewById(R.id.txtCreditCardNumber);
        txtDateExpiration = findViewById(R.id.txtDateExpiration);
        txtCVC = findViewById(R.id.txtCVC);
        btnConfirm = findViewById(R.id.btnConfirmCreditCard);

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        btnConfirm.setOnClickListener(this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        initData();
    }

    private void insertCard() {

        String creditCardNumber = txtCreditCardNumber.getText().toString().trim();
        String dateExpiration = txtDateExpiration.getText().toString().trim();
        String cvc = txtCVC.getText().toString().trim();

        if (!validateForm(creditCardNumber, dateExpiration, cvc)) {
            return;
        }

        CreditCard creditCard = new CreditCard(creditCardNumber, dateExpiration, cvc);
        String userId = firebaseAuth.getCurrentUser().getUid();

        reference.child("users").child(userId).child("creditCard").setValue(creditCard);
        user.setCreditCard(creditCard);

        // Go to ProfileActivity
        Toast.makeText(CreditCardActivity.this, "Credit card inserted.", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
        i.putExtra(USER_KEY, user);
        startActivity(i);
        finish();
        // VRATI ME NAZAD NA MAIN ACTIVITY
    }

    private boolean validateForm(String creditCardNumber, String dateExpiration, String cvc) {
        // Check if the inserted information is valid
        return true;
    }

    private void initData() {
        if(getIntent() != null) {
            user = getIntent().getParcelableExtra(USER_KEY);
        }
    }

    @Override
    public void onClick(View v) {
        insertCard();
    }
}
