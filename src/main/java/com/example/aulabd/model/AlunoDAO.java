package com.example.aulabd.model;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;

@Repository
public class AlunoDAO {

    @Autowired
    DataSource dataSource;
    JdbcTemplate jdbc;

    private final RowMapper<Aluno> alunoMapper = (rs, rowNum) ->
        new Aluno(rs.getString("cpf"), rs.getString("id"), rs.getString("nome"));

    @PostConstruct
    private void initialize() { jdbc = new JdbcTemplate(dataSource); }

    public void inserirAluno(Aluno aluno) {
        jdbc.update("INSERT INTO aluno(nome, cpf) VALUES (?, ?)", aluno.getNome(), aluno.getCpf());
    }

    public void atualizarAluno(Aluno aluno) {
        jdbc.update("UPDATE aluno SET nome = ?, cpf = ? WHERE id = ?::uuid",
            aluno.getNome(), aluno.getCpf(), aluno.getId());
    }

    public void excluirAluno(String id) {
        jdbc.update("DELETE FROM aluno WHERE id = ?::uuid", id);
    }

    public Aluno buscarPorIdExato(String id) {
        List<Aluno> result = jdbc.query(
            "SELECT id, nome, cpf FROM aluno WHERE id = ?::uuid", alunoMapper, id);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Aluno> listarTodos() {
        return jdbc.query("SELECT id, nome, cpf FROM aluno ORDER BY nome", alunoMapper);
    }

    public List<Aluno> buscarPorNome(String nome) {
        return jdbc.query("SELECT id, nome, cpf FROM aluno WHERE LOWER(nome) LIKE LOWER(?) ORDER BY nome",
            alunoMapper, "%" + nome + "%");
    }

    public List<Aluno> buscarPorCpf(String cpf) {
        String limpo = cpf.replaceAll("[^0-9]", "");
        return jdbc.query("SELECT id, nome, cpf FROM aluno WHERE cpf LIKE ? ORDER BY nome",
            alunoMapper, "%" + limpo + "%");
    }

    public List<Aluno> buscarPorId(String id) {
        return jdbc.query("SELECT id, nome, cpf FROM aluno WHERE CAST(id AS TEXT) LIKE ? ORDER BY nome",
            alunoMapper, "%" + id + "%");
    }

    public int contarAlunos() {
        Integer t = jdbc.queryForObject("SELECT COUNT(*) FROM aluno", Integer.class);
        return t != null ? t : 0;
    }
}
