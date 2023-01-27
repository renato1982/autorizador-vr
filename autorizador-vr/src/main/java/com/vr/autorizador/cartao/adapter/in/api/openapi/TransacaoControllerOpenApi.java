package com.vr.autorizador.cartao.adapter.in.api.openapi;

import com.vr.autorizador.cartao.IncluirTransacaoUseCase.IncluirTransacaoCmd;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface TransacaoControllerOpenApi {

    @PostMapping
    @Operation(description = "Realizar uma Transação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "OK"),
        @ApiResponse(responseCode = "422", description = "SALDO_INSUFICIENTE"),
        @ApiResponse(responseCode = "422", description = "SENHA_INVALIDA"),
        @ApiResponse(responseCode = "422", description = "CARTAO_INEXISTENTE"),
    })
    ResponseEntity<Void> transacionar(@RequestBody @Valid IncluirTransacaoCmd cmd);

}
