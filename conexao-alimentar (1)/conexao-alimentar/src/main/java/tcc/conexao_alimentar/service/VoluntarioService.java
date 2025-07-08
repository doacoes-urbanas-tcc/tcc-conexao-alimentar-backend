package tcc.conexao_alimentar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.VoluntarioRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioResponseDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.VoluntarioMapper;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.model.VoluntarioModel;
import tcc.conexao_alimentar.repository.VoluntarioRepository;

@Service
@RequiredArgsConstructor
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;
    private final PasswordEncoder passwordEncoder;
    private UsuarioService usuarioService;

    public void cadastrar(VoluntarioRequestDTO dto) {
        VoluntarioModel model = VoluntarioMapper.toEntity(dto);
        model.setSenha(passwordEncoder.encode(dto.getSenha()));
        model.setTipoUsuario(TipoUsuario.VOLUNTARIO);
        model.setAtivo(false);
        voluntarioRepository.save(model);
    }

    public List<VoluntarioResponseDTO> listarTodos() {
        return voluntarioRepository.findAll()         
            .stream()                              
            .map(VoluntarioMapper::toResponse)        
            .collect(Collectors.toList());         
    }

    public VoluntarioResponseDTO buscarPorId(Long id) {
    VoluntarioModel voluntario = voluntarioRepository.findById(id)
        .orElseThrow(() -> new RegraDeNegocioException("Voluntário não encontrado"));
     return VoluntarioMapper.toResponse(voluntario);
    }
    
    @Transactional
    public void atualizarEmail(Long voluntarioId, String novoEmail) {
        usuarioService.atualizarEmail(voluntarioId, novoEmail);
    }

    @Transactional
    public void atualizarSenha(Long voluntarioId, String novaSenha) {
        usuarioService.atualizarSenha(voluntarioId, novaSenha, passwordEncoder);
    }

    public VoluntarioResponseDTO visualizarPerfil(Long id) {
    UsuarioModel usuario = usuarioService.buscarPorId(id)
     .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));

    if (!(usuario instanceof VoluntarioModel)) {
        throw new RegraDeNegocioException("Usuário não é um voluntário.");
    }

   VoluntarioModel voluntario= (VoluntarioModel) usuario;
    return VoluntarioMapper.toResponse(voluntario);
}
    
}
