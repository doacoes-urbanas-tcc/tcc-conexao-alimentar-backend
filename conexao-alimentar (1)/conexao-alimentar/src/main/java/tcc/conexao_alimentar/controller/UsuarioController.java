package tcc.conexao_alimentar.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.ComercioCadastroDTO;
import tcc.conexao_alimentar.DTO.ComercioResponseDTO;
import tcc.conexao_alimentar.DTO.OngCadastroDTO;
import tcc.conexao_alimentar.DTO.OngResponseDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaCadastroDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaResponseDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralCadastroDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralResponseDTO;
import tcc.conexao_alimentar.DTO.VoluntarioCadastroDTO;
import tcc.conexao_alimentar.DTO.VoluntarioResponseDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping("/pessoa-fisica")
    public ResponseEntity<PessoaFisicaResponseDTO> cadastrarPessoaFisica(@RequestBody PessoaFisicaCadastroDTO dto) {
        return ResponseEntity.ok(usuarioService.cadastrarPessoaFisica(dto));
    }

    @PostMapping("/voluntario")
    public ResponseEntity<VoluntarioResponseDTO> cadastrarVoluntario(@RequestBody VoluntarioCadastroDTO dto) {
        return ResponseEntity.ok(usuarioService.cadastrarVoluntario(dto));
    }

    @PostMapping("/comercio")
    public ResponseEntity<ComercioResponseDTO> cadastrarComercio(@RequestBody ComercioCadastroDTO dto) {
        return ResponseEntity.ok(usuarioService.cadastrarComercio(dto));
    }

    @PostMapping("/produtor-rural")
    public ResponseEntity<ProdutorRuralResponseDTO> cadastrarProdutorRural(@RequestBody ProdutorRuralCadastroDTO dto) {
        return ResponseEntity.ok(usuarioService.cadastrarProdutorRural(dto));
    }

    @PostMapping("/ong")
    public ResponseEntity<OngResponseDTO> cadastrarOng(@RequestBody OngCadastroDTO dto) {
        return ResponseEntity.ok(usuarioService.cadastrarOng(dto));
    }

    @GetMapping("/{tipo}/{id}")
    public ResponseEntity<UsuarioModel> buscarUsuarioPorId(
            @PathVariable TipoUsuario tipo,
            @PathVariable Long id) {
        Optional<UsuarioModel> usuario = usuarioService.buscarPorId(id, tipo);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
