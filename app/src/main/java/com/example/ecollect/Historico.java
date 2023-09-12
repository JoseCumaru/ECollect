package com.example.ecollect;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Historico extends AppCompatActivity {

    private ListView historico;
    String usuarioID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        historico = findViewById(R.id.lista_Empresas);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference solicitacoesRef = db.collection("Usuarios").document(usuarioID).collection("Solicitacoes");


        // Consulta os documentos da coleção "SolicitacoesUsuario"
        solicitacoesRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> solicitacoesList = new ArrayList<>();

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    // Obtem os campos TipoLixo e NomeEmpresa de cada documento
                    String tipoLixo = documentSnapshot.getString("tipoLixo");
                    String nomeEmpresa = documentSnapshot.getString("nomeEmpresa");

                    // Cria uma string com os campos obtidos
                    String solicitacao = "\n-----Aguardando confirmação-----\n"+"  -Tipo de Lixo: " + tipoLixo + "\n     Nome da Empresa: " + nomeEmpresa+"\n";

                    // Adiciona a string à lista de solicitações
                    solicitacoesList.add(0, solicitacao);
                }

                // Cria um ArrayAdapter para exibir as solicitações na ListView
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Historico.this, R.layout.item_lista, solicitacoesList);

                // Define o adaptador na ListView
                historico.setAdapter(adapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Trata a falha na obtenção dos dados
                Toast.makeText(Historico.this, "Erro ao obter os dados: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}