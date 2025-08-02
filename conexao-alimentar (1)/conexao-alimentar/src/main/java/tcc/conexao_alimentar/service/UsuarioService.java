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
import tcc.conexao_alimentar.DTO.OngRequestDTO;
import tcc.conexao_alimentar.DTO.OngResponseDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaRequestDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaResponseDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralRequestDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralResponseDTO;
import tcc.conexao_alimentar.DTO.VoluntarioRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioResponseDTO;
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
import tcc.conexao_alimentar.repository.OngRepository;
import tcc.conexao_alimentar.repository.PessoaFisicaRepository;
import tcc.conexao_alimentar.repository.ProdutorRuralRepository;
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

    public PessoaFisicaResponseDTO cadastrarPessoaFisica(PessoaFisicaRequestDTO dto) {
        validarEmail(dto.getEmail());
        PessoaFisicaModel pf = PessoaFisicaMapper.toEntity(dto);
        pf.setAtivo(true);
        return PessoaFisicaMapper.toResponse(pessoaFisicaRepository.save(pf));
    }

    public VoluntarioResponseDTO cadastrarVoluntario(VoluntarioRequestDTO dto) {
        validarEmail(dto.getEmail());
        VoluntarioModel v = VoluntarioMapper.toEntity(dto);
        v.setAtivo(true);
        return VoluntarioMapper.toResponse(voluntarioRepository.save(v));
    }

    public ComercioResponseDTO cadastrarComercio(ComercioRequestDTO dto) {
        validarEmail(dto.getEmail());
        ComercioModel c = ComercioMapper.toEntity(dto);
        c.setAtivo(true);
        return ComercioMapper.toResponse(comercioRepository.save(c));
    }

    public ProdutorRuralResponseDTO cadastrarProdutorRural(ProdutorRuralRequestDTO dto) {
        validarEmail(dto.getEmail());
        ProdutorRuralModel pr = ProdutorRuralMapper.toEntity(dto);
        pr.setAtivo(true);
        return ProdutorRuralMapper.toResponse(produtorRuralRepository.save(pr));
    }

    public OngResponseDTO cadastrarOng(OngRequestDTO dto) {
        validarEmail(dto.getEmail());
        OngModel ong = OngMapper.toEntity(dto);
        ong.setAtivo(false);
        return OngMapper.toResponse(ongRepository.save(ong));
    }

    public Optional<UsuarioModel> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    private void validarEmail(String email) {
        boolean jaExiste = usuarioRepository.existsByEmail(email);
        if (jaExiste) {
            throw new RuntimeException("E-mail já cadastrado.");
        }
    }

    @Transactional
    public void atualizarEmail(Long id, String novoEmail) {
        validarEmail(novoEmail);
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));
        usuario.setEmail(novoEmail);
        usuarioRepository.save(usuario);
    }
    
    @Transactional
    public void atualizarSenha(Long id, String novaSenha) {
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
    }

    public List<ComercioResponseDTO> listarComerciosPendentes(){
        return usuarioRepository.findByAtivo(false)
              .stream()
              .filter(usuario -> usuario instanceof 
              ComercioModel)
              .map(usuario ->
              ComercioMapper.toResponse((ComercioModel)
              usuario))
              .collect(Collectors.toList());
    }
    public List<OngResponseDTO> listarOngsPendentes(){
        return usuarioRepository.findByAtivo(false)
              .stream()
              .filter(usuario -> usuario instanceof 
              OngModel)
              .map(usuario ->
              OngMapper.toResponse((OngModel)
              usuario))
              .collect(Collectors.toList());
    }
    public List<PessoaFisicaResponseDTO> listarPessoasFisicasPendentes(){
        return usuarioRepository.findByAtivo(false)
              .stream()
              .filter(usuario -> usuario instanceof 
              PessoaFisicaModel)
              .map(usuario ->
              PessoaFisicaMapper.toResponse((PessoaFisicaModel)
              usuario))
              .collect(Collectors.toList());
    }
    public List<ProdutorRuralResponseDTO> listarProdutoresRuraisPendentes(){
        return usuarioRepository.findByAtivo(false)
              .stream()
              .filter(usuario -> usuario instanceof 
              ProdutorRuralModel)
              .map(usuario ->
              ProdutorRuralMapper.toResponse((ProdutorRuralModel)
              usuario))
              .collect(Collectors.toList());
    }
    public List<VoluntarioResponseDTO> listarVoluntariosPendentes(){
        return usuarioRepository.findByAtivo(false)
              .stream()
              .filter(usuario -> usuario instanceof 
              VoluntarioModel)
              .map(usuario ->
              VoluntarioMapper.toResponse((VoluntarioModel)
              usuario))
              .collect(Collectors.toList());
    }
    public List<Object> listarUsuariosPendentes() {
        List<UsuarioModel> usuarios = usuarioRepository.findByAtivoFalse();

        return usuarios.stream()
            .map(usuario -> {
                if (usuario instanceof VoluntarioModel) {
                    return VoluntarioMapper.toResponse((VoluntarioModel) usuario);
                } else if (usuario instanceof ComercioModel) {
                    return ComercioMapper.toResponse((ComercioModel) usuario);
                } else if (usuario instanceof OngModel) {
                    return OngMapper.toResponse((OngModel) usuario);
                } else if (usuario instanceof PessoaFisicaModel) {
                    return PessoaFisicaMapper.toResponse((PessoaFisicaModel) usuario);
                } else if (usuario instanceof ProdutorRuralModel) {
                    return ProdutorRuralMapper.toResponse((ProdutorRuralModel) usuario);
                } else {
                    throw new RegraDeNegocioException("Tipo de usuário desconhecido");
                }
            })
            .collect(Collectors.toList());
    }

    public List<ComercioResponseDTO> listarComerciosAtivos(){
        return usuarioRepository.findByAtivo(true)
              .stream()
              .filter(usuario -> usuario instanceof 
              ComercioModel)
              .map(usuario ->
              ComercioMapper.toResponse((ComercioModel)
              usuario))
              .collect(Collectors.toList());
    }
    
    public List<OngResponseDTO> listarOngsAtivas(){
        return usuarioRepository.findByAtivo(true)
              .stream()
              .filter(usuario -> usuario instanceof 
              OngModel)
              .map(usuario ->
              OngMapper.toResponse((OngModel)
              usuario))
              .collect(Collectors.toList());
    }
    public List<PessoaFisicaResponseDTO> listarPessoasFisicasAtivas(){
        return usuarioRepository.findByAtivo(true)
              .stream()
              .filter(usuario -> usuario instanceof 
              PessoaFisicaModel)
              .map(usuario ->
              PessoaFisicaMapper.toResponse((PessoaFisicaModel)
              usuario))
              .collect(Collectors.toList());
    }
    public List<ProdutorRuralResponseDTO> listarProdutoresRuraisAtivos(){
        return usuarioRepository.findByAtivo(true)
              .stream()
              .filter(usuario -> usuario instanceof 
              ProdutorRuralModel)
              .map(usuario ->
              ProdutorRuralMapper.toResponse((ProdutorRuralModel)
              usuario))
              .collect(Collectors.toList());
    }
    public List<VoluntarioResponseDTO> listarVoluntariosAtivos(){
        return usuarioRepository.findByAtivo(true)
              .stream()
              .filter(usuario -> usuario instanceof 
              VoluntarioModel)
              .map(usuario ->
              VoluntarioMapper.toResponse((VoluntarioModel)
              usuario))
              .collect(Collectors.toList());
    }
    @Transactional
    public void aprovarUsuario(Long id) {
    UsuarioModel usuario = usuarioRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));
    usuario.setAtivo(true);
    usuarioRepository.save(usuario);
    }
    public void reprovarUsuario(Long id, String justificativa) {
    UsuarioModel usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    usuario.setAtivo(false);
    usuario.setJustificativaReprovacao(justificativa);
    usuarioRepository.save(usuario);
}



    public List<UsuarioModel> listarAtivos() {
    return usuarioRepository.findByAtivo(true);
    }

    public List<Object> listarUsuariosReprovados() {
    List<UsuarioModel> usuarios = usuarioRepository.findByAtivoFalse();

    return usuarios.stream()
        .filter(usuario -> usuario.getJustificativaReprovacao() != null)
        .map(usuario -> {
            if (usuario instanceof VoluntarioModel) {
                VoluntarioModel voluntario = (VoluntarioModel) usuario;
                return VoluntarioMapper.toResponse(voluntario);
            } else if (usuario instanceof ComercioModel) {
                ComercioModel comercio = (ComercioModel) usuario;
                return ComercioMapper.toResponse(comercio);
            } else if (usuario instanceof OngModel) {
                OngModel ong = (OngModel) usuario;
                return OngMapper.toResponse(ong);
            } else if (usuario instanceof PessoaFisicaModel) {
                PessoaFisicaModel pf = (PessoaFisicaModel) usuario;
                return PessoaFisicaMapper.toResponse(pf);
            } else if (usuario instanceof ProdutorRuralModel) {
                ProdutorRuralModel pr = (ProdutorRuralModel) usuario;
                return ProdutorRuralMapper.toResponse(pr);
            } else {
                throw new RegraDeNegocioException("Tipo de usuário desconhecido.");
            }
        })
        .collect(Collectors.toList());
    }

     public long contarUsuariosAtivos() {
        return usuarioRepository.countByAtivoIsTrueAndTipoUsuarioNot(TipoUsuario.ADMIN);
    }

    public long contarUsuariosInativos() {
        return usuarioRepository.countByAtivoIsFalseAndTipoUsuarioNot(TipoUsuario.ADMIN);
    }
    public List<UsuarioModel> buscarUltimosPendentes() {
    return usuarioRepository.findTop5ByAtivoIsFalseAndTipoUsuarioNotOrderByIdDesc(TipoUsuario.ADMIN);
    }
    

}
