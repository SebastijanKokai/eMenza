package com.example.eMenza;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.eMenza.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    private LinearLayout mGallery;
    private String[] imageUrls;
    private LayoutInflater mInflater;
    private String[] txtOfImgs;
    private HorizontalScrollView horizontalScrollView;
    User user;



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
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                i.putExtra("user", user);
                startActivity(i);
                return true;
            case R.id.restaurants:
                return true;
            case R.id.notifications:
                startActivity(new Intent(getApplicationContext(), Notifications.class));
                return true;
            case R.id.log_out:
                logout();
                return true;
            default:
                return false;
        }
    }

    private void initData() {
        imageUrls = new String[]{"http://kulinarskirecepti.info/wp-content/uploads/2014/09/pasulj_1345537802_670x0-575x262.jpg",
                "https://i.pinimg.com/736x/21/be/4b/21be4b308e1627d76464c81ca2a0b476.jpg",
                "https://i.ytimg.com/vi/UWOcYRrRxDY/maxresdefault.jpg",
                "http://gurmanija.com/wp-content/uploads/2016/04/musaka-od-krompira22.jpg"
        };
        txtOfImgs = new String[] {
                "Pasulj", "Snicle", "Fasir", "Musaka"
        };



        if(getIntent() != null) {
            Intent i = getIntent();
            user = i.getParcelableExtra("user");
        }

    }

    private void initView() {

        mGallery = findViewById(R.id.id_gallery);

        for (int i = 0; i < imageUrls.length; i++) {

            View view = mInflater.inflate(R.layout.gallery_item,
                    mGallery, false);
            ImageView img = view
                    .findViewById(R.id.id_index_gallery_item_image);
            Glide.with(this).load(imageUrls[i]).into(img);
            TextView txt = view
                    .findViewById(R.id.id_index_gallery_item_text);
            txt.setText(txtOfImgs[i]);
            mGallery.addView(view);
        }
        if(user != null) {
            Button btnBreakfast = findViewById(R.id.btnBreakfast);
            Button btnLunch = findViewById(R.id.btnLunch);
            Button btnDinner = findViewById(R.id.btnDinner);

            // Setting data for buttons
            btnBreakfast.setText(String.valueOf(user.numberOfBreakfast));
            btnLunch.setText(String.valueOf(user.numberOfLunch));
            btnDinner.setText(String.valueOf(user.numberOfDinner));
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
// POTVRDI BUTTON DA POSALJE U FIREBASE
// menjaj balance i promeni broj obroka

// Glide - open image from URL
// Jsoup - get HTML from web page
// Obavestenja


// @TODO
// AI to Android XML


