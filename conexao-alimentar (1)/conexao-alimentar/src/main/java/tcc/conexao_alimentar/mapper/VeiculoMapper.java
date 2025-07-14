package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.VeiculoRequestDTO;
import tcc.conexao_alimentar.DTO.VeiculoResponseDTO;
import tcc.conexao_alimentar.model.VeiculoModel;
import tcc.conexao_alimentar.model.VoluntarioModel;

public class VeiculoMapper {

     public static VeiculoModel toEntity(VeiculoRequestDTO dto, VoluntarioModel voluntario) {
        VeiculoModel veiculo = new VeiculoModel();
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setCor(dto.getCor());
        veiculo.setCapacidadeCarga(dto.getCapacidadeCarga());
        veiculo.setVoluntario(voluntario);
        return veiculo;
    }

    public static VeiculoResponseDTO toResponse(VeiculoModel model) {
        return new VeiculoResponseDTO(
            model.getId(),
            model.getPlaca(),
            model.getModelo(),
            model.getCor(),
            model.getCapacidadeCarga()
        );
    }

}
