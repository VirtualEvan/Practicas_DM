package com.devbaltasarq.calculaletranif;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/** Actividad de arranque de la app */
public class Main extends Activity {
    @Override
    public void onCreate(Bundle appState) {
        super.onCreate( appState );
        this.setContentView( R.layout.main );

        EditText edDni = (EditText) this.findViewById( R.id.edDni );
        edDni.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Main.this.calcula();
                return false;
            }
        } );
    }

    private void calcula() {
        EditText edDni = (EditText) this.findViewById( R.id.edDni );
        TextView lblResult = (TextView) this.findViewById( R.id.lblResult );

        try {
            int dni = Integer.parseInt( edDni.getText().toString() );
            lblResult.setText( Character.toString( CalculoLetraNif.calculaLetraNif( dni ) ) );
        }
        catch(NumberFormatException exc) {
            lblResult.setText( R.string.label_default_result );
        }
    }
}
