package com.br.beertech.listeners;

import com.br.beertech.dto.TransferDto;
import com.br.beertech.messages.TransferMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TransferListener {

  private final RestTemplate restTemplate;

  @Autowired
  public TransferListener(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @RabbitListener(queues = "transfer",containerFactory = "simpleContainerFactory")
  public void receive(@Payload TransferMessage transferMessage){
    System.out.println("enviando requisição de transferencia da conta:"
            + transferMessage.getContaDebito() + " para a conta "
            + transferMessage.getContaCredito());
    TransferDto transferDto = new TransferDto(transferMessage.getContaDebito(),transferMessage.getContaCredito(),transferMessage.getValor());
    try{
      restTemplate.postForObject("http://localhost:8080/transferencias/", transferDto ,Void.class);
    }catch (Exception e){
      System.out.println("Error on try request");
    }
  }
}
