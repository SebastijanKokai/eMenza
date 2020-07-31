package com.example.eMenza;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eMenza.classes.User;
import com.example.eMenza.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;
import java.net.URL;

import static com.example.eMenza.MainActivity.USER_KEY;

public class ProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private Button btnPayment;
    private Button btnInsertCard;
    private Button btnMenu;
    private User user = null;
    private TextView txtName;
    private TextView txtCollege;
    private TextView txtIndex;
    private TextView txtBirthDate;
    private TextView txtCardNumber;
    private TextView txtDateExpire;
    private TextView txtDateOfIssue;
    private TextView txtCreditCardNumber;
    private TextView txtDateExpiration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setListeners();
        initData();
        initView();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.profile:
                return true;
            case R.id.restaurants:
                return true;
            case R.id.notifications:
                Intent i = new Intent(ProfileActivity.this, NotificationActivity.class);
                i.putExtra(USER_KEY, user);
                startActivity(i);
                return true;
            case R.id.log_out:
                logout();
                return true;
            default:
                return false;
        }
    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.mainmenu);
        popupMenu.show();
    }

    private void initData() {
        if(getIntent() != null) {
            Intent i = getIntent();
            user = i.getParcelableExtra(USER_KEY);
        }
    }

    private void initView() {
        if(user != null) {
            txtName = findViewById(R.id.txtName);
            txtCollege = findViewById(R.id.txtCollege);
            txtIndex = findViewById(R.id.txtIndex);
            txtBirthDate = findViewById(R.id.txtBirthDate);
            txtCardNumber = findViewById(R.id.txtCardNumber);
            txtDateExpire = findViewById(R.id.txtDateExpire);
            txtDateOfIssue = findViewById(R.id.txtDateOfIssue);

            txtName.setText(getString(R.string.profileName) + user.getName() + " " + user.getSurname());
            txtCollege.setText(getString(R.string.profileCollege) + user.getCollege());
            txtIndex.setText(getString(R.string.profileIndex) + user.getIndex());
            txtBirthDate.setText(getString(R.string.profileBirthDate) + user.getDateOfBirth());
            txtCardNumber.setText(getString(R.string.profileCardNumber) + user.getCardNumber());
            txtDateExpire.setText(getString(R.string.profileDateOfExpire) + user.getDateOfExpire());
            txtDateOfIssue.setText(getString(R.string.profileDateOfIssue) + user.getDateOfIssue());

            if(user.getCreditCard() != null) {
                btnInsertCard.setText("Izmeni kreditnu karticu");
                btnPayment.setEnabled(true);
                txtCreditCardNumber = findViewById(R.id.txtProfileCreditCard);
                txtDateExpiration = findViewById(R.id.txtProfileDateExpiration);

                txtCreditCardNumber.setText("Broj kreditne kartice: " + user.getCreditCard().getCardNumber());
                txtDateExpiration.setText("Vazi do: " + user.getCreditCard().getDateExpiration());

                txtCreditCardNumber.setVisibility(View.VISIBLE);
                txtDateExpiration.setVisibility(View.VISIBLE);
            }
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void setListeners() {

        btnPayment = findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, PaymentActivity.class);
                i.putExtra(USER_KEY, user);
                startActivity(i);
            }
        });
        // If credit card doesn't exist
        btnPayment.setEnabled(false);

        btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                i.putExtra(USER_KEY, user);
                startActivity(i);
            }
        });

        btnInsertCard = findViewById(R.id.btnInsertCard);
        btnInsertCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, CreditCardActivity.class);
                i.putExtra(USER_KEY, user);
                startActivity(i);
            }
        });

    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, "src name");
        } catch (Exception e) {
            return null;
        }
    }

}
