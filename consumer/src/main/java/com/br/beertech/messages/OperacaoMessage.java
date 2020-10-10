package com.br.beertech.messages;

public class OperacaoMessage {

  private String conta;
  private String operacao;
  private Double valor;

  public String getConta() {
    return conta;
  }

  public void setConta(String conta) {
    this.conta = conta;
  }

  public String getOperacao() {
    return operacao;
  }

  public void setOperacao(String operacao) {
    this.operacao = operacao;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }

  @Override
  public String toString() {
    return "OperacaoMessage{" +
        "conta='" + conta + '\'' +
        ", operacao='" + operacao + '\'' +
        ", valor=" + valor +
        '}';
  }
}
