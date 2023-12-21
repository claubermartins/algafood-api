package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTests {

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Test   //when then
    public void whenCadastrarCozinha_ThenDeveAtribuirId() {
        //Happy path (caminho feliz)
        // cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        // ação
        novaCozinha = cadastroCozinha.salvar(novaCozinha);

        // validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test()
    public void whenCadastrarCozinhaSemNome_ThenDeveFalhar() {
        //Unhappy path (caminho infeliz)
        //cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        //ação
        ConstraintViolationException erroEsperado =
                Assertions.assertThrows(ConstraintViolationException.class, () -> {
                    cadastroCozinha.salvar(novaCozinha);
                });

        // validação
        assertThat(erroEsperado).isNotNull().hasMessageContaining(erroEsperado.getMessage());

    }

    @Test
    public void whenExcluirCozinhaEmUso_ThenDeveFalhar() {
        // Cenário
        Cozinha cozinhaEmUso = new Cozinha();
        cozinhaEmUso.setId(1L);
        cozinhaEmUso.setNome("Chinesa");

        // Ação
        EntidadeEmUsoException erroEsperado = Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
            cadastroCozinha.excluir(1L);
        });

        // Validação
        assertThat(erroEsperado).isNotNull().hasMessageContaining(erroEsperado.getMessage());
    }

    @Test
    public void whenExcluirCozinhaInexistente_ThenDeveFalhar() {
        Cozinha cozinhaInexistente = new Cozinha();
        cozinhaInexistente.setId(1L);
        cozinhaInexistente.setNome("Brasileira");

        EntidadeNaoEncontradaException erroEsperado = Assertions.assertThrows(EntidadeNaoEncontradaException.class, () ->{
            cadastroCozinha.excluir(100L);
        });

        assertThat(erroEsperado).isNotNull().hasMessageContaining(erroEsperado.getMessage());
    }

}
