package tcc.conexao_alimentar.mapper;

import java.time.LocalDateTime;

import tcc.conexao_alimentar.DTO.ReservaRequestDTO;
import tcc.conexao_alimentar.DTO.ReservaResponseDTO;
import tcc.conexao_alimentar.enums.StatusReserva;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.ReservaModel;
import tcc.conexao_alimentar.model.UsuarioModel;

public class ReservaMapper {

    public static ReservaResponseDTO toResponse(ReservaModel model) {
        return new ReservaResponseDTO(
            model.getId(),
            model.getDoacao().getId(),
            model.getBeneficiario().getId(),
            model.getDataReserva(),
            model.getStatus(),
            model.getJustificativaCancelamento()
        );
    }

    public static ReservaModel toEntity(
            ReservaRequestDTO dto,
            DoacaoModel doacao,
            UsuarioModel beneficiario
    ) {
        ReservaModel model = new ReservaModel();
        model.setDoacao(doacao);
        model.setBeneficiario(beneficiario);
        model.setDataReserva(LocalDateTime.now());
        model.setStatus(StatusReserva.RESERVADA);
        model.setJustificativaCancelamento(null);
        return model;
    }

}
