package tcc.conexao_alimentar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.PessoaFisicaCadastroDTO;
import tcc.conexao_alimentar.service.PessoaFisicaService;

@RestController
@RequestMapping("pessoa-fisica")
@RequiredArgsConstructor
public class PessoaFisicaCadastroController {

    private final PessoaFisicaService pessoaFisicaService;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody PessoaFisicaCadastroDTO dto) {
        pessoaFisicaService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
