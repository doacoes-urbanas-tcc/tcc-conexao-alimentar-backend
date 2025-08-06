package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.VoluntarioTiRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioTiResponseDTO;
import tcc.conexao_alimentar.model.VoluntarioTiModel;

public class VoluntarioTiMapper {

     
    public static VoluntarioTiModel toEntity(VoluntarioTiRequestDTO dto) {
        VoluntarioTiModel model = new VoluntarioTiModel();
        model.setSetorTi(dto.getSetorTi());
        model.setStackConhecimento(dto.getStackConhecimento());
        model.setCertificacoes(dto.getCertificacoes());
        model.setExperiencia(dto.getExperiencia());
        model.setLinkedin(dto.getLinkedin());
        model.setGithub(dto.getGithub());
        model.setDisponibilidadeHoras(dto.getDisponibilidadeHoras());
        return model;
    }

    public static VoluntarioTiResponseDTO toResponse(VoluntarioTiModel model) {
        return new VoluntarioTiResponseDTO(
            model.getId(),
            model.getSetorTi(),
            model.getStackConhecimento(),
            model.getCertificacoes(),
            model.getExperiencia(),
            model.getLinkedin(),
            model.getGithub(),
            model.getDisponibilidadeHoras()
        );
    }

}
