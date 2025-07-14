package tcc.conexao_alimentar.service;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tcc.conexao_alimentar.DTO.ComercioRequestDTO;
import tcc.conexao_alimentar.DTO.ComercioResponseDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.ComercioMapper;
import tcc.conexao_alimentar.model.ComercioModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.ComercioRepository;

@Service
@RequiredArgsConstructor
public class ComercioService {

    private final ComercioRepository comercioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    public void cadastrar(ComercioRequestDTO dto) {
        ComercioModel model = ComercioMapper.toEntity(dto);
        model.setSenha(passwordEncoder.encode(dto.getSenha()));
        model.setTipoUsuario(TipoUsuario.COMERCIO);
        model.setAtivo(false);
        comercioRepository.save(model);
    }

    @Transactional
    public void atualizarEmail(Long comercioId, String novoEmail) {
        usuarioService.atualizarEmail(comercioId, novoEmail);
    }

    @Transactional
    public void atualizarSenha(Long comercioId, String novaSenha) {
        usuarioService.atualizarSenha(comercioId, novaSenha);
    }

    public ComercioResponseDTO visualizarPerfil(Long id) {
    UsuarioModel usuario = usuarioService.buscarPorId(id)
     .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));

    if (!(usuario instanceof ComercioModel)) {
        throw new RegraDeNegocioException("Usuário não é um comércio.");
    }

    ComercioModel comercio = (ComercioModel) usuario;
    return ComercioMapper.toResponse(comercio);
    }

    public Optional<ComercioModel> buscarPorId(Long id) {
        return comercioRepository.findById(id);
    }

}
