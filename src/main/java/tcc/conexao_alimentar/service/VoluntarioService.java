package tcc.conexao_alimentar.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.DashboardVoluntarioTIResponseDTO;
import tcc.conexao_alimentar.DTO.RespostaDTO;
import tcc.conexao_alimentar.DTO.VoluntarioRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioResponseDTO;
import tcc.conexao_alimentar.DTO.VoluntarioTiRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioTiResponseDTO;
import tcc.conexao_alimentar.enums.SetorAtuacao;
import tcc.conexao_alimentar.enums.StatusUsuario;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.VoluntarioMapper;
import tcc.conexao_alimentar.mapper.VoluntarioTiMapper;
import tcc.conexao_alimentar.model.RespostaTaskModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.model.VoluntarioModel;
import tcc.conexao_alimentar.model.VoluntarioTiModel;
import tcc.conexao_alimentar.repository.RespostaTaskTiRepository;
import tcc.conexao_alimentar.repository.TaskTiRepository;
import tcc.conexao_alimentar.repository.VoluntarioRepository;
import tcc.conexao_alimentar.repository.VoluntarioTiRepository;

@Service
@RequiredArgsConstructor
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;
    private final VoluntarioTiRepository voluntarioTiRepository;
    private final FileUploadService fileUploadService;
    private final RespostaTaskTiRepository respostaTaskTiRepository;
    private final TaskTiRepository taskTiRepository;
    
   @Transactional
   public VoluntarioModel cadastrar(VoluntarioRequestDTO dto, MultipartFile comprovante, MultipartFile foto) throws IOException {
    VoluntarioModel model = VoluntarioMapper.toEntity(dto);
    model.setSenha(passwordEncoder.encode(dto.getSenha()));
    model.setTipoUsuario(TipoUsuario.VOLUNTARIO);
    model.setStatus(StatusUsuario.PENDENTE);

    String comprovanteUrl = fileUploadService.salvarArquivo(comprovante, "docs_comprovantes_voluntarios");
    model.setDocumentoComprovante(comprovanteUrl);

    String fotoUrl = fileUploadService.salvarArquivo(foto, "usuarios");
    model.setFotoUrl(fotoUrl);

    return voluntarioRepository.save(model); }



    public VoluntarioResponseDTO buscarPorId(Long id) {
    VoluntarioModel voluntario = voluntarioRepository.findById(id)
        .orElseThrow(() -> new RegraDeNegocioException("Voluntário não encontrado"));
     return VoluntarioMapper.toResponse(voluntario);
    }
    
    @Transactional
    public void atualizarEmail(Long voluntarioId, String novoEmail) {
        usuarioService.atualizarEmail(voluntarioId, novoEmail);
    }

    @Transactional
    public void atualizarSenha(Long voluntarioId, String senhaAtual, String novaSenha) {
        usuarioService.atualizarSenha(voluntarioId,senhaAtual, novaSenha);
    }

    public VoluntarioResponseDTO visualizarPerfil(Long id) {
    UsuarioModel usuario = usuarioService.buscarPorId(id)
     .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));

    if (!(usuario instanceof VoluntarioModel)) {
        throw new RegraDeNegocioException("Usuário não é um voluntário.");
    }

   VoluntarioModel voluntario= (VoluntarioModel) usuario;
    return VoluntarioMapper.toResponse(voluntario);
   }
    public void cadastrarPerfilTI(Long voluntarioId, VoluntarioTiRequestDTO dto) {
        VoluntarioModel voluntario = voluntarioRepository.findById(voluntarioId)
            .orElseThrow(() -> new RegraDeNegocioException("Voluntário não encontrado"));

        if (!voluntario.getSetorAtuacao().equals(SetorAtuacao.TI)) {
            throw new RegraDeNegocioException("Este voluntário não é do setor TI.");
        }

        VoluntarioTiModel model = VoluntarioTiMapper.toEntity(dto);
        

        model.setVoluntario(voluntario);

        voluntarioTiRepository.save(model);
    }

    public VoluntarioTiResponseDTO visualizarPerfilTI(Long voluntarioId) {
        VoluntarioTiModel model = voluntarioTiRepository.findByVoluntarioId(voluntarioId)
            .orElseThrow(() -> new RegraDeNegocioException("Perfil TI não encontrado."));
        return VoluntarioTiMapper.toResponse(model);
    }

    @Transactional
    public void atualizarPerfilTI(Long voluntarioId, VoluntarioTiRequestDTO dto) {
        VoluntarioTiModel model = voluntarioTiRepository.findByVoluntarioId(voluntarioId)
            .orElseThrow(() -> new RegraDeNegocioException("Perfil TI não encontrado."));

        model.setSetorTi(dto.getSetorTi());
        model.setStackConhecimento(dto.getStackConhecimento());
        model.setExperiencia(dto.getExperiencia());
        model.setLinkedin(dto.getLinkedin());
        model.setGithub(dto.getGithub());
        model.setDisponibilidadeHoras(dto.getDisponibilidadeHoras());

        voluntarioTiRepository.save(model);
    }
    public Map<String, Object> visualizarPerfilVoluntario(Long id) {
    VoluntarioModel voluntario = voluntarioRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Voluntário não encontrado"));

    Map<String, Object> dto = new HashMap<>();
    dto.put("id", voluntario.getId());
    dto.put("nome", voluntario.getNome());
    dto.put("email", voluntario.getEmail());
    dto.put("telefone", voluntario.getTelefone());
    dto.put("cpf", voluntario.getCpf());
    dto.put("setorAtuacao", voluntario.getSetorAtuacao().name());
    dto.put("documentoComprovante", voluntario.getDocumentoComprovante());
    dto.put("fotoUrl", voluntario.getFotoUrl());
    dto.put("tipoUsuario", voluntario.getTipoUsuario().name());
    dto.put("endereco", voluntario.getEndereco());
    dto.put("status", voluntario.getStatus().name());

    if (voluntario.getSetorAtuacao() == SetorAtuacao.TI) {
        voluntarioTiRepository.findByVoluntarioId(id).ifPresent(perfilTi -> {
            dto.put("stackConhecimento", perfilTi.getStackConhecimento());
            dto.put("certificacoes", perfilTi.getCertificacoes());
            dto.put("experiencia", perfilTi.getExperiencia());
            dto.put("linkedin", perfilTi.getLinkedin());
            dto.put("github", perfilTi.getGithub());
            dto.put("disponibilidadeHoras", perfilTi.getDisponibilidadeHoras());
        });
    }

    if (voluntario.getSetorAtuacao() == SetorAtuacao.TRANSPORTE) {
        if (voluntario.getVeiculo() != null) {
            dto.put("placaVeiculo", voluntario.getVeiculo().getPlaca());
            dto.put("modeloVeiculo", voluntario.getVeiculo().getModelo());
            dto.put("corVeiculo", voluntario.getVeiculo().getCor());
            dto.put("capacidadeCarga", voluntario.getVeiculo().getCapacidadeCarga());
            dto.put("fotoCNH", voluntario.getVeiculo().getUrlCnh());
        }
    }

    return dto;
    }
    
    public DashboardVoluntarioTIResponseDTO gerarDashboardVoluntarioTi(Long voluntarioId) {
    String nome = usuarioService.buscarNomePorId(voluntarioId);
    int tasksRespondidas = (int) respostaTaskTiRepository.countByVoluntarioId(voluntarioId);
    int tasksAbertas = (int) taskTiRepository.countByFechadaFalse();
    double mediaAvaliacoes = usuarioService.buscarMediaAvaliacoes(voluntarioId);

    List<RespostaTaskModel> respostas = respostaTaskTiRepository.findByVoluntarioId(voluntarioId);

    List<RespostaDTO> respostaDTOs = respostas.stream()
        .map(r -> new RespostaDTO(
            r.getTaskTi().getTitulo(),
            r.getLinkSolucao(),
            r.getDataResposta()
        ))
        .collect(Collectors.toList());

    DashboardVoluntarioTIResponseDTO dto = new DashboardVoluntarioTIResponseDTO();
    dto.setNome(nome);
    dto.setTasksRespondidas(tasksRespondidas);
    dto.setTasksAbertas(tasksAbertas);
    dto.setMediaAvaliacoes(mediaAvaliacoes);
    dto.setRespostas(respostaDTOs);

    return dto;
}



    
}
