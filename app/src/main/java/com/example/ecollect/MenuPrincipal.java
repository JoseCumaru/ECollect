package com.example.ecollect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecollect.Modelo.Usuario;
import com.example.ecollect.view.Apresentacao;
import com.example.ecollect.view.formlogin.LoginActivity;
import com.example.ecollect.view.formsolicitar.SolicitarTL;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class MenuPrincipal extends AppCompatActivity {
    Usuario usuario = new Usuario();
    BlurView blurView;
    private TextView nomeUsuario;
    private String nomeUser;
    private ImageButton solicitar;
    private ImageButton historico;
    private ImageButton apresentar;
    private ImageButton buttonConfig;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        inicializarComponentes();

        //listaFrases();

        blurBackground();
        //Toast.makeText(MenuPrincipal.this, "Bem vindo, "+nomeUser, Toast.LENGTH_SHORT).show();
        buttons();

    }

    private void listaFrases(){
        List<String> frases = Arrays.asList(
                "Não há desperdício de vidro na reciclagem. Ou seja, 1kg de vidro reciclado se torna um 1kg de vidro novo",
                "Seis milhões de toneladas de lixo são jogadas no mar todos os anos, na maior parte plástico",
                "95% das informações do mundo continuam sendo armazenadas em papel",
                "Reciclar uma tonelada de papel economiza 2,5 mil litros de petróleo, 26,5 mil litros de água e evita a derrubada de 17 árvores",
                "A coleta seletiva também pode ser benéfica para a economia, pois os materiais reciclados podem ser vendidos e transformados em novos produtos.",
                "Alguns países têm taxas sobre os resíduos enviados a aterros sanitários para incentivar a coleta seletiva e a reciclagem.",
                "A coleta seletiva pode ser um trabalho importante e bem remunerado em algumas regiões, especialmente em países em desenvolvimento.",
                "A reciclagem de um único quilo de papel pode economizar até 17 árvores e 7.000 litros de água.",
                "Alguns estudos sugerem que a coleta seletiva pode ajudar a reduzir a emissão de gases de efeito estufa.",
                "Alguns materiais, como a madeira, podem ser reciclados várias vezes sem perder sua qualidade.",
                "Alguns países têm leis que obrigam as empresas a reciclar ou reutilizar determinados produtos, como pneus, óleos lubrificantes e pilhas.",
                "A coleta seletiva de lixo eletrônico, é importante porque esses produtos podem conter substâncias tóxicas que podem prejudicar o meio ambiente se não forem descartados de maneira adequada."
        );
        Random random = new Random();
        int index = random.nextInt(frases.size());
        TextView textView = findViewById(R.id.textViewFraseAleatoria);
        textView.setText(frases.get(index));
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

    private void buttons(){
        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentL = new Intent(MenuPrincipal.this, ConfigActivity.class);
                startActivity(intentL);
            }
        });
        historico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentH = new Intent(MenuPrincipal.this, Historico.class);
                startActivity(intentH);
            }
        });
        solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentS = new Intent(MenuPrincipal.this, SolicitarTL.class);
                startActivity(intentS);
            }
        });

        apresentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentA = new Intent(MenuPrincipal.this, Apresentacao.class);
                startActivity(intentA);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    nomeUsuario.setText(documentSnapshot.getString("nome"));
                    nomeUser = documentSnapshot.getString("nome");
                }
            }
        });
        listaFrases();

    }
    private void inicializarComponentes(){
        blurView = findViewById(R.id.blurView);
        nomeUsuario = findViewById(R.id.textNomeusuarioR);
        buttonConfig = findViewById(R.id.buttonConfig);
        solicitar = findViewById(R.id.imageButtonSolicitar);
        historico = findViewById(R.id.imageButtonHistorico);
        apresentar = findViewById(R.id.imageButtonApresentar);
    }
}