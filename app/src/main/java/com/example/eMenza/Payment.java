package com.example.eMenza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eMenza.login.LoginActivity;
import com.example.eMenza.login.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.Currency;

public class Payment extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener  {

    private int breakfast=0;
    private int lunch=0;
    private int dinner=0;

    private final Double breakfastPrice = 40.0;
    private final Double lunchPrice = 72.0;
    private final Double dinnerPrice = 59.0;
    private Double balance = 0.0;
    private Double price = 0.0;
    private Double balanceAfter = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        SetSpinners();
        SetListeners();

        balance = 1260.0;
        Resources res = getResources();
        String text = String.format(res.getString(R.string.balance), balance.toString());
        TextView txtBalance = (TextView) findViewById(R.id.txtBalance);
        txtBalance.setText(text);

        countPrice();
    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
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
                return true;
            case R.id.notifications:
                return true;
            case R.id.log_out:
                logout();
                return true;
            default:
                return false;
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void SetSpinners() {
        Spinner spinnerBreakfast = (Spinner) findViewById(R.id.spinnerBreakfast);
        Spinner spinnerLunch = (Spinner) findViewById(R.id.spinnerLunch);
        Spinner spinnerDinner = (Spinner) findViewById(R.id.spinnerDinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.paymentChoices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBreakfast.setAdapter(adapter);
        spinnerLunch.setAdapter(adapter);
        spinnerDinner.setAdapter(adapter);

    }
    private void SetListeners() {
        // buttons
        Button btnMinusBreakfast = (Button) findViewById(R.id.btnMinusBreakfast);
        Button btnMinusLunch = (Button) findViewById(R.id.btnMinusLunch);
        Button btnMinusDinner = (Button) findViewById(R.id.btnMinusDinner);
        Button btnPlusBreakfast = (Button) findViewById(R.id.btnPlusBreakfast);
        Button btnPlusLunch = (Button) findViewById(R.id.btnPlusLunch);
        Button btnPlusDinner = (Button) findViewById(R.id.btnPlusDinner);
        // spinners
        Spinner spinnerBreakfast = (Spinner) findViewById(R.id.spinnerBreakfast);
        Spinner spinnerLunch = (Spinner) findViewById(R.id.spinnerLunch);
        Spinner spinnerDinner = (Spinner) findViewById(R.id.spinnerDinner);

        btnMinusBreakfast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(breakfast > 0) {
                    breakfast--;
                    Spinner spinnerBreakfast = (Spinner) findViewById(R.id.spinnerBreakfast);
                    spinnerBreakfast.setSelection(breakfast);
                    countPrice();
                }
            }
        });
        btnPlusBreakfast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(breakfast < 20) {
                    breakfast++;
                    Spinner spinnerBreakfast = (Spinner) findViewById(R.id.spinnerBreakfast);
                    spinnerBreakfast.setSelection(breakfast);
                    countPrice();
                }
            }
        });
        btnMinusLunch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lunch > 0) {
                    lunch--;
                    Spinner spinnerLunch = (Spinner) findViewById(R.id.spinnerLunch);
                    spinnerLunch.setSelection(lunch);
                    countPrice();
                }
            }
        });
        btnPlusLunch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lunch < 20) {
                    lunch++;
                    Spinner spinnerLunch = (Spinner) findViewById(R.id.spinnerLunch);
                    spinnerLunch.setSelection(lunch);
                    countPrice();
                }
            }
        });
        btnMinusDinner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(dinner > 0) {
                    dinner--;
                    Spinner spinnerDinner = (Spinner) findViewById(R.id.spinnerDinner);
                    spinnerDinner.setSelection(dinner);
                    countPrice();
                }
            }
        });
        btnPlusDinner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(dinner < 20) {
                    dinner++;
                    Spinner spinnerDinner = (Spinner) findViewById(R.id.spinnerDinner);
                    spinnerDinner.setSelection(dinner);
                    countPrice();
                }
            }
        });
        spinnerBreakfast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                breakfast=position;
                countPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        spinnerLunch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                lunch=position;
                countPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        spinnerDinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                dinner=position;
                if(balance >= price) {
                    countPrice();
                } else {
                    Toast.makeText(getApplicationContext(), "The price is too high.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }

    private void countPrice() {
            price = breakfast * breakfastPrice + lunch * lunchPrice + dinner * dinnerPrice;
            balanceAfter = balance - price;

            Resources res = getResources();
            String text = String.format(res.getString(R.string.price), price.toString());
            TextView txtPrice = (TextView) findViewById(R.id.txtPrice);
            txtPrice.setText(text);

            text = String.format(res.getString(R.string.afterTransaction), balanceAfter.toString());
            TextView txtAfter = (TextView) findViewById(R.id.txtAfter);
            txtAfter.setText(text);


    }

}
// @TODO Ogranici da ne sme price > balance
