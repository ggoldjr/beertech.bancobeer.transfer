package br.com.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 0)
    private Double saldo;

    @OneToMany(mappedBy =  "conta")
    @JsonIgnore
    private List<Transacao> transacao;

    public Conta(Long id, Double saldo, List<Transacao> transacao) {
        this.id = id;
        this.saldo = saldo;
        this.transacao = new ArrayList<>();
    }

    public Double getSaldo() {
        return Optional.ofNullable(saldo).orElse(0d);
    }

    public void saque(Double valor) {
        this.setSaldo(this.saldo + valor *-1);
    }

    public void deposito(Double valor) {
        this.setSaldo(this.saldo + valor);
    }

}
