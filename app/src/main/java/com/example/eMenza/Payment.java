package com.example.eMenza;

import androidx.appcompat.app.AppCompatActivity;

import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Payment extends AppCompatActivity {
    public int breakfast=0;
    public int lunch=0;
    public int dinner=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        SetSpinners();
        SetListeners();



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
        Button btnMinusBreakfast = (Button) findViewById(R.id.btnMinusBreakfast);
        Button btnMinusLunch = (Button) findViewById(R.id.btnMinusLunch);
        Button btnMinusDinner = (Button) findViewById(R.id.btnMinusDinner);
        Button btnPlusBreakfast = (Button) findViewById(R.id.btnPlusBreakfast);
        Button btnPlusLunch = (Button) findViewById(R.id.btnPlusLunch);
        Button btnPlusDinner = (Button) findViewById(R.id.btnPlusDinner);
        //spinners
        Spinner spinnerBreakfast = (Spinner) findViewById(R.id.spinnerBreakfast);
        Spinner spinnerLunch = (Spinner) findViewById(R.id.spinnerLunch);
        Spinner spinnerDinner = (Spinner) findViewById(R.id.spinnerDinner);
        btnMinusBreakfast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                breakfast--;
                Spinner spinnerBreakfast = (Spinner) findViewById(R.id.spinnerBreakfast);
                spinnerBreakfast.setSelection(breakfast);
            }
        });
        btnPlusBreakfast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                breakfast++;
                Spinner spinnerBreakfast = (Spinner) findViewById(R.id.spinnerBreakfast);
                spinnerBreakfast.setSelection(breakfast);
            }
        });
        btnMinusLunch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lunch--;
                Spinner spinnerLunch = (Spinner) findViewById(R.id.spinnerLunch);
                spinnerLunch.setSelection(lunch);
            }
        });
        btnPlusLunch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lunch++;
                Spinner spinnerLunch = (Spinner) findViewById(R.id.spinnerLunch);
                spinnerLunch.setSelection(lunch);
            }
        });
        btnMinusDinner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dinner--;
                Spinner spinnerDinner = (Spinner) findViewById(R.id.spinnerDinner);
                spinnerDinner.setSelection(dinner);
            }
        });
        btnPlusDinner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dinner++;
                Spinner spinnerDinner = (Spinner) findViewById(R.id.spinnerDinner);
                spinnerDinner.setSelection(dinner);
            }
        });
        spinnerBreakfast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                breakfast=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        spinnerLunch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                lunch=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        spinnerDinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                dinner=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }
}
