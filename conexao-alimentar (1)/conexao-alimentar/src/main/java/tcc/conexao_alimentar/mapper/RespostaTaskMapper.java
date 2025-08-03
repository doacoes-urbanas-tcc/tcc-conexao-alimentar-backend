package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.RespostaTaskTiResponseDTO;
import tcc.conexao_alimentar.model.RespostaTaskModel;

public class RespostaTaskMapper {


    public static RespostaTaskTiResponseDTO toDTO(RespostaTaskModel model) {
        return new RespostaTaskTiResponseDTO(
            model.getId(),
            model.getVoluntario().getId(),
            model.getVoluntario().getNome(),
            model.getLinkSolucao(),
            model.getDataResposta(),
            model.getStatus()
        );
    }

}


