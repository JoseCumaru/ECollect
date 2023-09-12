package com.example.ecollect.view.formsolicitar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecollect.MenuPrincipal;
import com.example.ecollect.Modelo.Solicitacao.Solicitacao;
import com.example.ecollect.Modelo.Solicitacao.SolicitacaoSingleton;
import com.example.ecollect.Modelo.Usuario;
import com.example.ecollect.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class SolicitarConfirmar extends AppCompatActivity {
    BlurView blurView;
    String tipoLixo;
    String nomeEmpresa;
    TextView tipoResiduo;
    TextView empresa;
    TextView enderecoUsuario;
    TextView mudarEnderecoUsuario;
    Button confirmar;
    SolicitacaoSingleton sol;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_confirmar);
        inicializarComponentes();

        blurBackground();
        Intent intent = getIntent();
        Solicitacao solicitacao = sol.getSolicitacao();
        tipoResiduo.setText(solicitacao.getTipoLixo());
        empresa.setText(solicitacao.getNomeEmpresa());
        tipoLixo = intent.getStringExtra("tipoLixo");
        nomeEmpresa = intent.getStringExtra("nomeEmpresa");
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novaSolicitacao();
                Toast.makeText(SolicitarConfirmar.this, "Solicitação concluida, aguarde confirmação", Toast.LENGTH_SHORT).show();
                Intent intentM = new Intent(SolicitarConfirmar.this, MenuPrincipal.class);
                startActivity(intentM);
                finish();
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
                if (documentSnapshot != null) {
                    enderecoUsuario.setText(documentSnapshot.getString("endereco"));

                }
            }
        });
    }
    private void novaSolicitacao(){
        // Cria um novo objeto Map com os dados da solicitação
        //Solicitacao solicitacao = new Solicitacao();
        Solicitacao solicitacao = sol.getSolicitacao();
        Map<String, Object> solicitationData = new HashMap<>();
        solicitationData.put("tipoLixo", solicitacao.getTipoLixo());
        solicitationData.put("nomeEmpresa", solicitacao.getNomeEmpresa());

        // Adiciona um novo documento ao subcoleção "SolicitacoesUsuario" do usuário atual
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("Usuarios")
                .document(usuarioId)
                .collection("Solicitacoes")
                .add(solicitationData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // A solicitação foi adicionada com sucesso ao Firestore
                        // Você pode tomar alguma ação aqui, se necessário
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Ocorreu um erro ao adicionar a solicitação ao Firestore
                        // Trate o erro aqui, se necessário
                    }
                });
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
        Usuario usuario;

        blurView = findViewById(R.id.blurView);
        tipoResiduo = findViewById(R.id.textViewTipoResiduo);
        empresa = findViewById(R.id.textViewNomeEmpresa);
        enderecoUsuario = findViewById(R.id.textViewEnderecoUsuario);
        //mudarEnderecoUsuario = findViewById(R.id.textViewAlterarEndereco);
        confirmar = findViewById(R.id.buttonConfirmar);
    }

}