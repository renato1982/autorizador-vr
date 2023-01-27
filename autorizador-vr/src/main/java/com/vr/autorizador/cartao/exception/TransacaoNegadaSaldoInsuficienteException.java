package com.vr.autorizador.cartao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class TransacaoNegadaSaldoInsuficienteException extends RuntimeException {

}
