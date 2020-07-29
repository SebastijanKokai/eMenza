package com.example.eMenza;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eMenza.login.LoginActivity;
import com.example.eMenza.login.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    User user = null;

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
                Intent i = new Intent(ProfileActivity.this, Notifications.class);
                i.putExtra("user", user);
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
            user = (User)i.getParcelableExtra("user");
        }
    }

    private void initView() {
        if(user != null) {
            TextView txtName = findViewById(R.id.txtName);
            TextView txtCollege = findViewById(R.id.txtCollege);
            TextView txtIndex = findViewById(R.id.txtIndex);
            TextView txtBirthDate = findViewById(R.id.txtBirthDate);
            TextView txtCardNumber = findViewById(R.id.txtCardNumber);
            TextView txtDateExpire = findViewById(R.id.txtDateExpire);
            TextView txtDateOfIssue = findViewById(R.id.txtDateOfIssue);

            txtName.setText(getString(R.string.profileName) + user.getName() + " " + user.getSurname());
            txtCollege.setText(getString(R.string.profileCollege) + user.getCollege());
            txtIndex.setText(getString(R.string.profileIndex) + user.getIndex());
            txtBirthDate.setText(getString(R.string.profileBirthDate) + user.getDateOfBirth());
            txtCardNumber.setText(getString(R.string.profileCardNumber) + user.getCardNumber());
            txtDateExpire.setText(getString(R.string.profileDateOfExpire) + user.getDateOfExpire());
            txtDateOfIssue.setText(getString(R.string.profileDateOfIssue) + user.getDateOfIssue());
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void setListeners() {

        Button btnPayment = findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, Payment.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                i.putExtra("user", user);
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
