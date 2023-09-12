package com.example.ecollect.view.formsolicitar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ecollect.Modelo.Solicitacao.Solicitacao;
import com.example.ecollect.Modelo.Solicitacao.SolicitacaoSingleton;
import com.example.ecollect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class SolicitarE extends AppCompatActivity {
    BlurView blurView;
    private ListView listaEmpresas;
    String nomeEmpresa;
    String tipoLixo;
    Query query;
    //private String codigoDocumento;
    SolicitacaoSingleton sol;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitare);
        listaEmpresas = findViewById(R.id.lista_Empresas);
        blurView = findViewById(R.id.blurView);
        Intent intent = getIntent();
        blurBackground();
        tipoLixo = intent.getStringExtra("tipoLixo");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference empresasRef = db.collection("EmpresasColeta");
        query = empresasRef.whereEqualTo("tipoLixo", tipoLixo);
        Solicitacao solicitacao = new Solicitacao();
        consultar();


        listaEmpresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Recupere o DocumentSnapshot correspondente ao item selecionado
                DocumentSnapshot documentSnapshot = documentSnapshots.get(position);

                // Obtenha o código do documento
                //codigoDocumento = documentSnapshot.getId();
                nomeEmpresa = documentSnapshot.getString("nomeEmpresa");
                Intent intent = new Intent(SolicitarE.this, SolicitarConfirmar.class);
                intent.putExtra("tipoLixo", tipoLixo);
                intent.putExtra("nomeEmpresa", nomeEmpresa);
                startActivity(intent);
                Solicitacao solicitacao = sol.getSolicitacao();
                solicitacao.setTipoLixo(tipoLixo);
                solicitacao.setNomeEmpresa(nomeEmpresa);

            }
        });



    }
    List<DocumentSnapshot> documentSnapshots = new ArrayList<>();

    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
        documentSnapshots.clear();

        documentSnapshots.addAll(queryDocumentSnapshots.getDocuments());
    }


    public void consultar(){
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> empresas = new ArrayList<>();
                if (task.isSuccessful()) {
                    // A consulta foi bem-sucedida, obtenha os documentos retornados
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        List<DocumentSnapshot> documentos = querySnapshot.getDocuments();
                        // Faça algo com os documentos retornados (por exemplo, exiba as empresas na interface do usuário)
                        for (DocumentSnapshot documento : documentos) {
                            // Obtenha os campos relevantes do documento
                            String nomeEmpresa = documento.getString("nomeEmpresa");
                            // ...
                            // Faça algo com os dados da empresa
                            //EmpresaColeta empresa = new EmpresaColeta(tipoLixo, nomeEmpresa);
                            String listaEmpresas = "\n  Empresa: " + nomeEmpresa +"\n";//"\nTipo de residuos: " + tipoLixo;
                            empresas.add(0, listaEmpresas);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SolicitarE.this, R.layout.item_lista, empresas);
                        listaEmpresas.setAdapter(adapter);
                        onSuccess(querySnapshot);
                    }
                } else {
                    // A consulta falhou, trate o erro aqui
                    Log.e("Firestore", "Erro ao realizar a consulta: " + task.getException());
                }
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

}