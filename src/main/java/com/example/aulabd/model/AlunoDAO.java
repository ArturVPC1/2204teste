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
    private void initialize() {
        jdbc = new JdbcTemplate(dataSource);
    }

    public void inserirAluno(Aluno aluno) {
        String sql = "INSERT INTO aluno(nome, cpf) VALUES (?, ?)";
        jdbc.update(sql, aluno.getNome(), aluno.getCpf());
    }

    public void excluirAluno(String id) {
        String sql = "DELETE FROM aluno WHERE id = ?::uuid";
        jdbc.update(sql, id);
    }

    public List<Aluno> listarTodos() {
        return jdbc.query("SELECT id, nome, cpf FROM aluno ORDER BY nome", alunoMapper);
    }

    public List<Aluno> buscarPorNome(String nome) {
        return jdbc.query("SELECT id, nome, cpf FROM aluno WHERE LOWER(nome) LIKE LOWER(?) ORDER BY nome", alunoMapper, "%" + nome + "%");
    }

    public List<Aluno> buscarPorCpf(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        return jdbc.query("SELECT id, nome, cpf FROM aluno WHERE cpf LIKE ? ORDER BY nome", alunoMapper, "%" + cpfLimpo + "%");
    }

    public List<Aluno> buscarPorId(String id) {
        return jdbc.query("SELECT id, nome, cpf FROM aluno WHERE CAST(id AS TEXT) LIKE ? ORDER BY nome", alunoMapper, "%" + id + "%");
    }

    public int contarAlunos() {
        Integer total = jdbc.queryForObject("SELECT COUNT(*) FROM aluno", Integer.class);
        return total != null ? total : 0;
    }
}
