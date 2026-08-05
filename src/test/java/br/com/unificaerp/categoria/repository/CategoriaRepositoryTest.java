package br.com.unificaerp.categoria.repository;

import br.com.unificaerp.categoria.model.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CategoriaRepositoryTest {

    private CategoriaRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(CategoriaRepository.class);
    }

    @Test
    @DisplayName("Deve retornar todas as categorias da empresa")
    void findAll() {

        Categoria categoria = new Categoria();

        when(repository.findAll(1L))
                .thenReturn(List.of(categoria));

        List<Categoria> resultado = repository.findAll(1L);

        assertThat(resultado)
                .hasSize(1)
                .contains(categoria);

        verify(repository).findAll(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Deve buscar categoria por nome")
    void buscaPorNome() {

        Categoria categoria = new Categoria();

        when(repository.buscaPorNome("Bebidas", 1L))
                .thenReturn(List.of(categoria));

        List<Categoria> resultado =
                repository.buscaPorNome("Bebidas", 1L);

        assertThat(resultado)
                .hasSize(1);

        verify(repository).buscaPorNome("Bebidas", 1L);
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir categoria")
    void existePorNome() {

        when(repository.existePorNome("Bebidas", 1L))
                .thenReturn(true);

        boolean resultado =
                repository.existePorNome("Bebidas", 1L);

        assertThat(resultado).isTrue();

        verify(repository).existePorNome("Bebidas", 1L);
    }

    @Test
    @DisplayName("Deve verificar existência por nome diferente do id")
    void existePorNomeDiferenteId() {

        when(repository.existePorNomeDiferenteId(2L, "Bebidas", 1L))
                .thenReturn(true);

        boolean resultado =
                repository.existePorNomeDiferenteId(2L, "Bebidas", 1L);

        assertThat(resultado).isTrue();

        verify(repository).existePorNomeDiferenteId(2L, "Bebidas", 1L);
    }

    @Test
    @DisplayName("Deve excluir categoria")
    void deleteById() {

        doNothing().when(repository)
                .deleteById(1L, 1L);

        repository.deleteById(1L, 1L);

        verify(repository).deleteById(1L, 1L);
    }
}