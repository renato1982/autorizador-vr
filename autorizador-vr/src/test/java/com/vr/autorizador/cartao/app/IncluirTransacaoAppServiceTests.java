package com.vr.autorizador.cartao.app;

import com.vr.autorizador.cartao.CriarCartaoUseCase;
import com.vr.autorizador.cartao.CriarCartaoUseCase.CriarCartaoCmd;
import com.vr.autorizador.cartao.IncluirTransacaoUseCase;
import com.vr.autorizador.cartao.IncluirTransacaoUseCase.IncluirTransacaoCmd;
import com.vr.autorizador.cartao.domain.CartaoDomainRepository;
import com.vr.autorizador.cartao.exception.TransacaoNegadaCartaoInexistenteException;
import com.vr.autorizador.cartao.exception.TransacaoNegadaSaldoInsuficienteException;
import com.vr.autorizador.cartao.exception.TransacaoNegadaSenhaInvalidaException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
class IncluirTransacaoAppServiceTests {

    @Autowired
    CriarCartaoUseCase criarCartao;

    @Autowired
    IncluirTransacaoUseCase incluirTransacao;

    @Autowired
    CartaoDomainRepository repository;

    @Test
    void realizarUmaTransacaoComSucesso() {
        var cmdCriar = CriarCartaoCmd.builder()
            .numeroCartao("999999999999")
            .senha("1234")
            .build();

        criarCartao.handle(cmdCriar);

        var cmdIncluirTransacao = IncluirTransacaoCmd.builder()
            .numeroCartao("999999999999")
            .senhaCartao("1234")
            .valor(BigDecimal.TEN)
            .build();

        incluirTransacao.handle(cmdIncluirTransacao);

        var cartao = repository.findByNumeroCartao(cmdCriar.getNumeroCartao()).orElse(null);

        assertEquals(BigDecimal.valueOf(490), cartao.getSaldo());

        repository.deleteByNumeroCartao("999999999999");
    }

    @Test
    void naoIncluirTransacaoComCartaoInexistente() {
        var cmdIncluirTransacao = IncluirTransacaoCmd.builder()
            .numeroCartao("999999999999")
            .senhaCartao("1234")
            .valor(BigDecimal.TEN)
            .build();

        assertThrows(TransacaoNegadaCartaoInexistenteException.class, () -> incluirTransacao.handle(cmdIncluirTransacao));
    }

    @Test
    void naoIncluirTransacaoComSenhaInvalida() {
        var cmdCriar = CriarCartaoCmd.builder()
            .numeroCartao("999999999999")
            .senha("1234")
            .build();

        criarCartao.handle(cmdCriar);

        var cmdIncluirTransacao = IncluirTransacaoCmd.builder()
            .numeroCartao("999999999999")
            .senhaCartao("12345")
            .valor(BigDecimal.TEN)
            .build();

        assertThrows(TransacaoNegadaSenhaInvalidaException.class, () -> incluirTransacao.handle(cmdIncluirTransacao));

        repository.deleteByNumeroCartao("999999999999");
    }

    @Test
    void naoIncluirTransacaoComSaldoInsuficiente() {
        var cmdCriar = CriarCartaoCmd.builder()
            .numeroCartao("999999999999")
            .senha("1234")
            .build();

        criarCartao.handle(cmdCriar);

        var cmdIncluirTransacao = IncluirTransacaoUseCase.IncluirTransacaoCmd.builder()
            .numeroCartao("999999999999")
            .senhaCartao("1234")
            .valor(BigDecimal.valueOf(600))
            .build();

        assertThrows(TransacaoNegadaSaldoInsuficienteException.class, () -> incluirTransacao.handle(cmdIncluirTransacao));

        repository.deleteByNumeroCartao("999999999999");
    }

}
