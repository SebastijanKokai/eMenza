package com.example.eMenza;

import android.content.Intent;
import android.content.res.Resources;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eMenza.classes.User;
import com.example.eMenza.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.eMenza.MainActivity.USER_KEY;

public class PaymentActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private int breakfast = 0;
    private int lunch = 0;
    private int dinner = 0;

    private final Double breakfastPrice = 40.0;
    private final Double lunchPrice = 72.0;
    private final Double dinnerPrice = 59.0;
    private Double balance = 0.0;
    private Double price = 0.0;
    private Double balanceAfter = 0.0;

    // Database
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;

    // User
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initData();

        SetSpinners();
        SetListeners();

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        // Get balance from credit card and put into variable balance
        balance = 10000.0;

        Resources res = getResources();
        String text = String.format(res.getString(R.string.balance), balance.toString());
        TextView txtBalance = (TextView) findViewById(R.id.txtBalance);
        txtBalance.setText(text);

        countPrice();
    }

    private void initData() {
        if(getIntent() != null) {
            user = getIntent().getParcelableExtra(USER_KEY);
        }
    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.mainmenu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.profile:
                i = new Intent(PaymentActivity.this, ProfileActivity.class);
                i.putExtra(USER_KEY, user);
                startActivity(i);
                return true;
            case R.id.restaurants:
                return true;
            case R.id.notifications:
                i = new Intent(PaymentActivity.this, NotificationActivity.class);
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

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void SetSpinners() {
        Spinner spinnerBreakfast = (Spinner) findViewById(R.id.spinnerBreakfast);
        Spinner spinnerLunch = (Spinner) findViewById(R.id.spinnerLunch);
        Spinner spinnerDinner = (Spinner) findViewById(R.id.spinnerDinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.paymentChoices,
                R.layout.color_spinner_layout);
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
        Button btnMenu = (Button) findViewById(R.id.btnMenu);
        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        // spinners
        final Spinner spinnerBreakfast = (Spinner) findViewById(R.id.spinnerBreakfast);
        final Spinner spinnerLunch = (Spinner) findViewById(R.id.spinnerLunch);
        final Spinner spinnerDinner = (Spinner) findViewById(R.id.spinnerDinner);

        btnMinusBreakfast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (breakfast > 0) {
                    breakfast--;
                    countPrice();
                    Spinner spinnerBreakfast = (Spinner) findViewById(R.id.spinnerBreakfast);
                    spinnerBreakfast.setSelection(breakfast);
                }
            }
        });
        btnPlusBreakfast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (breakfast < 20) {
                    breakfast++;
                    countPrice();
                    if (balanceAfter < 0) {
                        breakfast--;
                        Toast.makeText(getApplicationContext(), R.string.paymentPriceError, Toast.LENGTH_SHORT).show();
                    } else {
                        Spinner spinnerBreakfast = (Spinner) findViewById(R.id.spinnerBreakfast);
                        spinnerBreakfast.setSelection(breakfast);
                    }
                }
            }
        });
        btnMinusLunch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (lunch > 0) {
                    lunch--;
                    Spinner spinnerLunch = (Spinner) findViewById(R.id.spinnerLunch);
                    spinnerLunch.setSelection(lunch);
                    countPrice();
                }
            }
        });
        btnPlusLunch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (lunch < 20) {
                    lunch++;
                    countPrice();
                    if (balanceAfter < 0) {
                        lunch--;
                        Toast.makeText(getApplicationContext(), R.string.paymentPriceError, Toast.LENGTH_SHORT).show();
                    } else {
                        Spinner spinnerLunch = (Spinner) findViewById(R.id.spinnerLunch);
                        spinnerLunch.setSelection(lunch);
                    }
                }
            }
        });
        btnMinusDinner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (dinner > 0) {
                    dinner--;
                    Spinner spinnerDinner = (Spinner) findViewById(R.id.spinnerDinner);
                    spinnerDinner.setSelection(dinner);
                    countPrice();
                }
            }
        });
        btnPlusDinner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (dinner < 20) {
                    dinner++;
                    countPrice();
                    if (balanceAfter < 0) {
                        dinner--;
                        Toast.makeText(getApplicationContext(), R.string.paymentPriceError, Toast.LENGTH_SHORT).show();
                    } else {
                        Spinner spinnerDinner = (Spinner) findViewById(R.id.spinnerDinner);
                        spinnerDinner.setSelection(dinner);
                    }
                }
            }
        });
        spinnerBreakfast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int oldPosition = breakfast;
                breakfast = position;
                countPrice();
                if (balanceAfter < 0) {
                    breakfast = oldPosition;

                    spinnerBreakfast.setSelection(oldPosition);
                    Toast.makeText(getApplicationContext(), R.string.paymentPriceError, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        spinnerLunch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int oldPosition = lunch;
                lunch = position;
                countPrice();
                if (balanceAfter < 0) {
                    lunch = oldPosition;

                    spinnerLunch.setSelection(oldPosition);
                    Toast.makeText(getApplicationContext(), R.string.paymentPriceError, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        spinnerDinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int oldPosition = dinner;
                dinner = position;
                countPrice();
                if (balanceAfter < 0) {
                    dinner = oldPosition;

                    spinnerDinner.setSelection(oldPosition);
                    Toast.makeText(getApplicationContext(), R.string.paymentPriceError, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(PaymentActivity.this, MainActivity.class);
                i.putExtra(USER_KEY, user);
                startActivity(i);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // UPDATE THE BALANCE OF CREDIT CARD
                price = breakfast * breakfastPrice + lunch * lunchPrice + dinner * dinnerPrice;
                balance = balanceAfter;

                reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // UPDATE THE MEALS AFTER REMOVING CASH FROM CREDIT CARD BALANCE
                        User user = snapshot.getValue(User.class);
                        boolean success = false;

                        if(breakfast > 0) {
                            int breakfastDB = user.getNumberOfBreakfast();
                            reference.child("numberOfBreakfast").setValue(breakfast + breakfastDB);
                            user.setNumberOfBreakfast(breakfast + breakfastDB);
                            success = true;
                        }

                        if(lunch > 0) {
                            int lunchDB = user.getNumberOfLunch();
                            reference.child("numberOfLunch").setValue(lunch + lunchDB);
                            user.setNumberOfLunch(lunch + lunchDB);
                            success = true;
                        }

                        if(dinner > 0) {
                            int dinnerDB = user.getNumberOfDinner();
                            reference.child("numberOfDinner").setValue(dinner + dinnerDB);
                            user.setNumberOfDinner(dinner + dinnerDB);
                            success = true;
                        }

                        if(success) {
                            Toast.makeText(PaymentActivity.this, "Payment successful.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(PaymentActivity.this, MainActivity.class);
                            i.putExtra(USER_KEY, user);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }

    private void countPrice() {
        price = breakfast * breakfastPrice + lunch * lunchPrice + dinner * dinnerPrice;
        balanceAfter = balance - price;

        if (balanceAfter >= 0) {
            Resources res = getResources();
            String text = String.format(res.getString(R.string.price), price.toString());
            TextView txtPrice = (TextView) findViewById(R.id.txtPrice);
            txtPrice.setText(text);

            text = String.format(res.getString(R.string.afterTransaction), balanceAfter.toString());
            TextView txtAfter = (TextView) findViewById(R.id.txtAfter);
            txtAfter.setText(text);
        }

    }

}
// @TODO Ogranici da ne sme price > balance
