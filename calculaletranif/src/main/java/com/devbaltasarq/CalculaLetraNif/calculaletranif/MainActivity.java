package com.devbaltasarq.CalculaLetraNif.calculaletranif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle appState) {
        super.onCreate(appState);
        this.setContentView(R.layout.activity_main);

        // Link to events
        EditText edDni = (EditText) this.findViewById( R.id.edDni );
        edDni.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                MainActivity.this.calcula();
            }
        });
    }

    private void calcula() {
        EditText edDni = (EditText) this.findViewById( R.id.edDni );
        TextView lblResult = (TextView) this.findViewById( R.id.lblResult );

        try {
            String value = edDni.getText().toString();
            int dni = Integer.parseInt( value );
            String result = value;
            result += Character.toString(CalculoLetraNif.calculaLetraNif(dni));
            lblResult.setText( result );
        }
        catch(NumberFormatException exc) {
            lblResult.setText( R.string.label_default_result );
            Toast.makeText( this.getApplicationContext(), R.string.label_default_result, Toast.LENGTH_SHORT ).show();
        }
    }

    private int opc;
}
