package br.com.api.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class TransferDto {

    private String contaDebito;
    private String contaCredito;
    @Min(value = 0, message = "valor inválido!")
    private Double valor;

}
