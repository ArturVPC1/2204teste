package com.example.aulabd.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    @Autowired
    AlunoDAO alunoDAO;

    public void inserirAluno(Aluno aluno) {
        alunoDAO.inserirAluno(aluno);
    }

    public List<Aluno> listarTodos() {
        return alunoDAO.listarTodos();
    }

    public List<Aluno> buscarPorNome(String nome) {
        return alunoDAO.buscarPorNome(nome);
    }

    public List<Aluno> buscarPorCpf(String cpf) {
        return alunoDAO.buscarPorCpf(cpf);
    }

    public List<Aluno> buscarPorId(String id) {
        return alunoDAO.buscarPorId(id);
    }

    public int contarAlunos() {
        return alunoDAO.contarAlunos();
    }
}
