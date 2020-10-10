package br.com.beertech.listener;

import static org.mockito.ArgumentMatchers.eq;

import com.br.beertech.dto.TransacaoDto;
import com.br.beertech.listeners.OperacaoListener;
import com.br.beertech.messages.OperacaoMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class OperacaoListenerTests {

  public static final String URL = "http://localhost:8080/contas/";

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private OperacaoListener operacaoListener;

  @Test
  public void sendValidMessage() {
    OperacaoMessage operacaoMessageMock = new OperacaoMessage();
    operacaoMessageMock.setConta("001");
    operacaoMessageMock.setOperacao("DEPOSITO");
    operacaoMessageMock.setValor(10.01);

    ArgumentCaptor<TransacaoDto> argumentCaptorTransacao = ArgumentCaptor
        .forClass(TransacaoDto.class);

    operacaoListener.receive(operacaoMessageMock);

    Mockito.verify(restTemplate, Mockito.times(1))
        .postForObject(eq(URL + operacaoMessageMock.getConta()), argumentCaptorTransacao.capture(),
            eq(Void.class));

    TransacaoDto resultTransacao = argumentCaptorTransacao.getValue();

    Assert.assertEquals(operacaoMessageMock.getOperacao(), resultTransacao.getOperacao());
    Assert.assertEquals(operacaoMessageMock.getValor(), resultTransacao.getValor());

  }
}
