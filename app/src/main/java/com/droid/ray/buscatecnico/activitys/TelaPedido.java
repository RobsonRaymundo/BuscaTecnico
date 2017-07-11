package com.droid.ray.buscatecnico.activitys;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.ray.buscatecnico.R;
import com.droid.ray.buscatecnico.dbase.FireBase;
import com.droid.ray.buscatecnico.dbase.Pedido;
import com.droid.ray.buscatecnico.lists.Fabricante;
import com.droid.ray.buscatecnico.lists.TipoProblema;
import com.droid.ray.buscatecnico.others.Globais;
import com.droid.ray.buscatecnico.services.RegistrationIntentService;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaPedido extends AppCompatActivity {

    public String token;
    private Context context;
    private Spinner sp_fabricante;
    private Spinner sp_defeito;
    private TextView tv_regiao;
    private EditText edtObs;
    private Button btnAvancar;
    private String fabricante;
    private String defeito;
    private Button btnBusqueMeuLocal;
    private double latitude = 0;
    private double longitude = 0;

    private ArrayAdapter<String> adapter_fabricante;
    private ArrayAdapter<String> adapter_defeito;

    private LocationManager lm;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_pedido);

        context = getBaseContext();

        lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        tv_regiao = (TextView) findViewById(R.id.pedido_tv_regiao);
        edtObs = (EditText) findViewById(R.id.pedido_edt_Obs);

        btnAvancar = (Button) findViewById(R.id.pedido_btn_avancar);

        btnBusqueMeuLocal = (Button) findViewById(R.id.pedido_btn_meu_local);

        sp_fabricante = (Spinner) findViewById(R.id.pedido_sp_fabricante);
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


        sp_defeito = (Spinner) findViewById(R.id.pedido_sp_defeito);
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
                pedido.setNome(Globais.nomeUsuario);
                pedido.setTelefone(currentUser.getPhoneNumber().toString());
                pedido.setFabricante(fabricante);
                pedido.setDefeito(defeito);
                pedido.setLatitude(latitude);
                pedido.setLongitude(longitude);
                pedido.setObservacao(edtObs.getText().toString());
                pedido.setStatus("Em aberto");
                pedido.setTecnico_nome("");
                pedido.setTecnico_telefone("");
                pedido.setData(getDateTime().toString());
                pedido.Salvar();

                Intent intentService = new Intent(TelaPedido.this, RegistrationIntentService.class);
                intentService.putExtra("nomeSolicitantePedido", Globais.nomeUsuario);
                startService(intentService);

                Intent intent = new Intent(context, TelaLogado.class);
                startActivity(intent);

            }
        });

        btnBusqueMeuLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkGPS()) {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        Toast.makeText(
                                context,
                                "Necessario permissão especial",
                                Toast.LENGTH_SHORT
                        ).show();

                        return;
                    }
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, servicos_GPS);

                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(v.getContext());
                    //
                    alerta.setTitle("Servico de Localizacao")
                            .setMessage("GPS Desabilitado. Deseja ir para configuracoes?");
                    alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(mIntent);
                        }
                    });
                    //
                    alerta.setNegativeButton("Cancelar", null);
                    //
                    alerta.show();
                }
            }
        });

    }

    private boolean checkGPS() {
        if ((lm != null) &&
                (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                        lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                )) {
            return true;
        } else {
            return false;
        }
    }

    private LocationListener servicos_GPS = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            //
            try {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                address = BuscarEndereco();
                tv_regiao.setText(address.getSubLocality() + " - " + address.getLocality() + " - " + address.getAdminArea());
                //
                lm.removeUpdates(servicos_GPS);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    private String getDateTime() {

        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleFormat.format(new Date(System.currentTimeMillis()));
    }


    private Address BuscarEndereco() throws IOException {
        Geocoder geocoder;
        Address address = null;
        List<Address> addressList;
        geocoder = new Geocoder(getApplicationContext());


        addressList = geocoder.getFromLocation(latitude, longitude, 1);
        if (addressList.size() > 0)
            address = addressList.get(0);
        return address;
    }

}
