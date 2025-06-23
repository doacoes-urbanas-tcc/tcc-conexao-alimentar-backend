package tcc.conexao_alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.DoacaoModel;

public interface DoacaoRepository extends JpaRepository<DoacaoModel,Long> {

}
