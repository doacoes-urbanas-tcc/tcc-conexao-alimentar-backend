package tcc.conexao_alimentar.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.UsuarioRepository;

@RestController
@RequestMapping("/admin/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping("/pendentes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarPendentes() {
        List<UsuarioModel> pendentes = usuarioRepository.findByAtivoFalse();
        return ResponseEntity.ok(pendentes);
    }

    @GetMapping("/pendentes/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarPendentesPorTipo() {
    List<UsuarioModel> pendentes = usuarioRepository.findByAtivoFalse();
    Map<TipoUsuario, List<UsuarioModel>> agrupados = pendentes.stream()
            .collect(Collectors.groupingBy(UsuarioModel::getTipoUsuario));
    return ResponseEntity.ok(agrupados);
}


    @PatchMapping("/aprovar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> aprovarUsuario(@PathVariable Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuário aprovado com sucesso!");
    }

}
