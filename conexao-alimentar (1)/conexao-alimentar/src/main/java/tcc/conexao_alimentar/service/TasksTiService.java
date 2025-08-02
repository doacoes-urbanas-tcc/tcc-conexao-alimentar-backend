package tcc.conexao_alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import tcc.conexao_alimentar.DTO.TaskTiRequestDTO;
import tcc.conexao_alimentar.mapper.TaskTiMapper;
import tcc.conexao_alimentar.model.TaskTiModel;
import tcc.conexao_alimentar.repository.TaskTiRepository;

@Service
public class TasksTiService {

    @Autowired
    private TaskTiRepository taskTiRepository;

    public TaskTiModel criar(TaskTiRequestDTO dto) {
        TaskTiModel model = TaskTiMapper.toEntity(dto);
        return taskTiRepository.save(model);
    }

    public List<TaskTiModel> listarAbertas() {
        return taskTiRepository.findByFechadaFalseOrderByDataCriacaoDesc();
    }

    public TaskTiModel buscarPorId(Long id) {
        return taskTiRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task não encontrada"));
    }

    public void fechar(Long id) {
        TaskTiModel task = buscarPorId(id);
        task.setFechada(true);
        taskTiRepository.save(task);
    }
    public List<TaskTiModel> listarTodas() {
    return taskTiRepository.findAll(Sort.by(Sort.Direction.DESC, "dataCriacao"));
    }


}
