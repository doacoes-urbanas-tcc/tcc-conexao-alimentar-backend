package tcc.conexao_alimentar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tcc.conexao_alimentar.enums.StatusReserva;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.ReservaModel;

public interface ReservaRepository extends JpaRepository<ReservaModel,Long>{

    boolean existsByDoacaoIdAndStatus(Long doacaoId, StatusReserva status);
    List<ReservaModel> findByStatus(StatusReserva status);
    boolean existsByDoacao(DoacaoModel doacao);
    Optional<ReservaModel> findByDoacaoId(Long doacaoId);
    List<ReservaModel> findByReceptorId(Long receptorId);
    Optional<ReservaModel> findByDoacao(DoacaoModel doacao);
    @Query("SELECT COUNT(r) FROM ReservaModel r " +"WHERE r.receptor.id = :ongId AND r.status = StatusReserva.RETIRADA")
    Long countDoacoesRecebidasByOng(@Param("ongId") Long ongId);






}
