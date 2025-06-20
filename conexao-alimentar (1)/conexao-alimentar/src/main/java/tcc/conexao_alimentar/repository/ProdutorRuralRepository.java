package tcc.conexao_alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.ProdutorRuralModel;

public interface ProdutorRuralRepository extends JpaRepository<ProdutorRuralModel,Long> {

    boolean existsByEmail(String email);


}
