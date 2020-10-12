package br.com.api.service;

import br.com.api.dto.TransacaoDto;
import br.com.api.dto.TransferDto;
import br.com.api.model.Conta;
import br.com.api.model.Transacao;
import br.com.api.repository.ContaRepository;
import br.com.api.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransferService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    @Autowired
    public TransferService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public Conta findByHash(String hash) {
        return contaRepository.findByHash(hash).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public Double getSaldo(String hash) {
        Conta conta = findByHash(hash);
        return conta.getSaldo();
    }

    public Conta save(TransacaoDto request, String hash) {

        Conta conta = contaRepository.findByHash(hash).orElseThrow(() -> new RuntimeException("Not Found"));

        Double valor= request.getValor();

        Transacao.Operacao operacao = Transacao.Operacao.valueOf(request.getOperacao().toUpperCase());

        if(operacao == Transacao.Operacao.SAQUE) {
            if (conta.getSaldo() < valor) throw new RuntimeException("Saldo insuficiente");
            conta.saque(valor);
        }

        if (operacao == Transacao.Operacao.DEPOSITO) conta.deposito(valor);

        Transacao transacao= Transacao
                .builder()
                .conta(conta)
                .operacao(operacao)
                .valor(request.getValor())
                .dataOperacao(LocalDateTime.now())
                .build();

        conta.getTransacao().add(transacao);
        transacaoRepository.save(transacao);
        return contaRepository.save(conta);
    }

    public void transferExec(TransferDto transfer) {
        //Verifica se as contas existem
        Conta contaDebito = contaRepository.findByHash(transfer.getContaDebito()).orElseThrow(() -> new RuntimeException("contaDebito Not Found"));
        Conta contaCredito = contaRepository.findByHash(transfer.getContaCredito()).orElseThrow(() -> new RuntimeException("contaCredito Not Found"));

        //Verifica se a conta de debito tem saldo
        if(contaDebito.getSaldo() < transfer.getValor()){
            new RuntimeException("Saldo insuficiente...");
        }

        //executa o debito e o credito
        TransacaoDto debito = new TransacaoDto();
        debito.setOperacao("saque");
        debito.setValor(transfer.getValor());
        save(debito,transfer.getContaDebito());

        TransacaoDto credito = new TransacaoDto();
        credito.setOperacao("deposito");
        credito.setValor(transfer.getValor());
        save(credito,transfer.getContaCredito());
    }
}
