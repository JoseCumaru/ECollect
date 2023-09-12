package com.example.ecollect.Modelo;

public class Usuario {
    String nome;
    String email;
    String senha;
    String endereco;
    Long id;


    public Usuario(){}

    public Usuario(Long id, String editNomeRContent, String editEmailRContent, String editSenhaRContent, String endereco) {
        setNome(editNomeRContent);
        setEmail(editEmailRContent);
        setSenha(editSenhaRContent);
        setEndereco(endereco);
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getSenha(){
        return senha;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public String getEndereco(){
        return endereco;
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }


    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    /*public void setlogado(boolean log){
        logado = log;
    }*/

}
