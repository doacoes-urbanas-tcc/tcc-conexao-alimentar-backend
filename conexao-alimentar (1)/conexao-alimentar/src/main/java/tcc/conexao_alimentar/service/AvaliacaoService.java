package tcc.conexao_alimentar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.AvaliacaoRequestDTO;
import tcc.conexao_alimentar.DTO.AvaliacaoResponseDTO;
import tcc.conexao_alimentar.mapper.AvaliacaoMapper;
import tcc.conexao_alimentar.model.AvaliacaoModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.AvaliacaoRepository;
import tcc.conexao_alimentar.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {
    private final AvaliacaoRepository avaliacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public AvaliacaoResponseDTO avaliar(Long avaliadoId, AvaliacaoRequestDTO dto) {
        UsuarioModel avaliador = obterUsuarioLogado();

        UsuarioModel avaliado = usuarioRepository.findById(avaliadoId).orElseThrow(() -> new RuntimeException("Avaliado não encontrado"));

        AvaliacaoModel avaliacao = AvaliacaoMapper.toEntity(dto, avaliador, avaliado);
        avaliacaoRepository.save(avaliacao);

        return AvaliacaoMapper.toResponse(avaliacao);
    }

    public List<AvaliacaoResponseDTO> listarAvaliacoesDoUsuario(Long avaliadoId) {
        return avaliacaoRepository.findByAvaliadoId(avaliadoId).stream()
                .map(AvaliacaoMapper::toResponse)
                .collect(Collectors.toList());
    }

    private UsuarioModel obterUsuarioLogado() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.toString(); 
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário logado não encontrado"));
    }

}
