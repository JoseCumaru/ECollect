package com.example.ecollect.Modelo;

public class EmpresaColeta {
    String nome;
    String tipoLixo;
    String endereço;
    String senha;
    Long id;

    public EmpresaColeta(){}

    public EmpresaColeta(String tipoLixo, String nome) {
        setNome(nome);
        setTipoLixo(tipoLixo);
        /*setEndereço(endereco);
        setSenha(senha);
        setId(id);*/
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNome(){
        return nome;
    }

    public void setTipoLixo(String tipoLixo){
        this.tipoLixo = tipoLixo;
    }
    public String getCnpj(){
        return tipoLixo;
    }

    public void setEndereço(String endereco){
        this.endereço = endereco;
    }
    public String getEndereço(){
        return endereço;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }
    public String getSenha(){
        return senha;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

}
