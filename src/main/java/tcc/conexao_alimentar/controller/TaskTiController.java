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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.service.RespostaTaskService;
import tcc.conexao_alimentar.service.TasksTiService;

@RestController
@RequestMapping("/tasks-ti")
@RequiredArgsConstructor
@Tag(name = "Tasks de TI", description = "Endpoints referentes as tasks de TI")
public class TaskTiController {

    private final TasksTiService taskService;
    private final RespostaTaskService respostaService;

    @Operation(summary = "Endpoint para o administrador cadastrar uma task",description = "Endpoint para um administrador cadastrar uma task para que os voluntários de TI responderem")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Task cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskTiResponseDTO> criarTask(@RequestBody TaskTiRequestDTO dto) {
        TaskTiModel model = taskService.criar(dto);
        return ResponseEntity.ok(TaskTiMapper.toDTO(model));
    }

    @Operation(summary = "Endpoint para o administrador  fechar uma task",description = "Endpoint para um administrador fechar uma task para que os voluntários de TI não consigam visualizar nem responder mais")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Task fechada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    
    @PutMapping("/admin/{id}/fechar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> fechar(@PathVariable Long id) {
        taskService.fechar(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Endpoint para o administrador visualizar as respostas de uma task",description = "Endpoint para um administrador visualizar as respostas que os voluntários de TI deram para a task cadastrada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Respostas carregadas com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

    @GetMapping("/admin/{id}/respostas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RespostaTaskTiResponseDTO>> listarRespostas(@PathVariable Long id) {
        List<RespostaTaskModel> respostas = respostaService.listarRespostasDaTask(id);
        List<RespostaTaskTiResponseDTO> dtos = respostas.stream()
            .map(RespostaTaskMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Endpoint para o administrador mudar o status da resposta de uma task",description = "Endpoint para um administrador mudar o status da resposta de uma task")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    @PutMapping("/admin/respostas/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> atualizarStatus(
        @PathVariable Long id,
        @RequestParam StatusResposta status
    ) {
        respostaService.atualizarStatus(id, status);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Endpoint para um voluntário de TI visualizar as tasks abertas",description = "Endpoint para um voluntário de TI visualizar as tasks abertas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Task carregadas com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

    @GetMapping("/voluntario")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<List<TaskTiResponseDTO>> listarTasksAbertas() {
        List<TaskTiModel> tasks = taskService.listarAbertas();
        List<TaskTiResponseDTO> dtos = tasks.stream()
            .map(TaskTiMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    @Operation(summary = "Endpoint para um voluntário de TI visualizar os detalhes da task selecionada",description = "Endpoint para um voluntário de TI visualizar os detalhes da task selecionada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Task carregadas com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

    @GetMapping("/voluntario/{id}")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<TaskTiResponseDTO> detalhesTask(@PathVariable Long id) {
        TaskTiModel task = taskService.buscarPorId(id);
        return ResponseEntity.ok(TaskTiMapper.toDTO(task));
    }
    @Operation(summary = "Endpoint para um voluntário de TI responder uma task",description = "Endpoint para um voluntário de TI  responder uma task")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Task respondida com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

    @PostMapping("/voluntario/{id}/responder")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<RespostaTaskTiResponseDTO> responder(
        @PathVariable Long id,
        @RequestBody RespostaTaskRequestDTO dto
    ) {
        RespostaTaskModel resposta = respostaService.responder(id, dto.getVoluntarioId(), dto.getLinkSolucao());
        return ResponseEntity.ok(RespostaTaskMapper.toDTO(resposta));
    }
    @Operation(summary = "Endpoint para o administrador listar todas as tasks ",description = "Endpoint para o administrador listar todas as tasks abertas cadastrardas no sistema ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tasks carregadas com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskTiResponseDTO>> listarTodasTasks() {
    List<TaskTiModel> tasks = taskService.listarTodas(); 
    List<TaskTiResponseDTO> dtos = tasks.stream()
        .map(TaskTiMapper::toDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
   }
    @Operation(summary = "Endpoint para o administrador visualizar os detalhes task ",description = "Endpoint para o administrador visualizar os detalhes task ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalhes carregados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
   @GetMapping("/admin/{id}")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<TaskTiResponseDTO> detalhesTaskAdmin(@PathVariable Long id) {
    TaskTiModel task = taskService.buscarPorId(id);
    return ResponseEntity.ok(TaskTiMapper.toDTO(task));
   }
    @Operation(summary = "Endpoint para o voluntário de TI visualizar as tasks cadastrardas que tem as tags da stacks cadastradas por ele",description = "Endpoint para o voluntário de TI visualizar as tasks cadastrardas que tem as tags da stacks cadastradas por ele")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalhes carregados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

   @GetMapping("/voluntario/recomendadas")
   @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<List<TaskTiResponseDTO>> listarTasksRecomendadas() {
    UsuarioModel usuario = tcc.conexao_alimentar.security.SecurityUtils.getUsuarioLogado();
    List<TaskTiModel> tasks = taskService.listarRecomendadasParaVoluntario(usuario);
    List<TaskTiResponseDTO> dtos = tasks.stream()
        .map(TaskTiMapper::toDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
   }
   
    @Operation(summary = "Listar doações do doador logado", description = "Retorna uma lista das doações cadastradas pelo doador logado.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
    @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
   })
    @GetMapping("/minhas-respostas")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    public ResponseEntity<List<RespostaTaskTiResponseDTO>> listarMinhasRespostas() {
    List<RespostaTaskTiResponseDTO> minhasRespostas = taskService.listarTasksRespondidasPeloVoluntario();
    return ResponseEntity.ok(minhasRespostas);
    }




}
