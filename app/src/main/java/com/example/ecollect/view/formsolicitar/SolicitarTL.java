package com.example.ecollect.view.formsolicitar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


//import com.example.ecollect.Modelo.Solicitacao.Solicitacao;
import com.example.ecollect.Modelo.Solicitacao.Solicitacao;
import com.example.ecollect.Modelo.Solicitacao.SolicitacaoSingleton;
import com.example.ecollect.R;

import eightbitlab.com.blurview.BlurView;
//import eightbitlab.com.blurview.BlurViewFacade;
import eightbitlab.com.blurview.RenderScriptBlur;

public class SolicitarTL extends AppCompatActivity {
    BlurView blurView;
    private CheckBox checkPapel;
    private CheckBox checkPlastico;
    private CheckBox checkMetal;
    private CheckBox checkOrganico;
    private CheckBox checkHospitalar;
    private TextView textoMensagem;
    private Button botaoVerificar;
    public String tipoLixo;
    SolicitacaoSingleton sol;
    Solicitacao solicitacao = new Solicitacao();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar);
        sol.setSolicitacao(solicitacao);

        inicializarComponentes();

        blurBackground();

        checkButtons();
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

    private void inicializarComponentes(){
        checkPapel = findViewById(R.id.checkBoxPapel);
        checkPlastico = findViewById(R.id.checkBoxPlastico);
        checkMetal = findViewById(R.id.checkBoxMetal);
        checkOrganico = findViewById(R.id.checkBoxOrganico);
        checkHospitalar = findViewById(R.id.checkBoxHospitalar);
        botaoVerificar = findViewById(R.id.buttonVerificar);
        textoMensagem = findViewById(R.id.textViewMensagem);
        blurView = findViewById(R.id.blurView);
    }

    private void checkButtons(){
        checkPapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPlastico.setChecked(false);
                checkMetal.setChecked(false);
                checkOrganico.setChecked(false);
                checkHospitalar.setChecked(false);
            }
        });

        checkPlastico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPapel.setChecked(false);
                checkMetal.setChecked(false);
                checkOrganico.setChecked(false);
                checkHospitalar.setChecked(false);
            }
        });

        checkMetal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPapel.setChecked(false);
                checkPlastico.setChecked(false);
                checkOrganico.setChecked(false);
                checkHospitalar.setChecked(false);
            }
        });

        checkOrganico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPapel.setChecked(false);
                checkPlastico.setChecked(false);
                checkMetal.setChecked(false);
                checkHospitalar.setChecked(false);
            }
        });

        checkHospitalar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPapel.setChecked(false);
                checkPlastico.setChecked(false);
                checkMetal.setChecked(false);
                checkOrganico.setChecked(false);
            }
        });

        botaoVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificar();
                if(checkPapel.isChecked() || checkPlastico.isChecked() || checkMetal.isChecked() || checkOrganico.isChecked() || checkHospitalar.isChecked()){
                    Intent intent = new Intent(SolicitarTL.this, SolicitarE.class);
                    intent.putExtra("tipoLixo", tipoLixo);
                    startActivity(intent);
                    textoMensagem.setText("");
                }
            }
        });
    }

    public void verificar() {
        if (checkPapel.isChecked()) {
            tipoLixo = "Papel";

        } else if (checkPlastico.isChecked()) {
            tipoLixo = "Plástico";

        } else if (checkMetal.isChecked()) {
            tipoLixo = "Metal";

        } else if (checkOrganico.isChecked()) {
            tipoLixo = "Orgânico";

        }
        else if (checkHospitalar.isChecked()) {
            tipoLixo = "Hospitalar";

        }else{
            textoMensagem.setText("Informe o tipo de lixo que deseja reciclar!");
        }
    }
}