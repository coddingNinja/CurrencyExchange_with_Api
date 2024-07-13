package com.example.currencyexchange;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText Amount_to_converted, converted_amount;
    TextView textView_from, textView_to;
    Spinner spinner_from, spinner_to;

    String to;
    String from;
    Double INR = 2.0;
    Map<String, Double> rates;
    public static String url = "https://api.freecurrencyapi.com/v1/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        spinner_from = findViewById(R.id.from_spinner);
        spinner_to = findViewById(R.id.to_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_from.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.currencies_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_to.setAdapter(adapter1);
        Amount_to_converted = findViewById(R.id.amount);
        converted_amount = findViewById(R.id.convertesfinal);
        textView_from = findViewById(R.id.from_edittext);
        textView_to = findViewById(R.id.to_edittext);
        Amount_to_converted.setText(String.valueOf(1.0));
        defalt(adapter,adapter1,spinner_from,spinner_to);

        RetrofitInstance.getInstance().currencyApiService.getLatestExchangeRates().enqueue(new Callback<modelclass>() {
            @Override
            public void onResponse(Call<modelclass> call, Response<modelclass> response) {

                if (response.isSuccessful() && response.body() != null) {

                    rates = response.body().getData();

                    calculate();


                }
            }

            @Override
            public void onFailure(Call<modelclass> call, Throwable t) {

            }
        });
        spinner_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String fromCurrency = parent.getItemAtPosition(position).toString();
                textView_from.setText(fromCurrency);
                if (!Amount_to_converted.getText().toString().isEmpty()) {
                    calculate();
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView_from.setText("USD");
            }
        });
        spinner_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String toCurrency = parent.getItemAtPosition(position).toString();
                textView_to.setText(toCurrency);
                if (!Amount_to_converted.getText().toString().isEmpty()) {
                    calculate();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView_from.setText("INR");


            }

        });

        Amount_to_converted.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Amount_to_converted.getText().toString().isEmpty()) {
                    calculate();
                }if (Amount_to_converted.getText().toString().isEmpty()) {
                    converted_amount.setText(String.valueOf(0));

                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Amount_to_converted.getText().toString().isEmpty()) {
                    calculate();
                }
                if (Amount_to_converted.getText().toString().isEmpty()) {
                    converted_amount.setText(String.valueOf(0));

                }



            }
        });


    }

    public void calculate() {
        if (rates == null || rates.isEmpty()) {
            return;
        }

        double to = rates.get(textView_to.getText().toString());
        double from = rates.get(textView_from.getText().toString());
        double text = Double.parseDouble(Amount_to_converted.getText().toString());

        double cal = to / from * text;
        if(textView_from.getText().toString().isEmpty())
        {
            textView_to.setText(String.valueOf(0));
            return;
        }

        converted_amount.setText(String.valueOf(cal));



    }
    public void defalt(ArrayAdapter<CharSequence> adapter,ArrayAdapter<CharSequence> adapter1,Spinner s1,Spinner s2)
    {
        int spinnerposition=adapter.getPosition("USD");
        s1.setSelection(spinnerposition);

        spinnerposition=adapter1.getPosition("INR");
        s2.setSelection(spinnerposition);

    }
}