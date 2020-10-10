package com.br.beertech.listeners;

import com.br.beertech.dto.TransacaoDto;
import com.br.beertech.messages.OperacaoMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OperacaoListener {

  private final RestTemplate restTemplate;

  @Autowired
  public OperacaoListener(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @RabbitListener(queues = "operacao",containerFactory = "simpleContainerFactory")
  public void receive(@Payload OperacaoMessage operacaoMessage){
    System.out.println("enviando requisição para conta:" + operacaoMessage.getConta());
    TransacaoDto transacaoDto = new TransacaoDto(operacaoMessage.getOperacao(),operacaoMessage.getValor());
    try{
      restTemplate.postForObject("http://localhost:8080/contas/" + operacaoMessage.getConta(), transacaoDto ,Void.class);
    }catch (Exception e){
      System.out.println("Error on try request");
    }
  }
}
