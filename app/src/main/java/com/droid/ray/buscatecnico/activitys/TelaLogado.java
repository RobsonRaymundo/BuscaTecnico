package com.droid.ray.buscatecnico.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.droid.ray.buscatecnico.R;
import com.droid.ray.buscatecnico.dbase.FireBase;
import com.droid.ray.buscatecnico.dbase.Pedido;
import com.droid.ray.buscatecnico.dbase.Usuario;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class TelaLogado extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView txtNavHeaderNome;
    private TextView txtNavHeaderTelefone;
    private String telefone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_logado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdicionarPedido);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SelecionarTipoDefeito.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FirebaseUser currentUser = FireBase.getFirebaseAuth().getCurrentUser();

        if (currentUser != null) {
            telefone = currentUser.getPhoneNumber().toString();
        }
        if (!telefone.isEmpty()) {
            FireBase.getFireBaseUsers().child(telefone).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    Usuario getUsuario = dataSnapshot.getValue(Usuario.class);


                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    View headerView = navigationView.getHeaderView(0);
                    txtNavHeaderNome = (TextView) headerView.findViewById(R.id.txtNavHeaderNome);
                    txtNavHeaderNome.setText(getUsuario.getNome().toString());
                    txtNavHeaderTelefone = (TextView) headerView.findViewById(R.id.txtNavHeaderTelefone);
                    txtNavHeaderTelefone.setText(getUsuario.getTelefone().toString());


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


            FireBase.getFireBasePedido().child(telefone).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Pedido getPedido = dataSnapshot.getValue(Pedido.class);
                    if (getPedido != null) {
                        String fabricante = getPedido.getFabricante().toString();
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else {
            Intent intent = new Intent(getBaseContext(), TelaRegistro.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tela_logado, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_buscarTecnico) {
            Intent intent = new Intent(getBaseContext(), SelecionarTipoDefeito.class);
            startActivity(intent);
        } else if (id == R.id.nav_Ajuda) {

        } else if (id == R.id.nav_meusPedidos) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







}
