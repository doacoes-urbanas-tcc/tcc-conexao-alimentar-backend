package tcc.conexao_alimentar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.AvaliacaoModel;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoModel,Long>{

    
    List<AvaliacaoModel> findByAvaliadoId(Long avaliadoId);

    List<AvaliacaoModel> findByAvaliadorId(Long avaliadorId);

}
