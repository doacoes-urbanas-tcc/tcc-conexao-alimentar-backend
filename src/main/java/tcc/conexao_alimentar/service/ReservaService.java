package tcc.conexao_alimentar.service;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
import tcc.conexao_alimentar.enums.TipoUsuario;
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
        if (reservaRepository.existsByDoacao(doacao)) {
            throw new RegraDeNegocioException("Esta doação já foi reservada.");
        }
    }

    @Transactional
    public void cadastrar(ReservaRequestDTO dto) {
        DoacaoModel doacao = doacaoRepository.findById(dto.getDoacaoId())
            .orElseThrow(() -> new RegraDeNegocioException("Doação não encontrada."));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioModel receptor = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RegraDeNegocioException("Receptor não encontrado."));

        validarDisponibilidadeDoacao(doacao);

        doacao.setStatus(StatusDoacao.AGUARDANDO_RETIRADA);
        doacaoRepository.save(doacao);

        ReservaModel reserva = ReservaMapper.toEntity(dto, doacao, receptor);

        reserva.setDataReserva(OffsetDateTime.now(ZoneOffset.UTC));       
        reserva.setAvaliacaoFeitaPeloDoador(false);
        reserva.setAvaliacaoFeitaPeloReceptor(false);
        reserva.setDataHoraExpiracao(OffsetDateTime.now().plusHours(2));


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
            String url = qrCodeService.generateQRCodeAndUpload(qrCodeDTO, doacao.getId());
            reserva.setUrlQrCode(url);
            reservaRepository.save(reserva);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Erro ao gerar QR Code", e);
        }
    }

    public List<ReservaResponseDTO> listarTodas() {
        return reservaRepository.findAll().stream()
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
            if (reserva.getDoacao().getDataExpiracao().isBefore(OffsetDateTime.now())) {
                reserva.setStatus(StatusReserva.EXPIRADA);
                reservaRepository.save(reserva);
            }
        });
    }

    public List<ReservaResponseDTO> listarReservasDoReceptor() {
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioModel receptor = usuarioRepository.findByEmail(emailUsuario)
            .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));

        return reservaRepository.findByReceptorId(receptor.getId()).stream()
            .map(ReservaMapper::toResponse)
            .collect(Collectors.toList());
    }public List<ReservaResponseDTO> listarReservasParaAvaliar() {
    String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
    UsuarioModel usuario = usuarioRepository.findByEmail(emailUsuario)
        .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));

    List<ReservaModel> todasReservas = reservaRepository.findAll();

    List<ReservaModel> reservasFiltradas;

    if (usuario.getTipoUsuario() == TipoUsuario.ONG) {
        reservasFiltradas = todasReservas.stream()
            .filter(reserva -> reserva.getDoacao().getStatus() == StatusDoacao.CONCLUIDA)
            .filter(reserva -> reserva.getReceptor().getId().equals(usuario.getId()))
            .filter(reserva -> Boolean.FALSE.equals(reserva.getAvaliacaoFeitaPeloReceptor()))
            .collect(Collectors.toList());
    } else if (usuario.getTipoUsuario() == TipoUsuario.COMERCIO) {
        reservasFiltradas = todasReservas.stream()
            .filter(reserva -> reserva.getDoacao().getStatus() == StatusDoacao.CONCLUIDA)
            .filter(reserva -> reserva.getDoacao().getDoador().getId().equals(usuario.getId()))
            .filter(reserva -> Boolean.FALSE.equals(reserva.getAvaliacaoFeitaPeloDoador()))
            .collect(Collectors.toList());
    } else {
        throw new RegraDeNegocioException("Usuário não autorizado a avaliar.");
    }

    return reservasFiltradas.stream()
        .map(ReservaMapper::toResponse)
        .collect(Collectors.toList());
    }
    public ReservaResponseDTO buscarProximaReservaParaAvaliar() {
    List<ReservaResponseDTO> pendentes = listarReservasParaAvaliar()
        .stream()
        .filter(reserva -> reserva.getStatus() == StatusReserva.RETIRADA)
        .collect(Collectors.toList());

    if (pendentes.isEmpty()) {
        return null;
    }

    return pendentes.get(0); 
}



}
