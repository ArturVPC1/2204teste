package com.example.aulabd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.aulabd.model.Aluno;
import com.example.aulabd.model.AlunoService;

@Controller
public class PaginaController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/")
    public String index() { return "redirect:/alunos"; }

    @GetMapping("/landing")
    public String landing() { return "landing"; }

    @GetMapping("/contato")
    public String contato() { return "contato"; }

    @GetMapping("/alunos")
    public String paginaPrincipal(Model model) {
        model.addAttribute("alunos", alunoService.listarTodos());
        model.addAttribute("total", alunoService.contarAlunos());
        return "index";
    }

    @GetMapping("/buscar")
    public String buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String id,
            Model model) {

        List<Aluno> resultados;
        String tipoBusca;
        String termoBusca = "";

        if (nome != null && !nome.isBlank()) {
            resultados = alunoService.buscarPorNome(nome);
            tipoBusca = "nome"; termoBusca = nome;
        } else if (cpf != null && !cpf.isBlank()) {
            resultados = alunoService.buscarPorCpf(cpf);
            tipoBusca = "cpf"; termoBusca = cpf;
        } else if (id != null && !id.isBlank()) {
            resultados = alunoService.buscarPorId(id);
            tipoBusca = "id"; termoBusca = id;
        } else {
            resultados = alunoService.listarTodos();
            tipoBusca = "todos";
        }

        model.addAttribute("resultados", resultados);
        model.addAttribute("tipoBusca", tipoBusca);
        model.addAttribute("termoBusca", termoBusca);
        model.addAttribute("total", alunoService.contarAlunos());
        return "buscar";
    }

    @GetMapping("/aluno")
    public String formAluno(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "formaluno";
    }

    @PostMapping("/aluno")
    public String postAluno(@ModelAttribute Aluno aluno) {
        alunoService.inserirAluno(aluno);
        return "sucesso";
    }

    @PostMapping("/aluno/excluir")
    public String excluirAluno(@RequestParam String id) {
        alunoService.excluirAluno(id);
        return "redirect:/alunos";
    }
}
