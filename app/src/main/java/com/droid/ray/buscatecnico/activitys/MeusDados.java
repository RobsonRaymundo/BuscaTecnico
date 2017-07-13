package com.droid.ray.buscatecnico.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.droid.ray.buscatecnico.R;
import com.droid.ray.buscatecnico.dbase.FireBase;
import com.droid.ray.buscatecnico.dbase.Usuario;
import com.droid.ray.buscatecnico.others.Globais;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MeusDados extends AppCompatActivity {


    private EditText edtNome;
    private EditText edtTelefone;
    private EditText edtEmail;
    private EditText edtCpfCnpj;
    private EditText edtCep;
    private EditText edtTituloAnuncio;
    private EditText edtDescricaoAnuncio;
    private Button btnAvancar;
    private Usuario dadosUsuario;
    private String telaAnterior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String telaAnterior = getIntent().getStringExtra("tela");

        setContentView(R.layout.meus_dados);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtCpfCnpj = (EditText) findViewById(R.id.edtCpfCnpj);
        edtCep = (EditText) findViewById(R.id.edtCep);
        edtTituloAnuncio = (EditText) findViewById(R.id.edtTituloAnuncio);
        edtDescricaoAnuncio = (EditText) findViewById(R.id.edtDescricaoAnuncio);
        btnAvancar = (Button) findViewById(R.id.btnAvancar);

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dadosUsuario != null) {
                    dadosUsuario.setNome(edtNome.getText().toString());
                    dadosUsuario.setTelefone(edtTelefone.getText().toString());
                    dadosUsuario.setEmail(edtEmail.getText().toString());
                    dadosUsuario.setCpfcnpj(edtCpfCnpj.getText().toString());
                    dadosUsuario.setCep(edtCep.getText().toString());
                    dadosUsuario.setTituloAnuncio(edtTituloAnuncio.getText().toString());
                    dadosUsuario.setDescricaoAnuncio(edtDescricaoAnuncio.getText().toString());
                    dadosUsuario.Salvar();
                }

                if (telaAnterior != null && telaAnterior.contains("TelaRegistro")) {
                    Intent mIntent = new Intent(getBaseContext(), TelaLogado.class);
                    startActivity(mIntent);
                }
                finish();
            }
        });

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
                    dadosUsuario = dataSnapshot.getValue(Usuario.class);

                    edtNome.setText(dadosUsuario.getNome().toString());
                    edtTelefone.setText(dadosUsuario.getTelefone().toString());

                    if (dadosUsuario.getEmail() != null)
                        edtEmail.setText(dadosUsuario.getEmail().toString());

                    if (dadosUsuario.getCpfcnpj() != null)
                        edtCpfCnpj.setText(dadosUsuario.getCpfcnpj().toString());

                    if (dadosUsuario.getCep() != null)
                        edtCep.setText(dadosUsuario.getCep().toString());

                    if (dadosUsuario.getTituloAnuncio() != null)
                        edtTituloAnuncio.setText(dadosUsuario.getTituloAnuncio().toString());

                    if (dadosUsuario.getDescricaoAnuncio() != null)
                        edtDescricaoAnuncio.setText(dadosUsuario.getDescricaoAnuncio().toString());

                    if (dadosUsuario.getTipo() != null) {
                        if (dadosUsuario.getTipo().toString().contains("Cliente")) {
                            edtTituloAnuncio.setVisibility(View.GONE);
                            edtDescricaoAnuncio.setVisibility(View.GONE);
                            edtCpfCnpj.setHint("CPF");
                        }

                    }
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


    }
}
