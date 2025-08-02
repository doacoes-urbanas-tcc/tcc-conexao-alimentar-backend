package tcc.conexao_alimentar.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.UsuarioResponseDTO;
import tcc.conexao_alimentar.mapper.UsuarioMapper;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.service.DoacaoService;
import tcc.conexao_alimentar.service.TasksTiService;
import tcc.conexao_alimentar.service.UsuarioService;


@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class DashboardAdminController {

    private final UsuarioService usuarioService;
    private final DoacaoService doacaoService;
    private final TasksTiService taskTiService;


    @GetMapping("/resumo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> obterResumo() {
        Map<String, Long> resumo = new HashMap<>();

    resumo.put("usuariosAtivos", usuarioService.contarUsuariosAtivos());
    resumo.put("usuariosInativos", usuarioService.contarUsuariosInativos());
    resumo.put("doacoesAtivas", doacaoService.contarDoacoesAtivas());
    resumo.put("tasksAbertas", taskTiService.contarTasksAbertas());

    return ResponseEntity.ok(resumo);
    }
    @GetMapping("/usuarios/pendentes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUltimosPendentes() {
    List<UsuarioModel> pendentes = usuarioService.buscarUltimosPendentes();

    List<UsuarioResponseDTO> dtos = pendentes.stream()
        .map(UsuarioMapper::toResponse)
        .toList();

    return ResponseEntity.ok(dtos);
}


}



