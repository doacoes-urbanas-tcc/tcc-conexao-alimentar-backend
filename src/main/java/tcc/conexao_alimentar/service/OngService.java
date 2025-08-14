package tcc.conexao_alimentar.service;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tcc.conexao_alimentar.DTO.OngLocationDTO;
import tcc.conexao_alimentar.DTO.OngRequestDTO;
import tcc.conexao_alimentar.DTO.OngResponseDTO;
import tcc.conexao_alimentar.DTO.OngdashboardDTO;
import tcc.conexao_alimentar.enums.StatusUsuario;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.OngMapper;
import tcc.conexao_alimentar.model.OngModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.AvaliacaoRepository;
import tcc.conexao_alimentar.repository.OngRepository;
import tcc.conexao_alimentar.repository.ReservaRepository;

@Service
@RequiredArgsConstructor
public class OngService {

    private final OngRepository ongRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;
    private final ReservaRepository reservaRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    public void cadastrar(OngRequestDTO dto) {
        OngModel model = OngMapper.toEntity(dto);
        model.setSenha(passwordEncoder.encode(dto.getSenha()));
        model.setTipoUsuario(TipoUsuario.ONG);
        model.setStatus(StatusUsuario.PENDENTE);
        ongRepository.save(model);
    }

    
    @Transactional
    public void atualizarEmail(Long ongId, String novoEmail) {
        usuarioService.atualizarEmail(ongId, novoEmail);
    }

    @Transactional
    public void atualizarSenha(Long ongId, String senhaAtual,String novaSenha) {
        usuarioService.atualizarSenha(ongId, senhaAtual,novaSenha);
    }

    public OngResponseDTO visualizarPerfil(Long id) {
    UsuarioModel usuario = usuarioService.buscarPorId(id)
     .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));

    if (!(usuario instanceof OngModel)) {
        throw new RegraDeNegocioException("Usuário não é uma ong.");
    }

    OngModel ong = (OngModel) usuario;
    return OngMapper.toResponse(ong);
    }
     public Optional<OngModel> buscarPorId(Long id) {
        return ongRepository.findById(id);
    }public OngdashboardDTO getEstatisticas(Long ongId) {
    Long totalDoacoes = reservaRepository.countDoacoesRecebidasByOng(ongId);
    Double mediaAvaliacoes = avaliacaoRepository.findMediaAvaliacoesByOng(ongId);
    String nome = usuarioService.buscarNomePorId(ongId);

    return new OngdashboardDTO(nome, totalDoacoes, mediaAvaliacoes != null ? mediaAvaliacoes : 0.0);
   }
    public OngLocationDTO getOngLocation(Long idOng) {
        OngModel ong = ongRepository.findById(idOng)
                .orElseThrow(() -> new RuntimeException("ONG não encontrada"));

        if (ong.getEndereco() == null || 
            ong.getEndereco().getLatitude() == null || 
            ong.getEndereco().getLongitude() == null) {
            throw new RuntimeException("Localização da ONG não cadastrada");
        }

        return new OngLocationDTO(
                ong.getEndereco().getLatitude(),
                ong.getEndereco().getLongitude()
        );
    }



}
