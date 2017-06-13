package com.droid.ray.buscatecnico.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.droid.ray.buscatecnico.R;
import com.droid.ray.buscatecnico.lists.Fabricante;
import com.droid.ray.buscatecnico.lists.TipoProblema;

/**
 * Created by Robson on 13/06/2017.
 */

public class ListarTipoProblema extends AppCompatActivity {
    private Context context;
    private ListView lv_contatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_tvs);

        context = getBaseContext();
        //
        lv_contatos = (ListView) findViewById(R.id.lv_tvs);
        //
        lv_contatos.setAdapter(

                new ArrayAdapter<String>(
                        context,
                        R.layout.simple_list_item_1,
                        TipoProblema.ListarTipoProblema()
                )

        );

        lv_contatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String cAux = (String) parent.getItemAtPosition(position);
                //
                Toast.makeText(
                        context,
                        cAux,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}

