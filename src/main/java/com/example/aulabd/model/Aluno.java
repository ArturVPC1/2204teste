
package com.example.aulabd.model;

public class Aluno {

    private String id, nome, cpf;

    //Construtor para a pagina do formulario
    public Aluno(){
        
    }

    //Construtor para Select
    public Aluno(String cpf, String id, String nome) {
        this.cpf = cpf;
        this.id = id;
        this.nome = nome;
    }

    //Construtor para insert
    public Aluno(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
