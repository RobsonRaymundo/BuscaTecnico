package com.droid.ray.buscatecnico.activitys;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.droid.ray.buscatecnico.R;
import com.droid.ray.buscatecnico.dbase.FireBase;
import com.droid.ray.buscatecnico.dbase.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.TimeUnit;

public class TelaRegistro extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private Context context;

    private ViewGroup layInicial;
    private ViewGroup laySMS;

    private EditText edtNome;
    private EditText edtTelefone;
    private TextView txtTituloSMS;
    private Button btnAvancar;
    private EditText edtSms;
    private Button btnEnviar;
    private Button btnSair;
    private String mVerificationId;
    private RadioButton radCliente;
    private RadioButton radTecnico;
    private RadioGroup groUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_registro);

        context = getBaseContext();

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);
        btnAvancar = (Button) findViewById(R.id.btnAvancar);
        edtSms = (EditText) findViewById(R.id.edtSMS);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnSair = (Button) findViewById(R.id.btnSair);
        txtTituloSMS = (TextView) findViewById(R.id.txtTituloSMS);
        layInicial = (ViewGroup) findViewById(R.id.layInicial);
        laySMS = (ViewGroup) findViewById(R.id.laySMS);
        radCliente = (RadioButton) findViewById(R.id.radCliente);
        radTecnico = (RadioButton) findViewById(R.id.radTecnico);
        groUsuario = (RadioGroup) findViewById(R.id.groUsuario);

        layInicial.setVisibility(View.VISIBLE);
        laySMS.setVisibility(View.GONE);

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateTipoUsuario() || !validatePhoneNumber() ) {
                    return;
                }

                layInicial.setVisibility(View.GONE);
                laySMS.setVisibility(View.VISIBLE);

                String telefone = edtTelefone.getText().toString();
                txtTituloSMS.setText("Aguardando SMS para " + telefone);
                startPhoneNumberVerification (telefone);

            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edtSms.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    edtSms.setError("Não pode ser em branco");
                    return;
                }
                verifyPhoneNumberWithCode(mVerificationId, code);
            }
        });

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                mVerificationId = "";


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                layInicial.setVisibility(View.VISIBLE);
                laySMS.setVisibility(View.GONE);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    edtTelefone.setError("Número de telefone inválido.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Tentativas de envio excedido.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                //mResendToken = token;


            }
        };
        // [END phone_auth_callbacks]

    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FireBase.getFirebaseAuth().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();

                            Usuario usuario = new Usuario();
                            usuario.setId(user.getUid());
                            usuario.setTelefone(user.getPhoneNumber().toString());
                            usuario.setNome(edtNome.getText().toString());
                            Intent mIntent;
                            if(radCliente.isChecked())
                            {
                                usuario.setTipo("Cliente");
                                mIntent = new Intent(context, TelaPedido.class);
                            }
                            else
                            {
                                usuario.setTipo("Tecnico");
                                mIntent = new Intent(context, MeusDados.class);
                                FirebaseMessaging.getInstance().subscribeToTopic("news");

                            }
                            usuario.setTela("Registro");
                            usuario.Salvar();


                            startActivity(mIntent);
                            finish();

                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                edtSms.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = edtTelefone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            edtTelefone.setError("Número de telefone inválido.");
            return false;
        }

        return true;
    }

    private boolean validateTipoUsuario()
    {

        if (groUsuario.getCheckedRadioButtonId() == -1)
        {
            radCliente.setError("Selecione uma das opções"); // no radio buttons are checked
            radTecnico.setError("Selecione uma das opções"); // no radio buttons are checked
            return false;
        }
        else
        {
            // one of the radio buttons is checked
            return true;
        }

    }

    /*
    private void signOut() {
        mAuth.signOut();
    }
*/

}
