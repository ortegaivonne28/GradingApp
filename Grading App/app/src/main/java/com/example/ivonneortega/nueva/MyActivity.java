package com.example.ivonneortega.nueva;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity
{

    //Declaracion de Variables
    EditText titulo_nota, nota_nueva;
    List<Nota> Notas = new ArrayList<Nota>();
    ListView lista_notas;
    BaseDatos bd;
    EditText usern,passw;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usern = (EditText) findViewById(R.id.username);
        passw = (EditText) findViewById(R.id.password);

        final Button crear_bo = (Button) findViewById(R.id.crear_usuario_b);
        final Button ingresar_bo = (Button) findViewById(R.id.login_b);


        //Cuando se presiona el boton CREAR
        crear_bo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), String.valueOf(usern.getText()) + " ha sido creado", Toast.LENGTH_SHORT).show();
                crear_nuevas_notas();

            }
        });
        //Cuando se presiona el boton INGRESAR
        ingresar_bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), String.valueOf(usern.getText()) + " ha ingresado", Toast.LENGTH_SHORT).show();
                crear_nuevas_notas();

            }
        });

        usern.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ingresar_bo.setEnabled(String.valueOf(passw.getText()).trim().length() > 0);
                crear_bo.setEnabled(String.valueOf(passw.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                //Nada
            }
        });

        passw.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ingresar_bo.setEnabled(String.valueOf(usern.getText()).trim().length() > 0);
                crear_bo.setEnabled(String.valueOf(usern.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                //Nada
            }
        });
    }

    //Ventana crear nuevas notas
    public void crear_nuevas_notas()
    {

        setContentView(R.layout.activity_my);

        //Vincular las variables a los nombres del LayOut
        titulo_nota = (EditText) findViewById(R.id.nuevo_titulo);
        nota_nueva = (EditText) findViewById(R.id.nueva_nota);
        lista_notas = (ListView) findViewById(R.id.listView);
        bd = new BaseDatos(getApplicationContext());

        //TabHost Para las Pestanas
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        //Pestana Crear
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("nueva_nota");
        tabSpec.setContent(R.id.PestCrear);
        tabSpec.setIndicator("Nueva Nota");
        tabHost.addTab(tabSpec);

        //Pestana Ver Notas
        tabSpec = tabHost.newTabSpec("ver_notas");
        tabSpec.setContent(R.id.PestNotas);
        tabSpec.setIndicator("Ver Notas");
        tabHost.addTab(tabSpec);


        //Boton Crear Nota
        final Button crear_boton = (Button) findViewById(R.id.btnAdd);
        crear_boton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Nota nota = new Nota(bd.numero_notas(), String.valueOf(titulo_nota.getText()), String.valueOf(nota_nueva.getText()));
                bd.crear_nota(nota);
                Notas.add(nota);
                Toast.makeText(getApplicationContext(), String.valueOf(titulo_nota.getText()) + " ha sido agregado satisfactoriamente", Toast.LENGTH_SHORT).show();
                //return;
                crear_nuevas_notas();

            }
        });

        titulo_nota.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {

            }
            //Procedimiento para validad que exista por lo menos un titulo antes de crear una nota nueva.
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {
                crear_boton.setEnabled(String.valueOf(titulo_nota.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                //Nada
            }
        });

        if (bd.numero_notas() != 0)
            Notas.addAll(bd.obtener_todas());

        Llenar_lista();
    }



    public void onActivityResult(int reqCode, int resCode, Intent data)
    {
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                //Error
            }
        }
    }

    private void Llenar_lista()
    {
        ArrayAdapter<Nota> adapter = new lista_notas_adapter();
        lista_notas.setAdapter(adapter);
    }

    private class lista_notas_adapter extends ArrayAdapter<Nota>
    {
        public lista_notas_adapter()
        {
            super (MyActivity.this, R.layout.listview_item, Notas);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Nota nota_aux = Notas.get(position);
            TextView titulo = (TextView) view.findViewById(R.id.titulo_lista);
            titulo.setText(nota_aux.obtener_titulo());
            TextView texto = (TextView) view.findViewById(R.id.texto_lista);
            texto.setText(nota_aux.obtener_texto());
            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

}

