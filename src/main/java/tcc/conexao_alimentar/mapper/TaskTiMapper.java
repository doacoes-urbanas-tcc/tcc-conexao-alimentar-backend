package tcc.conexao_alimentar.mapper;

import java.time.OffsetDateTime;

import tcc.conexao_alimentar.DTO.TaskTiRequestDTO;
import tcc.conexao_alimentar.DTO.TaskTiResponseDTO;
import tcc.conexao_alimentar.model.TaskTiModel;

public class TaskTiMapper {

      public static TaskTiModel toEntity(TaskTiRequestDTO dto) {
        TaskTiModel task = new TaskTiModel();
        task.setTitulo(dto.getTitulo());
        task.setDescricao(dto.getDescricao());
        task.setLinkRepositorio(dto.getLinkRepositorio());
        task.setTags(dto.getTags());
        task.setDataCriacao(OffsetDateTime.now());
        task.setFechada(false);
        return task;
    }
     public static TaskTiResponseDTO toDTO(TaskTiModel model) {
        return new TaskTiResponseDTO(
            model.getId(),
            model.getTitulo(),
            model.getDescricao(),
            model.getLinkRepositorio(),
            model.getTags(),
            model.isFechada(),
            model.getDataCriacao()
        );
    }
    
}


