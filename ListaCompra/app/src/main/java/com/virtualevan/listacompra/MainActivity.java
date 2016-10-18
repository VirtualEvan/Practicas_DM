package com.virtualevan.listacompra;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.SearchView;
import android.util.StringBuilderPrinter;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> list;
    private ArrayAdapter<String> listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lvList = (ListView) this.findViewById( R.id.lvList );
        final Button btAdd = (Button) this.findViewById( R.id.btAdd );

        list = new ArrayList<>();
        listAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_selectable_list_item, this.list);
        lvList.setAdapter( this.listAdapter );
        /*lvList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if ( position >= 0){
                    MainActivity.this.selectAction( position );
                    return true;
                }

                return false;
            }
        });
        */
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.anadirDlg();
            }
        });

        this.registerForContextMenu( lvList );
    }

    /* Guardar valores con preferencias
    @Override
    public void onPause(){
        super.onPause();

        SharedPreferences.Editor saver = this.getPreferences( Context.MODE_PRIVATE ).edit();

        StringBuilder builder = new StringBuilder();
        for(String item: this.list){
            builder.append(item);
            builder.append(',');
        }

        saver.putString( "items", builder.toString() );
        saver.apply();
    }

    @Override
    public void onResume(){
        super.onResume();

        SharedPreferences prefs = this.getPreferences( Context.MODE_PRIVATE );
        String codedItems = prefs.getString( "items", ", ");
        String[] items = codedItems.split( "," );

        this.list.clear();
        for(int i = 0; i < items.length; ++i){
            this.list.add( items[ i ]);
        }

        this.listAdapter.notifyDataSetChanged();
    }
    */

    @Override
    public void onPause(){
        super.onPause();

        SharedPreferences.Editor saver = this.getPreferences( Context.MODE_PRIVATE ).edit();

        saver.putStringSet( "set_items", new HashSet<String>( this.list ) );

        saver.apply();
    }

    @Override
    public void onResume(){
        super.onResume();

        SharedPreferences prefs = this.getPreferences( Context.MODE_PRIVATE );
        Set<String> items= prefs.getStringSet( "set_items", new HashSet<String>( this.list ) );

        this.list.clear();
        this.list.addAll( items );


        this.listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo cmi){
        super.onCreateContextMenu( menu, view, cmi );

        if( view.getId() == R.id.lvList ){
            this.getMenuInflater().inflate( R.menu.contextual_menu, menu );
            menu.setHeaderTitle( R.string.lblContextTitulo );
        }
    }



    @Override
    public boolean onContextItemSelected(MenuItem menuItem){
        boolean toret = false;

        switch( menuItem.getItemId() ){
            case R.id.op_elimina :
                int pos = ( (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo() ).position;
                delete( pos );
                toret = true;
                break;
            //case R.id. :
        }

        return toret;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu( menu );
        this.getMenuInflater().inflate( R.menu.main_menu, menu );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        boolean toret = false;

        switch( menuItem.getItemId() ){
            case R.id.op_inserta:
                this.anadirDlg();
                toret = true;
                break;
            case R.id.op_elimina_ultimo:
                if( this.list.size() > 0 ) {
                    this.list.remove( this.list.size()-1 );
                    this.listAdapter.notifyDataSetChanged();
                    toret = true;
                }
                break;
        }

        return toret;
    }

    public void selectAction(final int position){

        AlertDialog.Builder dlg = new AlertDialog.Builder( this );
        dlg.setTitle( "Qué desea hacer?" );
        dlg.setNegativeButton( "Cancelar", null );
        dlg.setNeutralButton("Modificar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                modify( position );
            }
        });
        dlg.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete( position );
            }
        });
        dlg.create().show();

    }

    public void modify(final int position){
        AlertDialog.Builder dlg = new AlertDialog.Builder( this );
        final EditText edModify = new EditText( this );

        dlg.setTitle( "Modificar" );
        dlg.setView( edModify );
        dlg.setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectAction( position );
            }
        });
        dlg.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.list.remove( position );
                MainActivity.this.listAdapter.add( edModify.getText().toString() );
                MainActivity.this.listAdapter.notifyDataSetChanged();
            }
        });
        dlg.create().show();
    }


    public void delete(int position){
        this.list.remove( position );
        this.listAdapter.notifyDataSetChanged();
        this.updateStatus();
    }

    private void inserta(String item){
        this.listAdapter.add( item );
        this.updateStatus();
    }

    public void anadirDlg(){
        final EditText edItem = new EditText( MainActivity.this );
        AlertDialog.Builder dlg = new AlertDialog.Builder( MainActivity.this );
        dlg.setTitle( "Añadir elemento" );
        dlg.setView( edItem );
        dlg.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.inserta( edItem.getText().toString() );
            }
        });
        dlg.create().show();
    }

    private void updateStatus() {
        TextView txtNum = (TextView) this.findViewById( R.id.tvCont );
        txtNum.setText( Integer.toString( this.listAdapter.getCount() ) );
    }




    /**********************************************************************/
    //Mensaje de aviso temporal
    private void aviso(String msg) {
        Toast.makeText( this, msg, Toast.LENGTH_LONG ).show();
    }

    //Aviso que pausa la aplicacion hasta que se pulsa
    private void avisoBloqueante(String msg){
        AlertDialog.Builder dlg = new AlertDialog.Builder( this );
        dlg.setMessage( msg );
        dlg.create().show();
    }


    //Mensaje con respuesta positiva y negativa
    public void pregunta (String msg) {
        AlertDialog.Builder dlg = new AlertDialog.Builder( this );
        dlg.setMessage( msg );
        dlg.setPositiveButton( "Boton Positivo", null);
        dlg.setNegativeButton("Boton Negativo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit( 0 );
            }
        });
        dlg.create().show();
    }


    //Mesaje con elementos editables
    private String leeInfo(String msg){
        final EditText edInfo = new EditText( this );
        final StringBuilder toRet = new StringBuilder();

        AlertDialog.Builder dlg = new AlertDialog.Builder( this );
        dlg.setTitle( msg );
        dlg.setView( edInfo );
        dlg.setNegativeButton( "Calcel", null );
        dlg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toRet.append( edInfo.getText().toString() );
                MainActivity.this.aviso( toRet.toString() );
            }

        });
        dlg.create().show();

        return toRet.toString();
    }
}
