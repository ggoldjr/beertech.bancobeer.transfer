package br.com.api.rest;

import br.com.api.dto.TransacaoDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import br.com.api.model.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import br.com.api.service.ContaService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    ContaService contaService;

    @GetMapping(produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="Lista as contas disponíveis.", produces="application/json")
    public List<Conta> listAll(){
        return contaService.listAll();
    }

    @PostMapping(path = "/{hash}",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="Insere operação a conta.", produces="application/json")

    public Conta addTransacao(
            @ApiParam(name="hash", required=true, value="Hash de conta", example="1")
            @PathVariable String hash,
            @ApiParam(name="request", required=true, value="Objeto com as reservas a serem criadas/atualizadas")
            @Valid @RequestBody TransacaoDto request
            ){

            return contaService.save(request,hash);
    }

    @GetMapping(path = "/{hash}/saldos",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="Retorna.", produces="application/json")
    public Double getSaldo(@ApiParam(name="hash", required=true, value="Hash de conta", example="1") @PathVariable String hash){
        return contaService.getSaldo(hash);
    }

    @PostMapping(consumes={MediaType.APPLICATION_JSON_VALUE},
            produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="Criar conta.", produces="application/json")

    public Conta criarConta(){
        return contaService.criarConta();
    }

}
