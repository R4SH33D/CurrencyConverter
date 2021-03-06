package com.r4sh33d.currencyconverter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.r4sh33d.currencyconverter.R;
import com.r4sh33d.currencyconverter.model.Currency;
import com.r4sh33d.currencyconverter.utils.Utils;

import java.text.DecimalFormat;

public class ConversionActivity extends AppCompatActivity {
    EditText editTextBtc, editTextBaseCurrency, editTextEth;
    TextView  baseCurrencyShortCode , headerText, oneBtcEquivalentTV, oneEthEquivalentBc;
    Currency currency;
    DecimalFormat formatter;
    ImageView countryflagIv, currencySymbolRow1Iv, currencySymbolRow2Iv , baseCurrencySymbol;
    TextWatcher editTextBaseCurrencyWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            editTextBtc.removeTextChangedListener(editTextBtcTextWatcher);
            editTextEth.removeTextChangedListener(editTextEthWatecher);
            String enteredValue = s.toString();
            if (Utils.isAValidNumber(enteredValue)) {
                editTextBtc.setText(formatter.format(baseCurrencyToBtc(Double.parseDouble(enteredValue))));
                editTextEth.setText(formatter.format(baseCurrencyToEth(Double.parseDouble(enteredValue))));
            }
            editTextBtc.addTextChangedListener(editTextBtcTextWatcher);
            editTextEth.addTextChangedListener(editTextEthWatecher);
        }
    };
    TextWatcher editTextBtcTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {

            editTextEth.removeTextChangedListener(editTextEthWatecher);
            editTextBaseCurrency.removeTextChangedListener(editTextBaseCurrencyWatcher);
            String enteredValue = s.toString();
            if (Utils.isAValidNumber(enteredValue)) {
                editTextEth.setText(formatter.format(btcToEth(Double.parseDouble(enteredValue))));
                editTextBaseCurrency.setText(formatter.format(btcTobaseCurrency(Double.parseDouble(enteredValue))));
            }
            editTextEth.addTextChangedListener(editTextEthWatecher);
            editTextBaseCurrency.addTextChangedListener(editTextBaseCurrencyWatcher);

        }
    };
    TextWatcher editTextEthWatecher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {

            editTextBaseCurrency.removeTextChangedListener(editTextBaseCurrencyWatcher);
            editTextBtc.removeTextChangedListener(editTextBtcTextWatcher);
            String enteredValue = s.toString();
            if (Utils.isAValidNumber(enteredValue)) {
                editTextBaseCurrency.setText(formatter.format(ethToBaseCurrency(Double.parseDouble(enteredValue))));
                editTextBtc.setText(formatter.format(ethToBtc(Double.parseDouble(enteredValue))));
            }
            editTextBaseCurrency.addTextChangedListener(editTextBaseCurrencyWatcher);
            editTextBtc.addTextChangedListener(editTextBtcTextWatcher);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        bindViews();
        formatter = new DecimalFormat("#");
        formatter.setMinimumIntegerDigits(1);
        formatter.setMaximumFractionDigits(4);
        currency = getIntent().getParcelableExtra(Utils.CURRENCY_INTENT_KEY);
        setViewValues();
        setEditTextChangedListeners();
    }

    public void bindViews(){
        baseCurrencyShortCode = (TextView) findViewById(R.id.baseCurrencyShortCode);
        editTextBtc = (EditText) findViewById(R.id.editTextBtc);
        editTextEth = (EditText) findViewById(R.id.editTextETH);
        editTextBaseCurrency = (EditText) findViewById(R.id.editTextBaseCurrency);
        headerText = (TextView)findViewById(R.id.headerText);
        oneBtcEquivalentTV = (TextView) findViewById(R.id.btcEquivalent);
        oneEthEquivalentBc = (TextView) findViewById(R.id.ethEquivalentTv);
        countryflagIv = (ImageView) findViewById(R.id.imageViewFlag);
        currencySymbolRow1Iv = (ImageView)findViewById(R.id.currencySymbol1);
        currencySymbolRow2Iv = (ImageView) findViewById(R.id.currencySymbol2);
        baseCurrencySymbol = (ImageView) findViewById(R.id.baseCurrencySymbol);
    }

    void setViewValues(){
        baseCurrencyShortCode.setText(currency.countryShortCode);
        baseCurrencySymbol.setImageResource(currency.currencySymbolResource);
        headerText.setText( currency.countryName + " [" + currency.countryShortCode + "] ");
        oneBtcEquivalentTV.setText(String.valueOf(currency.oneBtcEquivalent));
        oneEthEquivalentBc.setText(String.valueOf(currency.oneEthEquivalent));
        countryflagIv.setImageResource(currency.countryFlagResource);
        currencySymbolRow1Iv.setImageResource(currency.currencySymbolResource);
        currencySymbolRow2Iv.setImageResource(currency.currencySymbolResource);
        editTextBaseCurrency.setHint("Enter " + currency.countryShortCode + " value");

    }

    void setEditTextChangedListeners() {
        editTextBaseCurrency.addTextChangedListener(editTextBaseCurrencyWatcher);
        editTextBtc.addTextChangedListener(editTextBtcTextWatcher);
        editTextEth.addTextChangedListener(editTextEthWatecher);
    }


    double btcTobaseCurrency(double btcValue) {
        return currency.oneBtcEquivalent * btcValue;
    }

    double ethToBaseCurrency(double ethValue) {
        return currency.oneEthEquivalent * ethValue;
    }

    double baseCurrencyToBtc(double baseCurrencyValue) {
        return baseCurrencyValue / currency.oneBtcEquivalent;
    }

    double baseCurrencyToEth(double baseCurrencyValue) {
        return baseCurrencyValue / currency.oneEthEquivalent;
    }

    double ethToBtc(double ethValue) {
        return ethValue * currency.oneEthEquivalent / currency.oneBtcEquivalent;
    }

    double btcToEth(double btcValue) {
        return btcValue * currency.oneBtcEquivalent / currency.oneEthEquivalent;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
