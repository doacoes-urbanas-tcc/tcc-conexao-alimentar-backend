package tcc.conexao_alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.AvaliacaoModel;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoModel,Long>{

}
