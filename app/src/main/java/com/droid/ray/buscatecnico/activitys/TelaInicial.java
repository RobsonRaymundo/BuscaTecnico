package com.droid.ray.buscatecnico.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.droid.ray.buscatecnico.R;
import com.droid.ray.buscatecnico.dbase.FireBase;
import com.droid.ray.buscatecnico.dbase.Usuario;
import com.droid.ray.buscatecnico.others.Globais;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Robson on 21/06/2017.
 */

public class TelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);

        FirebaseUser currentUser = FireBase.getFirebaseAuth().getCurrentUser();
        String telefone = "";
        if (currentUser != null) {
            telefone = currentUser.getPhoneNumber().toString();
        }
        if (!telefone.isEmpty()) {
            FireBase.getFireBaseUsers().child(telefone).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    Usuario getUsuario = dataSnapshot.getValue(Usuario.class);
                    Intent intent = null;

                    Globais.tipoUsuario = getUsuario.getTipo().toString();
                    Globais.nomeUsuario = getUsuario.getNome().toString();
                    Globais.telefoneUsuario = getUsuario.getTelefone().toString();

                    if (getUsuario != null && getUsuario.getTela().toString().equals("Registro")) {
                        intent = new Intent(getBaseContext(), TelaLogado.class);

                    } else {
                        intent = new Intent(getBaseContext(), TelaRegistro.class);
                    }
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    //   Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    // [START_EXCLUDE]
                    //     Toast.makeText(PostDetailActivity.this, "Failed to load post.",
                    //              Toast.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }
            });
        } else {
            Intent intent = new Intent(getBaseContext(), TelaRegistro.class);
            startActivity(intent);
            finish();
        }

/*
        FireBase.getFireBaseUsers().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Usuario getUsuario = dataSnapshot.getValue(Usuario.class);

                Intent intent = null;

                if (getUsuario != null && getUsuario.getTela().toString().equals("Registro")) {
                    intent = new Intent(getBaseContext(), ListarTvs.class);
                    //} else {
                    //  intent = new Intent(getBaseContext(), TelaRegistro.class);
                }
                startActivity(intent);
                finish();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

                String a = "";
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String a = "";
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                String a = "";
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String a = "";
            }
        });
        */
    }


}
