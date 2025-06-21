package tcc.conexao_alimentar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.ProdutorRuralModel;

public interface ProdutorRuralRepository extends JpaRepository<ProdutorRuralModel,Long> {

    Optional<ProdutorRuralModel> findByEmail(String email);
    boolean existsByEmail(String email); 
    List<ProdutorRuralModel> findByAtivoFalse();



}
