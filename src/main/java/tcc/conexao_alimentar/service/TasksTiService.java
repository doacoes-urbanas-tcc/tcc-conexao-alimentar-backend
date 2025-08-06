package tcc.conexao_alimentar.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.RespostaTaskTiResponseDTO;
import tcc.conexao_alimentar.DTO.TaskTiRequestDTO;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.RespostaTaskMapper;
import tcc.conexao_alimentar.mapper.TaskTiMapper;
import tcc.conexao_alimentar.model.RespostaTaskModel;
import tcc.conexao_alimentar.model.TaskTiModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.model.VoluntarioModel;
import tcc.conexao_alimentar.model.VoluntarioTiModel;
import tcc.conexao_alimentar.repository.RespostaTaskTiRepository;
import tcc.conexao_alimentar.repository.TaskTiRepository;
import tcc.conexao_alimentar.repository.UsuarioRepository;
import tcc.conexao_alimentar.repository.VoluntarioRepository;
import tcc.conexao_alimentar.repository.VoluntarioTiRepository;

@Service
@RequiredArgsConstructor
public class TasksTiService {

    private final TaskTiRepository taskTiRepository;
    private final VoluntarioTiRepository voluntarioTiRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final RespostaTaskTiRepository respostaTaskTiRepository;

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

    public long contarTasksAbertas() {
    return taskTiRepository.countByFechadaFalse();
    }

    public List<TaskTiModel> listarRecomendadasParaVoluntario(UsuarioModel usuario) {
        VoluntarioModel voluntario = voluntarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Voluntário não encontrado"));

        VoluntarioTiModel ti = voluntarioTiRepository.findByVoluntarioId(voluntario.getId())
                .orElseThrow(() -> new RuntimeException("Perfil de TI não encontrado"));

        String stack = ti.getStackConhecimento(); 
        List<String> palavrasChave = Arrays.stream(stack.split(","))
            .map(String::trim)
            .map(String::toLowerCase)
            .toList();

        List<TaskTiModel> todasTasks = taskTiRepository.findByFechadaFalseOrderByDataCriacaoDesc();

        return todasTasks.stream()
            .filter(task -> task.getTags().stream()
                .anyMatch(tag -> palavrasChave.contains(tag.toLowerCase())))
            .toList();
    }

    public List<RespostaTaskTiResponseDTO> listarTasksRespondidasPeloVoluntario() {
    String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

    UsuarioModel voluntario = usuarioRepository.findByEmail(emailUsuario)
        .orElseThrow(() -> new RegraDeNegocioException("Voluntário não encontrado"));

    List<RespostaTaskModel> respostas = respostaTaskTiRepository.findByVoluntario(voluntario);
   return respostas.stream()
        .map(RespostaTaskMapper::toDTO)
        .collect(Collectors.toList());
    }

    public int contarTasksRespondidas(Long voluntarioId) {
    return taskRepository.countByVoluntarioId(voluntarioId);
   }

   


}
