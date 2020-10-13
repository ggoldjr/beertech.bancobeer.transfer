package br.com.api.service;

import br.com.api.dto.TransacaoDto;
import br.com.api.model.Conta;
import br.com.api.model.Transacao;
import br.com.api.repository.TransacaoRepository;
import ch.qos.logback.core.net.SyslogOutputStream;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.api.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    @Autowired
    public ContaService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public List<Conta> listAll() {
        return contaRepository.findAll();
    }

    public Conta findById(Long idConta) {
        return contaRepository.findById(idConta).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public Conta findByHash(String hash) {
        return contaRepository.findByHash(hash).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public Double getSaldo(String hash) {
        Conta conta = findByHash(hash);
        return conta.getSaldo();
    }

    public Conta save(TransacaoDto request, String hash) {

        Conta conta = findByHash(hash);

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

    public Conta criarConta() {
        Conta newConta = new Conta();
        //cria um hash a partir do timestamp da criacao da conta
        newConta.setSaldo(0d);

        //Cria o HASH da conta (ex.; 00052-8)
        String newContaId = contaRepository.max().toString(); //Pega o próximo ID de conta
        //Calculo do digito verificador
        int soma = 0;
        int fator = 2;
        for (int i = newContaId.length(); i > 0; i--) {
            soma += Integer.parseInt(String.valueOf(Integer.parseInt(newContaId.substring(i-1,i)) * fator));
            fator++;
        }
        int digito = soma % 11;
        if (digito == 10) {
            digito = 0;
        }
        //Adiciona "zeros" a esquerda se necessario
        newContaId = String.format("%1$5s", newContaId).replace(' ', '0'); // 00000 (conta com 5 digitos)
        newContaId += "-" + digito; // CONTA = Id-DV (00025-3)
        newConta.setHash(newContaId);

        return contaRepository.save(newConta);
    }
}
