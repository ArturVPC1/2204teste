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

    // RowMapper reutilizável: converte linha do banco → objeto Aluno
    //----------ESTUDAR ESSA PARTE!--------
    private final RowMapper<Aluno> alunoMapper = (rs, rowNum) ->
        new Aluno(
            rs.getString("cpf"),
            rs.getString("id"),
            rs.getString("nome")
        );

    @PostConstruct
    private void initialize() {
        jdbc = new JdbcTemplate(dataSource);
    }

    // ── INSERT ──────────────────────────────────────────
    public void inserirAluno(Aluno aluno) {
        String sql = "INSERT INTO aluno(nome, cpf) VALUES (?, ?)";
        jdbc.update(sql, aluno.getNome(), aluno.getCpf());
    }

    // ── SELECT todos ─────────────────────────────────────
    public List<Aluno> listarTodos() {
        String sql = "SELECT id, nome, cpf FROM aluno ORDER BY nome";
        return jdbc.query(sql, alunoMapper);
    }

    // ── SELECT por nome (busca parcial, case-insensitive) ─
    public List<Aluno> buscarPorNome(String nome) {
        String sql = "SELECT id, nome, cpf FROM aluno WHERE LOWER(nome) LIKE LOWER(?) ORDER BY nome";
        return jdbc.query(sql, alunoMapper, "%" + nome + "%");
    }

    // ── SELECT por CPF ───────────────────────────────────
    public List<Aluno> buscarPorCpf(String cpf) {
        // remove caracteres não numéricos para facilitar busca
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        String sql = "SELECT id, nome, cpf FROM aluno WHERE cpf LIKE ? ORDER BY nome";
        return jdbc.query(sql, alunoMapper, "%" + cpfLimpo + "%");
    }

    // ── SELECT por ID ────────────────────────────────────
    public List<Aluno> buscarPorId(String id) {
        String sql = "SELECT id, nome, cpf FROM aluno WHERE CAST(id AS TEXT) LIKE ? ORDER BY nome";
        return jdbc.query(sql, alunoMapper, "%" + id + "%");
    }

    // ── Contagem total ───────────────────────────────────
    public int contarAlunos() {
        String sql = "SELECT COUNT(*) FROM aluno";
        Integer total = jdbc.queryForObject(sql, Integer.class);
        return total != null ? total : 0;
    }
}
//----------ESTUDAR ESSA PARTE!--------