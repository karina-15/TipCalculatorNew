package edu.miracosta.cs134.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import edu.miracosta.cs134.tipcalculator.model.Bill;

public class MainActivity extends AppCompatActivity {

    // Instance variables
    // Bridge the View and Model
    private Bill currentBill;

    // in activity_main.xml
    private EditText amountEditText;
    private TextView percentTextView;
    private SeekBar percentSeekBar;
    private TextView tipTextView;
    private TextView totatTextView;

    // Instance variables to format output (currency and percent)
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
    NumberFormat percent = NumberFormat.getPercentInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // "Wire up" instance variables (initialize them)
        currentBill = new Bill();
        amountEditText = findViewById(R.id.amountEditText);
        percentTextView = findViewById(R.id.percentTextView);
        percentSeekBar = findViewById(R.id.percentSeekBar);
        tipTextView = findViewById(R.id.tipTextView);
        totatTextView = findViewById(R.id.totalTextView);

        // Let's set the current tip percentage
        // int to double
        currentBill.setTipPercent(percentSeekBar.getProgress() / 100.0);

        // Implement the interface for the EditText
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // use while user is typing
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Read the input from the amountEditText (View) and store in the currentBill (Model)
                // getText is converted to string and string is converted to double
                try {
                    double amount = Double.parseDouble(amountEditText.getText().toString());
                    // Store the amount in the bill
                    currentBill.setAmount(amount);
                } catch (NumberFormatException e) {
                    currentBill.setAmount(0.0);
                }

                calculateBill();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Implement the interface for the SeekBar
        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the tip percent
                // convert int percent to double
                currentBill.setTipPercent(percentSeekBar.getProgress() / 100.0);
                // Update the view
                percentTextView.setText(percent.format(currentBill.getTipPercent()));
                calculateBill();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void calculateBill(){
        // Update the tipTextView and totalTextView
        tipTextView.setText(currency.format(currentBill.getTipAmount()));
        totatTextView.setText(currency.format(currentBill.getTotalAmount()));

    }

}
