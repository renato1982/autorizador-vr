package com.vr.autorizador.cartao.adapter.out.db;

import com.vr.autorizador.cartao.domain.Cartao;
import com.vr.autorizador.cartao.domain.CartaoDomainRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartaoRepositoryJPA extends CartaoDomainRepository, MongoRepository<Cartao, String> {

}
