package tcc.conexao_alimentar.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tcc.conexao_alimentar.model.OngModel;

public interface OngRepository extends JpaRepository<OngModel,Long> {
    
    @Query("SELECT AVG(a.nota) FROM AvaliacaoModel a WHERE a.avaliado.id = :ongId")
    Double findMediaAvaliacoesByOng(@Param("ongId") Long ongId);


}
