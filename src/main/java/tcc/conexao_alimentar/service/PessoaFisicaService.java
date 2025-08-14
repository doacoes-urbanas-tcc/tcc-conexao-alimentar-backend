package tcc.conexao_alimentar.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.PessoaFisicaRequestDTO;
import tcc.conexao_alimentar.DTO.PessoaFisicaResponseDTO;
import tcc.conexao_alimentar.enums.StatusUsuario;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.PessoaFisicaMapper;
import tcc.conexao_alimentar.model.PessoaFisicaModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.PessoaFisicaRepository;

@Service
@RequiredArgsConstructor
public class PessoaFisicaService {

    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;
    private final FileUploadService fileUploadService;

    @Transactional
    public PessoaFisicaModel cadastrar(PessoaFisicaRequestDTO dto, MultipartFile comprovante, MultipartFile foto) throws IOException {
    PessoaFisicaModel model = PessoaFisicaMapper.toEntity(dto);
    
    model.setSenha(passwordEncoder.encode(dto.getSenha()));
    model.setTipoUsuario(TipoUsuario.PESSOA_FISICA);
    model.setStatus(StatusUsuario.PENDENTE);

    String comprovanteUrl = fileUploadService.salvarArquivo(comprovante, "docs_comprovantes_pessoa_fisica");
    model.setDocumentoComprovante(comprovanteUrl);

    String fotoUrl = fileUploadService.salvarArquivo(foto, "usuarios");
    model.setFotoUrl(fotoUrl);

    return pessoaFisicaRepository.save(model);
    }

    @Transactional
    public void atualizarEmail(Long pfId, String novoEmail) {
        usuarioService.atualizarEmail(pfId, novoEmail);
    }

    @Transactional
    public void atualizarSenha(Long pfId,String senhaAtual, String novaSenha) {
        usuarioService.atualizarSenha(pfId, senhaAtual, novaSenha);
    }

    public PessoaFisicaResponseDTO visualizarPerfil(Long id) {
    UsuarioModel usuario = usuarioService.buscarPorId(id)
     .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."));

    if (!(usuario instanceof PessoaFisicaModel)) {
        throw new RegraDeNegocioException("Usuário não é uma pessoa física.");
    }

   PessoaFisicaModel pf= (PessoaFisicaModel) usuario;
    return PessoaFisicaMapper.toResponse(pf);
   }
   
    public Optional<PessoaFisicaModel> buscarPorId(Long id) {
        return pessoaFisicaRepository.findById(id);
    }

    

    

}


