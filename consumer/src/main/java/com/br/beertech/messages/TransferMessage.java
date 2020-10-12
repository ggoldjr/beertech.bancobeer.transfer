package com.br.beertech.messages;

public class TransferMessage {

  private String contaCredito;
  private String contaDebito;
  private Double valor;

  public String getContaCredito() {
    return contaCredito;
  }

  public void setContaCredito(String contaCredito) {
    this.contaCredito = contaCredito;
  }

  public String getContaDebito() {
    return contaDebito;
  }

  public void setContaDebito(String contaDebito) {
    this.contaDebito = contaDebito;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }

  @Override
  public String toString() {
    return "transferMessage{" +
        "contaDebito='" + contaDebito + '\'' +
        ", contaCredito='" + contaCredito + '\'' +
        ", valor=" + valor +
        '}';
  }
}
