package com.vr.autorizador.cartao;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

public interface ObterSaldoUseCase {

    BigDecimal handle(ObterSaldoCmd cmd);

    @Value
    @Builder
    class ObterSaldoCmd {

        String numeroCartao;

    }
}
