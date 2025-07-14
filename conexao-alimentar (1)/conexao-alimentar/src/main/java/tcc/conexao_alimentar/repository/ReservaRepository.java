package tcc.conexao_alimentar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.enums.StatusReserva;
import tcc.conexao_alimentar.model.ReservaModel;

public interface ReservaRepository extends JpaRepository<ReservaModel,Long>{

    boolean existsByDoacaoIdAndStatus(Long doacaoId, StatusReserva status);
    List<ReservaModel> findByBeneficiarioId(Long beneficiarioId);

}
