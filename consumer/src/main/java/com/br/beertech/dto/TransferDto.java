package com.br.beertech.dto;

public class TransferDto {

    private String contaDebito;
    private String contaCredito;
    private Double valor;

    public TransferDto(String contaDebito, String contaCredito, Double valor) {
        this.contaDebito = contaDebito;
        this.contaCredito = contaCredito;
        this.valor = valor;
    }

    public String getContaDebito() {
        return contaDebito;
    }

    public void setContaDebito(String contaDebito) {
        contaDebito = contaDebito;
    }

    public String getContaCredito() {
        return contaCredito;
    }

    public void setContaCredito(String contaCredito) {
        contaCredito = contaCredito;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
