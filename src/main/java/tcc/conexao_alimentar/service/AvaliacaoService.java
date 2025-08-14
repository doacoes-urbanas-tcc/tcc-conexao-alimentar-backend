package tcc.conexao_alimentar.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.AvaliacaoRequestDTO;
import tcc.conexao_alimentar.DTO.AvaliacaoResponseDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.AvaliacaoMapper;
import tcc.conexao_alimentar.model.AvaliacaoModel;
import tcc.conexao_alimentar.model.ReservaModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.AvaliacaoRepository;
import tcc.conexao_alimentar.repository.ReservaRepository;
import tcc.conexao_alimentar.repository.UsuarioRepository;
@Service
@RequiredArgsConstructor
public class AvaliacaoService {
    private final AvaliacaoRepository avaliacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReservaRepository reservaRepository;

    public AvaliacaoResponseDTO avaliarPorReserva(Long reservaId, AvaliacaoRequestDTO dto) {
        ReservaModel reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada."));

        UsuarioModel avaliador = usuarioRepository.findByEmail(
            SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));

        UsuarioModel avaliado;

        if (avaliador.getTipoUsuario() == TipoUsuario.ONG) {
            avaliado = reserva.getDoacao().getDoador();
            reserva.setAvaliacaoFeitaPeloReceptor(true);
        } else if (avaliador.getTipoUsuario() == TipoUsuario.COMERCIO || avaliador.getTipoUsuario() == TipoUsuario.PRODUTOR_RURAL || avaliador.getTipoUsuario() == TipoUsuario.PESSOA_FISICA ) {
            avaliado = reserva.getReceptor();
            reserva.setAvaliacaoFeitaPeloDoador(true);
        } else {
            throw new RegraDeNegocioException("Tipo de usuário não pode fazer avaliação.");
        }

        AvaliacaoModel avaliacao = new AvaliacaoModel();
        avaliacao.setAvaliador(avaliador);
        avaliacao.setAvaliado(avaliado);
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setDataCriacao(OffsetDateTime.now());

        reservaRepository.save(reserva);
        avaliacaoRepository.save(avaliacao);

        return AvaliacaoMapper.toResponse(avaliacao);
    }

    public List<AvaliacaoResponseDTO> listarAvaliacoesDoUsuario(Long avaliadoId) {
        return avaliacaoRepository.findByAvaliadoId(avaliadoId)
            .stream()
            .map(AvaliacaoMapper::toResponse)
            .toList();
    }

    public double calcularMediaAvaliacoesVoluntario(Long voluntarioId) {
    List<AvaliacaoModel> avaliacoes = avaliacaoRepository.findByAvaliadoId(voluntarioId);
    if (avaliacoes.isEmpty()) return 0.0;

    return avaliacoes.stream()
        .mapToInt(AvaliacaoModel::getNota)
        .average()
        .orElse(0.0);
    }

}
