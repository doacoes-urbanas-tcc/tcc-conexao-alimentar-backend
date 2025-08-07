package tcc.conexao_alimentar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.DashboardVoluntarioTIResponseDTO;
import tcc.conexao_alimentar.enums.SetorTi;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.model.TaskTiModel;
import tcc.conexao_alimentar.model.VoluntarioTiModel;
import tcc.conexao_alimentar.repository.RespostaTaskTiRepository;
import tcc.conexao_alimentar.repository.TaskTiRepository;
import tcc.conexao_alimentar.repository.VoluntarioTiRepository;
@Service
@RequiredArgsConstructor
public class DashboardVoluntarioService {
    private final VoluntarioTiRepository voluntarioTiRepository;
    private final TaskTiRepository taskTiRepository;
    private final RespostaTaskTiRepository respostaTaskRepository;
    private final AvaliacaoService avaliacaoService;

    public DashboardVoluntarioTIResponseDTO gerarDashboard(Long voluntarioId) {
        VoluntarioTiModel voluntarioTi = voluntarioTiRepository.findByVoluntarioId(voluntarioId)
            .orElseThrow(() -> new RegraDeNegocioException("Voluntário TI não encontrado"));

        SetorTi setorVoluntario = voluntarioTi.getSetorTi();

        long respondidas = respostaTaskRepository.countByVoluntarioId(voluntarioId);

        List<TaskTiModel> tasksAbertas = taskTiRepository.findByFechadaFalse();
        long abertasCompativeis = tasksAbertas.stream()
            .filter(task -> !respostaTaskRepository.existsByTaskTiIdAndVoluntarioId(task.getId(), voluntarioId))
            .filter(task -> task.getTags().stream()
                .anyMatch(tag -> tag.equalsIgnoreCase(setorVoluntario.name())))
            .count();

        double mediaAvaliacoes = avaliacaoService.calcularMediaAvaliacoesVoluntario(voluntarioId);

        DashboardVoluntarioTIResponseDTO dto = new DashboardVoluntarioTIResponseDTO();
        dto.setQuantidadeTasksRespondidas(respondidas);
        dto.setQuantidadeTasksAbertasCompativeis(abertasCompativeis);
        dto.setMediaAvaliacoesRecebidas(mediaAvaliacoes);

        return dto;
    }
}
