package tcc.conexao_alimentar.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.enums.StatusDoacao;
import tcc.conexao_alimentar.enums.StatusReserva;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.ReservaModel;
import tcc.conexao_alimentar.repository.DoacaoRepository;
import tcc.conexao_alimentar.repository.ReservaRepository;

@Component
@RequiredArgsConstructor
public class DoacaoSheduler {
    private final DoacaoRepository doacaoRepository;
    private final ReservaRepository reservaRepository;

    @Scheduled(fixedRate = 60 * 60 *  1000) 
    public void verificarExpiracao() {
        LocalDateTime agora = LocalDateTime.now();

        List<DoacaoModel> doacoesPendentes = doacaoRepository.findByStatus(StatusDoacao.PENDENTE);
        for (DoacaoModel doacao : doacoesPendentes) {
            Duration duracao = Duration.between(doacao.getDataCadastro(), agora);
            if (duracao.toHours() >= 48) {
                doacao.setStatus(StatusDoacao.EXPIRADA);
                doacaoRepository.save(doacao);
                System.out.println("Doação expirada ID: " + doacao.getId());
            }
        }

        List<ReservaModel> reservas = reservaRepository.findByStatus(StatusReserva.RESERVADA);
        for (ReservaModel reserva : reservas) {
            Duration duracao = Duration.between(reserva.getDataReserva(), agora);
            if (duracao.toHours() >= 48) {
                reserva.setStatus(StatusReserva.EXPIRADA);
                reservaRepository.save(reserva);
                System.out.println("Reserva expirada ID: " + reserva.getId());
            }
        }
    }

}
