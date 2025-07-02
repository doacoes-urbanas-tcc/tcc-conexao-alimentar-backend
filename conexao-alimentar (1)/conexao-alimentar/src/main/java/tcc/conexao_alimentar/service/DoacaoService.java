package tcc.conexao_alimentar.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tcc.conexao_alimentar.DTO.DoacaoRequestDTO;
import tcc.conexao_alimentar.DTO.DoacaoResponseDTO;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.DoacaoMapper;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.DoacaoRepository;
import tcc.conexao_alimentar.repository.UsuarioRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoacaoService {

    private final DoacaoRepository doacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public void cadastrar(DoacaoRequestDTO dto) {
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioModel doador = usuarioRepository.findByEmail(emailUsuario)
            .orElseThrow(() -> new RuntimeException("Doador não encontrado."));
        
            if (!StringUtils.hasText(dto.getNomeAlimento())) {
                throw new RegraDeNegocioException("O nome do alimento deve ser preenchido.");
            }
            if (dto.getUnidadeMedida() == null) {
                throw new RegraDeNegocioException("Não é possível cadastrar uma doação sem preencher a unidade de medida do item doado.");
            }
            if (dto.getQuantidade() <= 0) {
                throw new RegraDeNegocioException("Não é possivel cadastrar um uma doação sem informar a quantidade do item doado.");
            }
            if (dto.getDataValidade().isBefore(LocalDate.now())) {
                throw new RegraDeNegocioException("A data de validade não pode ser anterior a data de doação.");
            }
            if (!StringUtils.hasText(dto.getDescricao())) {
                throw new RegraDeNegocioException("Descrição é obrigatória.");
            }
            if (dto.getCategoria() == null) {
                throw new RegraDeNegocioException("Categoria é obrigatóira.");
                
            }
            

        DoacaoModel model = DoacaoMapper.toEntity(dto, doador);
        model.setDataExpiracao(model.getDataCadastro().plusHours(48));
        log.info("Doação cadastrada para {}", doador.getEmail());

        doacaoRepository.save(model);

    }

    public List<DoacaoResponseDTO> listarTodas() {
        return doacaoRepository.findAll()         
            .stream()                              
            .map(DoacaoMapper::toResponse)        
            .collect(Collectors.toList());         
    }

    public DoacaoResponseDTO buscarPorId(Long id) {
    DoacaoModel doacao = doacaoRepository.findById(id)
        .orElseThrow(() -> new RegraDeNegocioException("Doação não encontrada"));
    return DoacaoMapper.toResponse(doacao);
    }

    public void removerDoacao(Long id, Long doadorId) {
     String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioModel doador = usuarioRepository.findByEmail(emailUsuario)
            .orElseThrow(() -> new RegraDeNegocioException("Doador não encontrado."));

        DoacaoModel doacao = doacaoRepository.findById(id)
            .orElseThrow(() -> new RegraDeNegocioException("Doação não encontrada."));

        if (!doacao.getDoador().getId().equals(doador.getId())) {
            throw new RegraDeNegocioException("Você não tem permissão para excluir esta doação.");
        }

        doacaoRepository.deleteById(id);
        log.info("Doação {} removida pelo doador {}", id, doador.getEmail());
    }
}




    





