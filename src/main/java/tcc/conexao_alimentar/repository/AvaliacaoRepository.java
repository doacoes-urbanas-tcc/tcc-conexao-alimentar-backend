package tcc.conexao_alimentar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tcc.conexao_alimentar.model.AvaliacaoModel;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoModel,Long>{

    
    List<AvaliacaoModel> findByAvaliadoId(Long avaliadoId);

    List<AvaliacaoModel> findByAvaliadorId(Long avaliadorId);
    @Query("SELECT AVG(a.nota) FROM AvaliacaoModel a WHERE a.avaliado.id = :ongId")
    Double findMediaAvaliacoesByOng(@Param("ongId") Long ongId);


}
