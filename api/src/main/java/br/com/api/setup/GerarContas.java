package br.com.api.setup;

import br.com.api.model.Conta;
import br.com.api.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class GerarContas {


    private final ContaRepository contaRepository;

    @Autowired
    public GerarContas(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public void criar() {
        contaRepository.deleteAll();
        Conta conta = new Conta();
        conta.setSaldo(1000d);
        conta.setHash(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()));
        contaRepository.save(conta);
    }
}
