package com.droid.ray.buscatecnico.activitys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.droid.ray.buscatecnico.R;
import com.droid.ray.buscatecnico.dbase.FireBase;
import com.droid.ray.buscatecnico.dbase.Pedido;
import com.droid.ray.buscatecnico.lists.Fabricante;
import com.droid.ray.buscatecnico.lists.TipoProblema;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class TelaPedido extends AppCompatActivity {

    Context context;
    Spinner sp_fabricante;
    Spinner sp_defeito;
    EditText edtObs;
    Button btnAvancar;
    String fabricante;
    String defeito;

    private ArrayAdapter<String> adapter_fabricante;
    private ArrayAdapter<String> adapter_defeito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_pedido);

        context = getBaseContext();
        edtObs = (EditText) findViewById(R.id.edtObs);
        btnAvancar = (Button) findViewById(R.id.btnAvancar);

        sp_fabricante = (Spinner) findViewById(R.id.sp_fabricante);
        adapter_fabricante = new ArrayAdapter<String>(
                context,
                R.layout.simple_spinner_item,
                Fabricante.ListaFabricantes());
        adapter_fabricante.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        sp_fabricante.setAdapter(adapter_fabricante);
        sp_fabricante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fabricante = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_defeito = (Spinner) findViewById(R.id.sp_defeito);
        adapter_defeito = new ArrayAdapter<String>(
                context,
                R.layout.simple_spinner_item,
                TipoProblema.ListarTipoProblema());
        adapter_defeito.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        sp_defeito.setAdapter(adapter_defeito);
        sp_defeito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                defeito = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FireBase.getFirebaseAuth().getCurrentUser();
                Pedido pedido = new Pedido();
                pedido.setId(currentUser.getUid());
                pedido.setTelefone(currentUser.getPhoneNumber().toString());
                pedido.setFabricante(fabricante);
                pedido.setDefeito(defeito);
                pedido.setObservacao(edtObs.getText().toString());
                pedido.setStatus("Em aberto");

                pedido.setData(getDateTime().toString());
                pedido.Salvar();

                Intent intent = new Intent(context, TelaLogado.class);
                startActivity(intent);
            }
        });

    }

    private String getDateTime() {

        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleFormat.format( new Date( System.currentTimeMillis() ) );
    }

}
