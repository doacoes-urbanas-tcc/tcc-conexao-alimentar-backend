package tcc.conexao_alimentar.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tcc.conexao_alimentar.DTO.DoacaoRequestDTO;
import tcc.conexao_alimentar.DTO.DoacaoResponseDTO;
import tcc.conexao_alimentar.mapper.DoacaoMapper;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.repository.DoacaoRepository;
import tcc.conexao_alimentar.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoacaoService {

    private final DoacaoRepository doacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public void cadastrar(DoacaoRequestDTO dto) {
        UsuarioModel doador = usuarioRepository.findById(dto.getDoadorId())
            .orElseThrow(() -> new RuntimeException("Doador não encontrado"));

        DoacaoModel model = DoacaoMapper.toEntity(dto, doador);
        doacaoRepository.save(model);
    }

    public List<DoacaoResponseDTO> listarTodas() {
        return doacaoRepository.findAll().stream()
            .map(DoacaoMapper::toResponse)
            .collect(Collectors.toList());
    }

}
