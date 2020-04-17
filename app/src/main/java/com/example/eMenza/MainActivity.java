package com.example.eMenza;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eMenza.login.LoginActivity;
import com.example.eMenza.login.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    private LinearLayout mGallery;
    private int[] mImgIds;
    private LayoutInflater mInflater;
    private HorizontalScrollView horizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListeners();
        mInflater = LayoutInflater.from(this);
        initData();
        initView();
    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.mainmenu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.profile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            case R.id.restaurants:
                Toast.makeText(this, "Item 2 clicked", Toast.LENGTH_SHORT);
                return true;
            case R.id.notifications:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                return true;
            case R.id.log_out:
                logout();
                return true;
            default:
                return false;
        }
    }

    private void initData() {
        mImgIds = new int[] { R.drawable.ic_logo_background, R.drawable.roundbutton, R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground
        };
    }

    private void initView() {

        mGallery = (LinearLayout) findViewById(R.id.id_gallery);

        for (int i = 0; i < mImgIds.length; i++) {

            View view = mInflater.inflate(R.layout.gallery_item,
                    mGallery, false);
            ImageView img = (ImageView) view
                    .findViewById(R.id.id_index_gallery_item_image);
            img.setImageResource(mImgIds[i]);
            TextView txt = (TextView) view
                    .findViewById(R.id.id_index_gallery_item_text);
            txt.setText("info " + i);
            mGallery.addView(view);

        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void setListeners() {

        Button btnPayment = (Button)findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Payment.class);
                startActivity(i);
            }
        });
    }

}

// @TODO
// Profil
// Unos kreditne kartice
// Funkcionalnost payment
// MainActivity galerija hrane
// Promena restorana
// Obavestenja
