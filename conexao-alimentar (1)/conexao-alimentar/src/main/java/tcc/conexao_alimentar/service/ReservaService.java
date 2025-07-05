package tcc.conexao_alimentar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.ReservaRequestDTO;
import tcc.conexao_alimentar.DTO.ReservaResponseDTO;
import tcc.conexao_alimentar.enums.StatusReserva;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.ReservaMapper;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.ReservaModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.DoacaoRepository;
import tcc.conexao_alimentar.repository.ReservaRepository;
import tcc.conexao_alimentar.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class ReservaService {


    private final ReservaRepository reservaRepository;
    private final DoacaoRepository doacaoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public void cadastrar(ReservaRequestDTO dto) {
        DoacaoModel doacao = doacaoRepository.findById(dto.getDoacaoId())
            .orElseThrow(() -> new RegraDeNegocioException("Doação não encontrada."));

        UsuarioModel beneficiario = usuarioRepository.findById(dto.getBeneficiarioId())
            .orElseThrow(() -> new RegraDeNegocioException("Beneficiário não encontrado."));

        ReservaModel reserva = ReservaMapper.toEntity(dto, doacao, beneficiario);
        reservaRepository.save(reserva);
    }

    public List<ReservaResponseDTO> listarTodas() {
        return reservaRepository.findAll()
                .stream()
                .map(ReservaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ReservaResponseDTO buscarPorId(Long id) {
        ReservaModel reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada."));
        return ReservaMapper.toResponse(reserva);
    }

    @Transactional
    public void cancelarReserva(Long id, String justificativa) {
        ReservaModel reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada."));

        if (reserva.getStatus() == StatusReserva.CANCELADA) {
            throw new RegraDeNegocioException("Reserva já cancelada.");
        }

        reserva.setStatus(StatusReserva.CANCELADA);
        reserva.setJustificativaCancelamento(justificativa);
        reservaRepository.save(reserva);
    }

    @Transactional
    public void verificarReservasExpiradas() {
        List<ReservaModel> reservas = reservaRepository.findAll();
        reservas.forEach(reserva -> {
            if (reserva.getDoacao().getDataExpiracao().isBefore(java.time.LocalDateTime.now())) {
                reserva.setStatus(StatusReserva.EXPIRADA);
                reservaRepository.save(reserva);
            }
        });
    }

}
