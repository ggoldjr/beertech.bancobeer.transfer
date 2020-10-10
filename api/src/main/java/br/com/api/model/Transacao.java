package br.com.api.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Builder
public class Transacao implements Serializable {

    public enum Operacao{DEPOSITO,SAQUE}


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double valor;

    @JoinTable(name = "tipo_operacao", joinColumns = @JoinColumn(name = "transacao_id"))
    @Enumerated(EnumType.STRING)
    private Operacao operacao;

    private LocalDateTime dataOperacao;

    @ManyToOne
    @JoinColumn(name = "conta_id" )
    private Conta conta;

    public Transacao(Long id, double valor, Operacao operacao, LocalDateTime dataOperacao, Conta conta) {
        this.id = id;
        this.valor = valor;
        this.operacao = operacao;
        this.dataOperacao = dataOperacao;
        this.conta = conta;
    }
}
