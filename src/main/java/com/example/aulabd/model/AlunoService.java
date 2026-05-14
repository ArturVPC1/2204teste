package com.example.aulabd.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    @Autowired
    AlunoDAO alunoDAO;

    public void inserirAluno(Aluno aluno)   { alunoDAO.inserirAluno(aluno); }
    public void atualizarAluno(Aluno aluno) { alunoDAO.atualizarAluno(aluno); }
    public void excluirAluno(String id)     { alunoDAO.excluirAluno(id); }
    public Aluno buscarPorIdExato(String id){ return alunoDAO.buscarPorIdExato(id); }
    public List<Aluno> listarTodos()        { return alunoDAO.listarTodos(); }
    public List<Aluno> buscarPorNome(String n) { return alunoDAO.buscarPorNome(n); }
    public List<Aluno> buscarPorCpf(String c)  { return alunoDAO.buscarPorCpf(c); }
    public List<Aluno> buscarPorId(String id)  { return alunoDAO.buscarPorId(id); }
    public int contarAlunos()               { return alunoDAO.contarAlunos(); }
}
