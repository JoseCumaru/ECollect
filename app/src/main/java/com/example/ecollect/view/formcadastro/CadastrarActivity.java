package com.example.ecollect.view.formcadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ecollect.MenuPrincipal;
import com.example.ecollect.view.formlogin.LoginActivity;
import com.example.ecollect.R;
import com.example.ecollect.Modelo.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class CadastrarActivity extends AppCompatActivity {

    String usuarioID;
    BlurView blurView;
    private static EditText EditNomeR;
    private static EditText EditEmailR;
    private static EditText EditSenhaR;
    private static EditText EditEndereco;
    private TextView NotificarCadastro;
    private FirebaseAuth auth;

    Usuario user = new Usuario();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        auth = FirebaseAuth.getInstance();
        EditNomeR = findViewById(R.id.editNameR);
        EditEmailR = findViewById(R.id.editEmailR);
        EditSenhaR = findViewById(R.id.editSenhaR);
        EditEndereco = findViewById(R.id.editTextTextAdress);
        NotificarCadastro = findViewById(R.id.notificarCadastro);
        blurView = findViewById(R.id.blurView);
        blurBackground();
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

    public void onClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void cadastrar(View view){
        String nomeR = EditNomeR.getText().toString();
        String emailR = EditEmailR.getText().toString();
        String senhaR = EditSenhaR.getText().toString();
        //Usuario novoUsuario = new Usuario(-1, nomeR, emailR, senhaR);

        if(nomeR.isEmpty() && emailR.isEmpty() && senhaR.isEmpty()) {
            NotificarCadastro.setText("Preencha todos os dados");
        }
        else if(nomeR.isEmpty()){
            NotificarCadastro.setText("Insira seu nome");
        }
        else if(emailR.isEmpty()){
            NotificarCadastro.setText("Insira um email válido");
        }
        else if(senhaR.isEmpty()) {
            NotificarCadastro.setText("Insira uma senha numérica");
        }

        else {
            //cadastra no firebase Autentication
            auth.createUserWithEmailAndPassword(emailR, senhaR).addOnCompleteListener(cadastro ->{
                    if(cadastro.isSuccessful()){
                        salvarDadosUsuario();
                        Toast.makeText(CadastrarActivity.this,"Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                        Intent intentM = new Intent(this, MenuPrincipal.class);
                        startActivity(intentM);
                        finish();
                            }else{
                                String erro;
                                try{
                                    throw cadastro.getException();
                                } catch (FirebaseAuthWeakPasswordException e){
                                    erro = "Digite uma senha com no minimo 6 caracteres";
                                    Toast.makeText(CadastrarActivity.this, "Digite uma senha com no minimo 6 caracteres", Toast.LENGTH_SHORT).show();
                                } catch (FirebaseAuthUserCollisionException e){
                                    erro = "essa conta ja foi cadastrada";
                                    Toast.makeText(CadastrarActivity.this,erro, Toast.LENGTH_SHORT).show();
                                } catch (FirebaseAuthInvalidCredentialsException e){
                                    erro = "email invalido";
                                    Toast.makeText(CadastrarActivity.this,erro, Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e) {
                                    erro = "erro ao cadastrar usuario";
                                    Toast.makeText(CadastrarActivity.this,erro, Toast.LENGTH_SHORT).show();
                                }
                    }
                        });
            }
    }

    //Salva os dados do usuario no firestore
    private void salvarDadosUsuario(){
        String nome = EditNomeR.getText().toString();
        String email = EditEmailR.getText().toString();
        String senha = EditSenhaR.getText().toString();
        String endereco = EditEndereco.getText().toString();

        user.setNome(nome);
        user.setEmail(email);
        user.setSenha(senha);
        user.setEndereco(endereco);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("nome",user.getNome());
        usuarios.put("email",user.getEmail());
        usuarios.put("senha",user.getSenha());
        usuarios.put("endereco", user.getEndereco());

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentoReference = db.collection("Usuarios").document(usuarioID);
        documentoReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("db", "Sucesso ao salvar os dados");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db_error", "Erro ao salver os dados" + e.toString());
                    }
                });
    }

    protected void onDestroy(){
        super.onDestroy();
    }

}