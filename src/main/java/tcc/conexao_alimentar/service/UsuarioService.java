package tcc.conexao_alimentar.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.ComercioRequestDTO;
import tcc.conexao_alimentar.DTO.ComercioResponseDTO;
import tcc.conexao_alimentar.DTO.DashboardResumoDTO;
import tcc.conexao_alimentar.DTO.OngRequestDTO;
import tcc.conexao_alimentar.DTO.OngResponseDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaRequestDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaResponseDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralRequestDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralResponseDTO;
import tcc.conexao_alimentar.DTO.VoluntarioRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioResponseDTO;
import tcc.conexao_alimentar.enums.StatusDoacao;
import tcc.conexao_alimentar.enums.StatusUsuario;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.ComercioMapper;
import tcc.conexao_alimentar.mapper.OngMapper;
import tcc.conexao_alimentar.mapper.PessoaFisicaMapper;
import tcc.conexao_alimentar.mapper.ProdutorRuralMapper;
import tcc.conexao_alimentar.mapper.VoluntarioMapper;
import tcc.conexao_alimentar.model.ComercioModel;
import tcc.conexao_alimentar.model.OngModel;
import tcc.conexao_alimentar.model.PessoaFisicaModel;
import tcc.conexao_alimentar.model.ProdutorRuralModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.model.VoluntarioModel;
import tcc.conexao_alimentar.repository.ComercioRepository;
import tcc.conexao_alimentar.repository.DoacaoRepository;
import tcc.conexao_alimentar.repository.OngRepository;
import tcc.conexao_alimentar.repository.PessoaFisicaRepository;
import tcc.conexao_alimentar.repository.ProdutorRuralRepository;
import tcc.conexao_alimentar.repository.TaskTiRepository;
import tcc.conexao_alimentar.repository.UsuarioRepository;
import tcc.conexao_alimentar.repository.VoluntarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final ComercioRepository comercioRepository;
    private final ProdutorRuralRepository produtorRuralRepository;
    private final OngRepository ongRepository;
    private final PasswordEncoder passwordEncoder;
    private final DoacaoService doacaoService;
    private final DoacaoRepository doacaoRepository;
    private final TasksTiService taskService;
    private final TaskTiRepository taskRepository;
    private final EmailService emailService;


    public PessoaFisicaResponseDTO cadastrarPessoaFisica(PessoaFisicaRequestDTO dto) {
        validarEmail(dto.getEmail());
        PessoaFisicaModel pf = PessoaFisicaMapper.toEntity(dto);
        pf.setStatus(StatusUsuario.ATIVO);
        return PessoaFisicaMapper.toResponse(pessoaFisicaRepository.save(pf));
    }

    public VoluntarioResponseDTO cadastrarVoluntario(VoluntarioRequestDTO dto) {
        validarEmail(dto.getEmail());
        VoluntarioModel v = VoluntarioMapper.toEntity(dto);
        v.setStatus(StatusUsuario.ATIVO);
        return VoluntarioMapper.toResponse(voluntarioRepository.save(v));
    }

    public ComercioResponseDTO cadastrarComercio(ComercioRequestDTO dto) {
        validarEmail(dto.getEmail());
        ComercioModel c = ComercioMapper.toEntity(dto);
        c.setStatus(StatusUsuario.PENDENTE);
        return ComercioMapper.toResponse(comercioRepository.save(c));
    }

    public ProdutorRuralResponseDTO cadastrarProdutorRural(ProdutorRuralRequestDTO dto) {
        validarEmail(dto.getEmail());
        ProdutorRuralModel pr = ProdutorRuralMapper.toEntity(dto);
        pr.setStatus(StatusUsuario.PENDENTE);
        return ProdutorRuralMapper.toResponse(produtorRuralRepository.save(pr));
    }

    public OngResponseDTO cadastrarOng(OngRequestDTO dto) {
        validarEmail(dto.getEmail());
        OngModel ong = OngMapper.toEntity(dto);
        ong.setStatus(StatusUsuario.PENDENTE);
        return OngMapper.toResponse(ongRepository.save(ong));
    }

    public Optional<UsuarioModel> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    private void validarEmail(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("E-mail já cadastrado.");
        }
    }

    @Transactional
    public void atualizarEmail(Long id, String novoEmail) {
        validarEmail(novoEmail);
        UsuarioModel usuario = buscarPorId(id).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));
        usuario.setEmail(novoEmail);
        usuarioRepository.save(usuario);
    }

   public void atualizarSenha(Long id, String senhaAtual, String novaSenha) {
    UsuarioModel usuario = buscarPorId(id).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));
    
    if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
        throw new RegraDeNegocioException("Senha atual incorreta.");
    }

    usuario.setSenha(passwordEncoder.encode(novaSenha));
    usuarioRepository.save(usuario);
   }


    public List<Object> listarUsuariosPorStatus(StatusUsuario status) {
        return usuarioRepository.findByStatus(status).stream().map(usuario -> {
            if (usuario instanceof VoluntarioModel v) return VoluntarioMapper.toResponse(v);
            if (usuario instanceof ComercioModel c) return ComercioMapper.toResponse(c);
            if (usuario instanceof OngModel o) return OngMapper.toResponse(o);
            if (usuario instanceof PessoaFisicaModel pf) return PessoaFisicaMapper.toResponse(pf);
            if (usuario instanceof ProdutorRuralModel pr) return ProdutorRuralMapper.toResponse(pr);
            throw new RegraDeNegocioException("Tipo de usuário desconhecido");
        }).collect(Collectors.toList());
    }

    public List<UsuarioModel> listarAtivos() {
    return usuarioRepository.findByStatusAndTipoUsuarioNot(StatusUsuario.ATIVO, TipoUsuario.ADMIN);
    }


    public List<Object> listarUsuariosPendentes() {
        return listarUsuariosPorStatus(StatusUsuario.PENDENTE);
    }

    public List<Object> listarUsuariosReprovados() {
        return usuarioRepository.findByStatus(StatusUsuario.REPROVADO).stream()
                .filter(u -> u.getJustificativaReprovacao() != null)
                .map(usuario -> {
                    if (usuario instanceof VoluntarioModel v) return VoluntarioMapper.toResponse(v);
                    if (usuario instanceof ComercioModel c) return ComercioMapper.toResponse(c);
                    if (usuario instanceof OngModel o) return OngMapper.toResponse(o);
                    if (usuario instanceof PessoaFisicaModel pf) return PessoaFisicaMapper.toResponse(pf);
                    if (usuario instanceof ProdutorRuralModel pr) return ProdutorRuralMapper.toResponse(pr);
                    throw new RegraDeNegocioException("Tipo de usuário desconhecido.");
                })
                .collect(Collectors.toList());
    }

    public long contarUsuariosAtivos() {
        return usuarioRepository.countByStatusAndTipoUsuarioNot(StatusUsuario.ATIVO, TipoUsuario.ADMIN);
    }

    public long contarUsuariosInativos() {
        return usuarioRepository.countByStatusAndTipoUsuarioNot(StatusUsuario.REPROVADO, TipoUsuario.ADMIN)
             + usuarioRepository.countByStatusAndTipoUsuarioNot(StatusUsuario.DESATIVADO, TipoUsuario.ADMIN)
             + usuarioRepository.countByStatusAndTipoUsuarioNot(StatusUsuario.PENDENTE, TipoUsuario.ADMIN);
    }

    @Transactional
    public UsuarioModel aprovarUsuario(Long id) {
    UsuarioModel usuario = buscarPorId(id)
        .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));
    
    usuario.setStatus(StatusUsuario.ATIVO);
    usuario.setJustificativaReprovacao(null);

    return usuarioRepository.save(usuario); 
   }


    @Transactional
    public UsuarioModel reprovarUsuario(Long id, String justificativa) {
    UsuarioModel usuario = buscarPorId(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    
    usuario.setStatus(StatusUsuario.REPROVADO);
    usuario.setJustificativaReprovacao(justificativa);

    return usuarioRepository.save(usuario); 
    }


    public List<UsuarioModel> buscarUltimosPendentes() {
    return usuarioRepository.findTop5ByStatusAndTipoUsuarioNotOrderByIdDesc(
        StatusUsuario.PENDENTE, TipoUsuario.ADMIN
    );
    }
    public DashboardResumoDTO montarResumo() {
    DashboardResumoDTO dto = new DashboardResumoDTO();

        dto.setUsuariosAtivos(usuarioRepository.countByStatusAndTipoUsuarioNot(StatusUsuario.ATIVO, TipoUsuario.ADMIN));
        dto.setUsuariosPendentes(usuarioRepository.countByStatusAndTipoUsuarioNot(StatusUsuario.PENDENTE, TipoUsuario.ADMIN));
        dto.setUsuariosInativos(
            usuarioRepository.countByStatusAndTipoUsuarioNot(StatusUsuario.REPROVADO, TipoUsuario.ADMIN)
          + usuarioRepository.countByStatusAndTipoUsuarioNot(StatusUsuario.DESATIVADO, TipoUsuario.ADMIN)
        );

        dto.setDoacoesAtivas(
         doacaoRepository.countByStatus(StatusDoacao.PENDENTE)
         + doacaoRepository.countByStatus(StatusDoacao.AGUARDANDO_RETIRADA)
        );
        dto.setTasksAbertas(taskRepository.countByFechadaFalse());


        return dto;
    }

    public DashboardDoadorDTO getDashboardDoador(Long usuarioId) {
    int totalDoacoes = doacaoRepository.contarDoacoesPorUsuario(usuarioId);
    int ongsBeneficiadas = doacaoRepository.contarOngsBeneficiadas(usuarioId);
    Double mediaAvaliacoes = doacaoRepository.calcularMediaAvaliacoes(usuarioId);
    Optional<DoacaoModel> ultimaDoacaoOpt = doacaoRepository.buscarUltimaDoacao(usuarioId);

    UltimaDoacaoDTO ultima = ultimaDoacaoOpt.map(d -> new UltimaDoacaoDTO(
        d.getData().toString(),
        d.getItensDescricao(),
        d.getReserva() != null ? d.getReserva().getOng().getNome() : "Ainda não reservado",
        d.getStatus().toString()
    )).orElse(null);

    return new DashboardDoadorDTO(totalDoacoes, ongsBeneficiadas, mediaAvaliacoes, ultima);
}


    

}




