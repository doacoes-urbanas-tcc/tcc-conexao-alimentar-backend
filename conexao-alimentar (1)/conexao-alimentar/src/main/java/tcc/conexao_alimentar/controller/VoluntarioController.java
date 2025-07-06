package tcc.conexao_alimentar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tcc.conexao_alimentar.DTO.VeiculoRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioRequestDTO;
import tcc.conexao_alimentar.service.VeiculoService;
import tcc.conexao_alimentar.service.VoluntarioService;

@RestController
@RequestMapping("/voluntario")
@RequiredArgsConstructor
public class VoluntarioController {

    private final VoluntarioService voluntarioService;
    private final VeiculoService veiculoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody VoluntarioRequestDTO dto) {
        voluntarioService.cadastrar(dto);
        return ResponseEntity.ok("Voluntário cadastrado com sucesso! Aguarde aprovação.");
    }

    @PostMapping("/{voluntarioId}/veiculo")
    public ResponseEntity<String> cadastrarVeiculo(@PathVariable Long voluntarioId,@RequestBody VeiculoRequestDTO dto) {
        dto.setVoluntarioId(voluntarioId);  
        veiculoService.cadastrarVeiculo(dto);
        return ResponseEntity.ok("Veículo cadastrado com sucesso para voluntário!");
    }

   

}
