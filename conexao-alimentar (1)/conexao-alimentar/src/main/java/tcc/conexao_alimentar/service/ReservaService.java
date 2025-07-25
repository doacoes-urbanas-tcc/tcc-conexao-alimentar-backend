package tcc.conexao_alimentar.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.zxing.WriterException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.QrCodeDTO;
import tcc.conexao_alimentar.DTO.ReservaRequestDTO;
import tcc.conexao_alimentar.DTO.ReservaResponseDTO;
import tcc.conexao_alimentar.enums.StatusDoacao;
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
    private final QrCodeService qrCodeService;

    private void validarDisponibilidadeDoacao(DoacaoModel doacao) {
   if (doacao.getStatus() == StatusDoacao.EXPIRADA) {
       throw new RegraDeNegocioException("Doação expirada.");
   }
   if (doacao.getStatus() != StatusDoacao.PENDENTE) {
       throw new RegraDeNegocioException("Doação indisponível para reserva.");
   }
   }
    @Transactional
    public void cadastrar(ReservaRequestDTO dto) {
    DoacaoModel doacao = doacaoRepository.findById(dto.getDoacaoId())
        .orElseThrow(() -> new RegraDeNegocioException("Doação não encontrada."));

    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    UsuarioModel beneficiario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new RegraDeNegocioException("Beneficiário não encontrado."));

    validarDisponibilidadeDoacao(doacao);
    doacao.setStatus(StatusDoacao.AGUARDANDO_RETIRADA);
    ReservaModel reserva = ReservaMapper.toEntity(dto, doacao, beneficiario);
    reservaRepository.save(reserva);
    QrCodeDTO qrCodeDTO = new QrCodeDTO();
    qrCodeDTO.setDoacaoId(doacao.getId());
    qrCodeDTO.setNomeAlimento(doacao.getNomeAlimento());
    qrCodeDTO.setUnidadeMedida(doacao.getUnidadeMedida().toString());
    qrCodeDTO.setQuantidade(doacao.getQuantidade());
    qrCodeDTO.setDataValidade(doacao.getDataValidade().toString());
    qrCodeDTO.setDescricao(doacao.getDescricao());
    qrCodeDTO.setCategoria(doacao.getCategoria().toString());


    try {
    qrCodeService.generateQRCodeAndUpload(qrCodeDTO, doacao.getId());
   } catch (WriterException | IOException e) {
    throw new RuntimeException("Erro ao gerar QR Code", e);
   }


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
