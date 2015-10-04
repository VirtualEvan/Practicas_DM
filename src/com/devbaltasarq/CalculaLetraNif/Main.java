package com.devbaltasarq.calculaletranif;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;

/** Actividad de arranque de la app */
public class Main extends Activity {
    @Override
    public void onCreate(Bundle appState) {
        super.onCreate(appState);
        this.setContentView(R.layout.main);

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
                Main.this.calcula();
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
            result += Character.toString(com.devbaltasarq.calculaletranif.CalculoLetraNif.calculaLetraNif(dni));
            lblResult.setText(result);
        }
        catch(NumberFormatException exc) {
            lblResult.setText( R.string.label_default_result );
            Toast.makeText( this.getApplicationContext(), R.string.label_default_result, Toast.LENGTH_SHORT ).show();
        }
    }

    private int opc;
}
