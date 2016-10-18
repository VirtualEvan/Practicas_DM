package com.virtualevan.calculaarea;

import android.content.Context;
import android.content.SharedPreferences;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edit_lado1 = (EditText) this.findViewById(R.id.edit_lado1);
        final EditText edit_lado2 = (EditText) this.findViewById(R.id.edit_lado2);
        final EditText edit_resultado = (EditText) this.findViewById(R.id.edit_resultado);

        Button bt_Calcula = (Button) this.findViewById(R.id.button);
        bt_Calcula.setEnabled(false);

        edit_lado1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.calcular(edit_lado1, edit_lado2, edit_resultado);
            }
        });

        edit_lado2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.calcular(edit_lado1, edit_lado2, edit_resultado);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        final EditText edit_lado1 = (EditText) this.findViewById( R.id.edit_lado1 );
        final EditText edit_lado2 = (EditText) this.findViewById( R.id.edit_lado2 );
        final EditText edit_resultado = (EditText) this.findViewById( R.id.edit_resultado );

        SharedPreferences prefs = this.getPreferences( Context.MODE_PRIVATE );

        double lado1 = prefs.getFloat( "lado1", (float) 0.0 );
        double lado2 = prefs.getFloat( "lado2", (float) 0.0 );

        edit_lado1.setText( Double.toString( lado1 ) );
        edit_lado2.setText( Double.toString( lado2 ) );
        edit_resultado.setText( Double.toString( lado1 * lado2 ) );

    }

    @Override
    public void onPause(){
        super.onResume();

        final EditText edit_lado1 = (EditText) this.findViewById( R.id.edit_lado1 );
        final EditText edit_lado2 = (EditText) this.findViewById( R.id.edit_lado2 );

        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE );
        SharedPreferences.Editor saver = prefs.edit();

        saver.putFloat( "lado1", Float.parseFloat( edit_lado1.getText().toString() ) );
        saver.putFloat( "lado2", Float.parseFloat( edit_lado2.getText().toString() ) );

        saver.apply();

    }

        /*
        //Necesita que se presione el boton para calcular.

        bt_Calcula.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double l1 = Double.parseDouble( edit_lado1.getText().toString() );
                double l2 = Double.parseDouble( edit_lado2.getText().toString() );

                String result = Double.toString( l1 * l2);
                edit_resultado.setText( result );
            }
        });
        */

        private void calcular(EditText edit_lado1, EditText edit_lado2, EditText edit_resultado) {

            try {
                double l1 = Double.parseDouble( edit_lado1.getText().toString() );
                double l2 = Double.parseDouble( edit_lado2.getText().toString() );

                String result = Double.toString( l1 * l2);
                edit_resultado.setText( result );
            }
            catch ( NumberFormatException e){
                edit_resultado.setText( "Error" );
            }
        }

}
