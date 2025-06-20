package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.EnderecoDTO;
import tcc.conexao_alimentar.model.EnderecoModel;

public class EnderecoMapper {

     public static EnderecoModel toEntity(EnderecoDTO dto) {
        return new EnderecoModel(
            dto.getLogradouro(), dto.getNumero(), dto.getBairro(),
            dto.getCidade(), dto.getEstado(), dto.getCep(),
            dto.getLatitude(), dto.getLongitude()
        );
    }

    public static EnderecoDTO toDTO(EnderecoModel e) {
        return new EnderecoDTO(
            e.getLogradouro(), e.getNumero(), e.getBairro(),
            e.getCidade(), e.getEstado(), e.getCep(),
            e.getLatitude(), e.getLongitude()
        );
    }

}
