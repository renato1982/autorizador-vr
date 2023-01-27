package com.vr.autorizador.cartao.app;

import com.vr.autorizador.cartao.ObterSaldoUseCase;
import com.vr.autorizador.cartao.domain.CartaoDomainRepository;
import com.vr.autorizador.cartao.exception.CartaoNaoEncontratoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Transactional
@Service
public class ObterSaldoAppService implements ObterSaldoUseCase {

    private final CartaoDomainRepository repository;

    @Override
    public BigDecimal handle(ObterSaldoCmd cmd) {
        return repository.findByNumeroCartao(cmd.getNumeroCartao())
            .orElseThrow(CartaoNaoEncontratoException::new).getSaldo();
    }
}
