package tcc.conexao_alimentar.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.VoluntarioTiRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioTiResponseDTO;
import tcc.conexao_alimentar.enums.SetorAtuacao;
import tcc.conexao_alimentar.exception.RegraDeNegocioException;
import tcc.conexao_alimentar.mapper.VoluntarioTiMapper;
import tcc.conexao_alimentar.model.VoluntarioModel;
import tcc.conexao_alimentar.model.VoluntarioTiModel;
import tcc.conexao_alimentar.repository.VoluntarioRepository;
import tcc.conexao_alimentar.repository.VoluntarioTiRepository;
@Service
@RequiredArgsConstructor
public class VoluntarioTiService {

    
    private final VoluntarioTiRepository voluntarioTiRepository;
    private final VoluntarioRepository voluntarioRepository;

    public void cadastrar(VoluntarioTiRequestDTO dto, Long voluntarioId) {
        VoluntarioModel voluntario = voluntarioRepository.findById(voluntarioId)
            .orElseThrow(() -> new RegraDeNegocioException("Voluntário não encontrado."));

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
        model.setCertificacoes(dto.getCertificacoes());
        model.setExperiencia(dto.getExperiencia());
        model.setLinkedin(dto.getLinkedin());
        model.setGithub(dto.getGithub());
        model.setDisponibilidadeHoras(dto.getDisponibilidadeHoras());

        voluntarioTiRepository.save(model);
    }

}
