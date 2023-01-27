package com.vr.autorizador.cartao.app;

import com.vr.autorizador.cartao.CriarCartaoUseCase;
import com.vr.autorizador.cartao.ObterSaldoUseCase;
import com.vr.autorizador.cartao.domain.CartaoDomainRepository;
import com.vr.autorizador.cartao.exception.CartaoNaoEncontratoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
class ObterSaldoAppServiceTests {

    @Autowired
    CriarCartaoUseCase criarCartao;

    @Autowired
    ObterSaldoUseCase obterSaldo;

    @Autowired
    CartaoDomainRepository repository;

    @Test
    void consultarSaldoDoCartao() {
        var cmd = CriarCartaoUseCase.CriarCartaoCmd.builder()
            .numeroCartao("999999999999")
            .senha("1234")
            .build();

        criarCartao.handle(cmd);

        var saldo = obterSaldo.handle(ObterSaldoUseCase.ObterSaldoCmd.builder()
            .numeroCartao(cmd.getNumeroCartao())
            .build());

        assertEquals(BigDecimal.valueOf(500), saldo);

        repository.deleteByNumeroCartao("999999999999");
    }

    @Test
    void naoCriarUmCartaoRepetido() {
        assertThrows(CartaoNaoEncontratoException.class, ()-> obterSaldo.handle(ObterSaldoUseCase.ObterSaldoCmd.builder()
            .numeroCartao("")
            .build()));
    }

}
