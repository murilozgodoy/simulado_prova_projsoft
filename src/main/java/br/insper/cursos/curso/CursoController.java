package br.insper.cursos.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public List<Curso> listar(@RequestParam(required = false) String nome) {
        return cursoService.listar(nome);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Curso cadastrar(@RequestBody Curso curso) {
        return cursoService.cadastrar(curso);
    }
}