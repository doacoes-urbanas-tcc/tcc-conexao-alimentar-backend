package tcc.conexao_alimentar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.enums.StatusReserva;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.ReservaModel;

public interface ReservaRepository extends JpaRepository<ReservaModel,Long>{

    boolean existsByDoacaoIdAndStatus(Long doacaoId, StatusReserva status);
    List<ReservaModel> findByBeneficiarioId(Long beneficiarioId);
    List<ReservaModel> findByStatus(StatusReserva status);
    boolean existsByDoacao(DoacaoModel doacao);
    Optional<ReservaModel> findByDoacaoId(Long doacaoId);




}
