
package com.example.aulabd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
    private ApplicationContext context;

    // ── Páginas institucionais ──────────────────────────

    @GetMapping("/")
    public String index() {
        return "redirect:/alunos";
    }

    @GetMapping("/landing")
    public String landing() {
        return "landing";
    }

    @GetMapping("/contato")
    public String contato() {
        return "contato";
    }

    // ── Página principal: lista todos os alunos ─────────

    @GetMapping("/alunos")
    public String paginaPrincipal(Model model) {
        AlunoService service = context.getBean(AlunoService.class);
        List<Aluno> alunos = service.listarTodos();
        model.addAttribute("alunos", alunos);
        model.addAttribute("total", service.contarAlunos());
        return "index";
    }

    // ── Busca de alunos ─────────────────────────────────

    @GetMapping("/buscar")
    public String buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String id,
            Model model) {

        AlunoService service = context.getBean(AlunoService.class);
        List<Aluno> resultados = null;
        String tipoBusca = "";
        String termoBusca = "";

        if (nome != null && !nome.isBlank()) {
            resultados = service.buscarPorNome(nome);
            tipoBusca = "nome";
            termoBusca = nome;
        } else if (cpf != null && !cpf.isBlank()) {
            resultados = service.buscarPorCpf(cpf);
            tipoBusca = "cpf";
            termoBusca = cpf;
        } else if (id != null && !id.isBlank()) {
            resultados = service.buscarPorId(id);
            tipoBusca = "id";
            termoBusca = id;
        } else {
            resultados = service.listarTodos();
            tipoBusca = "todos";
        }

        model.addAttribute("resultados", resultados);
        model.addAttribute("tipoBusca", tipoBusca);
        model.addAttribute("termoBusca", termoBusca);
        model.addAttribute("total", service.contarAlunos());
        return "buscar";
    }

    // ── Cadastro de aluno ───────────────────────────────

    @GetMapping("/aluno")
    public String formAluno(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "formaluno";
    }

    @PostMapping("/aluno")
    public String postAluno(@ModelAttribute Aluno aluno, Model model) {
        AlunoService service = context.getBean(AlunoService.class);
        service.inserirAluno(aluno);
        return "sucesso";
    }

}
