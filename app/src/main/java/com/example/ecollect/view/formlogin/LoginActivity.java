package com.example.ecollect.view.formlogin;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ecollect.CarregamentoActivity;
import com.example.ecollect.MenuPrincipal;
import com.example.ecollect.Modelo.Usuario;
import com.example.ecollect.R;
import com.example.ecollect.view.formcadastro.CadastrarActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class LoginActivity extends AppCompatActivity {

    BlurView blurView;
    private EditText rEditEmail;
    private EditText rEditSenha;
    private TextView rTextRegistrese;
    private Button rButtonLogin;
    private  TextView rNotificarLogin;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        inicializarComponentes();
        blurView = findViewById(R.id.blurView);
        blurBackground();
    }


    private void inicializarComponentes(){
        rButtonLogin = findViewById(R.id.buttonLogin);
        rEditEmail = findViewById(R.id.editEmail);
        rEditSenha = findViewById(R.id.editSenha);
        rNotificarLogin = findViewById(R.id.notificarLogin);
        rTextRegistrese = findViewById(R.id.textRegistrese);
    }


    public void onClick(View view) {
        Intent intent = new Intent(this, CadastrarActivity.class);
        startActivity(intent);
        finish();
    }

    private void blurBackground(){
        float radius = 15f;

        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowsBackground = decorView.getBackground();

        blurView.setupWith(rootView)
            .setFrameClearDrawable(windowsBackground)
            .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        if(usuarioAtual != null){
            menuPrincipal();
        }
    }
    private void menuPrincipal(){
        Intent intentM = new Intent(this, MenuPrincipal.class);
        startActivity(intentM);
        finish();
    }

    public void logar(View view){
        String editEmailContent = rEditEmail.getText().toString();
        String editSenhaContent = rEditSenha.getText().toString();

        if(editEmailContent.isEmpty() && editSenhaContent.isEmpty()){
            rNotificarLogin.setText("Insira seus dados nos campos acima");
        }
        else if(editEmailContent.isEmpty()){
            rNotificarLogin.setText("Insira um email válido");
        }
        else if (editSenhaContent.isEmpty()){
            rNotificarLogin.setText("Insira sua senha");
        }
        else{
            /*Intent intentC = new Intent(this, CarregamentoActivity.class);
            startActivity(intentC);
            finish();*/
            auth.signInWithEmailAndPassword(editEmailContent, editSenhaContent).addOnCompleteListener(autenticacao ->{
                //FirebaseUser currentUser = auth.getCurrentUser();
                if(autenticacao.isSuccessful()){
                    menuPrincipal();
                }
            }).addOnFailureListener(exception ->{
                rNotificarLogin.setText("Email ou senha Incorretros");
                Toast.makeText(LoginActivity.this, "Erro na autenticação, tente novamente", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                Intent intentL = new Intent(this, LoginActivity.class);
                startActivity(intentL);
                finish();
            });
        }
    }
}