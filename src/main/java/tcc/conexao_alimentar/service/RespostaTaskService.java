package tcc.conexao_alimentar.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.enums.StatusResposta;
import tcc.conexao_alimentar.model.RespostaTaskModel;
import tcc.conexao_alimentar.model.TaskTiModel;
import tcc.conexao_alimentar.model.VoluntarioModel;
import tcc.conexao_alimentar.repository.RespostaTaskTiRepository;
import tcc.conexao_alimentar.repository.TaskTiRepository;
import tcc.conexao_alimentar.repository.VoluntarioRepository;

@Service
@RequiredArgsConstructor
public class RespostaTaskService {

    private final RespostaTaskTiRepository respostaRepo;

    private final TaskTiRepository taskRepo;

    private  final VoluntarioRepository voluntarioRepo;

    public RespostaTaskModel responder(Long taskId, Long voluntarioId, String link) {
        if (respostaRepo.existsByTaskTiIdAndVoluntarioId(taskId, voluntarioId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você já respondeu essa task.");
        }

        TaskTiModel task = taskRepo.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task não encontrada"));

        VoluntarioModel voluntario = voluntarioRepo.findById(voluntarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voluntário não encontrado"));

        RespostaTaskModel resposta = new RespostaTaskModel();
        resposta.setTaskTi(task);
        resposta.setVoluntario(voluntario);
        resposta.setLinkSolucao(link);
        resposta.setDataResposta(LocalDateTime.now());
        resposta.setStatus(StatusResposta.PENDENTE);

        return respostaRepo.save(resposta);
    }

    public List<RespostaTaskModel> listarRespostasDaTask(Long taskId) {
        return respostaRepo.findByTaskTiId(taskId);
    }

    public void atualizarStatus(Long respostaId, StatusResposta novoStatus) {
        RespostaTaskModel resposta = respostaRepo.findById(respostaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada"));
        resposta.setStatus(novoStatus);
        respostaRepo.save(resposta);
    }

    public List<RespostaTaskModel> listarPorVoluntario(Long voluntarioId) {
        return respostaRepo.findByVoluntarioId(voluntarioId);
    }

}
