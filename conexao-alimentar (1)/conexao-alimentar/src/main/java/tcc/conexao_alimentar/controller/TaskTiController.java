package tcc.conexao_alimentar.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.RespostaTaskRequestDTO;
import tcc.conexao_alimentar.DTO.RespostaTaskTiResponseDTO;
import tcc.conexao_alimentar.DTO.TaskTiRequestDTO;
import tcc.conexao_alimentar.DTO.TaskTiResponseDTO;
import tcc.conexao_alimentar.enums.StatusResposta;
import tcc.conexao_alimentar.mapper.RespostaTaskMapper;
import tcc.conexao_alimentar.mapper.TaskTiMapper;
import tcc.conexao_alimentar.model.RespostaTaskModel;
import tcc.conexao_alimentar.model.TaskTiModel;
import tcc.conexao_alimentar.service.RespostaTaskService;
import tcc.conexao_alimentar.service.TasksTiService;

@RestController
@RequestMapping("/tasks-ti")
@RequiredArgsConstructor
public class TaskTiController {

    private final TasksTiService taskService;
    private final RespostaTaskService respostaService;

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskTiResponseDTO> criarTask(@RequestBody TaskTiRequestDTO dto) {
        TaskTiModel model = taskService.criar(dto);
        return ResponseEntity.ok(TaskTiMapper.toDTO(model));
    }

    @PutMapping("/admin/{id}/fechar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> fechar(@PathVariable Long id) {
        taskService.fechar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/{id}/respostas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RespostaTaskTiResponseDTO>> listarRespostas(@PathVariable Long id) {
        List<RespostaTaskModel> respostas = respostaService.listarRespostasDaTask(id);
        List<RespostaTaskTiResponseDTO> dtos = respostas.stream()
            .map(RespostaTaskMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/admin/respostas/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> atualizarStatus(
        @PathVariable Long id,
        @RequestParam StatusResposta status
    ) {
        respostaService.atualizarStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/voluntario")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<List<TaskTiResponseDTO>> listarTasksAbertas() {
        List<TaskTiModel> tasks = taskService.listarAbertas();
        List<TaskTiResponseDTO> dtos = tasks.stream()
            .map(TaskTiMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/voluntario/{id}")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<TaskTiResponseDTO> detalhesTask(@PathVariable Long id) {
        TaskTiModel task = taskService.buscarPorId(id);
        return ResponseEntity.ok(TaskTiMapper.toDTO(task));
    }

    @PostMapping("/voluntario/{id}/responder")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<RespostaTaskTiResponseDTO> responder(
        @PathVariable Long id,
        @RequestBody RespostaTaskRequestDTO dto
    ) {
        RespostaTaskModel resposta = respostaService.responder(id, dto.getVoluntarioId(), dto.getLinkSolucao());
        return ResponseEntity.ok(RespostaTaskMapper.toDTO(resposta));
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskTiResponseDTO>> listarTodasTasks() {
    List<TaskTiModel> tasks = taskService.listarTodas(); 
    List<TaskTiResponseDTO> dtos = tasks.stream()
        .map(TaskTiMapper::toDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
   }
   @GetMapping("/admin/{id}")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<TaskTiResponseDTO> detalhesTaskAdmin(@PathVariable Long id) {
    TaskTiModel task = taskService.buscarPorId(id);
    return ResponseEntity.ok(TaskTiMapper.toDTO(task));
}


}
