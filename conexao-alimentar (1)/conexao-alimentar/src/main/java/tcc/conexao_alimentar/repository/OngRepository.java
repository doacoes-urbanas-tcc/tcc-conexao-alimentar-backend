package tcc.conexao_alimentar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tcc.conexao_alimentar.model.OngModel;

public interface OngRepository extends JpaRepository<OngModel,Long> {

   Optional<OngModel> findByEmail(String email);
    boolean existsByEmail(String email); 


}
