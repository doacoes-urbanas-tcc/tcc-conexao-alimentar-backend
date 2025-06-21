package tcc.conexao_alimentar.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcc.conexao_alimentar.DTO.OngCadastroDTO;
import tcc.conexao_alimentar.service.OngService;

@RestController
@RequestMapping("/ong")
@RequiredArgsConstructor
public class OngController {

    
    private final OngService ongService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody OngCadastroDTO dto) {
        ongService.cadastrar(dto);
        return ResponseEntity.ok("ONG cadastrada com sucesso! Aguarde aprovação.");
    }

}
