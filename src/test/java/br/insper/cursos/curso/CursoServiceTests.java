package br.insper.cursos.curso;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTests {

    @InjectMocks
    private CursoService cursoService;

    @Mock
    private CursoRepository cursoRepository;

    private Curso criarCurso(Long id, String nome) {
        Curso curso = new Curso();
        curso.setId(id);
        curso.setNome(nome);
        curso.setDescricao("descricao");
        curso.setCargaHoraria(40);
        curso.setProfessor("Eduardo");
        curso.setDeletado(false);
        return curso;
    }

    @Test
    public void testListarQuandoNomeEhNulo() {
        Mockito.when(cursoRepository.findByDeletadoFalse())
                .thenReturn(List.of(criarCurso(1L, "Java")));

        List<Curso> retorno = cursoService.listar(null);

        Assertions.assertEquals(1, retorno.size());
        Assertions.assertEquals("Java", retorno.get(0).getNome());
        Mockito.verify(cursoRepository, Mockito.times(1)).findByDeletadoFalse();
    }

    @Test
    public void testListarQuandoNomeEhVazio() {
        Mockito.when(cursoRepository.findByDeletadoFalse())
                .thenReturn(List.of(criarCurso(1L, "Java"), criarCurso(2L, "Python")));

        List<Curso> retorno = cursoService.listar("   ");

        Assertions.assertEquals(2, retorno.size());
        Mockito.verify(cursoRepository, Mockito.times(1)).findByDeletadoFalse();
    }

    @Test
    public void testListarComFiltroPorNome() {
        Mockito.when(cursoRepository.findByNomeStartingWithIgnoreCaseAndDeletadoFalse("Ja"))
                .thenReturn(List.of(criarCurso(1L, "Java")));

        List<Curso> retorno = cursoService.listar("Ja");

        Assertions.assertEquals(1, retorno.size());
        Assertions.assertEquals("Java", retorno.get(0).getNome());
        Mockito.verify(cursoRepository, Mockito.never()).findByDeletadoFalse();
    }

    @Test
    public void testCadastrarCurso() {
        Curso curso = criarCurso(99L, "Docker");
        curso.setDeletado(true);

        Mockito.when(cursoRepository.save(Mockito.any(Curso.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Curso retorno = cursoService.cadastrar(curso);

        Assertions.assertNull(retorno.getId());
        Assertions.assertFalse(retorno.getDeletado());
        Assertions.assertEquals("Docker", retorno.getNome());
    }
}