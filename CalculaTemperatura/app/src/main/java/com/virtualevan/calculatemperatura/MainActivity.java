package com.virtualevan.calculatemperatura;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText ed_celcius = (EditText) this.findViewById( R.id.ed_celcius );
        final EditText ed_fahrenheit = (EditText) this.findViewById( R.id.ed_fahrenheit );

        ed_celcius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ed_celcius.isFocused()){
                    calculaFahrenheit( ed_celcius, ed_fahrenheit );
                }
            }
        });

        ed_fahrenheit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ed_fahrenheit.isFocused()) {
                    calculaCelcius(ed_celcius, ed_fahrenheit);
                }
            }
        });
    }

        public void calculaCelcius( EditText ed_celcius, EditText ed_fahrenheit ) {
            try {
                double toRet = (Double.parseDouble(ed_fahrenheit.getText().toString()) - 32.0) * 0.5556;
                ed_celcius.setText( Double.toString(toRet) );
            }
            catch ( NumberFormatException e ) {
                ed_celcius.setText( "Error" );
            }
        }

        public void calculaFahrenheit( EditText ed_celcius, EditText ed_fahrenheit ) {
            try {
                double toRet = Double.parseDouble(ed_celcius.getText().toString())*1.8 + 32.0;
                ed_fahrenheit.setText( Double.toString(toRet) );
            }
            catch ( NumberFormatException e ) {
                ed_fahrenheit.setText( "Error" );
            }

        }
}
