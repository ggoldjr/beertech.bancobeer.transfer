package br.com.api.rest;

import br.com.api.dto.TransacaoDto;
import br.com.api.dto.TransferDto;
import br.com.api.model.Conta;
import br.com.api.service.ContaService;
import br.com.api.service.TransferService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transferencias")
public class TransferController {

    @Autowired
    TransferService transferService;

    @PostMapping(consumes={MediaType.APPLICATION_JSON_VALUE},
            produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="Executa transferencia.", produces="application/json")

    public ResponseEntity transferExecute(
        @ApiParam(name="transfer", required=true, value="Objeto com as contas de debito, credito e valor")
        @Valid @RequestBody TransferDto transfer
    ){
        transferService.transferExec(transfer);
        return ResponseEntity.status(201).build();
    }

}
