package tcc.conexao_alimentar.service;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tcc.conexao_alimentar.DTO.ProdutorRuralRequestDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralResponseDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.ProdutorRuralMapper;
import tcc.conexao_alimentar.model.ProdutorRuralModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.ProdutorRuralRepository;

@Service
@RequiredArgsConstructor
public class ProdutorRuralService {

    private final ProdutorRuralRepository produtorRuralRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    public void cadastrar(ProdutorRuralRequestDTO dto) {
        ProdutorRuralModel model = ProdutorRuralMapper.toEntity(dto);
        model.setSenha(passwordEncoder.encode(dto.getSenha()));
        model.setTipoUsuario(TipoUsuario.PRODUTOR_RURAL);
        model.setAtivo(false);
        produtorRuralRepository.save(model);
    }

    
    @Transactional
    public void atualizarEmail(Long prId, String novoEmail) {
        usuarioService.atualizarEmail(prId, novoEmail);
    }

    @Transactional
    public void atualizarSenha(Long prId, String novaSenha) {
        usuarioService.atualizarSenha(prId, novaSenha);
    }

    public ProdutorRuralResponseDTO visualizarPerfil(Long id) {
    UsuarioModel usuario = usuarioService.buscarPorId(id)
     .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));

    if (!(usuario instanceof ProdutorRuralModel)) {
        throw new RegraDeNegocioException("Usuário não é um produtor rural.");
    }

    ProdutorRuralModel pr= (ProdutorRuralModel) usuario;
    return ProdutorRuralMapper.toResponse(pr);
   }
   public Optional<ProdutorRuralModel> buscarPorId(Long id) {
        return produtorRuralRepository.findById(id);
    }

    

}
